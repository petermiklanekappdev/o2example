package app.futured.kmptemplate.android.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint as AndroidPaint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

@Composable
fun ScratchCard(
    modifier: Modifier = Modifier,
    isRevealed: Boolean = false,
    isEnabled: Boolean = true,
    brushSize: Dp = 36.dp,
    overlayColor: Color = Color.Gray,
    initialNormalizedStrokes: List<List<ScratchPoint>> = emptyList(),
    onNormalizedStrokesChanged: (List<List<ScratchPoint>>) -> Unit = {},
    revealThreshold: Float = 80f,
    sampleFactor: Int = 4, // 1 = full resolution, 2/4 = downscale for faster processing
    onReveal: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val brushPx = with(density) { brushSize.toPx() }

    var viewWidth by remember { mutableStateOf(0f) }
    var viewHeight by remember { mutableStateOf(0f) }

    var strokes by remember { mutableStateOf<List<List<Offset>>>(emptyList()) }
    var currentStroke by remember { mutableStateOf<List<Offset>>(emptyList()) }

    var scratchedPercent by remember { mutableStateOf(0f) }
    var revealedAlready by remember { mutableStateOf(false) }

    val computeScope = rememberCoroutineScope()

    // When initial normalized strokes or size changes -> map to pixel offsets
    LaunchedEffect(initialNormalizedStrokes, viewWidth, viewHeight) {
        if (viewWidth > 0f && viewHeight > 0f) {
            strokes = normalizedToOffsets(initialNormalizedStrokes, viewWidth, viewHeight)
        }
    }

    LaunchedEffect(strokes) {
        if (viewWidth <= 0f || viewHeight <= 0f) return@LaunchedEffect
        val normalized = offsetsToNormalized(strokes, viewWidth, viewHeight)
        try {
            onNormalizedStrokesChanged(normalized)
        } catch (_: Throwable) {
        }
    }

    Box(
        modifier = modifier.onSizeChanged { size ->
            viewWidth = size.width.toFloat()
            viewHeight = size.height.toFloat()
        }
    ) {
        Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
            content()
        }

        Canvas(
            modifier = Modifier
                .matchParentSize()
                .let { base ->
                    if (isEnabled) {
                        base.pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentStroke = listOf(offset)
                                },
                                onDrag = { change, _ ->
                                    val newPoint = change.position
                                    currentStroke = currentStroke + newPoint
                                },
                                onDragEnd = {
                                    if (currentStroke.isNotEmpty()) {
                                        strokes = strokes + listOf(currentStroke)
                                        currentStroke = emptyList()
                                    }
                                },
                                onDragCancel = {
                                    if (currentStroke.isNotEmpty()) {
                                        strokes = strokes + listOf(currentStroke)
                                        currentStroke = emptyList()
                                    }
                                }
                            )
                        }
                    } else {
                        base
                    }
                }
        ) {
            if (isRevealed) {
                if (!revealedAlready) {
                    revealedAlready = true
                    scratchedPercent = 100f
                    try {
                        onReveal?.invoke()
                    } catch (_: Throwable) {
                    }
                }
                return@Canvas
            }
            val w = size.width.toInt().coerceAtLeast(1)
            val h = size.height.toInt().coerceAtLeast(1)

            val tmpBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val androidCanvas = AndroidCanvas(tmpBitmap)

            val fillPaint = AndroidPaint().apply {
                isAntiAlias = true
                style = AndroidPaint.Style.FILL
                color = overlayColor.toArgb()
            }
            androidCanvas.drawRect(0f, 0f, w.toFloat(), h.toFloat(), fillPaint)

            val erasePaint = AndroidPaint().apply {
                isAntiAlias = true
                style = AndroidPaint.Style.STROKE
                strokeCap = AndroidPaint.Cap.ROUND
                strokeJoin = AndroidPaint.Join.ROUND
                strokeWidth = brushPx
                xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
            }

            fun drawStrokeToAndroidCanvas(points: List<Offset>) {
                if (points.isEmpty()) return
                if (points.size == 1) {
                    val p = points.first()
                    androidCanvas.drawCircle(p.x, p.y, brushPx / 2f, erasePaint)
                    return
                }
                val path = android.graphics.Path()
                points.forEachIndexed { i, o ->
                    if (i == 0) path.moveTo(o.x, o.y) else path.lineTo(o.x, o.y)
                }
                androidCanvas.drawPath(path, erasePaint)
            }

            strokes.forEach { drawStrokeToAndroidCanvas(it) }
            drawStrokeToAndroidCanvas(currentStroke)

            drawContext.canvas.nativeCanvas.drawBitmap(tmpBitmap, 0f, 0f, null)

            val bmpCopy: Bitmap? = try {
                val cfg = tmpBitmap.config ?: Bitmap.Config.ARGB_8888
                tmpBitmap.copy(cfg, false) // immutable copy
            } catch (t: Throwable) {
                null
            } finally {
                try {
                    if (!tmpBitmap.isRecycled) tmpBitmap.recycle()
                } catch (_: Throwable) { /* ignore */ }
            }

            bmpCopy?.let { safeBmp ->
                computeScope.launch {
                    val percent = try {
                        withContext(Dispatchers.Default) {
                            if (sampleFactor <= 1) {
                                computeClearedPercent(safeBmp)
                            } else {
                                val sw = (safeBmp.width / sampleFactor).coerceAtLeast(1)
                                val sh = (safeBmp.height / sampleFactor).coerceAtLeast(1)
                                val small = Bitmap.createScaledBitmap(safeBmp, sw, sh, true)
                                try {
                                    computeClearedPercent(small)
                                } finally {
                                    if (!small.isRecycled) small.recycle()
                                }
                            }
                        }
                    } catch (e: Throwable) {
                        0f
                    } finally {
                        try {
                            if (!safeBmp.isRecycled) safeBmp.recycle()
                        } catch (_: Throwable) { /* ignore */
                        }
                    }

                    scratchedPercent = percent
                    if (!revealedAlready && percent >= revealThreshold) {
                        revealedAlready = true
                        try {
                            onReveal?.invoke()
                        } catch (_: Throwable) { /* ignore callback errors */
                        }
                    }
                }
            }
        }
    }
}

fun offsetsToNormalized(
    strokes: List<List<Offset>>,
    width: Float,
    height: Float
): List<List<ScratchPoint>> {
    if (width <= 0f || height <= 0f) return emptyList()
    return strokes.map { stroke ->
        stroke.map { pt ->
            ScratchPoint(
                x = (pt.x / width).toDouble().coerceIn(0.0, 1.0),
                y = (pt.y / height).toDouble().coerceIn(0.0, 1.0)
            )
        }
    }
}

fun normalizedToOffsets(
    strokesDto: List<List<ScratchPoint>>,
    width: Float,
    height: Float
): List<List<Offset>> {
    if (width <= 0f || height <= 0f) return emptyList()
    return strokesDto.map { stroke ->
        stroke.map { p -> Offset((p.x * width).toFloat(), (p.y * height).toFloat()) }
    }
}

private fun computeClearedPercent(bitmap: Bitmap): Float {
    val w = bitmap.width
    val h = bitmap.height
    if (w == 0 || h == 0) return 0f
    val row = IntArray(w)
    var cleared = 0L
    val total = w.toLong() * h.toLong()
    for (y in 0 until h) {
        bitmap.getPixels(row, 0, w, 0, y, w, 1)
        for (x in 0 until w) {
            val alpha = (row[x] ushr 24) and 0xFF
            if (alpha == 0) cleared++
        }
    }
    return (cleared.toDouble() / total.toDouble() * 100.0).toFloat()
}

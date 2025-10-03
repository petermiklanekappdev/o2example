package app.futured.kmptemplate.android.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.arkivanov.decompose.extensions.compose.stack.animation.LocalStackAnimationProvider

@SuppressLint("ComposeCompositionLocalUsage")
val LocalExampleColorScheme = staticCompositionLocalOf { ExampleColorScheme() }

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    val colors = lightColorScheme(
        primary = Blue500,
        secondary = Gray950,
        background = Gray00,
        surface = Gray00
    )

    val exampleColorScheme = ExampleColorScheme(
        surface = SurfaceColorScheme(
            xHigh = Gray500,
            xLow = Gray00,
            brand = Blue500,
            danger = Red600,
            dangerVariant = Red100,
            warning = Yellow700,
            warningVariant = Yellow100,
        ),
        content = ContentColorScheme(
            onNeutral = ContentOnNeutralColorScheme(
                xxHigh = Gray950,
                medium = Gray500,
                low = Gray300,
                danger = Red600,
                warning = Red700,
            ),
        ),
        state = StateColorScheme(
            default = StateDefaultColorScheme(
                hover = Dim50,
                focus = Dim800,
            ),
        ),
    )

    val typography = Typography(
        bodyMedium = BodyM,
        labelMedium = LabelM,
        labelSmall = LabelS,
    )

    CompositionLocalProvider(
        LocalStackAnimationProvider provides TemplateStackAnimationProvider,
        LocalExampleColorScheme provides exampleColorScheme,
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = typography,
            content = content,
        )
    }
}

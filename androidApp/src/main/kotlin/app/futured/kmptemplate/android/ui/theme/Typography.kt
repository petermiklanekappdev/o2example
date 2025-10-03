package app.futured.kmptemplate.android.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.futured.kmptemplate.android.R

val InterFont: FontFamily = FontFamily(
    listOf(
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_black, FontWeight.Black),
        Font(R.font.inter_extra_bold, FontWeight.ExtraBold),
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_semi_bold, FontWeight.SemiBold),
        Font(R.font.inter_thin, FontWeight.Thin),
    )
)

val LabelS = TextStyle(
    fontFamily = InterFont,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 17.sp,
    letterSpacing = 0.16.sp
)


val LabelM = TextStyle(
    fontFamily = InterFont,
    fontWeight = FontWeight.W500,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.16.sp
)

val BodyM = TextStyle(
    fontFamily = InterFont,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.01.sp
)



package app.futured.kmptemplate.android.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ExampleColorScheme(
    val surface: SurfaceColorScheme = SurfaceColorScheme(),
    val content: ContentColorScheme = ContentColorScheme(),
    val state: StateColorScheme = StateColorScheme(),
)

@Immutable
data class SurfaceColorScheme(
    val xHigh: Color = Color.Unspecified,
    val xLow: Color = Color.Unspecified,
    val brand: Color = Color.Unspecified,
    val danger: Color = Color.Unspecified,
    val dangerVariant: Color = Color.Unspecified,
    val warning: Color = Color.Unspecified,
    val warningVariant: Color = Color.Unspecified
    )

@Immutable
data class ContentColorScheme(
    val onNeutral: ContentOnNeutralColorScheme = ContentOnNeutralColorScheme(),
)

@Immutable
data class ContentOnNeutralColorScheme(
    val xxHigh: Color = Color.Unspecified,
    val medium: Color = Color.Unspecified,
    val low: Color = Color.Unspecified,
    val danger: Color = Color.Unspecified,
    val warning: Color = Color.Unspecified
)

@Immutable
data class StateColorScheme(
    val default: StateDefaultColorScheme = StateDefaultColorScheme(),
)

@Immutable
data class StateDefaultColorScheme(
    val hover: Color = Color.Unspecified,
    val focus: Color = Color.Unspecified
)

package app.futured.kmptemplate.android.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.android.ui.theme.MyApplicationTheme
import app.futured.kmptemplate.android.ui.theme.InputRadius
import app.futured.kmptemplate.android.ui.theme.LocalExampleColorScheme
import app.futured.kmptemplate.android.ui.theme.SpacingXXS
import app.futured.kmptemplate.android.ui.theme.SpacingM
import app.futured.kmptemplate.android.ui.theme.SpacingXS
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@Composable
fun Input(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    startValidationField: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    info: String? = null,
    isError: Boolean = false,
    isOptional: Boolean = false,
    error: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    onLeadingIcon: () -> Unit = {},
    onTrailingIcon: () -> Unit = {},
) {
    var previousIsFocused by rememberSaveable {
        mutableStateOf(false)
    }

    val inputModifier = Modifier
        .fillMaxWidth()
        .onFocusEvent { focusState ->
            if (previousIsFocused && focusState.isFocused.not()) {
                startValidationField()
            }
            previousIsFocused = focusState.isFocused
        }
        .border(
            width = 1.dp,
            color = if (isError) {
                LocalExampleColorScheme.current.content.onNeutral.danger
            } else {
                LocalExampleColorScheme.current.content.onNeutral.medium
            },
            shape = RoundedCornerShape(InputRadius),
        )

    Column(modifier = modifier) {
        InputLabel(
            label = label,
            isOptional = isOptional,
            modifier = Modifier.padding(bottom = SpacingXS),
            isError = isError,
        )

        BaseTextField(
            placeholder = { InputPlaceholder(placeholder) },
            value = value,
            onValueChange = onValueChange,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                capitalization = capitalization,
            ),
            singleLine = singleLine,
            shape = RoundedCornerShape(InputRadius),
            isError = isError,
            enabled = enabled,
            textStyle = MaterialTheme.typography.bodyMedium,
            trailingIcon = if (trailingIcon != null) {
                { InputIconButton(trailingIcon, onTrailingIcon) }
            } else null,
            leadingIcon = if (leadingIcon != null) {
                { InputIconButton(leadingIcon, onLeadingIcon) }
            } else null,
            colors = InputColors(),
            modifier = inputModifier,
        )

        AnimatedVisibility(visible = !isError && !info.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(start = SpacingM, top = SpacingXXS),
                text = info ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = LocalExampleColorScheme.current.content.onNeutral.medium,
            )
        }

        AnimatedVisibility(visible = isError && !error.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(start = SpacingM, top = SpacingXXS),
                text = error ?: "",
                style = MaterialTheme.typography.labelSmall,
                color = LocalExampleColorScheme.current.content.onNeutral.danger,
            )
        }
    }
}

@Composable
private fun InputPlaceholder(
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = placeholder,
        style = MaterialTheme.typography.bodyMedium,
        color = LocalExampleColorScheme.current.content.onNeutral.low,
    )
}

@SuppressLint("ComposableNaming")
@Composable
private fun InputColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent,
    focusedContainerColor = LocalExampleColorScheme.current.surface.xLow,
    errorContainerColor = LocalExampleColorScheme.current.surface.xLow,
    unfocusedContainerColor = LocalExampleColorScheme.current.surface.xLow,
    disabledContainerColor = LocalExampleColorScheme.current.surface.xLow.copy(alpha = 0.5f),
    cursorColor = LocalExampleColorScheme.current.surface.brand,
)

@Composable
private fun InputIconButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
        )
    }
}

@Composable
private fun InputLabel(
    label: String,
    isOptional: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isError) {
                LocalExampleColorScheme.current.content.onNeutral.danger
            } else {
                LocalExampleColorScheme.current.content.onNeutral.xxHigh
            },
        )

        androidx.compose.animation.AnimatedVisibility(isOptional) {
            Text(
                text = kmpStringResource(MR.strings.input_optional),
                style = MaterialTheme.typography.labelSmall,
                color = LocalExampleColorScheme.current.content.onNeutral.medium,
                modifier = Modifier.padding(start = SpacingXS),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors(),
) {
    BasicTextField(
        value = value,
        modifier = modifier.defaultMinSize(
            minWidth = TextFieldDefaults.MinWidth,
        ),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                shape = shape,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
            )
        },
    )
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xffffffff,
)
private fun TextInputFieldPreview() {
    MyApplicationTheme {
        Input(
            modifier = Modifier.padding(16.dp),
            label = "Input",
            placeholder = "Placeholder",
            value = "Value",
            onValueChange = { },
            isOptional = true,
            info = "Info",
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xffffffff,
)
private fun TextInputFieldNoValuePreview() {
    MyApplicationTheme {
        Input(
            modifier = Modifier.padding(16.dp),
            label = "Input",
            value = "",
            placeholder = "Placeholder",
            onValueChange = { },
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xffffffff,
)
private fun TextInputFieldErrorPreview() {
    MyApplicationTheme {
        Input(
            modifier = Modifier.padding(16.dp),
            label = "Input",
            value = "",
            placeholder = "Placeholder",
            onValueChange = { },
            isError = true,
            isOptional = true,
            error = "Error",
        )
    }
}

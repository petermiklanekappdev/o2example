package app.futured.kmptemplate.android.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.android.ui.theme.MyApplicationTheme
import app.futured.kmptemplate.android.R

@Composable
fun PasswordInput(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    startValidationField: () -> Unit = {},
    info: String? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    error: String? = null,
) {
    var isPasswordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Input(
        label = label,
        value = value,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardType = KeyboardType.Password,
        onValueChange = onValueChange,
        trailingIcon = if (isPasswordVisible) {
            painterResource(id = R.drawable.ic_visibility_off)
        } else {
            painterResource(id = R.drawable.ic_visibility)
        },
        onTrailingIcon = {
            isPasswordVisible = !isPasswordVisible
        },
        startValidationField = startValidationField,
        isError = isError,
        error = error,
        modifier = modifier,
        enabled = enabled,
        placeholder = placeholder,
        info = info
    )
}

@Composable
@Preview(
    showBackground = true,
    backgroundColor = 0xffffffff,
)
private fun PasswordTextInputFieldPreview() {
    MyApplicationTheme {
        PasswordInput(
            modifier = Modifier.padding(16.dp),
            label = "Input",
            value = "12345",
            placeholder = "Password",
            onValueChange = {},
            startValidationField = { },
        )
    }
}

package app.futured.kmptemplate.feature.tools

private val emailAddressRegex = Regex(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
        "\\@" +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
        "(" +
        "\\." +
        "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
        ")+",
)

private val passwordRegex = Regex(
    "^(?=.*[A-Z])(?=.*\\d)(?=.*[?=#/%])[A-Za-z\\d?=#/%]{8,}\$",
)

fun String.isEmailValid(): Boolean {
    return matches(emailAddressRegex)
}


fun String.isPasswordValid(): Boolean {
    return matches(passwordRegex)
}

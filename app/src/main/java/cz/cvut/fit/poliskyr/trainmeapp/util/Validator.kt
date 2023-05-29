package cz.cvut.fit.poliskyr.trainmeapp.util

import java.util.regex.Pattern

class Validator {
    private val EMAIL_ADDRESS_PATTERN = Pattern.compile(
    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
    "\\@" +
    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
    "(" +
    "\\." +
    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
    ")+"
    );

    fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()
    }

    fun checkForm(
        username: String,
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        confirmPassword: String
    ): Boolean{
        return (username.isNotBlank()
                && email.isNotBlank()
                && firstName.isNotBlank()
                && lastName.isNotBlank()
                && password.isNotBlank()
                && confirmPassword.isNotBlank()
                )
    }

    fun checkPasswords(password: String, confirmPassword: String): Boolean{
        return password == confirmPassword
    }

}
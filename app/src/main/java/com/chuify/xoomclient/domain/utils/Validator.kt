package com.chuify.xoomclient.domain.utils

class Validator {

    companion object Validator {

        private const val NAME_REGEX = "[a-zA-Z]+"
        private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        private const val PHONE_REGEX = "[0-9]+"

        fun isValidName(string: String): Boolean {
            val pattern = NAME_REGEX.toRegex()
            return pattern.matches(string)
        }

        fun isValidEmail(string: String): Boolean {
            val pattern = EMAIL_REGEX.toRegex()
            return pattern.matches(string)
        }

        fun isValidPhone(string: String): Boolean {
            val pattern = PHONE_REGEX.toRegex()
            return pattern.matches(string)
        }
    }


}
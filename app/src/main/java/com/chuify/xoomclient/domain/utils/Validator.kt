package com.chuify.xoomclient.domain.utils

class Validator {

    companion object Validator {

        private const val NAME_REGEX = "[a-zA-Z]+"
        private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})";
        private const val PHONE_REGEX = "[\\+][254][0-9]{9}+"

        // +254703894372
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
            return true
        }
    }


}
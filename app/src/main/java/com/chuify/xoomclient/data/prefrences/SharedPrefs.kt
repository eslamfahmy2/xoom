package com.chuify.xoomclient.data.prefrences

import android.content.Context
import android.content.SharedPreferences
import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.domain.model.User
import com.chuify.xoomclient.presentation.application.BaseApplication
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SharedPrefs @Inject constructor(
    context: BaseApplication,
) {

    companion object {
        private const val PREF = "MyAppPrefName"
        const val IS_DARK = "IS_DARK"

        const val USER_FIRST_NAME = "USER_FIRST_NAME"
        const val USER_LAST_NAME = "USER_LAST_NAME"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_PHONE = "USER_PHONE"
        const val USER_TOKEN = "USER_TOKEN"
        const val USER_ID = "USER_ID"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun saveTheme(isDark: Boolean) {
        put(IS_DARK, isDark)
    }

    fun isDark() = get(IS_DARK, Boolean::class.java)

    fun saveToken(token: String) {
        put(USER_TOKEN, token)
    }

    fun saveUser(user: UserDto) {

        put(USER_FIRST_NAME, user.firstname)
        put(USER_LAST_NAME, user.lastname)
        put(USER_EMAIL, user.email)
        put(USER_PHONE, user.phone)
        put(USER_TOKEN, user.access_token)
        put(USER_ID, user.user_id)

    }

    fun getToken(): String {
        return get(USER_TOKEN, String::class.java)
    }

    fun getUserID(): String {
        return get(USER_ID, String::class.java)
    }

    fun getUser() = User(
        userId = get(USER_ID, String::class.java),
        firstname = get(USER_FIRST_NAME, String::class.java),
        lastname = get(USER_LAST_NAME, String::class.java),
        email = get(USER_EMAIL, String::class.java),
        phone = get(USER_PHONE, String::class.java),
        token = get(USER_TOKEN, String::class.java),
    )

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }


    fun clear() {
        sharedPref.edit().run {
            remove(PREF)
            remove(USER_FIRST_NAME)
            remove(USER_LAST_NAME)
            remove(USER_EMAIL)
            remove(USER_PHONE)
            remove(USER_TOKEN)
            remove(USER_ID)
        }.apply()
    }


}
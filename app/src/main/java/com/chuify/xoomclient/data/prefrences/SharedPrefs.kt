package com.chuify.xoomclient.data.prefrences

import android.content.SharedPreferences
import com.chuify.xoomclient.data.remote.dto.UserDto
import com.chuify.xoomclient.domain.model.User
import javax.inject.Inject
import com.google.gson.Gson


@Suppress("UNCHECKED_CAST")
class SharedPrefs @Inject constructor(
    private val sharedPref: SharedPreferences
) {

    companion object {
        private const val PREF = "MyAppPrefName"
        const val IS_DARK = "IS_DARK"

        const val USER_TOKEN = "USER_TOKEN"
        const val USER = "USER"
        const val USER_ID = "USER_ID"
        const val USER_POINTS = "USER_POINTS"

    }


    fun saveTheme(isDark: Boolean) {
        put(IS_DARK, isDark)
    }

    fun isDark() = get(IS_DARK, Boolean::class.java)

    fun saveToken(token: String) {
        put(USER_TOKEN, token)
    }

    fun saveUser(user: UserDto) {

        val gson = Gson()
        val it = gson.toJson(user)
        put(USER, it)

    }

    fun getToken(): String {
        return get(USER_TOKEN, String::class.java)
    }

    fun getUserID(): String {
        return get(USER_ID, String::class.java)
    }

    fun saveUserPoints(it: String) {
        put(USER_POINTS, it)
    }

    fun getUser(): UserDto? {
        val serializedUser = get(USER, String::class.java)
        val gson = Gson()
        return gson.fromJson(serializedUser, UserDto::class.java)
    }

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
            remove(USER)
            remove(USER_TOKEN)
            remove(USER_ID)
            remove(USER_POINTS)
        }.apply()
    }


}
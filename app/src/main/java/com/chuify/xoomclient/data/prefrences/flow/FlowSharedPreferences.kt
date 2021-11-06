package com.chuify.xoomclient.data.prefrences.flow

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

internal typealias KeyFlow = Flow<String?>

@ExperimentalCoroutinesApi

class FlowSharedPreferences @JvmOverloads constructor(
    private val sharedPreferences: SharedPreferences,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    private val keyFlow: KeyFlow = sharedPreferences.keyFlow

    @JvmOverloads
    fun getBoolean(key: String, defaultValue: Boolean = false): Preference<Boolean> =
        BooleanPreference(key, defaultValue, keyFlow, sharedPreferences, coroutineContext)


    fun <T> getUser(
        key: String,
        serializer: NullableSerializer<T>,
        defaultValue: T?
    ): Preference<T?> =
        NullableObjectPreference(
            key,
            serializer,
            defaultValue,
            keyFlow,
            sharedPreferences,
            coroutineContext
        )

    fun clear() = sharedPreferences.edit().clear().apply()

}
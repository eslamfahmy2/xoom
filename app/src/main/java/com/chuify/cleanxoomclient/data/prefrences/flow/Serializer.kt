package com.chuify.cleanxoomclient.data.prefrences.flow

interface Serializer<T : Any> {
    fun deserialize(serialized: String): T
    fun serialize(value: T): String
}

interface NullableSerializer<T> {
    fun deserialize(serialized: String?): T?
    fun serialize(value: T?): String?
}

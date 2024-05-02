package com.alexereh.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.google.crypto.tink.Aead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream


/**
 * An [Serializer] for the [UserData] proto.
 */
class UserDataSerializer(
    private val aead: Aead
) : Serializer<UserData> {
    override val defaultValue: UserData = userData { loggedIn = false }

    override suspend fun readFrom(input: InputStream): UserData =
        try {
            // readFrom is already called on the data store background thread
            val encryptedInput = input.readBytes()

            val decryptedInput = if (encryptedInput.isNotEmpty()) {
                aead.decrypt(encryptedInput, null)
            } else {
                encryptedInput
            }
            UserData.parseFrom(decryptedInput)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: UserData, output: OutputStream) {
        val encryptedBytes = aead.encrypt(t.toByteArray(), null)
        withContext(Dispatchers.IO) {
            output.write(encryptedBytes)
        }
    }
}
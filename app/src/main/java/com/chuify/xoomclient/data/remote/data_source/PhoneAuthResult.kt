package com.chuify.xoomclient.data.remote.data_source

import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

sealed class PhoneAuthResult {
    data class VerificationCompleted(val credentials: PhoneAuthCredential) : PhoneAuthResult()
    data class CodeSent(
        val verificationId: String,
        val token: PhoneAuthProvider.ForceResendingToken,
    ) : PhoneAuthResult()
}

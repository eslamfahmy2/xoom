package com.chuify.cleanxoomclient.presentation.ui.authentication

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuify.cleanxoomclient.domain.usecase.auth.LoginResult
import com.chuify.cleanxoomclient.domain.usecase.auth.SignInUseCase
import com.chuify.cleanxoomclient.domain.usecase.auth.SignUpUseCase
import com.chuify.cleanxoomclient.domain.utils.DataState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val TAG = "LoginViewModel"

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    //720242046
    //703894372
    val userIntent = Channel<AuthenticationIntent>(Channel.UNLIMITED)

    private val _phone: MutableStateFlow<String> = MutableStateFlow("703894372")
    val phone get() = _phone.asStateFlow()

    private val _firstName: MutableStateFlow<String> = MutableStateFlow(String())
    val firstName get() = _firstName.asStateFlow()

    private val _lastName: MutableStateFlow<String> = MutableStateFlow(String())
    val lastName get() = _lastName.asStateFlow()

    private val _email: MutableStateFlow<String> = MutableStateFlow(String())
    val email get() = _email.asStateFlow()


    private val _state: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Idl)
    val state get() = _state.asStateFlow()


    private val _stateUp: MutableStateFlow<AuthenticationState> =
        MutableStateFlow(AuthenticationState.Idl)
    val stateUp get() = _stateUp.asStateFlow()

    private val _stateVerify: MutableStateFlow<OTPState> =
        MutableStateFlow(OTPState.Idl)
    val stateVerify get() = _stateVerify.asStateFlow()

    private var codeVerificationId = ""

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { intent ->
                when (intent) {
                    AuthenticationIntent.SignIn -> {
                        login()
                    }
                    is AuthenticationIntent.PhoneChange -> {
                        _phone.value = intent.data
                    }
                    is AuthenticationIntent.EmailChange -> {
                        _email.value = intent.data
                    }
                    is AuthenticationIntent.FirstNameChange -> {
                        _firstName.value = intent.data
                    }
                    is AuthenticationIntent.LastNameChange -> {
                        _lastName.value = intent.data
                    }
                    AuthenticationIntent.SignUp -> {
                        signup()
                    }
                }
            }
        }
    }


    private suspend fun login() = viewModelScope.launch(Dispatchers.IO) {
        loginUseCase(
            phone = _phone.value
        ).collect { result ->
            when (result) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + result.message)
                    _state.value = AuthenticationState.Error(result.message)
                }
                is DataState.Loading -> {
                    Log.d(TAG, "Loading: " + result.message)
                    _state.value = AuthenticationState.Loading
                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: " + result.data)
                    result.data?.let {
                        _state.value = AuthenticationState.Success(it)
                    }

                }
            }
        }
    }

    private suspend fun signup() = viewModelScope.launch(Dispatchers.IO) {
        signUpUseCase(
            firstname = _firstName.value,
            lastname = _lastName.value,
            email = _email.value,
            phone = _phone.value
        ).collect { result ->
            when (result) {
                is DataState.Error -> {
                    Log.d(TAG, "Error: " + result.message)
                    _stateUp.value = AuthenticationState.Error(result.message)
                }
                is DataState.Loading -> {
                    Log.d(TAG, "Loading: " + result.message)
                    _stateUp.value = AuthenticationState.Loading
                }
                is DataState.Success -> {
                    Log.d(TAG, "Success: " + result.data)
                    _stateUp.value = AuthenticationState.Success(LoginResult.Login)
                }
            }
        }
    }

    fun idl() {
        _state.value = AuthenticationState.Idl
        _stateUp.value = AuthenticationState.Idl
    }

    fun sendSmsCode(value: String, activity: Activity) {

        val mAuth = FirebaseAuth.getInstance()
        //   mAuth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)

        viewModelScope.launch(Dispatchers.IO) {
            _stateVerify.value = OTPState.Loading
            val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted: ")
                    _stateVerify.value = OTPState.OnVerificationCompleted(credential.smsCode)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.d(TAG, "onVerificationFailed: " + e.message)
                    _stateVerify.value = OTPState.OnVerificationFailed(e.message)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken,
                ) {
                    Log.d(TAG, "onCodeSent: ")
                    codeVerificationId = verificationId
                    _stateVerify.value = OTPState.OnCodeSent(verificationId)
                }
            }
            val options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber("+254$value")
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callback)
                .setActivity(activity)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)

        }
    }

    fun verifyCode(code: String, activity: Activity) {

        val mAuth = FirebaseAuth.getInstance()
        val credential = PhoneAuthProvider.getCredential(codeVerificationId, code)
        _stateVerify.value = OTPState.Loading
        viewModelScope.launch(Dispatchers.IO) {

            mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        _stateVerify.value = OTPState.OnVerificationCompleted()
                    } else {
                        // Sign in failed, display a message and update the UI
                        _stateVerify.value = OTPState.OnVerificationFailed(task.exception?.message)
                    }
                }
        }
    }
}
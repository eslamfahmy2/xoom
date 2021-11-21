package com.chuify.cleanxoomclient.presentation.service


import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.chuify.cleanxoomclient.R
import com.chuify.cleanxoomclient.domain.repository.AuthRepo
import com.chuify.cleanxoomclient.presentation.SplashActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


private const val TAG = "FireBaseCloudMessagingS"

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalPagerApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class FireBaseCloudMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var authRepo: AuthRepo
    private val mainScope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private val CHANNEL_ID = "11"

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, token)
            sendRegistrationToServer(token)
        })
    }

    private fun sendRegistrationToServer(token: String) {
        mainScope.launch {
            Log.d(TAG, "sendRegistration ToServer: $token")
            try {
                authRepo.updateFirebaseToken(token)
            } catch (e: Exception) {
                Log.d(TAG, "sendRegistrationToServer: error " + e.message)
            }

        }

    }

    override fun onNewToken(p0: String) {
        sendRegistrationToServer(p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        mainScope.launch {
            try {
                p0.data.let {
                    val title: String? = it["title"]
                    val description: String? = it["description"]
                    val orderStatus: String? = it["orderstatus"]

                    val riderId: String? = it["rider_id"]
                    val tripId: String? = it["trip_id"]

                    Log.d(
                        TAG,
                        "onMessageReceived: title $title body $description status $orderStatus riderId $riderId tripId $tripId"
                    )
                    handleNotification(title, description)

                }
            } catch (e: Exception) {
                Log.d(TAG, "onMessageReceived: error " + e.localizedMessage)
            }
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun handleNotification(
        title: String?,
        description: String?,
    ) {

        val intent = Intent(applicationContext, SplashActivity::class.java)
        intent.action = Intent.ACTION_MAIN
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle(title)
            .setContentText(description)
            .setStyle(NotificationCompat.BigTextStyle().bigText(description))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        createNotificationChannel()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(0, builder.build())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = getString(R.string.app_name)
            val description = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )!!
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        mainScope.cancel()
    }

}

package com.example.news.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder
import android.os.SystemClock
import com.example.news.utils.Constants.BROADCAST_STRING_FOR_ACTION
import kotlinx.coroutines.*

class MyService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch {
            while (true){
                delay(1 * 1000 - SystemClock.elapsedRealtime() % 1000)
                val intent = Intent()
                intent.action = BROADCAST_STRING_FOR_ACTION
                intent.putExtra("online_status","${hasInternetConnection(this@MyService)}")
                sendBroadcast(intent)
            }
        }
        return START_STICKY
    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

}
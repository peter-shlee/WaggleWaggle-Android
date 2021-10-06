package com.somasoma.wagglewaggle.core

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.somasoma.wagglewaggle.domain.usecase.UserConnectedStateUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ApplicationService : Service() {

    @Inject
    lateinit var userConnectedStateUseCase: UserConnectedStateUseCase

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        userConnectedStateUseCase.postCurrentUserOffline()

        super.onTaskRemoved(rootIntent)
        stopSelf()
    }
}
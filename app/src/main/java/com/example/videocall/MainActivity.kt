package com.example.videocall

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier

import com.example.videocall.ui.theme.VideoCallTheme
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent

import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.core.call.state.FlipCamera
import io.getstream.video.android.core.call.state.LeaveCall
import io.getstream.video.android.core.call.state.ToggleCamera
import io.getstream.video.android.core.call.state.ToggleMicrophone
import io.getstream.video.android.model.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiKey = "mmhfdzb5evj2"
        val userToken =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiSmFuZ29fRmV0dCIsImlzcyI6Imh0dHBzOi8vcHJvbnRvLmdldHN0cmVhbS5pbyIsInN1YiI6InVzZXIvSmFuZ29fRmV0dCIsImlhdCI6MTcxNzQyNTIwMywiZXhwIjoxNzE4MDMwMDA4fQ.NW7jvudGiw1bE6YxijKfSUOfFnq8H8EmsOdk41qTe2s"
        val userId = "Jango_Fett"
        val callId = "sHm7JXvxikHA"

        val user = User(
            id = userId, // any string
            name = "Tutorial", // name and image are used in the UI
            image = "https://bit.ly/2TIt8NR",
            role = "admin"
        )

        val client = StreamVideoBuilder(
            context = applicationContext,
            apiKey = apiKey,
            geo = GEO.GlobalEdgeNetwork,
            user = user,
            token = userToken,
        ).build()

        setContent {
            val call = client.call(type = "default", id = callId)
            LaunchCallPermissions(call = call) {
                call.join(create = true)
            }
            VideoTheme { // Define required properties.
                CallContent(
                    modifier = Modifier.fillMaxSize(),
                    call = call,
                    enableInPictureInPicture = true,
                    onBackPressed = { finish() },
                    onCallAction = { callAction ->
                        when (callAction) {
                            is FlipCamera -> call.camera.flip()
                            is ToggleCamera -> call.camera.setEnabled(callAction.isEnabled)
                            is ToggleMicrophone -> call.microphone.setEnabled(callAction.isEnabled)
                            is LeaveCall -> finish()
                            else -> Unit
                        }
                    },
                )
            }
        }
    }

}

package com.fedyshyn.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.work.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart: Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener { runUploadImageWorker() }
    }

    private fun runUploadImageWorker() {
        val imageData = workDataOf(Constants.KEY_IMAGE_URI to "image url")

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadImageWorker>()
                .setInputData(imageData)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().getWorkInfoByIdLiveData(uploadWorkRequest.id)
                .observe(this, { workInfo ->

                    if (workInfo != null) {
                        Log.d(Constants.TAG, "state: ${workInfo.state}")

                        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                            val outputData = workInfo.outputData.getString(Constants.KEY_IMAGE_URI)
                            Log.d(Constants.TAG, "outputData: $outputData")
                        }
                    }
                })

        WorkManager.getInstance().enqueue(uploadWorkRequest)
    }
}
package com.fedyshyn.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class UploadImageWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val imageUriInput = inputData.getString(Constants.KEY_IMAGE_URI)
        Log.d(Constants.TAG, "Worker - inputData: $imageUriInput")

        val outputData = workDataOf(Constants.KEY_IMAGE_URI to "result image url")
        Log.d(Constants.TAG, "Worker - outputData: $outputData")

        return Result.success(outputData)
    }
}
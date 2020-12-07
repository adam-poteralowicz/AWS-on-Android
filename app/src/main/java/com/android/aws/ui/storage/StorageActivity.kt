package com.android.aws.ui.storage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.android.aws.R
import com.android.aws.databinding.ActivityStorageBinding
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class StorageActivity : Activity() {

    private lateinit var binding: ActivityStorageBinding
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        startService(Intent(applicationContext, TransferService::class.java))

        setupListeners()
    }

    private fun setupListeners() {
        binding.storageButton.setOnClickListener {
            val file = createFile("file.txt", "Test file")
            uploadFile(file)
        }
    }

    private fun createFile(name: String, content: String): File {
        val file = File(applicationContext.filesDir, name)

        val writer = BufferedWriter(FileWriter(file))
        writer.append(content)
        writer.close()

        return file
    }

    private fun uploadFile(file: File) {
        val transferUtility = TransferUtility.builder()
            .context(this)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(AmazonS3Client(AWSMobileClient.getInstance()))
            .build()

        val uploadObserver = transferUtility.upload("public/file.txt", file)
        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED === state) {
                    Timber.d("Transfer completed")
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                val percentDone = percentDonef.toInt()
                Timber.d("ID: $id, bytesCurrent: $bytesCurrent,  bytesTotal: $bytesTotal, percentDone: $percentDone%")
            }

            override fun onError(id: Int, ex: Exception) {
                Timber.e(ex)
            }
        })
    }
}
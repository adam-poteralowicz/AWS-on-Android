package com.android.aws.ui.storage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.client.Callback
import com.amazonaws.mobile.client.UserStateDetails
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.android.aws.databinding.ActivityStorageBinding
import com.android.aws.util.getPinpointManager
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class StorageActivity : Activity() {

    private lateinit var binding: ActivityStorageBinding
    private lateinit var transferUtility: TransferUtility
    private var pinpointManager: PinpointManager? = null
    private var storageActions = StorageActionsImpl()
    private lateinit var awsConfig: AWSConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startService(Intent(applicationContext, TransferService::class.java))
        awsConfig = AWSConfiguration(applicationContext)

        transferUtility = TransferUtility.builder()
            .context(this)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(AmazonS3Client(AWSMobileClient.getInstance()))
            .build()

        setupListeners()
        setupPinpointManager()
    }

    private fun setupListeners() {

        binding.uploadButton.setOnClickListener {
            val file = createFile("file.txt", "Test file")
            storageActions.uploadFile(file, transferUtility)
        }
        binding.downloadButton.setOnClickListener {
            val targetFile = createFile("download.txt", "")
            storageActions.downloadFile(targetFile, transferUtility)
        }

    }

    private fun setupPinpointManager() {

        if (pinpointManager == null) {
            AWSMobileClient.getInstance()
                .initialize(applicationContext, awsConfig, object : Callback<UserStateDetails?> {
                    override fun onResult(result: UserStateDetails?) {
                        result?.let { Timber.d("Initialize ${result.userState}") }
                    }

                    override fun onError(e: Exception?) {
                        Timber.e(e)
                    }
                })
            pinpointManager = getPinpointManager(getPinpointConfig())
        }
    }

    private fun createFile(name: String, content: String): File {

        val file = File(applicationContext.filesDir, name)

        val writer = BufferedWriter(FileWriter(file))
        writer.append(content)
        writer.close()

        return file
    }

    fun getPinpointConfig(): PinpointConfiguration {
        return PinpointConfiguration(
            applicationContext,
            AWSMobileClient.getInstance(),
            awsConfig
        )
    }

    fun showFile(content: String) {
        binding.fileContentTextView.text = content
    }
}
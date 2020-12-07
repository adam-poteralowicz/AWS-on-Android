package com.android.aws.ui.storage

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import timber.log.Timber
import java.io.File

class StorageActionsImpl : StorageActions {

    override fun downloadFile(targetFile: File, transferUtility: TransferUtility) {

        val downloadObserver = transferUtility.download("public/file.txt", targetFile)
        downloadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED == state) {
                    Timber.d("Download completed")
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

    override fun uploadFile(file: File, transferUtility: TransferUtility) {

        val uploadObserver = transferUtility.upload("public/file.txt", file)
        uploadObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED === state) {
                    Timber.d("Upload completed")
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
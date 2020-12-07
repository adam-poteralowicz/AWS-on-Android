package com.android.aws.ui.storage

import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import java.io.File

interface StorageActions {
    fun downloadFile(targetFile: File, transferUtility: TransferUtility)
    fun uploadFile(file: File, transferUtility: TransferUtility)
}
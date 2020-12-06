package com.android.aws.credentials

import com.amplifyframework.datastore.generated.model.Credentials

interface CredentialsRepository {

    fun getCredentials(id: String): Credentials?
    fun getAll(): List<Credentials>
    fun saveCredentials(credentials: Credentials)
}
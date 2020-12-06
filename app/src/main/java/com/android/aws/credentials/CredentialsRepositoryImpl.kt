package com.android.aws.credentials

import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Credentials
import timber.log.Timber

class CredentialsRepositoryImpl : CredentialsRepository {

    override fun getCredentials(id: String): Credentials? {
        val credentials = getAll()
        return credentials.firstOrNull{ it.id == id }
    }

    override fun getAll(): List<Credentials> {
        val credentials = emptyList<Credentials>().toMutableList()
        Amplify.DataStore.query(Credentials::class.java,
            { result -> for (cred in result) credentials += cred },
            { Timber.e("Error retrieving credentials") }
        )

        return credentials
    }

    override fun saveCredentials(credentials: Credentials) {
        Amplify.DataStore.save(credentials,
            { Timber.d("Saved login: ${credentials.login}, password: ${credentials.password}") },
            { Timber.e("Error saving credentials") }
        )
    }
}
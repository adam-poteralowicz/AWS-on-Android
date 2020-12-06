package com.android.aws.credentials

import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.Credentials
import timber.log.Timber

class CredentialsRepositoryImpl : CredentialsRepository {

    override fun deleteAll() = Amplify.DataStore.clear(
        { Timber.d("Successfully deleted all records") },
        { Timber.e("Failed to delete all records") }
    )

    override fun deleteCredentials(id: String) {
        val credentials = getCredentials(id)
        if (credentials != null) {
            Amplify.DataStore.delete(credentials,
                { Timber.d("Deleted credentials") },
                { Timber.e("Failed to delete credentials with id $id") }
            )
        }
    }

    override fun getCredentials(id: String): Credentials? {
        var credentials: Credentials? = null
        Amplify.DataStore.query(Credentials::class.java, Where.id(id),
            { result -> if (result.hasNext()) credentials = result.next() },
            { Timber.e("Error retrieving credentials with id $id") }
        )

        return credentials
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

    /** Models are immutable, need to invoke copyOfBuilder() **/
    override fun updateCredentials(id: String, newPassword: String) {
        val credentials = getCredentials(id)
        if (credentials != null) {
            val updated = credentials.copyOfBuilder()
                .password(newPassword)
                .build()

            Amplify.DataStore.save(updated,
                { Timber.d("Updated credentials $id with password $newPassword") },
                { Timber.e("Update failed") }
            )
        }
    }
}
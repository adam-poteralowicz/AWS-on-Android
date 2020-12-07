package com.android.aws.credentials

import com.amplifyframework.datastore.generated.model.Credentials
import timber.log.Timber

fun randomCredentials(): Credentials {
    val credentials = Credentials.builder()
        .login(randomString(8))
        .password(randomString(10))
        .build()
    Timber.d("Random credentials: ${credentials.login}:${credentials.password}")
    return credentials
}

private fun randomString(length: Int): String {
    val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    return (1..length)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("");
}
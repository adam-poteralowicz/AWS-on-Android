package com.android.aws.credentials

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Credentials

class CredentialsRecyclerView(context: Context, attrs: AttributeSet?) :
    RecyclerView(context, attrs) {

    private var repository = CredentialsRepositoryImpl()

    var credentials: List<Credentials> = emptyList()

    init {
        adapter = CredentialsAdapter(credentials)
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(DividerItemDecoration(context, HORIZONTAL))
        credentials = repository.getAll()
    }

    override fun getAdapter() = CredentialsAdapter(credentials)
}
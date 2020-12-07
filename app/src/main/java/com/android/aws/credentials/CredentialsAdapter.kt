package com.android.aws.credentials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.datastore.generated.model.Credentials
import com.android.aws.R

class CredentialsAdapter(private var credentials: List<Credentials>) : RecyclerView.Adapter<CredentialsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var login: TextView = view.findViewById(R.id.loginTextView)
        var password: TextView = view.findViewById(R.id.passwordTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.credentials_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.login.text = credentials[position].login
        viewHolder.password.text = credentials[position].password
    }

    override fun getItemCount() = credentials.size

    fun addItem(item: Credentials) {
        credentials += item
    }

    fun setItems(list: List<Credentials>) {
        this.credentials = list
    }
}
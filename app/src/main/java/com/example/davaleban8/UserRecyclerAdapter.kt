package com.example.davaleban8

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.davaleban8.databinding.ItemBinding

class UserRecyclerAdapter(private val users: MutableList<User>) :
    ListAdapter<User, UserRecyclerAdapter.UserViewHolder>(
        object : DiffUtil.ItemCallback<User>(){

            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.email == newItem.email
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind()
        holder.deleteUser()
    }

    inner class UserViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val user = users[adapterPosition]
            with(binding) {
                inputNameTv.text = user.name
                inputSurnameTv.text = user.surname
                inputEmailTv.text = user.email
            }
            binding.updateBtn.setOnClickListener {
                onItemClick?.invoke(user)
            }
        }

        fun deleteUser() {
            binding.deleteBtn.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    users.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
            }
        }
    }
}
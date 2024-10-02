package com.example.getirlite.view.fragments.profile.components

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.getirlite.R
import com.example.getirlite.databinding.CellProfileItemBinding
import com.example.getirlite.model.User
import com.example.getirlite.model.extension.AssetManager
import com.example.getirlite.model.product.Product

class ProfileItemAdapter(
    private val items: List<ProfileItem>,
    private var isEditable: Boolean = false,
    private val onClick: (ProfileItem) -> Unit = {}
    ) : RecyclerView.Adapter<ProfileItemAdapter.ProfileItemAdapterViewHolder>(){

    inner class ProfileItemAdapterViewHolder(private val binding: CellProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(item: ProfileItem, isLast: Boolean) {
            binding.divider.visibility = if (isLast) View.GONE else View.VISIBLE

            when {
                item.isButton -> {
                    binding.apply {
                        icon.setImageResource(item.icon)
                        editLabel.setText(item.title)
                        profileButton.visibility = View.VISIBLE
                        profileInfo.visibility = View.GONE
                        iconArrow.visibility = View.VISIBLE
                    }
                }
                item.isUserInfo -> {
                    binding.apply {
                        icon.setImageResource(item.icon)
                        editLabel.setTextColor(AssetManager.color(R.color.grayDark))
                        editLabel.setText(if (item == ProfileItem.email) User.email.string else User.phone.string)


                        editLabel.isFocusable = isEditable
                        editLabel.isFocusableInTouchMode = isEditable
                        if (isEditable) {
                            editLabel.requestFocus()
                            if (item == ProfileItem.email) editLabel.inputType = InputType.TYPE_CLASS_TEXT
                            else editLabel.inputType = InputType.TYPE_CLASS_PHONE
                        }

                        editLabel.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                            override fun afterTextChanged(p0: Editable?) {
                                 if (item == ProfileItem.email) User.email.set(p0.toString())
                                else User.phone.set(p0.toString())
                            }
                        })

                        iconArrow.visibility = View.GONE
                        profileButton.visibility = View.VISIBLE
                        profileInfo.visibility = View.GONE
                    }
                }
                else -> {
                    binding.apply {
                        editTextUserName.setText(User.userName.string)
                        editTextUserName.isFocusable = isEditable
                        editTextUserName.isFocusableInTouchMode = isEditable


                        editTextUserName.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                            override fun afterTextChanged(p0: Editable?) { User.userName.set(p0.toString()) }
                        })

                        profileButton.visibility = View.GONE
                        profileInfo.visibility = View.VISIBLE
                    }
                }
            }


            binding.main.setOnClickListener { onClick(item) }
        }
    }

    fun toggleEdit() {
        isEditable = !isEditable
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemAdapterViewHolder = ProfileItemAdapterViewHolder(CellProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ProfileItemAdapterViewHolder, position: Int) = holder.setData(item = items[position], isLast = position == itemCount - 1)

    override fun getItemCount(): Int = items.size
}
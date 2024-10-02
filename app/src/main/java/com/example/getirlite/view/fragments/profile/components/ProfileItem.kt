package com.example.getirlite.view.fragments.profile.components

import com.example.getirlite.R
import com.example.getirlite.model.User
import com.example.getirlite.model.extension.string
import com.example.getirlite.model.extension.stringRes
import com.example.getirlite.model.extension.title

enum class ProfileItem {
    userName, email, phone, login, favourite, help, logout; //address

    val title: String get() = "profileItem${name.title}".stringRes.string

    val isButton: Boolean get() = !userInfo.contains(this)

    val isUserInfo: Boolean get() = listOf(email, phone).contains(this)

    val icon: Int
        get() = when (this) {
            userName, login -> R.drawable.ic_profile
            email -> R.drawable.ic_email
            phone -> R.drawable.ic_phone
//            address -> R.drawable.ic_pin //TODO
            favourite -> R.drawable.ic_favorite
            help -> R.drawable.ic_help
            logout -> R.drawable.ic_logout
        }

    companion object {
        val userInfo: List<ProfileItem> get() = if (User.didLogIn.bool) listOf(userName, email, phone) else listOf()

        val buttons: List<ProfileItem> get() = if (User.didLogIn.bool) listOf(favourite, help, logout) else listOf(login, help)
    }
}
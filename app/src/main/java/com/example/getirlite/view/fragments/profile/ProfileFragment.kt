package com.example.getirlite.view.fragments.profile

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.databinding.ControllerProfileBinding
import com.example.getirlite.model.extension.AssetManager
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.onboarding.AccountManager
import com.example.getirlite.view.fragments.profile.components.ProfileItem
import com.example.getirlite.view.fragments.profile.components.ProfileItemAdapter
import com.example.getirlite.view.sheet.help.HelpSheet
import com.example.getirlite.view.sheet.onboarding.OnboardingSheet

class ProfileFragment: Fragment() {
    private var binding: ControllerProfileBinding? = null
    private val accountManager: AccountManager by activityViewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private var profileItemUserInfoAdapter: ProfileItemAdapter? = null

    private var isEditable: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerProfileBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        val binding = binding ?: return
        profileItemUserInfoAdapter = ProfileItemAdapter(items = ProfileItem.userInfo)
        binding.recyclerUserInfo.adapter = profileItemUserInfoAdapter

        binding.recyclerButtons.adapter = ProfileItemAdapter(items = ProfileItem.buttons) { profileItem ->
            Log.println(Log.ASSERT, profileItem.name, "click")
            when (profileItem) {
                ProfileItem.login -> {
                    OnboardingSheet().show(MainActivity.instance.supportFragmentManager, "OnboardingSheet")
                }
                ProfileItem.favourite -> {
                    val direction = ProfileFragmentDirections.actionProfileFragmentToFavouriteItemsFragment()
                    findNavController().navigate(direction)
                }
                ProfileItem.help -> HelpSheet().show(MainActivity.instance.supportFragmentManager, "HelpSheet")
                ProfileItem.logout -> {
                    accountManager.signOut()
                    cartViewModel.clearCart()
                    findNavController().navigate(R.id.action_global_onboardingFragment)
                    findNavController().popBackStack(R.id.onboardingFragment, false)
                }
                else -> {}
            }
        }

        binding.iconEdit.setOnClickListener {
            isEditable = !isEditable
            profileItemUserInfoAdapter?.toggleEdit()
            binding.recyclerButtons.visibility = if (isEditable) View.GONE else View.VISIBLE
            binding.iconEdit.setImageDrawable(AssetManager.drawable(if (isEditable) R.drawable.ic_exit else R.drawable.ic_edit))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.recyclerUserInfo?.adapter = null
        binding?.recyclerButtons?.adapter = null
        binding = null
    }
}
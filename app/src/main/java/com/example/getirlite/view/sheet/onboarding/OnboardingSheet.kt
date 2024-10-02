package com.example.getirlite.view.sheet.onboarding

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.getirlite.MainActivity
import com.example.getirlite.databinding.SheetOnboardingBinding
import com.example.getirlite.model.User
import com.example.getirlite.view.fragments.onboarding.AccountManager
import com.example.getirlite.view.fragments.onboarding.OnboardingFragmentDirections
import com.example.getirlite.view.sheet.BaseSheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class OnboardingSheet: BaseSheet() {
    private var binding: SheetOnboardingBinding? = null

    private val accountManager: AccountManager by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFullScreen = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SheetOnboardingBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }


    private fun bindView() {
        val binding = binding ?: return


        binding.labelContinueAsGuest.setOnClickListener {
            accountManager.signInAnonymously()
        }

        binding.buttonGoogle.setOnClickListener {
            accountManager.googleLogin()
        }

        accountManager.isLoggedIn.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val direction = OnboardingFragmentDirections.actionGlobalProductListFragment()
                findNavController().navigate(direction)
                User.didSeeOnboarding.set(true)
                dismiss()

            } else {
                Log.println(Log.ASSERT,"OnboardingSheet", "Login failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.labelContinueAsGuest?.setOnClickListener(null)

    }

    companion object {
        const val TAG = "OnboardingSheet"
    }
}
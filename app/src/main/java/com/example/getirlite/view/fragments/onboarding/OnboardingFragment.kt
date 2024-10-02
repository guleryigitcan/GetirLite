package com.example.getirlite.view.fragments.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.getirlite.MainActivity
import com.example.getirlite.databinding.ControllerOnboardingBinding
import com.example.getirlite.model.User
import com.example.getirlite.view.sheet.onboarding.OnboardingSheet

class OnboardingFragment: Fragment() {
    private var binding: ControllerOnboardingBinding? = null

    private val accountManager: AccountManager by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ControllerOnboardingBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        val binding = binding ?: return
        binding.labelContinueAsGuest.setOnClickListener {
            val direction = OnboardingFragmentDirections.actionOnboardingFragmentToProductListFragment()
            findNavController().navigate(direction)
            User.didSeeOnboarding.set(true)
            accountManager.signInAnonymously()
        }

        binding.buttonStart.setOnClickListener {
            OnboardingSheet().show(MainActivity.instance.supportFragmentManager, "OnboardingSheet")
            Log.println(Log.ASSERT, "OnboardingFragment", "start")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
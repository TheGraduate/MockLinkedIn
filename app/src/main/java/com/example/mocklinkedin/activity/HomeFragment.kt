package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private companion object {
        const val POSTS_TAG = "POSTS_TAG"
        const val EVENTS_TAG = "EVENTS_TAG"
        const val USERS_TAG = "USERS_TAG"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )

        val bottomNavigationView = binding.includeBottomNavigationMenu.root
        val actionBar = (activity as AppCompatActivity).supportActionBar
        val profileButton = activity?.findViewById<ImageView>(R.id.profile)
        val enterExitButton = activity?.findViewById<ImageView>(R.id.enterExit)

        profileButton?.setOnClickListener {
            enterExitButton?.visibility = View.GONE
            it.visibility = View.GONE
            actionBar?.setDisplayHomeAsUpEnabled(true)
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            requireParentFragment().findNavController().navigate(action)
        }

        enterExitButton?.setOnClickListener{
            profileButton?.visibility = View.GONE
            it.visibility = View.GONE
            actionBar?.setDisplayHomeAsUpEnabled(true)
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            requireParentFragment().findNavController().navigate(action)
        }

        if (childFragmentManager.findFragmentById(R.id.nav_host_home) == null) {
            loadFragment(POSTS_TAG) { FeedFragment() }
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item_feed -> {
                    loadFragment(POSTS_TAG) { FeedFragment() }
                    true
                }

                R.id.menu_item_events -> {
                    loadFragment(EVENTS_TAG) { EventsFragment() }
                    true
                }

                R.id.menu_item_users -> {
                    loadFragment(USERS_TAG) { UsersListFragment() }
                    true
                }

                else -> false
            }
        }

        return binding.root
    }
    private fun loadFragment(tag: String, fragmentFactory: () -> Fragment) {
        val cachedFragment = childFragmentManager.findFragmentByTag(tag)
        val currentFragment = childFragmentManager.findFragmentById(R.id.nav_host_home)

        if (currentFragment?.tag == tag) return

        childFragmentManager.commit {
            if (currentFragment != null) {
                detach(currentFragment)
            }
            if (cachedFragment != null) {
                attach(cachedFragment)
            } else {
                replace(R.id.nav_host_home, fragmentFactory(), tag)
            }
        }
    }
}
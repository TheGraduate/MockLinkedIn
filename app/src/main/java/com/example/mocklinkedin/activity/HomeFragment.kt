package com.example.mocklinkedin.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mocklinkedin.R
import com.example.mocklinkedin.databinding.FragmentHomeBinding
import com.example.mocklinkedin.viewmodel.AuthViewModel

class HomeFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels(
       // ownerProducer = ::requireParentFragment
    )
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
        //val enterButton = activity?.findViewById<ImageView>(R.id.log_in)
        //val exitButton = activity?.findViewById<ImageView>(R.id.log_out)

        profileButton?.setOnClickListener {
            //enterButton?.visibility = View.GONE
            it.visibility = View.GONE
            actionBar?.setDisplayHomeAsUpEnabled(true)
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            requireParentFragment().findNavController().navigate(action)
        }

        /*enterButton?.setOnClickListener{
            AppAuth.getInstance().setAuth(5, "x-token")
            profileButton?.visibility = View.GONE
            it.visibility = View.GONE
            exitButton?.visibility = View.GONE
            actionBar?.setDisplayHomeAsUpEnabled(true)
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            requireParentFragment().findNavController().navigate(action)
        }

        exitButton?.setOnClickListener{
            val fragmentManager = requireParentFragment()
            val dialogFragment = AuthAskFragment()
            dialogFragment.show(fragmentManager.parentFragmentManager, "my_ask_fragment_tag")
        }*/

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

   /* override fun onResume() {
        super.onResume()
        val enterButton = activity?.findViewById<ImageView>(R.id.log_in)
        val exitButton = activity?.findViewById<ImageView>(R.id.log_out)
        if (viewModel.authenticated) {
            enterButton?.visibility = View.VISIBLE
            exitButton?.visibility = View.GONE
        } else {
            enterButton?.visibility = View.GONE
            exitButton?.visibility = View.VISIBLE
        }
    }*/
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
package com.example.mocklinkedin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.example.mocklinkedin.activity.BlankFragment1
//import com.example.mocklinkedin.activity.BlankFragment2
import com.example.mocklinkedin.activity.FeedFragment

/*class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragmentList.size//{ return 3}

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlankFragment1()
            1 -> FeedFragment()
            2 -> BlankFragment2()
            else -> throw IllegalArgumentException("Invalid position")
        }
            //return fragmentList[position]
    }
}*/
/*class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    //private var navController: NavController
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    //private val fragmentIds = listOf(R.id.feedFragment, R.id.blankFragment1, R.id.blankFragment2)

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FeedFragment()
            1 -> BlankFragment1()
            2 -> BlankFragment2()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

   *//* fun getPositionForDestination(destinationId: Int): Int {
        return fragmentIds.indexOf(destinationId)
    }*//*
}*/

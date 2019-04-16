package com.quadriyanney.softcom_test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class PageAdapter(val fragments: List<Fragment>,
                  fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

}
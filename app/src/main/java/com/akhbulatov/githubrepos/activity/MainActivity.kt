package com.akhbulatov.githubrepos.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.fragments.RepositoriesFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateRepositoriesFragment()
    }

    private fun navigateRepositoriesFragment() {
        val fragment = RepositoriesFragment()
        navigateFragment(fragment)
    }

    private fun navigateFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }

}
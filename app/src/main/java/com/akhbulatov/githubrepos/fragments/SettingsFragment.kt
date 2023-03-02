package com.akhbulatov.githubrepos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    var binding : FragmentSettingsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)

        binding!!.settingsToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.navigator.goBack()
            }
        })

        val sharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )

        val repositoriesEditor: String? =
            sharedPreferences.getString("repositories", null)
        binding!!.repositories.setText(repositoriesEditor)

        binding!!.save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                saveSettings()
            }
        })
    }

    private fun saveSettings() {
        val sharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )
        val repositoriesList = binding!!.repositories.text.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositories", repositoriesList)
        editor.apply()

        val snackbar = Snackbar.make(
            requireView(),
            R.string.settings_have_been_saved,
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

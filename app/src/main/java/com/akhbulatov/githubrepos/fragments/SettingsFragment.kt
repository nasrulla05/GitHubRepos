package com.akhbulatov.githubrepos.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.akhbulatov.githubrepos.R
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var repositories: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.settings_toolbar)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        val sharedPreferences = requireContext().getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )

        repositories = view.findViewById(R.id.repositories)

        val repositoriesEditor: String? =
            sharedPreferences.getString("repositories", null)
        repositories.setText(repositoriesEditor)

        val button: Button = view.findViewById(R.id.save)
        button.setOnClickListener(object : View.OnClickListener {
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
        val repositoriesList = repositories.text.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositories", repositoriesList)
        editor.apply()

        val snackbar = Snackbar.make(
            requireView(),
            "Настройки сохранены",
            Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
}

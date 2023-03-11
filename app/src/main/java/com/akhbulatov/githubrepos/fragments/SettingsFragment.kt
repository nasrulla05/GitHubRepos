package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.FragmentSettingsBinding
import com.akhbulatov.githubrepos.viewModel.SettingsViewModel
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    var binding : FragmentSettingsBinding? = null
    val viewModel:SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }
    lateinit var repositories:EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        repositories = view.findViewById(R.id.repositories)

        binding!!.settingsToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })
        viewModel.repositoriesEditor()

        viewModel.repositoriesLiveData.observe(viewLifecycleOwner){t->
            binding!!.repositories.setText(t)
        }

        binding!!.save.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                saveSettings()
            }
        })

        viewModel.loadSaveSettings()
    }

    private fun saveSettings() {

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

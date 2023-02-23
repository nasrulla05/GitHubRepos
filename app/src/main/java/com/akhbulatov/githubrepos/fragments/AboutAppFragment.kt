package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.AboutAppBinding

class AboutAppFragment : Fragment(R.layout.about_app) {
    var binding : AboutAppBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = AboutAppBinding.bind(view)

        binding!!.aboutAppToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
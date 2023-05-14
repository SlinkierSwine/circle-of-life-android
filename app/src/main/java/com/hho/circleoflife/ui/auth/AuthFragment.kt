package com.hho.circleoflife.ui.auth

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hho.circleoflife.databinding.FragmentAuthBinding
import com.hho.circleoflife.repository.Repository


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private lateinit var viewModel: AuthViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
//        val viewModel =
//                ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val factory = AuthViewModel.Factory(Repository(sharedPreferences))

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        viewModel.newData.observe(viewLifecycleOwner) {
            it?.let { res->
                if (res.isLoading) {
                    binding.text.text = "Loading data from server"
                } else {
                    val text = "${res.username} with message ${res.message}"
                    binding.text.text = text
                }
            }
        }

        binding.btn.setOnClickListener {
            viewModel.getUserInfo()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
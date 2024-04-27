package com.dicoding.asclepius.ui.result

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.asclepius.R
import com.dicoding.asclepius.base.BaseFragment
import com.dicoding.asclepius.databinding.FragmentResultBinding
import com.dicoding.asclepius.viewModelFactory.HistoryModelFactory

class ResultFragment : BaseFragment<FragmentResultBinding>() {
    private val resultViewModel by viewModels<ResultViewModel> {
        HistoryModelFactory.getInstance(requireActivity())
    }
    private lateinit var label : String
    private lateinit var confidenceScore: String
    private lateinit var imageUri: String
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentResultBinding {
        return FragmentResultBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        if(!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }


    }

    override fun initActions() {
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        label = args.resultLabel
        confidenceScore = args.resultConfidence
        imageUri = args.imageUri

        binding.apply {
            resultText.text = label
            resultConfidence.text = confidenceScore
            resultImage.setImageURI(Uri.parse(imageUri))
        }
        binding.saveButton.setOnClickListener {
            resultViewModel.addHistory(
                label ?: "",
                confidenceScore ?: "",
                imageUri ?: ""
            )
            showToast(getString(R.string.analisa_tersimpan))
        }
    }

    override fun initProcess() {}

    override fun initObservers() {}

}
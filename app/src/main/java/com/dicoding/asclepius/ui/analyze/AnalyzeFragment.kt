package com.dicoding.asclepius.ui.analyze

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.dicoding.asclepius.base.BaseFragment
import com.dicoding.asclepius.databinding.FragmentAnalyzeBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.ui.analyze.AnalyzeFragmentDirections
import com.yalantis.ucrop.UCrop
import java.io.File

class AnalyzeFragment : BaseFragment<FragmentAnalyzeBinding>() {
    private lateinit var currentImageUri: Uri
    private lateinit var currentLabel: String
    private lateinit var currentConfidenceScore: String

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAnalyzeBinding {
        return FragmentAnalyzeBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        if(!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    override fun initActions() {
        binding.apply {
            galleryButton.setOnClickListener {
                startGallery()
            }
            analyzeButton.setOnClickListener {
                analyzeImage()
            }
        }
    }

    override fun initProcess() {}

    override fun initObservers() {}

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult (
        ActivityResultContracts.PickVisualMedia())
    { uri: Uri? ->
        if (uri != null) {
            startCropping(uri)
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "Tidak ada Media yang dipilih")
        } }

    private fun startCropping(imageUri : Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_image"))
        UCrop.of(imageUri, destinationUri)
            .withAspectRatio(1f,1f)
            .start(requireContext(), this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UCrop.REQUEST_CROP && resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                currentImageUri = it
                showImage()
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val error = UCrop.getError(data!!)
            Log.e("UCrop", "Error occurred during cropping: $error")
        }
    }
    private fun showImage() {
        currentImageUri.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {

        if(currentImageUri == null) {
            showToast("Gambar belum ada")
            return
        }
        binding.progressIndicator.visibility = View.VISIBLE
        ImageClassifierHelper(
            context = requireContext(),
            onSuccess = { label, confidenceScore ->
                currentLabel = label
                currentConfidenceScore = confidenceScore
                moveToResult()
            },
            onFailed = {
                showToast(it)
                binding.progressIndicator.visibility = View.GONE
            }
        ).classifyImage(currentImageUri)
    }

    private fun moveToResult() {
        val action = currentImageUri?.let { uri ->
            AnalyzeFragmentDirections.actionAnalyzeFragmentToResultFragment(
                currentLabel,
                currentConfidenceScore,
                uri.toString()
            )
        }
        action?.let { findNavController().navigate(it) }
    }
}
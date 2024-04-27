package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

import android.util.Log
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.text.NumberFormat

class ImageClassifierHelper(
    private var threshold: Float = 0.1f,
    private var maxResults: Int = 3,
    private val modelName: String = "cancer_classification.tflite",
    private val context: Context,
    private val onSuccess: (String, String) -> Unit,
    private val onFailed: (String) -> Unit
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    private fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            onFailed("Terjadi Kesalahan")
            Log.e(TAG, e.message.toString())
        }
    }

    fun classifyImage(imageUri: Uri) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }
        val bitmap = decodeBitmapFromUri(imageUri)
        bitmap?.let { bitmap ->
            val tensorImage = prepocessImg(bitmap)
            val results = imageClassifier?.classify(tensorImage)
            generateResult(results)
        } ?: run {
            onFailed("Gagal memuat gmabar dari URI")
            Log.e(TAG, "Gagal decode bitmap dari URI")
        }

    }

    private fun decodeBitmapFromUri(imageUri: Uri) : Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }?.copy(Bitmap.Config.ARGB_8888, true)
    }

    private fun prepocessImg(bitmap: Bitmap) : TensorImage {
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224,224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()
        return imageProcessor.process(TensorImage.fromBitmap(bitmap))
    }

    private fun generateResult(data : List<Classifications>?) {
        data?.let { it ->
            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                println(it)
                val highResult =
                    it[0].categories.maxBy { it?.score ?: 0.0f }
                val predictedLabel = highResult.label
                val confidenceScore = NumberFormat.getPercentInstance()
                    .format(highResult.score)
                onSuccess(predictedLabel, confidenceScore)
            }
        }
    }


    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
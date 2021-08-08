package com.geotec.cenotesapp.ui.cenote.fotos

import android.annotation.SuppressLint
import android.app.Activity
import android.app.usage.ExternalStorageStats
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.geotec.cenotesapp.BuildConfig
import com.geotec.cenotesapp.databinding.FragmentCenoteFotosSecBinding
import com.geotec.cenotesapp.model.CenoteFotosSec
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"
private val REQUEST_IMAGE_CAPTURE = 1

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteFotosSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteFotosSecFragment : Fragment() {
    private var _v: FragmentCenoteFotosSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    private lateinit var cFotosSec: CenoteFotosSec

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.sqlite = SqliteComunicate(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pCenoteSaved = it.getSerializable(ARG_CENOTE_SAVED) as CenoteSaved
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _v = FragmentCenoteFotosSecBinding.inflate(inflater, container, false)
        return v.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        v.btnSaveData.setOnClickListener {
            openCamera()
        }

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _v = null
    }

    lateinit var currentPhotoPhat: String
    private fun createCapturedPhoto(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("CENOTE_${timestamp}", ".png", storageDir).apply {
            currentPhotoPhat = absolutePath
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            context?.let { itContext ->
                intent.resolveActivity(itContext.packageManager)?.also {
                    val photoFile: File? = try {
                        createCapturedPhoto()
                    } catch (ex: IOException) {
                        null
                    }

                    photoFile?.also {
                        val photoURI = FileProvider.getUriForFile(
                            itContext, "${BuildConfig.APPLICATION_ID}.fileprovider", it)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // val imageBitmap = data?.extras?.get("data") as Bitmap
                val uri = Uri.parse(currentPhotoPhat)
                println("se tomó la foto")
                println(uri)

                v.imageTest.setImageURI(uri)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteFotosSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)}}
    }
}
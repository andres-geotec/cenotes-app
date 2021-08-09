package com.geotec.cenotesapp.ui.cenote.fotos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.geotec.cenotesapp.BuildConfig
import com.geotec.cenotesapp.databinding.FragmentCenoteFotosSecBinding
import com.geotec.cenotesapp.model.CenoteFoto
import com.geotec.cenotesapp.model.CenoteSaved
import com.geotec.cenotesapp.sqlite.SqliteComunicate
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CENOTE_SAVED: String = "cenoteSaved"

/**
 * A simple [Fragment] subclass.
 * Use the [CenoteFotosSecFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CenoteFotosSecFragment : Fragment(), CenoteFotosListener {
    private var _v: FragmentCenoteFotosSecBinding? = null
    private val v get() = _v!!

    private lateinit var sqlite: SqliteComunicate
    private lateinit var pCenoteSaved: CenoteSaved
    //private lateinit var cFoto: CenoteFoto

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

        fillComponents()

        return v.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*v.btnSaveData.setOnClickListener {
            openCamera()
        }*/

        v.btnCloseCenoteSection.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _v = null
    }

    private fun fillComponents() {
        v.rvCenoteFotos.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ConcatAdapter(
                HeaderCenoteFotosAdapter(),
                CenoteFotosAdapter(this@CenoteFotosSecFragment, getFotosList()),
                FooterCenoteFotosAdapter())
        }
    }

    private fun getFotosList(): ArrayList<CenoteFoto> {
        return ArrayList<CenoteFoto>().apply {
            add(CenoteFoto(pCenoteSaved.clave))
            add(CenoteFoto(pCenoteSaved.clave))
            add(CenoteFoto(pCenoteSaved.clave))
        }
    }

    lateinit var currentPhotoPhat: String
    private fun createCapturedPhoto(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("CENOTE_${timestamp}", ".png", storageDir).apply {
            currentPhotoPhat = absolutePath
        }
    }

    private val resultTakePicture = registerForActivityResult(StartActivityForResult()) { r ->
        if (r.resultCode == Activity.RESULT_OK) {
            val uri = Uri.parse(currentPhotoPhat)
            //v.imageTest.setImageURI(uri)
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        with(resultTakePicture) {
            context?.let { context ->
                takePictureIntent.resolveActivity(context.packageManager)?.also {
                    val photoFile: File? = try {
                        createCapturedPhoto()
                    } catch (ex: IOException) {
                        null
                    }
                    photoFile?.also {
                        val photoURI = FileProvider.getUriForFile(
                            context, "${BuildConfig.APPLICATION_ID}.fileprovider", it)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    }
                }
            }
            launch(takePictureIntent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteFotosSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)}}
    }
}

// class RecyclerView.ConcatAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder!>

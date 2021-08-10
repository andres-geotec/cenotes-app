package com.geotec.cenotesapp.ui.cenote.fotos

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geotec.cenotesapp.BuildConfig
import com.geotec.cenotesapp.R
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
    private lateinit var cFotosSec: ArrayList<CenoteFoto>
    private lateinit var currentPhotoPhat: String

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

        cFotosSec = getDataSec()
        fillComponents()

        if (cFotosSec.size == 0) {
            openCamera()
        }

        v.btnCreateNewPhoto.setOnClickListener {
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

    private fun getDataSec(): ArrayList<CenoteFoto> {
        val list = sqlite.readCenotesFotoSec(pCenoteSaved.clave)
        println(list.size)
        return if (list.size > 0) {
            for (x in list){
                println(x.id)
            }
            list
        } else {
            ArrayList<CenoteFoto>()
        }
    }

    private fun getAdapterContent(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return if (cFotosSec.size > 0) {
            ConcatAdapter (
                HeaderCenoteFotosAdapter(),
                CenoteFotosAdapter(this@CenoteFotosSecFragment, cFotosSec),
                FooterCenoteFotosAdapter(this))
        } else ConcatAdapter(
            HeaderCenoteFotosAdapter(),
            FooterCenoteFotosAdapter(this))
    }

    override fun onChangeText(idx: Int, newText: String) {
        cFotosSec[idx].desc = newText
    }

    override fun onSavePhotos() {
        pCenoteSaved.progreso_fotos = 0
        for ((idx, photo) in cFotosSec.iterator().withIndex()) {
            if (!photo.saved) {
                saveData(idx)
            } else {
                if (editData(photo)) pCenoteSaved.progreso_fotos += 1
            }
        }

        if(editProgress()) savedMessage(R.string.savedSuccessfulSec)

        if (pCenoteSaved.progreso_fotos < cFotosSec.size) {
            Toast.makeText(context, "${cFotosSec.size-pCenoteSaved.progreso_fotos} No se guardaron", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveData(idx: Int) {
        println("fuardar datos")
        val rowIdSaved = sqlite.insertCenoteFotoSec(cFotosSec[idx])
        if (rowIdSaved != null && rowIdSaved > -1) { // asegura que el cenoce se agregue a la bd
            // savedMessage(R.string.savedSuccessfulSec)
            cFotosSec[idx].id = rowIdSaved.toInt()
            cFotosSec[idx].saved = true
            pCenoteSaved.progreso_fotos += 1
        } // else savedMessage(R.string.savedErrorSec)
    }
    private fun editData(photo: CenoteFoto): Boolean {
        println("editar datos")
        val countSaved = sqlite.updateCenoteFotoSec(photo)
        return (countSaved != null && countSaved > 0)
    }
    private fun editProgress(): Boolean {
        val countSaved = sqlite.updateCenoteSaved(pCenoteSaved)
        return if (!(countSaved != null && countSaved > 0)) {
            savedMessage(R.string.savedError)
            false
        } else true
    }

    private fun fillComponents() {
        v.rvCenoteFotos.apply {
            v.pbCenotesPhotos.visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context)
            adapter = getAdapterContent()
            v.pbCenotesPhotos.visibility = View.GONE
        }

        if (cFotosSec.size >= 3) {
            v.btnCreateNewPhoto.visibility = View.GONE
        }
    }

    private val resultTakePicture = registerForActivityResult(StartActivityForResult()) { r ->
        if (r.resultCode == Activity.RESULT_OK) {
            // Después de tomar la foto
            val cenoteFoto = CenoteFoto((cFotosSec.size+1)*-1, pCenoteSaved.clave)
            cenoteFoto.ruta = currentPhotoPhat
            cFotosSec.add(cenoteFoto)
            fillComponents()
        }
    }

    private fun createCapturedPhoto(): File {
        val timestamp: String = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US).format(Date())
        val storageDir = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile("CENOTE_${pCenoteSaved.clave}_${timestamp}", ".png", storageDir).apply {
            currentPhotoPhat = absolutePath
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

    private fun savedMessage(rString: Int) {
        Toast.makeText(context, getString(rString), Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(pCenoteSaved: CenoteSaved) = CenoteFotosSecFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_CENOTE_SAVED, pCenoteSaved)}}
    }
}

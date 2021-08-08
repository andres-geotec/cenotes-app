package com.geotec.cenotesapp.ui.inicio

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import java.util.ArrayList

private const val PERMISSION_CAMERA_REQUEST_CODE = 201
private const val PERMISSION_STORAGE_REQUEST_CODE = 202

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        fragmentTransition.commit()

        fillPermissions()
    }

    override fun onResume() {
        super.onResume()
        checkAllPermissions()
    }

    class Permission(val name: String, val code: Int)
    private val permissions = ArrayList<Permission>()
    private fun fillPermissions() {
        permissions.apply {
            add(Permission(
                Manifest.permission.CAMERA, PERMISSION_CAMERA_REQUEST_CODE
            ))
            add(Permission(
                Manifest.permission.READ_EXTERNAL_STORAGE, PERMISSION_STORAGE_REQUEST_CODE
            ))
        }
    }
    private fun checkAllPermissions() {
        for (permission in permissions) {
            checkPermission(permission)
        }
    }
    private fun checkPermission(permission: Permission) {
        if (ContextCompat.checkSelfPermission(this, permission.name) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.name),permission.code)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.permissionCameraGrated, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
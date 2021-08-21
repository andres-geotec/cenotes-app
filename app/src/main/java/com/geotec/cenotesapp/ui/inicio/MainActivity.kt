package com.geotec.cenotesapp.ui.inicio

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.*
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import com.geotec.cenotesapp.room.User
import com.geotec.cenotesapp.room.UserViewModel
import java.util.ArrayList
import kotlin.math.log

private const val PERMISSION_CAMERA_REQUEST_CODE = 201
private const val PERMISSION_STORAGE_REQUEST_CODE = 202
private const val PERMISSION_FINE_LOCATION_REQUEST_CODE = 203
private const val PERMISSION_COARSE_LOCATION_REQUEST_CODE = 203

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        val mUserViewModel: UserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        fragmentTransition.commit()

        fillPermissions()

        bv.test.setOnClickListener {
            println("click")
            // insert
            val user = User(
                0,
                "Andrés",
                "Martínez González",
                26
            )
            // add data to view model
            mUserViewModel.addUser(user)
            Toast.makeText(this, "añadido!", Toast.LENGTH_LONG).show()
        }

        // get data
        mUserViewModel.getAllData.observe(this, Observer { user ->
            println(user)
        })
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
            add(Permission(
                Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_FINE_LOCATION_REQUEST_CODE
            ))
            add(Permission(
                Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSION_COARSE_LOCATION_REQUEST_CODE
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
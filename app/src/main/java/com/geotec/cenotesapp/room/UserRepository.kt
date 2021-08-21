package com.geotec.cenotesapp.room

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val getAllData: LiveData<List<User>> = userDao.getAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }
}
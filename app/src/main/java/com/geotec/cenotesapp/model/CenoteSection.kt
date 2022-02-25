package com.geotec.cenotesapp.model

class CenoteSection(
    val name: String,
    val navigation: Int,
    val active: Boolean,
    val advance: Int,
    val total: Int,
) {
    fun getProgress(): Int {
        return ((advance.toFloat() / total) * 100).toInt()
    }
}
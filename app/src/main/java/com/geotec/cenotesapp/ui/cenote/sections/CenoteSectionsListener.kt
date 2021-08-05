package com.geotec.cenotesapp.ui.cenote.sections

import com.geotec.cenotesapp.model.CenoteSection

interface CenoteSectionsListener {
    fun onCenoteSectionClick(cenoteSection: CenoteSection)
}
package com.geotec.cenotesapp.model

import java.util.ArrayList

class CenoteGeojson(
    val cenoteSaved: CenoteSaved,
    val generalSec: CenoteGeneralSec,

    val fotos: ArrayList<CenoteFoto>
) {}
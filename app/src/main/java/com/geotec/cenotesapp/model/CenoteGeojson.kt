package com.geotec.cenotesapp.model

import java.util.ArrayList

class CenoteGeojson(
    val cenoteSaved: CenoteSaved,
    val generalSec: CenoteGeneralSec,
    val clasifiSec: CenoteClasifiSec,
    val morfoSec: CenoteMorfoSec,
    val usoSec: CenoteUsoSec,
    val problemSec: CenoteProblemSec,
    val gestionSec: CenoteGestionSec,
    val fotos: ArrayList<CenoteFoto>
) {}
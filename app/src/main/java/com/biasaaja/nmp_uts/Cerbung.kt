package com.biasaaja.nmp_uts


class Cerbung(val id:Int, val title:String, val date:String, val author:String, val description:String,
                   val url:String, val genre: Int, val access: Boolean){
    fun findCerbungById(findId: Int): Cerbung? {
        return Global.cerbungs.find { it.id == findId }
    }
}

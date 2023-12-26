package com.biasaaja.nmp_uts


class Cerbung(val id:Int, val title:String, val date:String, val author:String, val description:String,
                   val url:String, val genre: String, var likeCount:Int, var parCount:Int,
              val is_private: Boolean, var is_liked:Boolean?){
//    fun findCerbungById(findId: Int): Cerbung? {
//        return Global.cerbungs.find { it.id == findId }
//    }
}

package com.biasaaja.nmp_uts

class Genre(val id:Int, val name:String){
    override fun toString(): String {
        return name
    }
    fun findGenreById(findId: Int): Genre? {
        return Global.genres.find { it.id == findId }
    }
}

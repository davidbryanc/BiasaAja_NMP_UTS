package com.biasaaja.nmp_uts

import java.util.Date

class User (val id:Int, val name:String){
    override fun toString(): String {
        return name
    }

    fun findUserById(findId: Int): User? {
        return Global.users.find { it.id == findId }
    }
}
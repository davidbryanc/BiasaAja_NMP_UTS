package com.biasaaja.nmp_uts

import java.util.Date

//class User (val username:String, val profilePictureURL: String, val password:String){
//    override fun toString(): String {
//        return username
//    }
//
//    fun findUserById(findUser: String): User? {
//        return Global.users.find { it.username == findUser }
//    }
//}

class User(var id:Int,
           var username:String,
           var password:String,
           var profile_url:String,
            var date_joined:String
){
    override fun toString(): String {
        return username
    }

}
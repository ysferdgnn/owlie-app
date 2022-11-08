package com.ysferdgnn.owlieapp.exception

class UserBadCredentials : Error(){
    override val message: String?
        get() = "User Credentials Wrong!"
}
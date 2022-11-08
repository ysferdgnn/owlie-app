package com.ysferdgnn.owlieapp.exception

class UnAuthorizedException : Exception() {
    override val message: String?
        get() = "Wrong User Credentials!"
}
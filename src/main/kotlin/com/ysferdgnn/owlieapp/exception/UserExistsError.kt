package com.ysferdgnn.owlieapp.exception

class UserExistsError : Error() {
    override val message: String?
        get() = "User Already Defined!"
}
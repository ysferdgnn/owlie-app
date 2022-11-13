package com.ysferdgnn.owlieapp.exception

class UserNotFoundException :Error() {

    override val message: String?
        get() = "User Not Found!"
}
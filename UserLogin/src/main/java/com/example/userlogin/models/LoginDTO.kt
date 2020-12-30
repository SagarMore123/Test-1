package com.example.userlogin.models

class LoginDTO {

    var deviceKey: String? = "123"
    var isForgotPassword: Boolean? = false
    var password: String? = ""
    var role: String? = ""
    var sourceId: String? = ""
    var username: String? = ""

}
class RefreshTokenDTO {
    var username: String? = ""
    var refreshToken: String? = ""
}
class AccessTokenDTO {
    var access_token: String? = ""
    var refresh_token: String? = ""
}
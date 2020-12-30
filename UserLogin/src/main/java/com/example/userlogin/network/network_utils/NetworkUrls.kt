package com.example.userlogin.network.network_utils

class NetworkUrls {
    companion object {


        const val SERVER_URL: String = "https://uat-api.checqk.com/edge-service/"  // uat server

        //const val SERVER_IMG_URL: String = "https://uat-api.checqk.com/~cravx/public_html/"
        const val SERVER_IMG_URL: String =
            "https://cravxfiles.s3.amazonaws.com/home/cravx/public_html"

//const val SERVER_URL: String = "http://103.146.177.39:7010/edge-service/"
//const val SERVER_IMG_URL: String = "http://103.146.177.39/~cravx/public_html/"

        const val OAUTH_URL: String = SERVER_URL + "oauth-server/"

        const val IS_FIRST_TIME_LOGIN_WITH_LOGIN_ID = OAUTH_URL + "isFirstTimeLoginWithLoginId"

        //const val LOGIN_WITH_LOGIN_ID =  OAUTH_URL + "loginWithLoginId"
        const val LOGIN_WITH_LOGIN_ID = OAUTH_URL + "login"
        const val RESET_PASSWORD = OAUTH_URL + "resetPassword"
        const val LOGIN_MASTERS = OAUTH_URL + "loginForMasters"
        const val REFRESH_TOKEN = OAUTH_URL + "refreshToken"

        const val LOGOUT_USER_URL = OAUTH_URL + "customLogout"

   }

}


package com.example.userlogin.network.services

import com.example.userlogin.models.AccessTokenDTO
import com.example.userlogin.models.LoginDTO
import com.example.userlogin.models.RefreshTokenDTO
import com.example.userlogin.models.ResetPassword
import com.example.userlogin.network.network_utils.NetworkUrls.Companion.IS_FIRST_TIME_LOGIN_WITH_LOGIN_ID
import com.example.userlogin.network.network_utils.NetworkUrls.Companion.LOGIN_MASTERS
import com.example.userlogin.network.network_utils.NetworkUrls.Companion.LOGIN_WITH_LOGIN_ID
import com.example.userlogin.network.network_utils.NetworkUrls.Companion.LOGOUT_USER_URL
import com.example.userlogin.network.network_utils.NetworkUrls.Companion.REFRESH_TOKEN
import com.example.userlogin.network.network_utils.NetworkUrls.Companion.RESET_PASSWORD
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST(IS_FIRST_TIME_LOGIN_WITH_LOGIN_ID)
    fun isFirstTimeLoginWithLoginId(@Body loginDTO: LoginDTO): Call<ResponseBody>

    @POST(LOGIN_MASTERS)
    fun loginMasters(@Body loginDTO: LoginDTO): Call<ResponseBody>

    @POST(REFRESH_TOKEN)
    fun refreshToken(@Body refreshTokenDTO: RefreshTokenDTO): Call<AccessTokenDTO>

    @POST(LOGIN_WITH_LOGIN_ID)
    fun login(@Body loginDTO: LoginDTO): Call<ResponseBody>

    @POST(LOGIN_WITH_LOGIN_ID)
    fun verifyOtp(@Body loginDTO: LoginDTO): Call<ResponseBody>

    @POST(RESET_PASSWORD)
    fun resetPassword(
//        @Header("Authorization") accessToken: String,
        @Body resetPassword: ResetPassword
    ): Call<ResponseBody>

    @POST(LOGOUT_USER_URL)
    fun logoutUser(
//        @Header("Authorization") access_token: String
    ): Call<ResponseBody>

}
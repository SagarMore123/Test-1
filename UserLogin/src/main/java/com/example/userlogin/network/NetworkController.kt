package com.example.userlogin.network

import android.content.Context
import com.astrika.staffappchecqk.network.network_utils.IDataSourceCallback
import com.astrika.staffappchecqk.network.network_utils.NetworkResponseCallback
import com.example.userlogin.R
import com.example.userlogin.models.CommonResponseDTO
import com.example.userlogin.models.LoginDTO
import com.example.userlogin.models.ResetPassword
import com.example.userlogin.network.network_utils.NetworkUtils.Companion.HTTP_RETROFIT_FAILURE
import com.example.userlogin.network.network_utils.NetworkUtils.Companion.HTTP_SUCCESS
import com.example.userlogin.network.network_utils.NetworkUtils.Companion.getStringResponseFromRaw
import com.example.userlogin.utils.Constants
import com.example.userlogin.utils.CustomGsonBuilder
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkController {

    companion object {

        var instance: NetworkController? = null
        lateinit var mContext: Context

        const val SERVER_ERROR = "Something went wrong on the server"

        fun getInstance(context: Context): NetworkController {
            mContext = context

            if (instance == null) {
                instance = NetworkController()
            }
            accessToken = Constants.getAccessToken(mContext) ?: ""
            return instance as NetworkController
        }

        const val contentType = "application/json"
        var accessToken = ""
    }

    class RetrofitServiceTask(var networkResponseCallback: NetworkResponseCallback) :
        Callback<ResponseBody> {

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.code() == HTTP_SUCCESS) {
                getStringResponseFromRaw(response)?.let { data ->
                    networkResponseCallback.onSuccess(data)
                }
            } else {
                var errorMsg = SERVER_ERROR
                val jsonError = getStringResponseFromRaw(response.errorBody()!!)
                try {
                    val jsonObject = JSONObject(jsonError.toString())
                    if (jsonObject.has("error")) {
                        if (jsonObject.getString("error")
                                .equals("invalid_token", ignoreCase = true)
                        ) {
//                            val intent = Intent(mContext, UserLoginActivity::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            intent.putExtra(Constants.ACCESS_TOKEN_INVALID, true)
                            Constants.clearSharedPrefs(mContext)
//                            mContext.startActivity(intent)

//                            accessToken = Constants.getRefreshToken(mContext) ?: ""
                        }

                        if (!jsonObject.getJSONObject("error").getString("message")
                                .isNullOrBlank()
                        ) {
                            networkResponseCallback.onError(
                                response.code(),
                                jsonObject.getJSONObject("error").getString("message")
                            )
                        } else {
                            networkResponseCallback.onError(response.code(), SERVER_ERROR)
                        }
                    }
                } catch (e: Exception) {
                    networkResponseCallback.onError(response.code(), SERVER_ERROR)
                }

            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            try {


                if (t.message != null) {
                    if (t.message?.contains("Failed") == true || t.message?.contains("failed to connect") == true) {
                        networkResponseCallback.onError(
                            HTTP_RETROFIT_FAILURE,
                            mContext.resources.getString(R.string.network_failure_string)
                        )
                    } else {
                        networkResponseCallback.onError(500, SERVER_ERROR)
                    }
                } else {
                    networkResponseCallback.onError(500, SERVER_ERROR)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    // returns CommonResponseDTO
    fun commonResponseDTO(data: String, callback: IDataSourceCallback<CommonResponseDTO>) {

        try {

            if (data.isNotBlank()) {

                val jsonObject = JSONObject(data)
                val gson = CustomGsonBuilder().getInstance().create()
                val commonResponseDTO: CommonResponseDTO = gson.fromJson(
                    jsonObject.toString(),
                    CommonResponseDTO::class.java
                )

                when {
                    commonResponseDTO.success != null -> {
                        callback.onDataFound(commonResponseDTO)
                    }
                    commonResponseDTO.error != null -> {
                        callback.onError(commonResponseDTO.error.message)
                    }
                    else -> {
                        callback.onError(SERVER_ERROR)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            callback.onError(SERVER_ERROR)
        }

    }

    // returns Message String
    fun commonResponseString(data: String, callback: IDataSourceCallback<String>) {

        try {

            if (data.isNotBlank()) {

                val jsonObject = JSONObject(data)
                val gson = CustomGsonBuilder().getInstance().create()
                val commonResponseDTO: CommonResponseDTO = gson.fromJson(
                    jsonObject.toString(),
                    CommonResponseDTO::class.java
                )

                when {
                    commonResponseDTO.success != null -> {
                        callback.onDataFound(commonResponseDTO.success.message)
                    }
                    commonResponseDTO.error != null -> {
                        callback.onError(commonResponseDTO.error.message)
                    }
                    else -> {
                        callback.onError(SERVER_ERROR)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            callback.onError(SERVER_ERROR)
        }

    }


    fun isFirstTimeLoginWithLoginId(loginDTO: LoginDTO, callback: NetworkResponseCallback) {
        val response = UserApi.retrofitService.isFirstTimeLoginWithLoginId(loginDTO)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun loginMasters(loginDTO: LoginDTO, callback: NetworkResponseCallback) {
        loginDTO.username = "visitor@syspiretechnologies.com"
        loginDTO.password = Constants.passwordEncrypt("P@ssw0rd")
        val response = UserApi.retrofitService.loginMasters(loginDTO)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun login(loginDTO: LoginDTO, callback: NetworkResponseCallback) {
        val response = UserApi.retrofitService.verifyOtp(loginDTO)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    /*

        fun refreshToken(refreshTokenDTO: RefreshTokenDTO, callback: NetworkResponseCallback) {
            val response = UserApi.retrofitService.refreshToken(refreshTokenDTO)
            val responseCall: Call<ResponseBody> = response
            responseCall.enqueue(RetrofitServiceTask(callback))
        }

    */
    fun verifyOtp(loginDTO: LoginDTO, callback: NetworkResponseCallback) {
        val response = UserApi.retrofitService.verifyOtp(loginDTO)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    fun resetPassword(resetPassword: ResetPassword, callback: NetworkResponseCallback) {
//        val response = UserApi.retrofitService.resetPassword(accessToken, resetPassword)
        val response = UserApi.retrofitService.resetPassword(resetPassword)
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }

    // logout service
    fun logoutUser(
        callback: NetworkResponseCallback
    ) {
        val response =
//            UserApi.retrofitService.logoutUser(accessToken)
            UserApi.retrofitService.logoutUser()
        val responseCall: Call<ResponseBody> = response
        responseCall.enqueue(RetrofitServiceTask(callback))
    }


}
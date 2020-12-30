package com.example.userlogin.source.user.remote

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.astrika.staffappchecqk.network.network_utils.IDataSourceCallback
import com.astrika.staffappchecqk.network.network_utils.NetworkResponseCallback
import com.example.userlogin.models.LoginDTO
import com.example.userlogin.models.LoginResponseDTO
import com.example.userlogin.models.ResetPassword
import com.example.userlogin.models.UserDTO
import com.example.userlogin.network.NetworkController
import com.example.userlogin.source.user.UserDataSource
import com.example.userlogin.utils.Constants
import com.example.userlogin.utils.CustomGsonBuilder
import org.json.JSONObject

class UserRemoteDataSource : UserDataSource {

    companion object {
        private var instance: UserRemoteDataSource? = null
        private var networkController: NetworkController? = null
        private var mContext: Context? = null
        private lateinit var sharedPreferences: SharedPreferences

        @JvmStatic
        fun getInstance(context: Context?): UserDataSource? {
            mContext = context
            networkController = NetworkController.getInstance(mContext!!)
            sharedPreferences =
                Constants.getSharedPreferences(context!!.applicationContext)
            if (instance == null) {
                instance = UserRemoteDataSource()
            }
            return instance
        }

    }

    override fun isFirstTimeLoginWithLoginId(
        loginDTO: LoginDTO,
        callback: IDataSourceCallback<LoginResponseDTO>
    ) {
        networkController?.isFirstTimeLoginWithLoginId(loginDTO, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                if (data.isNotEmpty()) {
                    try {
                        val jsonObject = JSONObject(data)
                        val gson = CustomGsonBuilder().getInstance().create()
                        val loginResponseDTO =
                            gson.fromJson(jsonObject.toString(), LoginResponseDTO::class.java)

                        if (loginResponseDTO.success != null && loginResponseDTO.error == null) {
                            if (loginResponseDTO.success.status == "200") {
                                callback.onDataFound(loginResponseDTO)
                            }
                        } else {
                            if (loginResponseDTO.error != null) {
                                if (loginResponseDTO.error.message != null) {
                                    if (loginResponseDTO.error.message.contains("blocked")) {
                                        callback.onError(loginResponseDTO.error.message)
                                    } else {
                                        callback.onError(loginResponseDTO.error.message)
                                    }
                                }

                            }
                        }
                    } catch (e: Exception) {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }

            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(errorData)
            }
        })
    }

    override fun login(loginDTO: LoginDTO, callback: IDataSourceCallback<UserDTO>) {
        networkController?.login(loginDTO, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                if (data.isNotEmpty()) {
                    try {
                        val jsonObject = JSONObject(data)
                        var accessToken = ""
                        if (jsonObject.has("userId")) {
                            val userId = jsonObject.optLong("userId")
                            userId?.let {
                                sharedPreferences.edit().putString(
                                    Constants.LOGIN_USER_ID,
                                    Constants.encrypt(userId.toString())
                                ).apply()
                            }
                        }
                        if (jsonObject.has("data")) {
                            val dataObject = jsonObject.getJSONObject("data")

                            // Access Token
                            if (dataObject.has("access_token")) {
                                accessToken = dataObject.getString("access_token")
                                Log.e("accessToken", accessToken)
                                NetworkController.accessToken = "Bearer $accessToken"
                                sharedPreferences.edit()?.putString(
                                    Constants.ACCESS_TOKEN,
                                    Constants.encrypt(accessToken)
                                )?.apply()
                            }

                            // Refresh Token
                            if (dataObject.has("refresh_token")) {
                                accessToken = dataObject.getString("refresh_token")
                                Log.e("refreshToken", accessToken)
                                sharedPreferences.edit()?.putString(
                                    Constants.REFRESH_TOKEN,
                                    Constants.encrypt(accessToken)
                                )?.apply()
                            }

                            if (dataObject.has("user")) {
                                val userJson = dataObject.getJSONObject("user")
                                val gson = CustomGsonBuilder().getInstance().create()
                                val userDTO =
                                    gson.fromJson(userJson.toString(), UserDTO::class.java)

                                userDTO?.loginId?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.LOGIN_ID,
                                        Constants.encrypt(userDTO.loginId)
                                    ).apply()
                                }
                                userDTO?.emailAddress?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.EMAIL_ID,
                                        Constants.encrypt(userDTO.emailAddress)
                                    ).apply()
                                }
                                userDTO?.userFirstName?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.FIRST_NAME,
                                        Constants.encrypt(userDTO.userFirstName)
                                    ).apply()
                                }
                                userDTO?.userLastName?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.LAST_NAME,
                                        Constants.encrypt(userDTO.userLastName)
                                    ).apply()
                                }
                                userDTO?.mobileNo?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.MOBILE_NO,
                                        Constants.encrypt(userDTO.mobileNo)
                                    ).apply()
                                }
                                userDTO?.outletId?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.OUTLET_ID,
                                        Constants.encrypt(userDTO.outletId.toString())
                                    ).apply()
                                }
                                userDTO?.productId?.let {
                                    sharedPreferences.edit().putString(
                                        Constants.PRODUCT_ID,
                                        Constants.encrypt(userDTO.productId.toString())
                                    ).apply()
                                }

                                //Profile
//                                userDTO?.profileImage?.base64 = ""
//                                userDTO?.profileImage?.path.let {
//                                    sharedPreferences.edit().putString(
//                                        Constants.PROFILE_IMAGE_PATH,
//                                        Constants.encrypt(userDTO?.profileImage?.path)
//                                    ).apply()
//                                }

//                                sharedPreferences.edit().putString(Constants.LOGIN_DTO,Constants.encrypt(Utils.setLoginDTO(loginDTO))).apply()
                                callback.onDataFound(userDTO)
                            }
                        } else {
                            if (jsonObject.has("error")) {
                                val error = jsonObject.getJSONObject("error")
                                val errorMessage = error.getString("message")
                                callback.onError(errorMessage)
                            }
                        }
                    } catch (e: Exception) {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(errorData)
            }

        })
    }

    override fun verifyOtp(loginDTO: LoginDTO, callback: IDataSourceCallback<String>) {
        networkController?.verifyOtp(loginDTO, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                if (data.isNotEmpty()) {
                    try {
                        commonCallback(data, callback)
                    } catch (e: Exception) {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(errorData)
            }
        })
    }

    override fun resetPassword(
        resetPassword: ResetPassword,
        callback: IDataSourceCallback<String>
    ) {
        networkController?.resetPassword(resetPassword, object : NetworkResponseCallback {
            override fun onSuccess(data: String) {
                if (data.isNotEmpty()) {
                    try {
                        commonCallback(data, callback)
                    } catch (e: Exception) {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }
            }

            override fun onError(errorCode: Int, errorData: String) {
                callback.onError(errorData)
            }

        })
    }

    private fun commonCallback(
        data: String,
        callback: IDataSourceCallback<String>
    ) {
        val jsonObject = JSONObject(data)
        var accessToken = ""
        if (jsonObject.has("data")) {
            val dataObject = jsonObject.getJSONObject("data")
            if (dataObject.has("access_token")) {
                accessToken = dataObject.getString("access_token")
                NetworkController.accessToken = "Bearer $accessToken"
                sharedPreferences.edit()?.putString(
                    Constants.ACCESS_TOKEN,
                    Constants.encrypt(accessToken)
                )?.apply()
            }
            callback.onDataFound("success")
        } else {
            if (jsonObject.has("error")) {
                val error = jsonObject.getJSONObject("error")
                val errorMessage = error.getString("message")
                callback.onError(errorMessage)
            }
        }
    }


    override fun logoutUser(callback: IDataSourceCallback<String>) {
        networkController?.logoutUser(
            object : NetworkResponseCallback {
                override fun onSuccess(data: String) {
                    try {
                        if (data.isNotBlank()) {
                            val jsonObject = JSONObject(data)
                            if (jsonObject.has("status")) {
                                val status = jsonObject.getString("status")
                                if (status == "Logout successfully")
                                    callback.onDataFound(status)
                            } else {
                                callback.onError(NetworkController.SERVER_ERROR)
                            }
                        } else {
                            callback.onError(NetworkController.SERVER_ERROR)
                        }
                    } catch (e: Exception) {
                        callback.onError(NetworkController.SERVER_ERROR)
                    }
                }

                override fun onError(errorCode: Int, errorData: String) {
                    callback.onError(errorData)
                }

            })
    }
}
package com.example.userlogin.source.user

import androidx.annotation.NonNull
import com.astrika.staffappchecqk.network.network_utils.IDataSourceCallback
import com.example.userlogin.models.LoginDTO
import com.example.userlogin.models.LoginResponseDTO
import com.example.userlogin.models.ResetPassword
import com.example.userlogin.models.UserDTO

interface UserDataSource {

    fun isFirstTimeLoginWithLoginId(
        @NonNull loginDTO: LoginDTO,
        @NonNull callback: IDataSourceCallback<LoginResponseDTO>
    )

    fun login(
        @NonNull loginDTO: LoginDTO,
        @NonNull callback: IDataSourceCallback<UserDTO>
    )

    fun verifyOtp(
        @NonNull loginDTO: LoginDTO,
        @NonNull callback: IDataSourceCallback<String>
    )

    fun resetPassword(
        @NonNull resetPassword: ResetPassword,
        @NonNull callback: IDataSourceCallback<String>
    )




    fun logoutUser(
        @NonNull callback: IDataSourceCallback<String>
    )

}
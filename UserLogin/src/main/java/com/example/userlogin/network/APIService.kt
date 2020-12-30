package com.example.userlogin.network

import com.example.userlogin.network.network_utils.NetworkUtils.Companion.retrofit
import com.example.userlogin.network.services.UserService

object UserApi {

    val retrofitService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

/*
    val mastersRetrofitService: MastersService by lazy {
        retrofit.create(MastersService::class.java)
    }

    val retrofitDashboardService: DashboardService by lazy {
        retrofit.create(DashboardService::class.java)
    }
*/


}
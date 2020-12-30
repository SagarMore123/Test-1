package com.astrika.staffappchecqk.network.network_utils

interface IDataSourceCallback<T> {

    fun onDataFound(data: T) {}

    fun onDataFound(data: T, responseCode: Int) {}

    fun onDataNotFound(){}

    fun onSuccess(message : String){}

    fun onError(error:String){}

}
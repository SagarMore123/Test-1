package com.example.userlogin.network.network_utils

class NetworkUrls {
    companion object {


        const val SERVER_URL: String = "https://uat-api.checqk.com/edge-service/"  // uat server

        //const val SERVER_IMG_URL: String = "https://uat-api.checqk.com/~cravx/public_html/"
        const val SERVER_IMG_URL: String =
            "https://cravxfiles.s3.amazonaws.com/home/cravx/public_html"

//const val SERVER_URL: String = "http://103.146.177.39:7010/edge-service/"
//const val SERVER_IMG_URL: String = "http://103.146.177.39/~cravx/public_html/"

        const val CATALOGUE_URL: String = SERVER_URL + "catalogue-service/"
        const val BOOKING_URL: String = SERVER_URL + "booking-service/"
        const val CHECK_IN_URL: String = SERVER_URL + "checkin-service/"
        const val ORDER_URL: String = SERVER_URL + "order-service/"
        const val DISCOUNT_URL: String = SERVER_URL + "discount-service/"
        const val RESTAURANT_URL: String = SERVER_URL + "restaurant-service/"
        const val SVM_URL: String = SERVER_URL + "system-service/"
        const val OTP_URL: String = SERVER_URL + "otp-service/"
        const val OAUTH_URL: String = SERVER_URL + "oauth-server/"

        const val IS_FIRST_TIME_LOGIN_WITH_LOGIN_ID = OAUTH_URL + "isFirstTimeLoginWithLoginId"

        //const val LOGIN_WITH_LOGIN_ID =  OAUTH_URL + "loginWithLoginId"
        const val LOGIN_WITH_LOGIN_ID = OAUTH_URL + "login"
        const val RESET_PASSWORD = OAUTH_URL + "resetPassword"
        const val LOGIN_MASTERS = OAUTH_URL + "loginForMasters"
        const val REFRESH_TOKEN = OAUTH_URL + "refreshToken"

        const val LOGOUT_USER_URL = OAUTH_URL + "customLogout"
        const val GET_DRAWER_MENU = "get/MenuDrawer"

        //const val GET_CHECK_IN_LISTING = CHECK_IN_URL + "listing/InCustomerRequest"
        const val GET_CHECK_IN_LISTING = CHECK_IN_URL + "get/AllInCustomerRequest/ById"
        const val CHANGE_STATUS_APPROVED = CHECK_IN_URL + "changeStatus/InCustomerRequest/Approved"
        const val CHANGE_DINE_IN_STATUS = CHECK_IN_URL + "changeStatus/InCustomerRequest"


        const val GET_ALL_MENU_CATEGORIES = CATALOGUE_URL + "get/CatalogueCategory/ByOutletId"
        const val GET_ALL_DISHES = CATALOGUE_URL + "get/Product/ByCatalogueCategoryId"
        const val SAVE_DISH_AVAILABILITY = CATALOGUE_URL + "save/DishAvailability"

        const val GET_ORDER_DETAILS = ORDER_URL + "get/Order/ByInCustomerRequestId"
        const val SAVE_ORDER_DETAILS = ORDER_URL + "save/Order"
        const val FETCH_SVM = SVM_URL + "listing/SystemValueMasters"
        const val SAVE_TABLE_LISTING = RESTAURANT_URL + "save/TableList"
        const val GET_TABLE_LISTING = RESTAURANT_URL + "listing/Table"
//const val ASSIGN_TABLE_TO_USER = CHECK_IN_URL + "save/CheckInRequest"

        // Profile
        const val GET_PROFILE_DETAILS = "get/User/ById/{userId}"
        const val UPDATE_PROFILE_DETAILS = "user-service/update/User/ProfilePic"
        const val GET_USER_BY_MOBILE = "user-service/get/User/ByMobile"
        const val SAVE_VISITOR_DETAILS = "user-service/save/visitor"

        const val SEND_OTP_URL = OTP_URL + "sendOtp"
        const val VERIFY_OTP_URL = OTP_URL + "verifyOTP"
        const val SAVE_STAFF_CHECKIN_VISITOR = CHECK_IN_URL + "save/StaffCheckInRequest"


        // Bill Generation
        const val GET_BILL_DETAILS = CHECK_IN_URL + "generateBillDetails"
        const val VIEW_BILL_DETAILS_BY_DINE_IN_ID =
            CHECK_IN_URL + "get/BillDetails/ByInCustomerRequestId"


        // Discount Masters
        const val FETCH_DISCOUNT_CATEGORIES = DISCOUNT_URL + "listing/OutletDiscountCategory"
        const val FETCH_DISCOUNT_MEMBERSHIP_PLAN = RESTAURANT_URL + "listing/OutletMembershipPlan"
        const val FETCH_CRAVX_CARDS_DISCOUNT_MEMBERSHIP_HOLDER_MASTERS =
            DISCOUNT_URL + "listing/CravxCard"
        const val FETCH_HW_DISCOUNT_MEMBERSHIP_HOLDER_MASTERS = DISCOUNT_URL + "listing/HWCard"
        const val FETCH_IN_HOUSE_DISCOUNT_MEMBERSHIP_HOLDER_MASTERS =
            DISCOUNT_URL + "listing/InHousesCard"

        // Discount URLs
        const val FETCH_OUTLET_DISCOUNT_DETAILS_LIST =
            DISCOUNT_URL + "get/OutletDiscountDetails/OutletId"
        const val FETCH_OUTLET_MEMBERSHIP_PLAN_MAPPING =
            RESTAURANT_URL + "get/OutletMembershipPlanMapping/ByOutletId"
        const val FETCH_OUTLET_CORPORATE_MEMBERSHIP_OR_ONE_DASHBOARD =
            DISCOUNT_URL + "get/CorporateMembership/productIdAndoutletId"

    }

}


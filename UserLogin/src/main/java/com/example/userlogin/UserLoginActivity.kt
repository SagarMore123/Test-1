package com.example.userlogin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.userlogin.utils.Constants
import com.example.userlogin.utils.SnackbarUtils.showSnackbar
import kotlinx.android.synthetic.main.activity_login.*

class UserLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(Constants.ACCESS_TOKEN_INVALID)) {
                if (bundle.getBoolean(Constants.ACCESS_TOKEN_INVALID)) {
                    showSnackbar(
                        root,
                        "User has been logged out"
                    )
                }
            }
        }

/*

        SyncData.loginMasterServicesCounts.value = 0

        SyncData.loginMasterServicesCounts.observe(this, Observer {

            if (it == SyncData.loginMasterServices) {
                val intent = Intent(this, DashboardActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }
        })
*/

    }


    override fun onBackPressed() {

    }


}

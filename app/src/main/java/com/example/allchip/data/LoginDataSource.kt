package com.example.allchip.data

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.allchip.MyApplication
import com.example.allchip.data.model.LoggedInUser
import com.google.gson.Gson
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String ): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
//            var requestBean : RequestBean<LoggedInUser>? = null
//            var user :LoggedInUser? = null
//            val queue = Volley.newRequestQueue(context)
//            val url = "192.168.66.223:8080/login"
//
//             Request a string response from the provided URL.
//            val stringRequest = StringRequest(
//                Request.Method.GET, url,
//                Response.Listener<String> { response ->
//                     Display the first 500 characters of the response string.
//                    requestBean = Gson().fromJson<RequestBean<LoggedInUser>>(response , RequestBean::class.java)
//
//
//                },
//                Response.ErrorListener {
//                        error->
//                })

            // Add the request to the RequestQueue.
//            queue.add(stringRequest)
            return Result.Success(LoggedInUser("",""))
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
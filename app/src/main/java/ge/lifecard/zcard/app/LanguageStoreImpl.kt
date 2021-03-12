package ge.lifecard.zcard.app

import android.annotation.SuppressLint
import android.content.SharedPreferences
import ge.lifecard.zcard.models.UserSession
import java.util.*

object DataStore {

    private const val KEY_LANGUAGE = "key_language"
    private const val KEY_TOKEN = "AccessToken"

    private var sharedPreferences: SharedPreferences? = null

    fun initialize(sharedPreferences: SharedPreferences) {
        DataStore.sharedPreferences = sharedPreferences
    }

    fun getUserData(): UserSession {
        var user = UserSession("", "", "", "", "", "", "")
        user.email = sharedPreferences!!.getString("UserName", "").toString()
        user.firstName = sharedPreferences!!.getString("FirstName", "").toString()
        user.lastName = sharedPreferences!!.getString("LastName", "").toString()
        user.accessToken = sharedPreferences!!.getString("AccessToken", "").toString()
        user.isActive = sharedPreferences!!.getString("IsActive", "").toString()
        user.avatarPath = sharedPreferences!!.getString("Avatar", "").toString()
        user.id = sharedPreferences!!.getString("ID", "").toString()
        return user
    }

    fun setUserData(userData: UserSession) {
        val editor = sharedPreferences!!.edit()
        editor.putString("UserName", userData.email)
        editor.putString("FirstName", userData.firstName)
        editor.putString("LastName", userData.lastName)
        editor.putString("AccessToken", userData.accessToken)
        editor.putString("IsActive", userData.isActive)
        editor.putString("Avatar", userData.avatarPath)
        editor.putString("ID", userData.id)
        editor.apply()
    }

    fun clearUserData() {
        val editor = sharedPreferences!!.edit()
        editor.putString("UserName", null)
        editor.putString("FirstName", null)
        editor.putString("LastName", null)
        editor.putString("AccessToken", null)
        editor.putString("IsActive", null)
        editor.putString("Avatar", null)
        editor.putString("ID", null)
        editor.apply()
    }

    fun getLoginState(): Int {
        return sharedPreferences!!.getInt("IsActive", 0)
    }

    var language: String
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_LANGUAGE, value)?.commit()
        }
        get() {
            return sharedPreferences?.getString(KEY_LANGUAGE, Locale.getDefault().language)
                ?: throw RuntimeException("not initialized!!")
        }

    var accessToken: String?
        @SuppressLint("ApplySharedPref")
        set(value) {
            sharedPreferences?.edit()?.putString(KEY_TOKEN, value)?.commit()
        }
        get() {
            return (sharedPreferences ?: throw RuntimeException("not initialized!!"))
                .getString(KEY_TOKEN, null)
        }
}
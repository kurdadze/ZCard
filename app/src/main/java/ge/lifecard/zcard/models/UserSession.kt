package ge.lifecard.zcard.models

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class UserSession(
    @Json(name = "access_token")
    var accessToken: String,
    @Json(name = "avatar_path")
    var avatarPath: String?,
    @Json(name = "email")
    var email: String,
    @Json(name = "first_name")
    var firstName: String,
    @Json(name = "id")
    var id: String,
    @Json(name = "is_active")
    var isActive: String,
    @Json(name = "last_name")
    var lastName: String
)
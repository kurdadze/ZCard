package ge.lifecard.zcard.models

import com.squareup.moshi.Json

class UserCards(
    @property:Json(name = "id") var id: String?,
    @property:Json(name = "user_id") var userId: String?,
    @property:Json(name = "brand_logo") var brandLogo: String?,
    @property:Json(name = "card_number") var cardNumber: String?,
    @property:Json(name = "card_holder") var cardHolder: String?,
    @property:Json(name = "more_info") var moreInfo: String?
)
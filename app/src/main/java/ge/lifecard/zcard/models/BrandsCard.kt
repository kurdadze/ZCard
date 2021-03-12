package ge.lifecard.zcard.models

import com.squareup.moshi.Json

class BrandsCard(@property:Json(name = "brand_logo") var brandLogo: String?,
                 @property:Json(name = "brand_name") var brandName: String?,
                 @property:Json(name = "id") var brandId: Int?
)
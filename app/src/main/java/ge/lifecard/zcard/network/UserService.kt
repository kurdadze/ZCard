package ge.lifecard.zcard.network

import ge.lifecard.zcard.models.BrandsCard
import ge.lifecard.zcard.models.UserCards
import ge.lifecard.zcard.models.UserSession
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("auth.php")
    suspend fun login(
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<UserSession>

    @GET("registration.php")
    suspend fun registration(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("firstname") firstname: String,
        @Query("lastname") lastname: String,
        @Query("avatar") avatar: Any?
    ): Response<Any>

    @GET("logout.php")
    suspend fun logout(
        @Query("user_id") id: String
    ): Response<UserSession>

    @GET("getBrands.php")
    suspend fun getBrands(): List<BrandsCard>

    @GET("setUserCard.php")
    suspend fun acceptUserCard(
        @Query("user_id") userId: String,
        @Query("card_number") cardNumber: String,
        @Query("card_holder") cardHolder: String,
        @Query("more_info") moreInfo: String,
        @Query("brand_id") brandId: String
    ): Response<List<UserCards>>


    @GET("getUserCards.php")
    suspend fun getUserCards(
        @Query("user_id") userId: String
    ): List<UserCards>

}
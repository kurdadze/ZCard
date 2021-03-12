package ge.lifecard.zcard.network

import ge.lifecard.zcard.app.DataStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        DataStore.accessToken?.let {
            builder.addHeader("Authorization", "Bearer ${DataStore.accessToken}")
        }
        return chain.proceed(builder.build())
    }

}
package es.christianbegines.wonkamanagement.remote

import es.christianbegines.wonkamanagement.models.OompaLoompa
import es.christianbegines.wonkamanagement.remote.models.OommpaLoompaResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {

    @GET("oompa-loompas")
    suspend fun getOompaLoompas(
        @Query("page") page: Int): OommpaLoompaResponse

    @GET("oompa-loompas/{id}")
    suspend fun getOompaLoompa(@Path("id") id: String): OompaLoompa

    companion object {
        private const val BASE_URL = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/"

        fun create(): AppService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(AppService::class.java)
        }
    }
}
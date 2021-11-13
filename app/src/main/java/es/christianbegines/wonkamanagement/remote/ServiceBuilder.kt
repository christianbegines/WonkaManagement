package es.christianbegines.wonkamanagement.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ServiceBuilder {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val gson = GsonBuilder()
        .setLenient()
        .create()
    private val okHttp: OkHttpClient.Builder =
        OkHttpClient.Builder().addInterceptor(logger)

    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttp.build())

    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }
    companion object{
        private const val URL:String = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/"
    }
}
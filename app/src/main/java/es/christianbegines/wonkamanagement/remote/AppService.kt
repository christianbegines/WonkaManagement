package es.christianbegines.wonkamanagement.remote

import es.christianbegines.wonkamanagement.models.OompaLoompa
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AppService {

    @GET("oompa-loompas")
    fun getOompaLoompas(): Call<List<OompaLoompa>>

    @GET("oompa-loompas/{id}")
    fun getOompaLoompa(@Path("id") id: String): Call<OompaLoompa>
}
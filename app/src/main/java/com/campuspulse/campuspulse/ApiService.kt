package com.campuspulse.campuspulse

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/register")
    suspend fun register(@Body request: AuthRequest): Response<AuthResponse>

    @GET("events")
    suspend fun getEvents(): Response<List<Event>>

    @POST("tickets/rsvp")
    suspend fun rsvpEvent(@Body request: RsvpRequest): Response<SyncResponse>
}
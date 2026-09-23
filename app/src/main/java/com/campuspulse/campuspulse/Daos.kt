package com.campuspulse.campuspulse

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<Event>)

    @Query("DELETE FROM events")
    suspend fun clearEvents()
}

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets")
    suspend fun getAllTickets(): List<Ticket>

    @Query("SELECT * FROM tickets WHERE userId = :userId OR user_id = :userId")
    suspend fun getTicketsForUser(userId: String): List<Ticket>

    @Query("SELECT * FROM tickets WHERE isSynced = 0 OR sync_status = 'Pending'")
    suspend fun getUnsyncedTickets(): List<Ticket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickets(tickets: List<Ticket>)

    @Query("DELETE FROM tickets")
    suspend fun clearTickets()
}
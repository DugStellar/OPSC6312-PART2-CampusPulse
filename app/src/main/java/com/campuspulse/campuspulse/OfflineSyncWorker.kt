package com.campuspulse.campuspulse

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class OfflineSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val db = CampusPulseDatabase.getDatabase(applicationContext)
        val unsyncedTickets = db.ticketDao().getUnsyncedTickets()

        for (ticket in unsyncedTickets) {
            val eventId = ticket.getDisplayEventId()
            val userId = if (!ticket.user_id.isNullOrEmpty()) ticket.user_id else ticket.userId

            try {
                val response = ApiClient.apiService.rsvpEvent(RsvpRequest(eventId, userId))
                if (response.isSuccessful) {
                    val updatedTicket = ticket.copy(
                        sync_status = "Synced",
                        isSynced = true
                    )
                    db.ticketDao().insertTicket(updatedTicket)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return Result.success()
    }
}
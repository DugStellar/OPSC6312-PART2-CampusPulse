package com.campuspulse.campuspulse

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.campuspulse.campuspulse.databinding.ActivityEventDetailsBinding
import kotlinx.coroutines.launch
import java.util.UUID

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getStringExtra("EVENT_ID") ?: ""
        val title = intent.getStringExtra("EVENT_TITLE") ?: "Campus Event"
        val desc = intent.getStringExtra("EVENT_DESC") ?: "No description provided."
        val date = intent.getStringExtra("EVENT_DATE") ?: "TBD"
        val location = intent.getStringExtra("EVENT_LOCATION") ?: "Main Campus"

        // Wire views directly to layout IDs
        binding.tvDetailTitle.text = title
        binding.tvDetailDescription.text = desc
        binding.tvDetailDate.text = date
        binding.tvDetailVenue.text = location
        binding.tvDetailCategory.text = "Campus Life"

        val preferenceManager = PreferenceManager(this)
        val userId = preferenceManager.getUserId() ?: "1"

        binding.btnDetailRsvp.setOnClickListener {
            binding.btnDetailRsvp.isEnabled = false
            performRsvp(eventId, userId)
        }
    }

    private fun performRsvp(eventId: String, userId: String) {
        val db = CampusPulseDatabase.getDatabase(this)

        lifecycleScope.launch {
            val timestamp = System.currentTimeMillis()
            val newTicket = Ticket(
                id = UUID.randomUUID().toString(),
                ticketId = "TKT-$timestamp",
                ticket_id = "TKT-$timestamp",
                eventId = eventId,
                event_id = eventId,
                userId = userId,
                user_id = userId,
                qr_code_data = "QR-$eventId-$userId",
                sync_status = "Synced",
                isSynced = true
            )

            try {
                ApiClient.apiService.rsvpEvent(RsvpRequest(eventId, userId))
                db.ticketDao().insertTicket(newTicket)
                Toast.makeText(this@EventDetailsActivity, "RSVP Successful!", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                val offlineTicket = newTicket.copy(sync_status = "Pending", isSynced = false)
                db.ticketDao().insertTicket(offlineTicket)
                Toast.makeText(this@EventDetailsActivity, "RSVP saved offline.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
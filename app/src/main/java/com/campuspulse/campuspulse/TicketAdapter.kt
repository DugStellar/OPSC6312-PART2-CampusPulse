package com.campuspulse.campuspulse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campuspulse.campuspulse.databinding.ItemTicketBinding

class TicketAdapter(
    private var tickets: List<Ticket>
) : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {

    inner class TicketViewHolder(val binding: ItemTicketBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        val ticket = tickets[position]
        holder.binding.tvTicketId.text = "Ticket: ${ticket.getDisplayTicketId()}"
        holder.binding.tvEventId.text = "Event ID: ${ticket.getDisplayEventId()}"
        holder.binding.tvSyncStatus.text = "Status: ${ticket.getDisplaySyncStatus()}"
    }

    override fun getItemCount(): Int = tickets.size

    fun updateTickets(newTickets: List<Ticket>) {
        tickets = newTickets
        notifyDataSetChanged()
    }
}
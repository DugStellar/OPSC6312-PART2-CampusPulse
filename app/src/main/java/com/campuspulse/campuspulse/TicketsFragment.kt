package com.campuspulse.campuspulse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.campuspulse.campuspulse.databinding.FragmentTicketsBinding
import kotlinx.coroutines.launch

class TicketsFragment : Fragment() {

    private var _binding: FragmentTicketsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TicketAdapter(emptyList())
        binding.rvTickets.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTickets.adapter = adapter

        loadTickets()
    }

    private fun loadTickets() {
        val pref = PreferenceManager(requireContext())
        val userId = pref.getUserId() ?: "1"
        val db = CampusPulseDatabase.getDatabase(requireContext())

        val tvEmptyTickets = binding.root.findViewById<TextView>(
            resources.getIdentifier("tvEmptyTickets", "id", requireContext().packageName)
        ) ?: binding.root.findViewById<TextView>(
            resources.getIdentifier("tvEmptyState", "id", requireContext().packageName)
        )

        lifecycleScope.launch {
            val tickets = db.ticketDao().getTicketsForUser(userId)
            val allTickets = if (tickets.isEmpty()) db.ticketDao().getAllTickets() else tickets

            if (allTickets.isNotEmpty()) {
                adapter.updateTickets(allTickets)
                binding.rvTickets.visibility = View.VISIBLE
                tvEmptyTickets?.visibility = View.GONE
            } else {
                binding.rvTickets.visibility = View.GONE
                tvEmptyTickets?.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
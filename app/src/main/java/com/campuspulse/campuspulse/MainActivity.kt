package com.campuspulse.campuspulse

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.campuspulse.campuspulse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferenceManager = PreferenceManager(this)
        if (!preferenceManager.isLoggedIn()) {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
            return
        }

        // Setup bottom navigation selection listeners
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_events -> {
                    loadFragment(EventsFragment())
                    true
                }
                R.id.navigation_tickets -> {
                    loadFragment(TicketsFragment())
                    true
                }
                R.id.navigation_settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // Post loadFragment so the frame layout is completely attached to the window before transaction
        if (savedInstanceState == null) {
            binding.root.post {
                loadFragment(EventsFragment())
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val containerId = binding.fragmentContainer.id
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commitAllowingStateLoss()
    }
}
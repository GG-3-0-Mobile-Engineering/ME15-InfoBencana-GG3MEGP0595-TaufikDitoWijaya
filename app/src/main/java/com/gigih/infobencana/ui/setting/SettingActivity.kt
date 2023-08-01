package com.gigih.infobencana.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.gigih.infobencana.data.SettingPreferences
import com.gigih.infobencana.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTheme().observe(this) {
            if (it) {
                binding.themeSwitch.text = "Dark Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.themeSwitch.text = "Light Theme"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.themeSwitch.isChecked = it
        }

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTheme(isChecked)
        }
        }
    }

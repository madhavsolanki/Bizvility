package com.acculizein.zvility.screens.add_listing

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivityMonthlyAddListingScreenBinding
import com.acculizein.zvility.utils.BottomSheetFragment

class MonthlyAddListingScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMonthlyAddListingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthlyAddListingScreenBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPremiumListing.setOnClickListener{
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAddListingPlanOne.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAddListingPlanTwo.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAddListingPlanThree.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAddListingPlanFour.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

    }
}
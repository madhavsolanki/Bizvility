package com.acculizein.zvility.screens.add_listing

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivityYearlyAddListingBinding
import com.acculizein.zvility.utils.BottomSheetFragment

class YearlyAddListing : AppCompatActivity() {
    private lateinit var binding:ActivityYearlyAddListingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityYearlyAddListingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)  
            insets
        }

        binding.btnAnnualPremiumListing.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAdListingAnnualBasicPackage.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")

        }

        binding.btnAddListingAnnualWebsitePack.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAdListingBasicWithWebsitePack.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAddListingAnnualDynamicWebsitePack.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

        binding.btnAdListingAnnualPremiumMarketingPack.setOnClickListener {
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "BottomSheet")
        }

    }
}
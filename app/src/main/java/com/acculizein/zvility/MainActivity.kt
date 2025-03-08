package com.acculizein.zvility

import PersonAdapter
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.acculizein.zvility.adapters.CategoryAdapter
import com.acculizein.zvility.databinding.ActivityMainBinding
import com.acculizein.zvility.models.Category
import com.acculizein.zvility.models.Person
import com.acculizein.zvility.screens.add_listing.MonthlyAddListingScreen
import com.acculizein.zvility.screens.add_listing.YearlyAddListing
import com.acculizein.zvility.screens.profile.ProfileScreen
import com.acculizein.zvility.utils.GridSpacingItemDecoration
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProfileScreen::class.java))
        }


        // Image Slider
        setupImageSlider()

        // Category Recycler View
        categoriesRecyclerView()

        // Exclusive Recycler View
        setupExclusiveRecyclerView()

        // Apply Gradient to Text
        applyGradientToCategoryText()
        applyGradientToExclusiveOfferText()

        // Show Add listing Popup menu
        binding.btnAdListing.setOnClickListener{
            showPopupMenu(it)
        }


    }



    private fun setupImageSlider() {
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
            add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
            add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun categoriesRecyclerView(){
        val categories = listOf(
            Category(R.drawable.automotive, "Automotive"),
            Category(R.drawable.art_entertainment, "Art & Entertainment"),
            Category(R.drawable.beauty_spa, "Beauty & Spa"),
            Category(R.drawable.healthcare, "Healthcare"),
            Category(R.drawable.hotel, "Hotels"),
            Category(R.drawable.restaurant, "Restaurant"),
            Category(R.drawable.services, "Services"),
            Category(R.drawable.shopping, "Shopping")
        )

        binding.rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategories.adapter = CategoryAdapter(categories)
    }

    private fun setupExclusiveRecyclerView() {
        val persons = listOf(
            Person(R.drawable.doctor_one, "Dr. Saumyata Neeraj", "Health & Medical", "Agra","Open"),
            Person(R.drawable.doctor_two, "Dr. John Doe", "Dentist", "Delhi","Closed"),
            Person(R.drawable.doctor_three, "Dr. Jane Smith", "Cardiologist", "Mumbai", "Open"),
            Person(R.drawable.doctor_four, "Dr. Rahul Verma", "Neurologist", "Bangalore",  "Closed"),
            Person(R.drawable.doctor_one, "Dr. Saumyata Neeraj", "Health & Medical", "Agra","Open"),
        )

        val layoutManager = GridLayoutManager(this, 2) // 2 columns
        binding.rvExclusive.layoutManager = layoutManager
        binding.rvExclusive.adapter = PersonAdapter(persons)

        // Add spacing between the grid items
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_spacing)
        binding.rvExclusive.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels))
    }

    private fun applyGradientToCategoryText() {
        val paint = binding.tvCategories.paint
        val width = paint.measureText(binding.tvCategories.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvCategories.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvCategories.paint.shader = textShader
    }

    private fun applyGradientToExclusiveOfferText() {
        val paint = binding.tvExclusivePopular.paint
        val width = paint.measureText(binding.tvExclusivePopular.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvExclusivePopular.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvExclusivePopular.paint.shader = textShader
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.add_listing_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_monthly_plans -> {
                    startActivity(Intent(this, MonthlyAddListingScreen::class.java))
                    true
                }
                R.id.action_annual_plans -> {
                    startActivity(Intent(this, YearlyAddListing::class.java))
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

}
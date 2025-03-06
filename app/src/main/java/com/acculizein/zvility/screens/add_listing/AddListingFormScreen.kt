package com.acculizein.zvility.screens.add_listing

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acculizein.zvility.R
import com.acculizein.zvility.databinding.ActivityAddListingFormScreenBinding

class AddListingFormScreen : AppCompatActivity() {

    private lateinit var binding: ActivityAddListingFormScreenBinding


    private val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val businessHoursMap = mutableMapOf<String, View>()  // Stores Day -> View

    private val socialMediaList = listOf("Facebook", "Instagram", "Twitter", "LinkedIn", "YouTube", "TikTok")
    private val socialMediaEntries = mutableListOf<View>()
    private val faqEntries = mutableListOf<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingFormScreenBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Apply Gradient to Form Heading
        applyGradientToCategoryText()

        // Checkbox logic to show/hide tagline input
        binding.cbTagline.setOnCheckedChangeListener { _, isChecked ->
            binding.taglineLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        setupCategorySpinner()
        setupSwitchListeners()
        setupGenderSelection()
        setupPaymentSelection()

        setupPriceRangeSpinner()

        setupDefaultBusinessHours()
        setupAddButton()

        setupSocialMediaSection()
        setupFAQSection()
    }

    // Function to setup Category Spinner
    private fun setupCategorySpinner() {
        val categories = arrayOf("Shopping", "Automotive", "Food", "Health", "Education", "Entertainment")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerCategory.adapter = adapter
    }

    // Function to handle Switch logic (Delivery & Takeout)
    private fun setupSwitchListeners() {
        binding.switchDelivery.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Delivery: $isChecked", Toast.LENGTH_SHORT).show()
        }

        binding.switchTakeOut.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Take Out: $isChecked", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to handle Gender selection
    private fun setupGenderSelection() {
        binding.radioGroupGender.setOnCheckedChangeListener { _, checkedId ->
            val selectedGender = when (checkedId) {
                R.id.radioMale -> "Male"
                R.id.radioFemale -> "Female"
                else -> "None"
            }
            Toast.makeText(this, "Selected Gender: $selectedGender", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to handle Payment Method selection
    private fun setupPaymentSelection() {
        binding.checkCreditCard.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Toast.makeText(this, "Credit Card Selected", Toast.LENGTH_SHORT).show()
        }
        binding.checkBankTransfer.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Toast.makeText(this, "Bank Transfer Selected", Toast.LENGTH_SHORT).show()
        }
        binding.checkMobilePayments.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) Toast.makeText(this, "Mobile Payments Selected", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to setup Spinner
    private fun setupPriceRangeSpinner() {
        val priceOptions = resources.getStringArray(R.array.price_range_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priceOptions)
        binding.spinnerPriceRange.adapter = adapter
    }

    // Step 1: Load Default Business Hours (Mon-Fri)
    private fun setupDefaultBusinessHours() {
        for (i in 0 until 5) {
            addBusinessHour(daysOfWeek[i])
        }
    }

    // Step 2: Handle "Add" Button Click
    private fun setupAddButton() {
        binding.btnAddBusinessHour.setOnClickListener {
            if (businessHoursMap.size >= 7) {
                Toast.makeText(this, "All 7 days are already added!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val remainingDay = daysOfWeek.firstOrNull { day -> !businessHoursMap.containsKey(day) }
            if (remainingDay != null) {
                addBusinessHour(remainingDay)
            }
        }
    }

    // Step 3: Add Business Hour Row in Correct Order
    private fun addBusinessHour(day: String) {
        val inflater = LayoutInflater.from(this)
        val rowView = inflater.inflate(R.layout.item_business_hour, binding.layoutBusinessHours, false)

        val txtDay = rowView.findViewById<TextView>(R.id.txtDay)
        val btnRemove = rowView.findViewById<ImageButton>(R.id.btnRemove)
        txtDay.text = day

        btnRemove.setOnClickListener {
            removeBusinessHour(day, rowView)
        }

        businessHoursMap[day] = rowView
        insertRowInCorrectPosition(day, rowView)
    }

    // Step 4: Insert Row in Sequential Order
    private fun insertRowInCorrectPosition(day: String, rowView: View) {
        val index = daysOfWeek.indexOf(day)

        for (i in 0 until binding.layoutBusinessHours.childCount) {
            val childView = binding.layoutBusinessHours.getChildAt(i)
            val existingDay = (childView.findViewById<TextView>(R.id.txtDay)).text.toString()

            if (daysOfWeek.indexOf(existingDay) > index) {
                binding.layoutBusinessHours.addView(rowView, i)
                return
            }
        }
        binding.layoutBusinessHours.addView(rowView)  // If no larger index is found, add at the end
    }

    // Step 5: Remove Business Hour Row
    private fun removeBusinessHour(day: String, rowView: View) {
        binding.layoutBusinessHours.removeView(rowView)
        businessHoursMap.remove(day)
    }


    // =========================  SOCIAL MEDIA SECTION =========================
    private fun setupSocialMediaSection() {
        binding.btnAddSocialMedia.setOnClickListener {
            addSocialMediaEntry()
        }
    }

    private fun addSocialMediaEntry() {
        val inflater = LayoutInflater.from(this)
        val rowView = inflater.inflate(R.layout.item_social_media, binding.layoutSocialMedia, false)

        val spinner = rowView.findViewById<Spinner>(R.id.spinnerSocialMedia)
        val edtUrl = rowView.findViewById<EditText>(R.id.etSocialMediaUrl)
        val btnRemove = rowView.findViewById<ImageButton>(R.id.btnRemoveSocialMedia)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, socialMediaList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        btnRemove.setOnClickListener {
            binding.layoutSocialMedia.removeView(rowView)
            socialMediaEntries.remove(rowView)
        }

        socialMediaEntries.add(rowView)
        binding.layoutSocialMedia.addView(rowView)
    }

    // =========================  FAQ SECTION =========================
    private fun setupFAQSection() {
        binding.btnAddFAQ.setOnClickListener {
            addFAQEntry()
        }
    }

    private fun addFAQEntry() {
        val inflater = LayoutInflater.from(this)
        val rowView = inflater.inflate(R.layout.item_faq, binding.layoutFAQ, false)

        val edtQuestion = rowView.findViewById<EditText>(R.id.etFAQQuestion)
        val edtAnswer = rowView.findViewById<EditText>(R.id.etFAQAnswer)
        val btnRemove = rowView.findViewById<ImageButton>(R.id.btnRemoveFAQ)

        btnRemove.setOnClickListener {
            binding.layoutFAQ.removeView(rowView)
            faqEntries.remove(rowView)
        }

        faqEntries.add(rowView)
        binding.layoutFAQ.addView(rowView)
    }


    private fun applyGradientToCategoryText() {
        val paint = binding.tvFormHeading.paint
        val width = paint.measureText(binding.tvFormHeading.text.toString())

        val textShader = LinearGradient(
            0f, 0f, width, binding.tvFormHeading.textSize,
            intArrayOf(
                0xFFDA4453.toInt(), // #DA4453 (Start Color)
                0xFF89216B.toInt()  // #89216B (End Color)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        binding.tvFormHeading.paint.shader = textShader
    }




}
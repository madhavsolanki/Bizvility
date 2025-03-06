package com.acculizein.zvility.utils

import android.os.Bundle
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.acculizein.zvility.databinding.BottomSheetBinding
import com.acculizein.zvility.screens.add_listing.AddListingFormScreen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {


    private var _binding: BottomSheetBinding? = null
    private val binding get() = _binding!!

    private val categoryList = arrayListOf(
        "Automotive",
        "Art and Entertainment",
        "Beauty & Spa",
        "Healthcare",
        "Hotels",
        "Restaurant",
        "Services",
        "Shopping"
    )
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()

        binding.btnSubmit.setOnClickListener {
            val fullName = binding.etFullName.text.toString()
            val mobileNumber = binding.etMobileNumber.text.toString()
            val selectedCategory = binding.spinnerCategory.selectedItem.toString()

            if (fullName.isEmpty() || mobileNumber.isEmpty() || selectedCategory.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Submitted!", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), AddListingFormScreen::class.java)
                startActivity(intent)

                dismiss()
            }
        }
    }

    private fun setupCategorySpinner() {
        adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            categoryList
        )
        binding.spinnerCategory.adapter = adapter

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCategory = categoryList[position]
                    if (selectedCategory == "Add New Category") {
                        showAddCategoryDialog()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun showAddCategoryDialog() {
        val editText = EditText(requireContext())
        editText.hint = "Enter new category"

        AlertDialog.Builder(requireContext())
            .setTitle("Add Category")
            .setView(editText)
            .setPositiveButton("Add") { _, _ ->
                val newCategory = editText.text.toString().trim()
                if (newCategory.isNotEmpty()) {
                    categoryList.add(
                        categoryList.size - 1,
                        newCategory
                    ) // Add before "Add New Category"
                    adapter.notifyDataSetChanged()
                    binding.spinnerCategory.setSelection(categoryList.indexOf(newCategory)) // Select the new category
                } else {
                    Toast.makeText(requireContext(), "Category cannot be empty", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.musicshop

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.musicshop.databinding.ActivityMusicShopBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMusicShopBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ViewBinding is not initialized")

    private var quantity = 0
    private var selectedInstrument: String = ""

    private val instrumentPrices: Map<String, Int> = mapOf(
        "Guitar" to 200,
        "Piano" to 1500,
        "Drums" to 300,
        "Violin" to 500,
        "Flute" to 250,
        "Saxophone" to 700
    )

    private val instrumentImages: Map<String, Int> = mapOf(
        "Guitar" to R.drawable.guitar,
        "Piano" to R.drawable.piano,
        "Drums" to R.drawable.drums,
        "Violin" to R.drawable.violin,
        "Flute" to R.drawable.flute,
        "Saxophone" to R.drawable.saxophone
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMusicShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        binding.btnPlus.setOnClickListener {
            incrementQuantity()
        }

        binding.btnMinus.setOnClickListener {
            decrementQuantity()
        }
    }

    private fun setupSpinner() {
        val instruments = instrumentPrices.keys.toList()

        binding.abSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            instruments
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.abSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedInstrument = parent.getItemAtPosition(position) as String
                updatePrice()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updatePrice() {
        val pricePerUnit = instrumentPrices[selectedInstrument] ?: 0
        val totalPrice = pricePerUnit * quantity
        binding.tvPriceTitle.text = totalPrice.toString()

        val imageResId = instrumentImages[selectedInstrument]
        if (imageResId != null) {
            binding.ivMusicInstrumentPicture.setImageResource(imageResId)
        }
    }

    private fun decrementQuantity() {
        if (quantity > 0) {
            quantity--
            binding.tvQuantityTitle1.text = quantity.toString()
            updatePrice()
        }
    }

    private fun incrementQuantity() {
        quantity++
        binding.tvQuantityTitle1.text = quantity.toString()
        updatePrice()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
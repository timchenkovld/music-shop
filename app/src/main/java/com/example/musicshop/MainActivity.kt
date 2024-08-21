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

    private val instrumentPrices: Map<String, String> = mapOf(
        "Guitar" to "200$",
        "Piano" to "1500$",
        "Drums" to "300$",
        "Violin" to "500$",
        "Flute" to "250$",
        "Saxophone" to "700$"
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

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            instruments
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.abSpinner.adapter = adapter

        binding.abSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedInstrument = parent.getItemAtPosition(position) as String
                updatePrice(selectedInstrument)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updatePrice(instrument: String) {
        val price = instrumentPrices[instrument] ?: "0"
        binding.tvPriceTitle.text = price
    }

//    private fun setupSpinner() {
//        val instruments = listOf("Guitar", "Piano", "Drums", "Violin", "Flute", "Saxophone")
//
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, instruments)
//            .apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
//        binding.abSpinner.adapter = adapter
//    }


    private fun decrementQuantity() {
        if (quantity > 0) {
            quantity--
            binding.tvQuantityTitle1.text = quantity.toString()
        }
    }

    private fun incrementQuantity() {
        quantity++
        binding.tvQuantityTitle1.text = quantity.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
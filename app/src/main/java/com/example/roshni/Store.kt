package com.example.roshni

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Store : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView


    private val selectedProducts = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)
        val view_cart= findViewById<View>(R.id.view_cart_button) as Button

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val productList = generateSampleProducts()

        val adapter = ProductAdapter(this, productList,
            onAddToCartClick = { product -> addToCart(product) },
            onBuyNowClick = { product -> (product) })


        view_cart.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }



        recyclerView.adapter = adapter
    }

    private fun generateSampleProducts(): List<Product> {
        return listOf(
            Product(1, "Automated System", 20000.00, R.drawable.ic_automate),
            Product(2, "Street Bulbs", 1000.00, R.drawable.ic_halogen),
            Product(3, "Analytics for System", 1000.00, R.drawable.ic_analytics)
        )
    }

    private fun addToCart(product: Product) {
        selectedProducts.add(product)
        Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
    }


}
package com.example.roshni

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartItemsContainer = findViewById<LinearLayout>(R.id.cart_items_container)
        val totalTextView = findViewById<TextView>(R.id.total_text_view)

        val selectedProducts = (application as MyApp).selectedProducts

        var totalPrice = 0.0

        for (product in selectedProducts) {
            val cartItemView = layoutInflater.inflate(R.layout.cart_item, null)

            val productName = cartItemView.findViewById<TextView>(R.id.cart_product_name)
            val productPrice = cartItemView.findViewById<TextView>(R.id.cart_product_price)

            productName.text = product.name
            productPrice.text = "Price: $${product.price}"

            cartItemsContainer.addView(cartItemView)

            totalPrice += product.price
        }

        totalTextView.text = "Total: $${totalPrice}"

        val checkoutButton = findViewById<Button>(R.id.checkout_button)
        checkoutButton.setOnClickListener {
            // Implement the checkout process here
        }
    }
}

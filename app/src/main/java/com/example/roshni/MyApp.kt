package com.example.roshni

import android.app.Application

class MyApp : Application() {
    var selectedProducts: MutableList<Product> = mutableListOf()
}


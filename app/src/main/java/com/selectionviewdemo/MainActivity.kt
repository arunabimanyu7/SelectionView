package com.selectionviewdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arun.selectionview.SelectionView
import com.google.android.material.button.MaterialButton
import kotlin.math.log

class MainActivity : AppCompatActivity(), SelectionView.ItemSelectionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectionView = findViewById<SelectionView>(R.id.selectionview)
        val cardView = findViewById<MaterialButton>(R.id.card)
        val cashView = findViewById<MaterialButton>(R.id.cash)
        selectionView.setSelectedView(cashView)
        selectionView.setOnItemSelectionListener(this)
    }

    override fun onItemSelected(tag: Any?, view: View, id: Int, isSelected: Boolean) {
        Log.d("selectionView","is selected $isSelected id $id")
    }

    override fun onItemReSelected(tag: Any?, view: View, id: Int, isSelected: Boolean) {
        Log.d("selectionView","is selected $isSelected id $id")
    }

}

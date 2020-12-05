package com.selectionviewdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arun.selectionview.ViewSelectionListener
import com.arun.selectionview.SelectionView

class MainActivity : AppCompatActivity(), ViewSelectionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectionView = findViewById<SelectionView>(R.id.selectionview)
        selectionView.setOnItemSelectionListener(this)

    }

    override fun onItemSelected(tag: Any?, view: View, id: Int, isSelected: Boolean) {
        Log.d("selectionView", "is Reselected $isSelected id $id tag $tag")
    }

    override fun onItemReSelected(tag: Any?, view: View, id: Int, isSelected: Boolean) {
        Log.d("selectionView", "is selected $isSelected id $id tag $tag")
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}

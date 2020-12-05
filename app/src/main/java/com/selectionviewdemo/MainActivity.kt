package com.selectionviewdemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arun.selectionview.SelectionView
import com.arun.selectionview.ViewSingleSelectionListener

class MainActivity : AppCompatActivity(), ViewSingleSelectionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectionView = findViewById<SelectionView>(R.id.selectionview)
        selectionView.addAddSelectionListner(this)

    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onItemSelected(
        tag: Any?,
        view: View,
        id: Int,
        isSelected: Boolean,
        selectedViews: HashSet<View>
    ) {
        Log.d("selectionView", "is selected $isSelected id $id tag $tag")
    }

    override fun onItemReSelected(
        tag: Any?,
        view: View,
        id: Int,
        isSelected: Boolean,
        selectedViews: HashSet<View>
    ) {
        Log.d("selectionView", "is Reselected $isSelected id $id tag $tag")
    }

    override fun onSelectedViewListChanged(selectedViews: List<View>, selectedData: List<Any>) {
        Log.d(TAG, "onSelectedViewListChanged: selectedData $selectedData")
    }


}

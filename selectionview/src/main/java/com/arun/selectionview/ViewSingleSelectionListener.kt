package com.arun.selectionview

import android.view.View

/*
@Author Arunachalam K
@CreatedOn 1/31/2020
*/


interface ViewSingleSelectionListener {

    fun onItemSelected(
        tag: Any?,
        view: View,
        id: Int,
        isSelected: Boolean,
        selectedViews: HashSet<View>
    )

    fun onItemReSelected(
        tag: Any?, view: View, id: Int, isSelected: Boolean,
        selectedViews: HashSet<View>
    )

    fun onSelectedViewListChanged(selectedViews: List<View>, selectedData: List<Any>)

}

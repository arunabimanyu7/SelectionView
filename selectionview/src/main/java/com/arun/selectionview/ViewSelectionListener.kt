package com.arun.selectionview

import android.view.View

/*
@Author Arunachalam K
@CreatedOn 1/31/2020
*/


interface ViewSelectionListener {

    fun onItemSelected(tag: Any?, view: View, id: Int, isSelected: Boolean)

    fun onItemReSelected(tag: Any?, view: View, id: Int, isSelected: Boolean)

}

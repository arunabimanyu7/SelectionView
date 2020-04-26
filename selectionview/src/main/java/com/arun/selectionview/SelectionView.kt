package com.arun.selectionview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.annotation.IdRes


/*
@Author cr7
@CreatedOn 1/31/2020
*/

class SelectionView : LinearLayout,
    ViewGroup.OnHierarchyChangeListener, View.OnClickListener {

    private var selectedViewId: Int = View.NO_ID

    private val idList = ArrayList<Int>()
    private var itemSelectionListener: ItemSelectionListener? = null
    private var onBind = false
    private var selectionMode: Int = SelectionMode.SINGLE.ordinal
    override fun onFinishInflate() {
        super.onFinishInflate()
        if (selectedViewId != -1) {
            onBind = true
            setSelectedStateForView(selectedViewId, true)
            onBind = false
            setCheckedId(selectedViewId)
        }
    }

    constructor(context: Context?) : super(context) {
        orientation = VERTICAL
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val typeAttributeSet =
            context.theme.obtainStyledAttributes(attrs, R.styleable.SelectionView, 0, 0)

        if (typeAttributeSet.hasValue(R.styleable.SelectionView_selectedId)) {
            val value =
                typeAttributeSet.getResourceId(R.styleable.SelectionView_selectedId, View.NO_ID)
            selectedViewId = value
        }
        val index = typeAttributeSet.getInt(R.styleable.SelectionView_orientation, VERTICAL)
        selectionMode =
            typeAttributeSet.getInt(
                R.styleable.SelectionView_selectionMode,
                SelectionMode.SINGLE.ordinal
            )
        orientation = index
        typeAttributeSet.recycle()
        setOnHierarchyChangeListener(this)
    }

    override fun onChildViewRemoved(parent: View?, child: View?) {
        child?.setOnClickListener(null)
        child?.id?.let { idList.remove(it) }
    }

    override fun onChildViewAdded(parent: View?, child: View?) {
        var id = child!!.id
        if (id == View.NO_ID) {
            id = View.generateViewId()
            child.id = id
        }
        idList.add(child.id)
        child.setOnClickListener(this)
    }

    override fun generateDefaultLayoutParams(): LayoutParams? {
        return LayoutParams(
            RadioGroup.LayoutParams.WRAP_CONTENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setSelectedStateForView(viewId: Int, state: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null) {
            checkedView.isActivated = state
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            val changed = id != selectedViewId
            if (!changed) {
                itemSelectionListener?.onItemReSelected(v.tag, v, v.id, true)
            } else {
                tag = v.tag
                val isSelected = selectedViewId == v.id
                if (onBind) {
                    return
                }
                onBind = true
                if (selectedViewId != -1) {
                    setSelectedStateForView(selectedViewId, false)
                }
                onBind = false
                val id: Int? = v?.id
                id?.let { setCheckedId(it) }
                itemSelectionListener?.onItemSelected(v.tag, v, v.id, true)
            }

        }

    }

    private fun unCheck() {
        findViewById<View>(selectedViewId).isActivated = false
    }

    private fun setCheckedId(@IdRes id: Int) {
        val changed = id != selectedViewId
        selectedViewId = id
        if (changed) {
            unCheckAllId()
            setSelectedStateForView(selectedViewId, true)
        } else {
            setSelectedStateForView(selectedViewId, true)
        }
    }

    private fun unCheckAllId() {
        for (i in idList) {
            findViewById<View>(i).isActivated = false
        }
    }

    fun setOnItemSelectionListener(tagChangeListener: ItemSelectionListener) {
        this.itemSelectionListener = tagChangeListener
    }

    fun setSelectedView(selectedView: View) {
        onClick(selectedView)
    }


    interface ItemSelectionListener {
        fun onItemSelected(tag: Any?, view: View, id: Int, isSelected: Boolean)
        fun onItemReSelected(tag: Any?, view: View, id: Int, isSelected: Boolean)
    }

    enum class SelectionMode {
        SINGLE, SINGLE_OR_EMPTY
    }
}

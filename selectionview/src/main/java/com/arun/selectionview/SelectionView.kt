package com.arun.selectionview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.IdRes
import kotlin.math.roundToInt


/*
@Author Arunachalam K
@CreatedOn 1/31/2020
*/

class SelectionView : LinearLayout,
    ViewGroup.OnHierarchyChangeListener {

    private var selectedViewId: Int = -1

    private val idList = ArrayList<Int>()

    private var viewSingleSelectionListener: ViewSingleSelectionListener? = null

    private var onBind = false

    private var selectionMode: Int = SelectionMode.SINGLE.ordinal

    private var defaultTag: Any? = null

    private var selectedViews = HashSet<View>()

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

        if (typeAttributeSet.hasValue(R.styleable.SelectionView_defaultTag)) {
            defaultTag =
                typeAttributeSet.getString(R.styleable.SelectionView_defaultTag)

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
        child?.id?.let { idList.remove(it) }
    }

    override fun onChildViewAdded(parent: View?, child: View?) {
        var id = child!!.id
        if (id == View.NO_ID) {
            id = View.generateViewId()
            child.id = id
            if (child.tag == null) {
                if (defaultTag != null)
                    child.tag = defaultTag
            }
        }
        idList.add(child.id)
    }

    override fun generateDefaultLayoutParams(): LayoutParams? {
        return LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setSelectedStateForView(viewId: Int, state: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null) {
            checkedView.isActivated = state
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            val x = event.x.roundToInt()
            val y = event.y.roundToInt()
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (x > child.left && x < child.right && y > child.top && y < child.bottom) {
                    //touch is within this child
                    if (event.action == MotionEvent.ACTION_UP) {
                        onChildViewIsClicked(child)
                        return true
                    }
                }
            }
        }

        return true
    }

    private fun onChildViewIsClicked(child: View?) {

        if (child != null) {

            val isViewSelected = when (selectionMode) {
                SelectionMode.MULTI.ordinal -> {
                    selectedViews.contains(child)
                }
                SelectionMode.SINGLE.ordinal -> {
                    child.id == selectedViewId
                }
                else -> throw  IllegalStateException("Invalid Selection Mode")
            }

            if (isViewSelected) {
                if (selectionMode == SelectionMode.MULTI.ordinal) {
                    unCheck(child)
                }
                viewSingleSelectionListener?.onItemReSelected(
                    child.tag,
                    child,
                    child.id,
                    true,
                    selectedViews = selectedViews
                )
                viewSingleSelectionListener?.onSelectedViewListChanged(
                    selectedViews.toList(),
                    selectedViews.map {
                        it.tag
                    })
            } else {
                tag = child.tag
                if (onBind) {
                    return
                }
                onBind = true
                if (selectedViewId != -1 && selectionMode == SelectionMode.SINGLE.ordinal) {
                    setSelectedStateForView(selectedViewId, false)
                }
                onBind = false
                val id: Int? = child?.id
                id?.let { setCheckedId(it) }
                viewSingleSelectionListener?.onItemSelected(
                    child.tag,
                    child,
                    child.id,
                    true,
                    selectedViews
                )
                viewSingleSelectionListener?.onSelectedViewListChanged(
                    selectedViews.toList(),
                    selectedViews.map {
                        it.tag
                    })
            }


        }


    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    private fun unCheck(child: View) {
        selectedViews.remove(child)
        child.isActivated = false
    }

    private fun setCheckedId(@IdRes id: Int) {
        val changed = id != selectedViewId
        val selectedView = findViewById<View>(id)
        selectedViewId = id
        selectedViews.add(selectedView)
        if (selectionMode == SelectionMode.SINGLE.ordinal) {
            if (changed) {
                unCheckAllId()
                setSelectedStateForView(selectedViewId, true)
            } else {
                setSelectedStateForView(selectedViewId, true)
            }
        } else {
            setSelectedStateForView(selectedViewId, true)
        }

    }

    private fun unCheckAllId() {
        for (i in idList) {
            findViewById<View>(i).isActivated = false
        }
    }

    fun addAddSelectionListner(singleSelectionListener: ViewSingleSelectionListener) {
        this.viewSingleSelectionListener = singleSelectionListener
    }

    fun setSelectedView(selectedView: View) {
        onChildViewIsClicked(selectedView)
    }


}


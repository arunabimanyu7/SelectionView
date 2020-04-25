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

    private var mCheckedId: Int = 1

    @get:IdRes
    var checkedRadioButtonId = -1
        private set
    private val idList = ArrayList<Int>()
    private var tagChangeListener: TagChangeListener? = null
    private var mInitialCheckedId = View.NO_ID

    private var mProtectFromCheckedChange = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        // checks the appropriate radio button as requested in the XML file
        if (mCheckedId != -1) {
            mProtectFromCheckedChange = true
            setCheckedStateForView(mCheckedId, true)
            mProtectFromCheckedChange = false

            setCheckedId(mCheckedId)
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
            mCheckedId = value
            mInitialCheckedId = value
        }

        val index = typeAttributeSet.getInt(R.styleable.SelectionView_orientation, VERTICAL)
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

    private fun setCheckedStateForView(viewId: Int, state: Boolean) {
        val checkedView = findViewById<View>(viewId)
        if (checkedView != null) {
            checkedView.isActivated = state
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            tag = v.tag
            tagChangeListener?.onTagChanged(v.tag)
        }
        if (mProtectFromCheckedChange) {
            return
        }
        mProtectFromCheckedChange = true
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false)
        }
        mProtectFromCheckedChange = false
        val id: Int? = v?.id
        id?.let { setCheckedId(it) }
    }

    fun unCheck() {
        findViewById<View>(mCheckedId).isActivated = false
    }

    private fun setCheckedId(@IdRes id: Int) {
        val changed = id != mCheckedId
        mCheckedId = id
        if (changed) {
            unCheckAllId()
            setCheckedStateForView(mCheckedId, true)
        } else {
            setCheckedStateForView(mCheckedId, true)
        }
    }

    private fun unCheckAllId() {
        for (i in idList) {
            findViewById<View>(i).isActivated = false
        }
    }

    fun setTagChangeLisener(tagChangeListener: TagChangeListener) {
        this.tagChangeListener = tagChangeListener
    }


    interface TagChangeListener {
        fun onTagChanged(value: Any)
    }
}

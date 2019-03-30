package com.nice.baselibrary.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author JPlus
 * @date 2019/1/16.
 */
abstract class NiceFragment :Fragment() {
    private var mNiceActivity: NiceActivity?=null

    abstract fun getInitView(view: View?, bundle:Bundle?)

    abstract fun getLayoutId():Int

    fun getHolderActivity(): NiceActivity?{
        return mNiceActivity
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mNiceActivity = context as NiceActivity
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        getInitView(view, savedInstanceState)
        return view
    }
}
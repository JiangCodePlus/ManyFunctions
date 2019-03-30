package com.nice.baselibrary.base

import android.app.Application
import android.content.Context

/**
 * @author JPlus
 * @date 2019/2/13.
 */
abstract class NiceApplication :Application() {

    companion object {
        var mContext:Context?=null
        /**
         * 获取全局context
         * @return
         */
        fun getContext():Context?{
            return mContext
        }
    }
    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }



}
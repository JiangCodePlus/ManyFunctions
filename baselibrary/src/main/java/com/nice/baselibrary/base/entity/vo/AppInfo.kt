package com.nice.baselibrary.base.entity.vo


import android.graphics.drawable.Drawable

/**
 * @author JPlus
 * @date 2018/9/27.
 */
data class AppInfo(
      val appName:String,
      val packageName:String?=null,
      val versionCode:Int?=null,
      val versionName:String?=null,
      val icon:Drawable,
      val inTime:String,
      val permission:MutableList<String>,
      val signMd5:String
)
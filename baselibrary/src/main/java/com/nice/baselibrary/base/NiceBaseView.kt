package com.nice.baselibrary.base

/**
 * @author JPlus
 * @date 2019/2/12.
 */
interface NiceBaseView<in T> {
    fun setPresenter(presenter:T)
}
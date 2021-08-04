package com.yang.ibl.scrollrecyclerview.library

internal interface OnParentFlingListener {
    fun onScrollTop(isTop: Boolean,top:Int)
    fun onFling(childSpeed: Int)
}
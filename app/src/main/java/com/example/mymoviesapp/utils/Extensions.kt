package com.example.mymoviesapp.utils

import android.content.Context
import android.content.res.Resources.getSystem
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import com.example.mymoviesapp.MainApp
import com.example.mymoviesapp.di.AppComponent
import java.util.concurrent.TimeUnit

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> applicationContext.appComponent
    }

val Int.pxToDp: Int get() = (this / getSystem().displayMetrics.density).toInt()

fun String.setTypeface(typeface: Int): SpannableString {
    val start = this.split(" ").first().length
    val span = SpannableString(this)
    span.setSpan(StyleSpan(typeface), 0, start, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    return span
}

val Long.toHours: Int get() = TimeUnit.MILLISECONDS.toHours(this).toInt()

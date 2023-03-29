package com.d204.rumeet.bindindAdapters

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.d204.rumeet.ui.components.FilledEditText
import okhttp3.internal.notify
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@BindingAdapter("authentication_visibility")
fun TextView.bindAuthenticationVisibility(state : Boolean){
    if(state) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("chatting_date")
fun TextView.bindChattingDate(date : Long){
    //오늘 = 시간
    val timeSimpleDateFormat = SimpleDateFormat("mm:ss", Locale.KOREA)
    // 사흘부터
    val dateSimpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

    val today = LocalDate.now(ZoneId.systemDefault())
    val yesterday = today.minusDays(1)

    val time = Instant.ofEpochMilli(date)
    val dateToCheck = time.atZone(ZoneId.systemDefault()).toLocalDate()

    val isToday = dateToCheck == today
    val isYesterday = dateToCheck == yesterday

    text = when {
        isToday -> timeSimpleDateFormat.format(date)
        isYesterday -> "어제"
        else -> dateSimpleDateFormat.format(date)
    }
}

@BindingAdapter("time")
fun TextView.bindTime(date : Long){
    val timeSimpleDateFormat = SimpleDateFormat("mm:ss", Locale.KOREA)
    text = timeSimpleDateFormat.format(date)
}

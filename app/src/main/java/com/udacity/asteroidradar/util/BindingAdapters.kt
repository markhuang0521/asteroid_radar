package com.udacity.asteroidradar.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.NetWorkPictureOfDay
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.main.ApiStatus
import com.udacity.asteroidradar.main.MainRecyclerAdapter

@BindingAdapter("apiStatus")

fun bindstatus(imgView: ImageView, status: ApiStatus?) {

    status?.let {
        when (status) {
            ApiStatus.ERROR -> {
                imgView.visibility = View.VISIBLE
                imgView.setImageResource(R.drawable.ic_connection_error)
            }
            ApiStatus.LOADING -> {
                imgView.visibility = View.VISIBLE
                imgView.setImageResource(R.drawable.loading_animation)

            }
            ApiStatus.DONE -> {
                imgView.visibility = View.GONE
            }
        }
    }

}

//@BindingAdapter("listData")
//fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
//    val adapter = recyclerView.adapter as MainRecyclerAdapter
//    adapter.submitList(data)
//}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("imageUrl")
fun bindMainImageOfDay(imageView: ImageView, PicOfDay: PictureOfDay?) {
    PicOfDay?.let {
        Picasso.with(imageView.context)
            .load(PicOfDay.url)
            .placeholder(R.drawable.ic_connection_error)
            .into(imageView)
    }


}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

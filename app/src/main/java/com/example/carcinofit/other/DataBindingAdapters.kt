package com.example.carcinofit.other

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.carcinofit.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DataBindingAdapters {
    companion object {
        @BindingAdapter("android:loadImageWithGlide")
        @JvmStatic
        fun loadImage(imageView: ImageView, image: String) {
            val context = imageView.context
            val ref = Firebase.storage.reference.child("headers").child(image)
            Glide.with(context).load(ref).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView)
        }

        @BindingAdapter("android:setBitmap")
        @JvmStatic
        fun setBitmap(imageView: ImageView, bitmap: Bitmap) {
            imageView.setImageBitmap(bitmap)
        }

        @BindingAdapter("android:setIntegerText")
        @JvmStatic
        fun setText(textView: TextView, text: Int) {
            if (text == 0)
                return
            var t = text.toString()
            if (textView.id == R.id.duration) {
                t += " minutes"
            }
            textView.text = t
        }

        @BindingAdapter("android:setButtonBackgroundColor")
        @JvmStatic
        fun setColor(button: Button, color: String) {
            button.setBackgroundColor(Color.parseColor(color))
        }

        @BindingAdapter(value = ["imageUrl", "isGif"])
        @JvmStatic
        fun loadGif(imageView: ImageView, imageUrl: String, isGif: Boolean) {
            val ref = Firebase.storage.reference.child("gifs").child(imageUrl)
            if (isGif)
                Glide.with(imageView.context).asGif().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .load(ref).into(imageView)
            else
                Glide.with(imageView.context).load(ref)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView)
        }

        @BindingAdapter("android:setTags")
        @JvmStatic
        fun setTag(view: View, tag: Int) {
            view.tag = tag
        }

        @BindingAdapter("android:setTextColor")
        @JvmStatic
        fun setTextColor(view: View, color: String) {
            if (view is TextView)
                view.setTextColor(Color.parseColor(color))
            if (view is CircularProgressIndicator)
                view.setIndicatorColor(Color.parseColor(color))
            if (view is LinearProgressIndicator)
                view.setIndicatorColor(Color.parseColor(color))
            if (view is FloatingActionButton)
                view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
        }

    }
}
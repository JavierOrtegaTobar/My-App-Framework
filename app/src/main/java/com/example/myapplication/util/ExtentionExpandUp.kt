package com.example.myapplication.util

import android.animation.ValueAnimator
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.myapplication.Expand
import com.example.myapplication.R

fun Expand.expandirTexto(linearLayout: LinearLayout) {
    val initialHeight: Int = linearLayout.height
    val targetHeight: Int = if (isExpanded) 250 else 500 // altura

    val valueAnimator = ValueAnimator.ofInt(initialHeight, targetHeight)
    valueAnimator.duration = 500 //duracion de la animacion

    valueAnimator.addUpdateListener { animator ->
        val layoutParams: ViewGroup.LayoutParams = linearLayout.layoutParams
        layoutParams.height = animator.animatedValue as Int
        linearLayout.layoutParams = layoutParams
    }

    valueAnimator.start()
}
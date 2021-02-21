package com.app.movies.utils


import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.dpToPx

class SimpleItemDecoration() :
    RecyclerView.ItemDecoration() {
    private var space : Int = 20.dpToPx


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.bottom = 20.dpToPx
        if (parent.getChildAdapterPosition(view!!) == 0) {
            outRect.top = 20.dpToPx
        }
    }



}
package com.example.getirlite.view.sheet

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import com.example.getirlite.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseSheet: BottomSheetDialogFragment() {
    var isFullScreen: Boolean = false

    override fun onStart() {
        super.onStart()
        setupRatio(dialog as BottomSheetDialog,if (isFullScreen) 100 else 50)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            (dialogInterface as? BottomSheetDialog)?.let { bottomSheetDialog ->
                (bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout)?.let { frameLayout ->
                    BottomSheetBehavior.from(frameLayout).peekHeight = Resources.getSystem().displayMetrics.heightPixels
                    BottomSheetBehavior.from(frameLayout).state = BottomSheetBehavior.STATE_EXPANDED
                    (frameLayout.parent as View).setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog, percetage: Int) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight(percetage)
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(percentage: Int): Int = getWindowHeight() * percentage / 100


    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        MainActivity.instance.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
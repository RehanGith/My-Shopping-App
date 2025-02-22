package com.example.my_shoppings.dialogs

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.my_shoppings.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setUpBottomDialog(
    onSendClick: (String) -> Unit
) {
    val dialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
    dialog.setContentView(R.layout.reset_passowrd_dialog)
    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val edEmail = view?.findViewById<EditText>(R.id.edResetPassword)?.text?.trim()
    val btnCancel = view?.findViewById<EditText>(R.id.buttonCancelResetPassword)
    val btnReset = view?.findViewById<EditText>(R.id.buttonSendResetPassword)

    if (btnCancel != null) {
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    btnReset?.setOnClickListener {
        onSendClick(edEmail.toString())
        dialog.dismiss()
    }
}
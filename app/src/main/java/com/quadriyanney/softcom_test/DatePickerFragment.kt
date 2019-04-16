package com.quadriyanney.softcom_test

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARGUMENT_TAG = "ARGUMENT_TAG"

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var layoutTag = ""
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutTag = arguments!!.getString(ARGUMENT_TAG)!!

        try {
            listener = targetFragment as OnFragmentInteractionListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling Fragment must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener?.updateDateTime(layoutTag, year, month, dayOfMonth)
    }

    companion object {
        fun newInstance(layoutTag: String): DatePickerFragment =
                DatePickerFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARGUMENT_TAG, layoutTag)
                    }
                }

        val TAG = DatePickerFragment::class.java.simpleName
    }

    interface OnFragmentInteractionListener {
        fun updateDateTime(layoutTag: String, year: Int, month: Int, dayOfMonth: Int)
    }
}
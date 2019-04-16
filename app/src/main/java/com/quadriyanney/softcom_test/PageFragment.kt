package com.quadriyanney.softcom_test

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_page.*
import java.text.DecimalFormat

private const val ARGUMENT_PAGE = "ARGUMENT_PAGE"
private const val EMBEDDED_PHOTO = "embeddedphoto"
private const val TEXT = "text"
private const val FORMATTED_NUMERIC = "formattednumeric"
private const val YES_NO = "yesno"
private const val DATE_TIME = "datetime"

class PageFragment : Fragment(), DatePickerFragment.OnFragmentInteractionListener {

    private lateinit var page: Page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments!!.getParcelable(ARGUMENT_PAGE)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPageTitle.text = page.label

        for (section in page.sections) {
            for (element in section.elements) {
                if (element.type == EMBEDDED_PHOTO) {
                    pageLayout.addImageView(layoutInflater,element.file?:"", element.uniqueId)
                }

                if (element.type == TEXT) {
                    pageLayout.addTextField(layoutInflater,element.label?:"", element.uniqueId)
                }

                if (element.type == FORMATTED_NUMERIC) {
                    pageLayout.addTextField(layoutInflater,element.label?:"", element.uniqueId, InputType.TYPE_CLASS_PHONE)
                }

                if (element.type == YES_NO) {
                    pageLayout.addRadioGroup(layoutInflater,element.label?:"", element.uniqueId)
                }

                if (element.type == DATE_TIME) {
                    pageLayout.addFormFieldHeader(layoutInflater, element.label?:"")

                    val dateTimeLayout = pageLayout.createNewDateTimeLayout(layoutInflater, element.uniqueId)
                    dateTimeLayout.setOnClickListener {
                        val datePickerFragment = DatePickerFragment.newInstance(it.tag as String)
                        datePickerFragment.setTargetFragment(this, 0)
                        datePickerFragment.show(fragmentManager!!, DatePickerFragment.TAG)
                    }

                    pageLayout.addView(dateTimeLayout)
                }
            }
        }
    }

    override fun updateDateTime(layoutTag: String, year: Int, month: Int, dayOfMonth: Int) {
        val dateLayout = pageLayout.findViewWithTag<LinearLayout>(layoutTag)

        val decimalFormat = DecimalFormat("00")
        dateLayout.findViewById<TextView>(R.id.tvDay).text = decimalFormat.format(dayOfMonth)
        dateLayout.findViewById<TextView>(R.id.tvMonth).text = decimalFormat.format(month)
        dateLayout.findViewById<TextView>(R.id.tvYear).text = decimalFormat.format(year)
    }

    companion object {
        fun newInstance(page: Page): PageFragment =
                PageFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARGUMENT_PAGE, page)
                    }
                }
    }
}

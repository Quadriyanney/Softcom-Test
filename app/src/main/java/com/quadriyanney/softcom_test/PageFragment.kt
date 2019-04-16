package com.quadriyanney.softcom_test

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

        page.sections.forEach { section ->
            section.elements.forEach { element ->
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
                    val radioGroup = pageLayout.createRadioGroup(layoutInflater,element.label?:"", element.uniqueId)
                    radioGroup.findViewById<RadioButton>(R.id.rbYes).isChecked = true

                    radioGroup.setOnCheckedChangeListener { _, checkedId ->
                        run {
                            when (checkedId) {
                                R.id.rbYes -> {
                                    if (element.rules != null) {
                                        for (rule in element.rules) {
                                            for (target in rule.targets) {
                                                pageLayout.findViewWithTag<LinearLayout>(target).visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                }
                                R.id.rbNo -> {
                                    if (element.rules != null) {
                                        for (rule in element.rules) {
                                            for (target in rule.targets) {
                                                pageLayout.findViewWithTag<LinearLayout>(target).visibility = View.GONE
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    pageLayout.addView(radioGroup)
                }

                if (element.type == DATE_TIME) {
                    val dateTimeLayout = pageLayout.createNewDateTimeLayout(layoutInflater, element.uniqueId)
                    dateTimeLayout.findViewById<TextView>(R.id.tvDateTimeHeader).text = element.label?:""

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

    fun validateForm(): String {
        val string = StringBuilder()
        string.append(page.label)
        string.append("\n")
        page.sections.forEach { section ->
            section.elements.forEach { element ->
                if (element.type == TEXT || element.type == FORMATTED_NUMERIC) {
                    val rootLayout = pageLayout.findViewWithTag<LinearLayout>(element.uniqueId)
                    val inputLayout = rootLayout.findViewById<TextInputLayout>(R.id.textInput)
                    val editText = inputLayout.findViewById<TextInputEditText>(R.id.editText)

                    val value = editText.text.toString()

                    when {
                        value == "" && element.isMandatory!! -> {
                            string.append("${element.label}: Not Filled (Mandatory)")
                            string.append("\n\n")
                        }
                        value == "" && !element.isMandatory!! -> {}
                        else -> {
                            string.append("${element.label}: $value")
                            string.append("\n\n")
                        }
                    }
                }

                if (element.type == DATE_TIME) {
                    val dateTimeLayout = pageLayout.findViewWithTag<LinearLayout>(element.uniqueId)

                    val day = dateTimeLayout.findViewById<TextView>(R.id.tvDay).text.toString()
                    val month = dateTimeLayout.findViewById<TextView>(R.id.tvMonth).text.toString()
                    val year = dateTimeLayout.findViewById<TextView>(R.id.tvYear).text.toString()

                    if (day == "dd") {
                        string.append("${element.label}: Not Set (Mandatory)")
                        string.append("\n\n")
                    } else {
                        string.append("${element.label}: $day - $month - $year")
                        string.append("\n\n")
                    }
                }

                if (element.type == YES_NO) {
                    val radioLayout = pageLayout.findViewWithTag<RadioGroup>(element.uniqueId)

                    val checkedValue = if (radioLayout.checkedRadioButtonId == R.id.rbYes) {
                        "Yes"
                    } else {
                        "No"
                    }

                    string.append("${element.label}: $checkedValue")
                    string.append("\n\n")
                }
            }
        }

        return string.toString()
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

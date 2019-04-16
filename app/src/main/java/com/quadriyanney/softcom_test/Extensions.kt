package com.quadriyanney.softcom_test

import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso



fun ViewGroup.addTextField(inflater: LayoutInflater,
                           label: String, tag: String, inputType: Int = InputType.TYPE_CLASS_TEXT) {
    val linearLayout = inflater.inflate(R.layout.text_field_layout, this, false) as LinearLayout
    val textInputLayout = linearLayout.findViewById<TextInputLayout>(R.id.textInput)
    val textInputEditText = textInputLayout.findViewById<TextInputEditText>(R.id.editText)

    textInputEditText.inputType = inputType
    linearLayout.tag = tag

    if (inputType == InputType.TYPE_CLASS_PHONE) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(13)
        textInputEditText.filters = filterArray
        textInputEditText.addTextChangedListener(object : TextWatcher{
            var lastInput = ""
            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()

                if (input.length == 4 || input.length == 8 && !lastInput.endsWith("-")) {
                    textInputEditText.setText(textInputEditText.text.toString() + "-")
                    textInputEditText.setSelection(textInputEditText.text.toString().length)
                }
//                if (input.length == 5 || input.length == 9 && lastInput.endsWith("-")) {
//                    if (input.length == 5){
//                        textInputEditText.setText(textInputEditText.text.toString().substring(0,4))
//                        textInputEditText.setSelection(textInputEditText.text.toString().length)
//                    }
//
//                    if (input.length == 9) {
//                        textInputEditText.setText(textInputEditText.text.toString().substring(0,8))
//                        textInputEditText.setSelection(textInputEditText.text.toString().length)
//                    }
//
//                }

                lastInput = textInputEditText.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    if (label.length < 20) {
        textInputLayout.hint = label
    } else {
        linearLayout.findViewById<TextView>(R.id.textInputHeader).visibility = View.VISIBLE
        linearLayout.findViewById<TextView>(R.id.textInputHeader).text = label
    }

    addView(linearLayout)
}

fun ViewGroup.addImageView(inflater: LayoutInflater, imageUrl: String, tag: String) {
    val imageView = inflater.inflate(R.layout.image_view_layout, this, false) as ImageView
    imageView.tag = tag

    Picasso.get().load(imageUrl).into(imageView)
    addView(imageView)
}

fun ViewGroup.createRadioGroup(inflater: LayoutInflater, title: String, tag: String): RadioGroup {
    val radioGroup = inflater.inflate(R.layout.radio_group_layout, this, false) as RadioGroup

    radioGroup.findViewById<TextView>(R.id.tvRadioGroupTitle).text = title
    radioGroup.tag = tag

    return radioGroup
}

fun ViewGroup.createNewDateTimeLayout(inflater: LayoutInflater, tag: String): LinearLayout {
    val datetimeLayout = inflater.inflate(R.layout.datetime_layout, this, false) as LinearLayout
    datetimeLayout.tag = tag
    return datetimeLayout
}
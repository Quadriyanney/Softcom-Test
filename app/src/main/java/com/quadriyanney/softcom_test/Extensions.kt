package com.quadriyanney.softcom_test

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

fun ViewGroup.addTextField(inflater: LayoutInflater,
                           label: String, tag: String, inputType: Int = InputType.TYPE_CLASS_TEXT) {
    val textInputLayout = inflater.inflate(R.layout.text_field_layout, this, false) as TextInputLayout
    val textInputEditText = textInputLayout.findViewById<TextInputEditText>(R.id.editText)

    textInputEditText.inputType = inputType
    textInputEditText.tag = tag

    if (inputType == InputType.TYPE_CLASS_PHONE) {
        textInputEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }

    if (label.length < 20) {
        textInputLayout.hint = label
    } else {
        addTextFieldHeader(inflater, label)
    }

    addView(textInputLayout)
}

fun ViewGroup.addTextFieldHeader(inflater: LayoutInflater, label: String) {
    val textView = inflater.inflate(R.layout.text_field_header, this, false) as TextView
    textView.text = label
    addView(textView)
}

fun ViewGroup.addImageView(inflater: LayoutInflater, imageUrl: String, tag: String) {
    val imageView = inflater.inflate(R.layout.image_view_layout, this, false) as ImageView
    imageView.tag = tag

    Picasso.get().load(imageUrl).into(imageView)
    addView(imageView)
}

fun ViewGroup.addRadioGroup(inflater: LayoutInflater, title: String, tag: String) {
    val radioGroup = inflater.inflate(R.layout.radio_group_layout, this, false) as RadioGroup

    radioGroup.tag = tag
    radioGroup.findViewById<TextView>(R.id.tvRadioGroupTitle).text = title
    addView(radioGroup)
}
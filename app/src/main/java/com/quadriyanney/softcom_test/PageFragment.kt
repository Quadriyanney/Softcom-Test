package com.quadriyanney.softcom_test

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_page.*

private const val ARGUMENT_PAGE = "ARGUMENT_PAGE"

class PageFragment : Fragment() {

    private lateinit var page: Page

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        page = arguments!!.getParcelable(ARGUMENT_PAGE)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)

        val pageLayout = view.findViewById<LinearLayout>(R.id.pageLayout)

        for (section in page.sections) {
            for (element in section.elements) {
                if (element.type == "embeddedphoto") {
                    pageLayout.addImageView(inflater,element.file?:"", element.uniqueId)
                }

                if (element.type == "text") {
                    pageLayout.addTextField(inflater,element.label?:"", element.uniqueId)
                }

                if (element.type == "formattednumeric") {
                    pageLayout.addTextField(inflater,element.label?:"", element.uniqueId, InputType.TYPE_CLASS_PHONE)
                }

                if (element.type == "yesno") {
                    pageLayout.addRadioGroup(inflater,element.label?:"", element.uniqueId)
                }
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPageTitle.text = page.label
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

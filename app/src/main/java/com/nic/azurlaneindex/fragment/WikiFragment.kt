package com.nic.azurlaneindex.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nic.azurlaneindex.R
import com.nic.azurlaneindex.activity.ShipListActivity
import com.nic.azurlaneindex.tool.SettingSPUtil
import kotlinx.android.synthetic.main.fragment_wiki.*

class WikiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.cloneInContext(ContextThemeWrapper(activity,SettingSPUtil.themeID))
            .inflate(R.layout.fragment_wiki, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.w(this::class.java.name,"flag")
        ship_wiki_btn.setOnClickListener {
            startActivity(
                Intent(this.activity,
                    ShipListActivity::class.java)
            )
        }
    }
}

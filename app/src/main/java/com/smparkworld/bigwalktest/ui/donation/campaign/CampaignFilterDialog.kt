package com.smparkworld.bigwalktest.ui.donation.campaign

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.RadioGroup
import com.smparkworld.bigwalktest.R
import com.smparkworld.bigwalktest.data.CampaignType

class CampaignFilterDialog(
        context: Context,
        val listener: (CampaignType) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_campaign_filter)

        findViewById<RadioGroup>(R.id.radioType).setOnCheckedChangeListener { _, btnId ->
            when(btnId) {
                R.id.btnTypeAll -> listener(CampaignType.ALL)
                R.id.btnOpenType -> listener(CampaignType.OPEN)
                R.id.btnGroupType -> listener(CampaignType.GROUP)
            }
        }
    }
}
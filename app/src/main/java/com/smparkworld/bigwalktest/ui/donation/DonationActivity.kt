package com.smparkworld.bigwalktest.ui.donation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.smparkworld.bigwalktest.R
import com.smparkworld.bigwalktest.databinding.ActivityDonationBinding
import com.smparkworld.bigwalktest.ui.donation.campaign.CampaignFragment


class DonationActivity : AppCompatActivity() {

    private val mTabFragment: List<Fragment> by lazy {
        listOf(CampaignFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityDonationBinding> (
                this, R.layout.activity_donation
        ).apply {
            btnClose.setOnClickListener { onBackPressed() }
        }
        setSupportActionBar(findViewById(R.id.tlbActivityMain_toolbar))

        // 상단 탭 레이아웃 선택 시 Fragment 이동 구현 생략
        findViewById<TabLayout>(R.id.tlActivityMain_tabLayout).apply {
            addTab(newTab().setText("캠페인"))
            addTab(newTab().setText("포스트"))
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.flActivityDonation_Container, mTabFragment[0])
                .commit()
    }
}
package com.smparkworld.bigwalktest.ui.donation.campaign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.bigwalktest.R
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.databinding.ItemDonationCampaignParticipatedAdapterBinding

class CampaignParticipatedAdapter : RecyclerView.Adapter<CampaignParticipatedAdapter.CampaignViewHolder>() {

    lateinit var mCampaigns: List<Campaign>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val binding = DataBindingUtil.inflate<ItemDonationCampaignParticipatedAdapterBinding>(
            LayoutInflater.from(parent.context), R.layout.item_donation_campaign_participated_adapter, parent, false
        )
        return CampaignViewHolder(binding)
    }

    override fun getItemCount(): Int =
        if(::mCampaigns.isInitialized) mCampaigns.size else 0

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.bind(mCampaigns[position])
    }

    class CampaignViewHolder(
        private val mBinding: ItemDonationCampaignParticipatedAdapterBinding
    ) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: Campaign?) {
            if (item == null) return

            mBinding.campaign = item
            mBinding.executePendingBindings()
        }
    }
}
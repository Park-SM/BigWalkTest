package com.smparkworld.bigwalktest.ui.donation.campaign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.smparkworld.bigwalktest.R
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.databinding.ItemDonationCampaignListAdapterBinding

class CampaignListAdapter : PagedListAdapter<Campaign, CampaignListAdapter.CampaignViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val binding = DataBindingUtil.inflate<ItemDonationCampaignListAdapterBinding>(
            LayoutInflater.from(parent.context), R.layout.item_donation_campaign_list_adapter, parent, false
        )
        return CampaignViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Campaign>() {
            override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign): Boolean =
                oldItem == newItem
        }
    }

    class CampaignViewHolder(private val mBinding: ItemDonationCampaignListAdapterBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun bind(item: Campaign?) {
            if (item == null) return

            mBinding.campaign = item
            mBinding.executePendingBindings()
        }
    }
}
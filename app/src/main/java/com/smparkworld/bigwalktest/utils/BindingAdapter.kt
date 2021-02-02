package com.smparkworld.bigwalktest.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.ui.donation.campaign.CampaignParticipatedAdapter

@BindingAdapter("setImage")
fun loadImg(view: ImageView, campaign: Campaign) {
    val glide = Glide.with(view).load(campaign.imagePath).override(200, 200)

    if (campaign.isClose())
        glide.listener(GLIDE_LISTENER).into(view)
    else
        glide.into(view)
}

@BindingAdapter("setAdapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    view.adapter = adapter
}

@BindingAdapter("setItems")
fun setItems(view: RecyclerView, items: List<Campaign>?) {
    if (items != null) {
        (view.adapter as? CampaignParticipatedAdapter)?.let {
            it.mCampaigns = items
            it.notifyDataSetChanged()
        }
    }
}

val GLIDE_LISTENER = object:RequestListener<Drawable> {
    override fun onResourceReady(
        resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
    ): Boolean {
        resource?.alpha = 100
        return false
    }
    override fun onLoadFailed(
        e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
    ): Boolean {
        return false
    }
}
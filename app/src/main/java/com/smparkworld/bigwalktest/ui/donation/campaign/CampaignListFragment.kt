package com.smparkworld.bigwalktest.ui.donation.campaign

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.smparkworld.bigwalktest.BigWalkApp
import com.smparkworld.bigwalktest.data.CampaignType
import com.smparkworld.bigwalktest.databinding.FragmentDonationCapaignListBinding
import javax.inject.Inject

class CampaignListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel by viewModels<CampaignViewModel> { viewModelFactory }

    lateinit var mAdapter: CampaignListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as BigWalkApp).appComponent.donationComponent().create()
                .inject(this)

        mAdapter = CampaignListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDonationCapaignListBinding.inflate(inflater, container, false).apply {
            adapter = mAdapter
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mViewModel.loadCampaignsByPaging(viewLifecycleOwner, mAdapter, CampaignType.ALL).let {
            if (!it) Toast.makeText(context, "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
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
import com.smparkworld.bigwalktest.R
import com.smparkworld.bigwalktest.databinding.FragmentDonationCampaignBinding
import javax.inject.Inject

class CampaignFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val mViewModel by viewModels<CampaignViewModel> { viewModelFactory }

    lateinit var mFilterDialog: CampaignFilterDialog

    lateinit var mBinding: FragmentDonationCampaignBinding

    // 전체 캠페인 리스트 카테고리는 index 0
    private var mCurrentCampaignIndex = 0
    private val mCampaignListCategory: List<CampaignListFragment> by lazy {
        listOf(CampaignListFragment())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as BigWalkApp).appComponent.donationComponent().create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.loadParticipatedCampaigns()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentDonationCampaignBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = mViewModel
            adapter = CampaignParticipatedAdapter()
            filterBtn.setOnClickListener { mFilterDialog.show() }
        }

        requireActivity().supportFragmentManager.beginTransaction()     // 다른 캠페인 카테고리 선택 시 Fragment 이동 구현 생략
                .replace(R.id.flFragmentDonationCampaign_Container, mCampaignListCategory[0])
                .commit()
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // 필터 선택 시 현재 보여지고 있는 캠페인 카테고리 리스트에 필터 적용
        mFilterDialog = CampaignFilterDialog(requireContext()) { type ->
            mViewModel.loadCampaignsByPaging(this, mCampaignListCategory[mCurrentCampaignIndex].mAdapter, type).let {
                if (!it) Toast.makeText(requireContext(), "데이터를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
package com.smparkworld.bigwalktest.data.source.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.smparkworld.bigwalktest.data.Campaign
import com.smparkworld.bigwalktest.data.CampaignType
import com.smparkworld.bigwalktest.data.CampaignType.*
import com.smparkworld.bigwalktest.data.Result
import com.smparkworld.bigwalktest.data.Result.Error
import com.smparkworld.bigwalktest.data.Result.Success
import com.smparkworld.bigwalktest.data.source.CampaignRepository
import com.smparkworld.bigwalktest.data.source.remote.CampaignRemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class CampaignRepositoryImpl @Inject constructor(
    private val remoteDataSource: CampaignRemoteDataSource
) : CampaignRepository {

    override fun getCampaignsByPaging(type: CampaignType): Result<LiveData<PagedList<Campaign>>> {
        return Success(
                LivePagedListBuilder<Int, Campaign>(
                    Factory(remoteDataSource, type), PAGE_CONF
                ).build()
        )
    }

    override fun getParticipatedCampaigns(function: (Result<List<Campaign>>) -> Unit) {
        remoteDataSource.getCampaigns(object: Callback<List<Campaign>> {

            override fun onResponse(call: Call<List<Campaign>>, response: Response<List<Campaign>>) {
                if (response.isSuccessful && response.code() == 200) {

                    val participatedList = mutableListOf<Campaign>()
                    for (campaign in response.body() ?: emptyList()) {
                        if (campaign.my.donatedSteps > 0)
                            participatedList.add(campaign)
                    }
                    function(Success(participatedList))
                }
            }
            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                function(Error(Exception("데이터 로드 실패")))
            }
        })
    }

    companion object {
        val PAGE_CONF = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()
    }
}

class Factory(
    private val remoteDataSource: CampaignRemoteDataSource,
    private val type: CampaignType
) : DataSource.Factory<Int, Campaign>() {

    override fun create(): DataSource<Int, Campaign> = CampaignPagingDataSource(remoteDataSource, type)
}

class CampaignPagingDataSource(
    private val remoteDataSource: CampaignRemoteDataSource,
    private val type: CampaignType
): PageKeyedDataSource<Int, Campaign>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Campaign>) {
        var key = 0     // 시작 Page index
        var list: List<Campaign>

        // 필터가 적용된 후 페이지네이션 시 처음 로딩된 데이터 안에 필터링된 데이터가 없을 수도 있으므로 데이터가 없으면 다음 페이지로 이동
        do {
            list = fetchFromRemote(key++, params.requestedLoadSize)
        } while(list.isEmpty())

        callback.onResult(list, null, key)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Campaign>) {
        callback.onResult(
            fetchFromRemote(params.key, params.requestedLoadSize), params.key + 1
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Campaign>) {}

    private fun fetchFromRemote(page: Int, size: Int): List<Campaign> {

        val remoteCampaigns = remoteDataSource.getCampaigns(page, size)
        when(remoteCampaigns) {
            is Error -> Log.d("RemoteResult","Failed to fetch campaign list")
            is Success -> return filteringCampaigns(remoteCampaigns.data, type)
            else -> throw IllegalStateException()
        }
        return emptyList()
    }

    private fun filteringCampaigns(list: List<Campaign>, type: CampaignType): List<Campaign> {
        if (list.isEmpty() || type == ALL) return list

        val mutableList = list.toMutableList()

        var removedCnt = 0
        for (i in mutableList.indices) {
            when(type) {
                OPEN -> if (mutableList[i - removedCnt].organizations.isNotEmpty()) mutableList.removeAt(i - removedCnt++)
                GROUP -> if (mutableList[i - removedCnt].organizations.isEmpty()) mutableList.removeAt(i - removedCnt++)
            }
        }
        return mutableList
    }
}
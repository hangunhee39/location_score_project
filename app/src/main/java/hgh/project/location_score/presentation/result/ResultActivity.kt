package hgh.project.location_score.presentation.result

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.WebSharerClient
import com.kakao.sdk.template.model.*
import hgh.project.location_score.R
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.databinding.ActivityResultBinding
import hgh.project.location_score.presentation.BaseActivity
import hgh.project.location_score.presentation.adapter.ResultListAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class ResultActivity : BaseActivity<ResultViewModel, ActivityResultBinding>() {

    override val viewModel by viewModel<ResultViewModel> {
        parametersOf(
            intent.getParcelableExtra(RESULT_KEY)
        )
    }

    override fun getViewBinding() = ActivityResultBinding.inflate(layoutInflater)

    private val adapter = ResultListAdapter()

    companion object {

        const val RESULT_KEY = "RESULT_KEY"

        fun newIntent(context: Context, result: SearchResult) =
            Intent(context, ResultActivity::class.java).apply {
                putExtra(RESULT_KEY, result)
            }
    }

    override fun observeData() = viewModel.resultStateLiveData.observe(this) {
        when (it) {
            is ResultState.UnInitialized -> initView()
            is ResultState.Loading -> handleLoading()
            is ResultState.Success -> handleSuccess(it)
            is ResultState.Error -> handleError()
        }
    }

    private fun initView() = with(binding){
        resultRecyclerView.adapter= adapter
        sharedButton.setOnClickListener {
            sendKakaoLink(baseContext)
        }
    }

    private fun handleLoading() {

    }

    private fun handleSuccess(state: ResultState.Success) {
        adapter.setListAdapter(state.resultList)
        binding.score.text = state.score.toString()
    }

    private fun handleError() {

    }

    private fun sendKakaoLink(context: Context) {
        // 메시지 템플릿 만들기 (피드형)
        val defaultFeed = FeedTemplate(
            content = Content(
                title = "오늘의 디저트",
                description = "#케익 #딸기 #삼평동 #카페 #분위기 #소개팅",
                imageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                link = Link(
                    webUrl = "https://developers.kakao.com",
                    mobileWebUrl = "https://developers.kakao.com"
                )
            ),
            itemContent = ItemContent(
                profileText = "Kakao",
                profileImageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                titleImageUrl = "https://mud-kage.kakao.com/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                titleImageText = "Cheese cake",
                titleImageCategory = "Cake",
                items = listOf(
                    ItemInfo(item = "cake1", itemOp = "1000원"),
                    ItemInfo(item = "cake2", itemOp = "2000원"),
                    ItemInfo(item = "cake3", itemOp = "3000원"),
                    ItemInfo(item = "cake4", itemOp = "4000원"),
                    ItemInfo(item = "cake5", itemOp = "5000원")
                ),
                sum = "Total",
                sumOp = "15000원"
            ),
            social = Social(
                likeCount = 286,
                commentCount = 45,
                sharedCount = 845
            ),
            buttons = listOf(
                Button(
                    "웹으로 보기",
                    Link(
                        webUrl = "https://developers.kakao.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                Button(
                    "앱으로 보기",
                    Link(
                        androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                        iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                    )
                )
            )
        )

        if (ShareClient.instance.isKakaoTalkSharingAvailable(context)) {
            // 카카오톡으로 카카오톡 공유 가능
            ShareClient.instance.shareDefault(context, defaultFeed) { sharingResult, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡 공유 실패", error)
                } else if (sharingResult != null) {
                    Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                    startActivity(sharingResult.intent)
                }
            }
        }else{
            finish()
        }
    }
}
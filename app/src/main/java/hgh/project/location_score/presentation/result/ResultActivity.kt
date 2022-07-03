package hgh.project.location_score.presentation.result

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hgh.project.location_score.R
import hgh.project.location_score.data.entity.SearchResult
import hgh.project.location_score.databinding.ActivityResultBinding
import hgh.project.location_score.presentation.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class ResultActivity : BaseActivity<ResultViewModel, ActivityResultBinding>() {

    override val viewModel by viewModel<ResultViewModel> {
        parametersOf(
            intent.getParcelableExtra(RESULT_KEY)
        )
    }

    override fun getViewBinding() = ActivityResultBinding.inflate(layoutInflater)

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
            is ResultState.Success -> handleSuccess()
            is ResultState.Error -> handleError()
        }
    }

    private fun initView() {

    }

    private fun handleLoading() {

    }

    private fun handleSuccess() {

    }

    private fun handleError() {

    }
}
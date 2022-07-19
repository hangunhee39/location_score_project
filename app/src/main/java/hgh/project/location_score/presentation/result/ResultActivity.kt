package hgh.project.location_score.presentation.result

import android.content.Context
import android.content.Intent
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import hgh.project.location_score.R
import hgh.project.location_score.data.entity.HistoryEntity
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

        const val RESULT_CODE = 101

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

    private fun initView() = with(binding) {
        resultRecyclerView.adapter = adapter
        sharedButton.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "나의 위치 점수는 ${score.text}!! \n 테스트하러 가기")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }
        saveButton.setOnClickListener {
            showDialog()
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

    private fun showDialog() {
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.save_dialog, null)

        val alertDialog = AlertDialog.Builder(this)
            .setView(view)
            .create()

        val saveButton = view.findViewById<TextView>(R.id.saveButton)
        val cancelButton = view.findViewById<TextView>(R.id.cancelButton)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)

        saveButton.setOnClickListener {
            viewModel.addHistory(
                history = HistoryEntity(
                    name = nameEditText.text.toString() ?: " ",
                    score = binding.score.text.toString().toInt() ?: 0
                )
            )
            setResult(RESULT_CODE)
            finish()
        }
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}
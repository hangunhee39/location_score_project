package hgh.project.location_score.presentation.adapter

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hgh.project.location_score.R
import hgh.project.location_score.databinding.ResultItemBinding

class ResultListAdapter : RecyclerView.Adapter<ResultListAdapter.ResultItemHolder>() {

    private var resultList: List<String> = listOf()

    inner class ResultItemHolder(
        private val binding: ResultItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bindData(data: String) = with(binding) {

            val spanText =SpannableString(data)
            spanText.setSpan(ForegroundColorSpan(R.color.skyBlue),0,4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.text = spanText
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemHolder {
        return ResultItemHolder(
            ResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ResultItemHolder, position: Int) {
        holder.bindData(resultList[position])
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListAdapter(list: List<String>){
        resultList = list
        notifyDataSetChanged()
    }
}
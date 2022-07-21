package hgh.project.location_score.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hgh.project.location_score.data.entity.HistoryEntity
import hgh.project.location_score.databinding.HistoryItemBinding

class HistoryListAdapter : RecyclerView.Adapter<HistoryListAdapter.HistoryItemHolder>() {

    private var historyList: List<HistoryEntity> = listOf()
    private lateinit var historyClearClickListener: (HistoryEntity) -> Unit

    inner class HistoryItemHolder(
        private val binding: HistoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bindData(data: HistoryEntity) = with(binding) {
            nameText.text = data.name
            scoreText.text = data.score.toString()
            clearButton.setOnClickListener {
                historyClearClickListener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemHolder {
        return HistoryItemHolder(
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryItemHolder, position: Int) {
        holder.bindData(historyList[position])
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListAdapter(list: List<HistoryEntity>, clickListener: (HistoryEntity) -> Unit = { }) {
        historyList = list
        this.historyClearClickListener = clickListener
        notifyDataSetChanged()
    }
}
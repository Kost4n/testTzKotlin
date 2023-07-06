package com.kost4n.testandroidapp.roof.adapter

import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kost4n.testandroidapp.R
import com.kost4n.testandroidapp.roof.entity.Record

class RecordAdapter(
    private val onRecordClickListener: OnRecordClickListener
    ) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
    private var itemList = mutableListOf<Record>()

    inner class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recordDate = itemView.findViewById<TextView>(R.id.record_date)
        private val recordWorkouts = itemView.findViewById<TextView>(R.id.record_workout)
        private val recordCounts = itemView.findViewById<TextView>(R.id.record_count)

        fun bind(record: Record, position: Int) {
            recordDate.text = record.date.toString()
            recordWorkouts.text = record.workout.work.toString()
            recordCounts.text = record.workout.count.toString()

            itemView.setOnClickListener {
                onRecordClickListener.onRecordClick(record, position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_row, parent, false)

        Log.i("RecordAdapter", "----------onCreateViewHolder----------")
        return RecordViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) =
        holder.bind(itemList[position], position)

    fun update(items: MutableList<Record>) {
        itemList = items
        notifyDataSetChanged()
    }
    interface OnRecordClickListener {
        fun onRecordClick(record: Record, position: Int)
    }
}

class RecycleItemDecoration(
    val spaceHeight: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = spaceHeight
        outRect.bottom = spaceHeight
    }
}


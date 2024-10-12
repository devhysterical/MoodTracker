package com.example.moodtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter cho RecyclerView để hiển thị lịch sử các trạng thái cảm xúc.
 * Dữ liệu được truyền vào dưới dạng danh sách các cặp (ngày, cảm xúc).
 */
class MoodHistoryAdapter(private val moodHistory: List<Pair<String, String>>) :
    RecyclerView.Adapter<MoodHistoryAdapter.MoodViewHolder>() {
    /**
     * ViewHolder đại diện cho một item trong danh sách RecyclerView.
     * Mỗi ViewHolder chứa các View con để hiển thị ngày và cảm xúc.
     */
    inner class MoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // TextView hiển thị ngày
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)

        // TextView hiển thị cảm xúc
        val moodTextView: TextView = view.findViewById(R.id.moodTextView)
    }
    /**
     * Phương thức này được gọi khi cần tạo một ViewHolder mới.
     * Nó sẽ inflate (nạp) layout cho từng item trong RecyclerView từ file XML.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        // Inflate layout `item_mood_history` thành View
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mood_history, parent, false)
        // Trả về một ViewHolder mới được liên kết với View này
        return MoodViewHolder(view)
    }

    /**
     * Phương thức này được gọi khi cần gán dữ liệu vào ViewHolder.
     * Nó lấy dữ liệu tương ứng với vị trí hiện tại và hiển thị lên các TextView.
     */
    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        // Lấy cặp (ngày, cảm xúc) từ danh sách theo vị trí
        val (date, mood) = moodHistory[position]
        // Gán giá trị ngày vào TextView của ViewHolder
        holder.dateTextView.text = date
        // Gán giá trị cảm xúc vào TextView của ViewHolder
        holder.moodTextView.text = mood
    }

    /**
     * Phương thức này trả về số lượng item trong danh sách.
     * RecyclerView sẽ dựa vào số lượng này để biết cần tạo bao nhiêu ViewHolder.
     */
    override fun getItemCount(): Int = moodHistory.size
}

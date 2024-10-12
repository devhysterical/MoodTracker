package com.example.moodtracker

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * MainActivity là hoạt động chính của ứng dụng theo dõi tâm trạng.
 * Ứng dụng cho phép người dùng chọn tâm trạng và lưu lịch sử kèm theo ngày.
 */
class MainActivity : AppCompatActivity() {

    // Khai báo các biến cho MoodSelectorView và Adapter của RecyclerView
    private lateinit var moodSelectorView: MoodSelectorView
    private lateinit var moodHistoryAdapter: MoodHistoryAdapter

    // Danh sách chứa lịch sử tâm trạng, mỗi phần tử là cặp (ngày, tâm trạng)
    private val moodHistory = mutableListOf<Pair<String, String>>()

    /**
     * Phương thức onCreate được gọi khi Activity được tạo.
     * Ở đây, chúng ta khởi tạo giao diện và thiết lập các thành phần.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Liên kết View từ layout với các biến đã khai báo
        moodSelectorView = findViewById(R.id.moodSelectorView)
        val recyclerView: RecyclerView = findViewById(R.id.moodHistoryRecyclerView)
        val moodStatus: TextView = findViewById(R.id.moodStatus)
        // Khởi tạo Adapter cho RecyclerView và thiết lập LinearLayoutManager
        moodHistoryAdapter = MoodHistoryAdapter(moodHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = moodHistoryAdapter
        // Xử lý sự kiện khi người dùng nhấn vào MoodSelectorView
        moodSelectorView.setOnClickListener {
            val selectedMood = moodSelectorView.getSelectedMood() // Lấy tâm trạng được chọn
            if (selectedMood != null) {
                // Lấy ngày hiện tại
                val date = getCurrentDate()
                // Thêm cặp (ngày, tâm trạng) vào danh sách lịch sử và thông báo Adapter
                moodHistory.add(Pair(date, selectedMood))
                moodHistoryAdapter.notifyItemInserted(moodHistory.size - 1)
                // Cập nhật chủ đề màu sắc theo tâm trạng đã chọn
                updateTheme(selectedMood)
                // Hiển thị trạng thái tâm trạng hiện tại trong TextView
                moodStatus.text = getString(R.string.current_mood, selectedMood)
                // Hiển thị Snackbar thông báo đã lưu tâm trạng
                Snackbar.make(it, getString(R.string.mood_saved, selectedMood), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Phương thức lấy ngày hiện tại dưới dạng chuỗi theo định dạng "yyyy-MM-dd".
     */
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date()) // Trả về chuỗi ngày đã định dạng
    }

    /**
     * Cập nhật màu nền của Activity dựa trên tâm trạng đã chọn.
     */
    private fun updateTheme(mood: String) {
        // Đổi màu nền của cửa sổ ứng dụng dựa trên tâm trạng
        window.decorView.setBackgroundColor(
            when (mood) {
                "Vui" -> Color.YELLOW     // Màu vàng cho tâm trạng vui
                "Buồn" -> Color.DKGRAY     // Màu xám đậm cho tâm trạng buồn
                "Lo lắng" -> Color.LTGRAY  // Màu xám nhạt cho tâm trạng lo lắng
                "Giận dữ" -> Color.RED     // Màu đỏ cho tâm trạng giận dữ
                else -> Color.WHITE        // Mặc định là màu trắng
            }
        )
    }
}

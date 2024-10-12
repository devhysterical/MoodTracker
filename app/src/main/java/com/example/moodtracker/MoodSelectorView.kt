package com.example.moodtracker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

/**
 * Custom View cho phép người dùng chọn cảm xúc bằng cách nhấn vào các vòng tròn đại diện cho từng trạng thái.
 */
class MoodSelectorView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    // Khởi tạo Paint để vẽ với thuộc tính isAntiAlias giúp vẽ mượt hơn
    private val paint = Paint().apply { isAntiAlias = true }
    // Danh sách các trạng thái cảm xúc
    private val moods = listOf("Vui", "Buồn", "Lo lắng", "Giận dữ")
    // Biến lưu trạng thái cảm xúc đã chọn
    private var selectedMood: String? = null
    /**
     * Phương thức onDraw được gọi khi View cần vẽ.
     * Vẽ các vòng tròn đại diện cho từng trạng thái cảm xúc.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Tính bán kính để phân bố các vòng tròn đều trên View
        val radius = width / 4f

        // Duyệt qua từng cảm xúc và vẽ vòng tròn tương ứng
        moods.forEachIndexed { index, mood ->
            // Gán màu khác nhau cho mỗi cảm xúc bằng biểu thức `when`
            paint.color = when (mood) {
                "Vui" -> 0xFFFFD700.toInt()   // Gold (Màu vàng)
                "Buồn" -> 0xFF607D8B.toInt()  // Blue Grey (Xám xanh)
                "Lo lắng" -> 0xFFFFA000.toInt() // Orange (Cam)
                "Giận dữ" -> 0xFFD32F2F.toInt() // Red (Đỏ)
                else -> 0xFF000000.toInt()     // Black (Đen)
            }
            // Tính toán góc cho từng vòng tròn dựa trên chỉ số `index`
            val angle = Math.PI / 2 * index  // Mỗi góc cách nhau 90 độ
            // Tính toán tọa độ X và Y cho vòng tròn bằng hàm lượng giác cos và sin
            val cx = (width / 2f) + (radius * cos(angle)).toFloat() // Tọa độ X
            val cy = (height / 2f) + (radius * sin(angle)).toFloat() // Tọa độ Y
            // Vẽ vòng tròn với bán kính 100 pixel tại tọa độ (cx, cy)
            canvas.drawCircle(cx, cy, 100f, paint)
        }
    }

    /**
     * Xử lý sự kiện chạm (Touch) trên View.
     * Kiểm tra xem người dùng đã chạm vào đâu và cập nhật cảm xúc tương ứng.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {  // Chỉ xử lý khi người dùng nhấn xuống
            // Tính toán chỉ số cảm xúc dựa trên vị trí nhấn của người dùng
            val moodIndex = (event.x / width * moods.size).toInt()
                .coerceAtMost(moods.size - 1)  // Đảm bảo chỉ số không vượt quá giới hạn danh sách
            // Lưu lại cảm xúc đã chọn
            selectedMood = moods[moodIndex]
            // Gọi invalidate() để vẽ lại View với trạng thái mới
            invalidate()
            // Gọi performClick() để đảm bảo View phản hồi đúng với các công cụ trợ năng (Accessibility)
            performClick()
            return true  // Sự kiện đã được xử lý
        }
        // Gọi phương thức cha nếu sự kiện không phải là ACTION_DOWN
        return super.onTouchEvent(event)
    }

    /**
     * Xử lý sự kiện click cho View.
     * Được sử dụng để đảm bảo tương thích với các công cụ trợ năng.
     */
    override fun performClick(): Boolean {
        super.performClick()  // Gọi performClick() từ lớp cha
        return true  // Xác nhận sự kiện click đã được xử lý
    }
    /**
     * Trả về cảm xúc đã chọn của người dùng.
     */
    fun getSelectedMood(): String? = selectedMood
}

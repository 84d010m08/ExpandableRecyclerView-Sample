package expandable.recycler.view

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import expandable.recycler.view.databinding.ItemChildCheckboxBinding
import expandable.recycler.view.databinding.ItemChildChronometerBinding
import expandable.recycler.view.databinding.ItemChildRatingbarBinding
import expandable.recycler.view.databinding.ItemParentBinding
import java.util.*

class ExpandableRecyclerAdapter(context: Context, private val onExpanded: (position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recyclerItems = ArrayList<RecyclerItem>()

    private val inflater = LayoutInflater.from(context)

    private var isExpandedCheckbox = false
    private var isExpandedChronometer = false
    private var isExpandedRatingBar = false

    private val checkboxRecyclerItems = mutableListOf<ChildCheckboxRecyclerItem>()
    private val chronometerRecyclerItems = mutableListOf<ChildChronometerRecyclerItem>()
    private val ratingBarRecyclerItems = mutableListOf<ChildRatingBarRecyclerItem>()

    enum class RecyclerViewType(val value: Int) {
        CHRONOMETER_PARENT(value = 1000),
        CHRONOMETER_CHILD(value = 1001),
        CHECKBOX_PARENT(value = 2000),
        CHECKBOX_CHILD(value = 2001),
        RATING_BAR_PARENT(value = 3000),
        RATING_BAR_CHILD(value = 3001);
    }

    init {
        recyclerItems.also {
            it.add(ParentCheckboxRecyclerItem(text = "Pure Eyes\n純粋さを、捨てない。"))
            it.add(ParentChronometerRecyclerItem(text = "Urban Cowgirl\n“私”で、生きてゆく。"))
            it.add(ParentRatingBarRecyclerItem(text = "Mystic Journey\n旅を、やめない。"))
        }

        checkboxRecyclerItems.addAll(listOf(
                ChildCheckboxRecyclerItem(text = "瞳を閉じて", isChecked = true),
                ChildCheckboxRecyclerItem(text = "ジャコビニ彗星の日", isChecked = true),
                ChildCheckboxRecyclerItem(text = "スラバヤ通りの妹へ", isChecked = true),
                ChildCheckboxRecyclerItem(text = "雨の街を", isChecked = true),
                ChildCheckboxRecyclerItem(text = "緑の町に舞い降りて", isChecked = true),
                ChildCheckboxRecyclerItem(text = "セシルの週末", isChecked = true),
                ChildCheckboxRecyclerItem(text = "September Blue Moon", isChecked = true),
                ChildCheckboxRecyclerItem(text = "まずはどこへ行こう", isChecked = true),
                ChildCheckboxRecyclerItem(text = "ただわけもなく", isChecked = true),
                ChildCheckboxRecyclerItem(text = "海に来て", isChecked = true),
                ChildCheckboxRecyclerItem(text = "Summer Junction", isChecked = true),
                ChildCheckboxRecyclerItem(text = "きっと言える", isChecked = true),
                ChildCheckboxRecyclerItem(text = "Midnight Scarecrow", isChecked = true),
                ChildCheckboxRecyclerItem(text = "Autumn Park", isChecked = true),
                ChildCheckboxRecyclerItem(text = "雪だより", isChecked = true)))

        chronometerRecyclerItems.addAll(listOf(
                ChildChronometerRecyclerItem(text = "ふってあげる", millisecond = 295),
                ChildChronometerRecyclerItem(text = "思い出に間にあいたくて", millisecond = 244),
                ChildChronometerRecyclerItem(text = "ひとつの恋が終るとき", millisecond = 321),
                ChildChronometerRecyclerItem(text = "もう愛は始まらない", millisecond = 286),
                ChildChronometerRecyclerItem(text = "TUXEDO RAIN", millisecond = 272),
                ChildChronometerRecyclerItem(text = "心ほどいて", millisecond = 269),
                ChildChronometerRecyclerItem(text = "街角のペシミスト", millisecond = 280),
                ChildChronometerRecyclerItem(text = "NIGHT WALKER", millisecond = 303),
                ChildChronometerRecyclerItem(text = "Nobody Else", millisecond = 200),
                ChildChronometerRecyclerItem(text = "夕涼み", millisecond = 260),
                ChildChronometerRecyclerItem(text = "雨のステイション", millisecond = 316),
                ChildChronometerRecyclerItem(text = "幸せはあなたへの復讐", millisecond = 260),
                ChildChronometerRecyclerItem(text = "Cowgirl Blues", millisecond = 248),
                ChildChronometerRecyclerItem(text = "オールマイティ", millisecond = 265),
                ChildChronometerRecyclerItem(text = "フォーカス", millisecond = 316)))

        ratingBarRecyclerItems.addAll(listOf(
                ChildRatingBarRecyclerItem(text = "満月のフォーチュン", rating = 3f),
                ChildRatingBarRecyclerItem(text = "破れた恋の繕し方教えます", rating = 3f),
                ChildRatingBarRecyclerItem(text = "砂の惑星", rating = 3f),
                ChildRatingBarRecyclerItem(text = "朝陽の中で微笑んで", rating = 3f),
                ChildRatingBarRecyclerItem(text = "輪舞曲", rating = 3f),
                ChildRatingBarRecyclerItem(text = "ツバメのように", rating = 3f),
                ChildRatingBarRecyclerItem(text = "シャンソン", rating = 3f),
                ChildRatingBarRecyclerItem(text = "霧の中の影", rating = 3f),
                ChildRatingBarRecyclerItem(text = "AVALON", rating = 3f),
                ChildRatingBarRecyclerItem(text = "BABYLON", rating = 3f),
                ChildRatingBarRecyclerItem(text = "きみなき世界", rating = 3f),
                ChildRatingBarRecyclerItem(text = "Man in the Moon", rating = 3f),
                ChildRatingBarRecyclerItem(text = "SATURDAY NIGHT ZOMBIES", rating = 3f),
                ChildRatingBarRecyclerItem(text = "無限の中の一度", rating = 3f),
                ChildRatingBarRecyclerItem(text = "July", rating = 3f)))
    }

    override fun getItemViewType(position: Int): Int = recyclerItems[position].viewType().value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                RecyclerViewType.CHECKBOX_PARENT.value -> ParentCheckboxViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_parent, parent, false))
                RecyclerViewType.CHECKBOX_CHILD.value -> ChildCheckboxViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_child_checkbox, parent, false))
                RecyclerViewType.CHRONOMETER_PARENT.value -> ParentChronometerViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_parent, parent, false))
                RecyclerViewType.CHRONOMETER_CHILD.value -> ChildChronometerViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_child_chronometer, parent, false))
                RecyclerViewType.RATING_BAR_PARENT.value -> ParentRatingBarViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_parent, parent, false))
                else -> ChildRatingBarViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_child_ratingbar, parent, false))
            }

    override fun getItemCount(): Int = recyclerItems.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is ParentCheckboxViewHolder -> viewHolder.bind(recyclerItems[position] as ParentCheckboxRecyclerItem)
            is ChildCheckboxViewHolder -> viewHolder.bind(recyclerItems[position] as ChildCheckboxRecyclerItem)
            is ParentChronometerViewHolder -> viewHolder.bind(recyclerItems[position] as ParentChronometerRecyclerItem)
            is ChildChronometerViewHolder -> viewHolder.bind(recyclerItems[position] as ChildChronometerRecyclerItem)
            is ParentRatingBarViewHolder -> viewHolder.bind(recyclerItems[position] as ParentRatingBarRecyclerItem)
            is ChildRatingBarViewHolder -> viewHolder.bind(recyclerItems[position] as ChildRatingBarRecyclerItem)
        }
    }

    private fun findFirstPosition(viewType: RecyclerViewType): Int? {
        for (i in 0 until recyclerItems.size) if (recyclerItems[i].viewType() == viewType) return i
        return null
    }

    fun addRecyclerItems(viewType: RecyclerViewType, items: List<RecyclerItem>) =
            findFirstPosition(viewType)?.let { position ->
                recyclerItems.addAll(position + 1, items)
                notifyItemRangeInserted(position + 1, items.size)
            }

    fun removeRecyclerItems(viewType: RecyclerViewType) {
        val filteredRecyclerItems = recyclerItems.filter { it.viewType() == viewType }
        findFirstPosition(viewType)?.let { position ->
            for (recyclerItem in filteredRecyclerItems) recyclerItems.removeAt(position)
            notifyItemRangeRemoved(position, filteredRecyclerItems.size)
        }
    }

    inner class ParentCheckboxViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParentCheckboxRecyclerItem) {
            binding.textView.text = item.text

            itemView.setOnClickListener {
                if (isExpandedCheckbox) {
                    removeRecyclerItems(RecyclerViewType.CHECKBOX_CHILD)
                } else {
                    addRecyclerItems(RecyclerViewType.CHECKBOX_PARENT, checkboxRecyclerItems)

                    onExpanded.invoke(findFirstPosition(RecyclerViewType.CHECKBOX_PARENT)!!)
                }

                isExpandedCheckbox = !isExpandedCheckbox
                rotateArrow()
            }

            rotateArrow()
        }

        private fun rotateArrow() {
            binding.arrow.rotation = if (isExpandedCheckbox) 180f else 0f
        }
    }

    inner class ChildCheckboxViewHolder(private val binding: ItemChildCheckboxBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChildCheckboxRecyclerItem) {
            binding.textView.text = item.text
            binding.checkbox.isChecked = item.isChecked
        }
    }

    inner class ParentChronometerViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParentChronometerRecyclerItem) {
            binding.textView.text = item.text

            itemView.setOnClickListener {
                if (isExpandedChronometer) {
                    removeRecyclerItems(RecyclerViewType.CHRONOMETER_CHILD)
                } else {
                    addRecyclerItems(RecyclerViewType.CHRONOMETER_PARENT, chronometerRecyclerItems)

                    onExpanded.invoke(findFirstPosition(RecyclerViewType.CHRONOMETER_PARENT)!!)
                }

                isExpandedChronometer = !isExpandedChronometer
                rotateArrow()
            }

            rotateArrow()
        }

        private fun rotateArrow() {
            binding.arrow.rotation = if (isExpandedChronometer) 180f else 0f
        }
    }

    inner class ChildChronometerViewHolder(private val binding: ItemChildChronometerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChildChronometerRecyclerItem) {
            binding.textView.text = item.text

            binding.chronometer.base = SystemClock.elapsedRealtime() - (item.millisecond * 1000)
        }
    }

    inner class ParentRatingBarViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParentRatingBarRecyclerItem) {
            binding.textView.text = item.text

            itemView.setOnClickListener {
                if (isExpandedRatingBar) {
                    removeRecyclerItems(RecyclerViewType.RATING_BAR_CHILD)
                } else {
                    addRecyclerItems(RecyclerViewType.RATING_BAR_PARENT, ratingBarRecyclerItems)

                    onExpanded.invoke(findFirstPosition(RecyclerViewType.RATING_BAR_PARENT)!!)
                }

                isExpandedRatingBar = !isExpandedRatingBar
                rotateArrow()
            }

            rotateArrow()
        }

        private fun rotateArrow() {
            binding.arrow.rotation = if (isExpandedRatingBar) 180f else 0f
        }
    }

    inner class ChildRatingBarViewHolder(private val binding: ItemChildRatingbarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChildRatingBarRecyclerItem) {
            binding.textView.text = item.text
            binding.ratingBar.rating = item.rating
        }
    }

    interface RecyclerItem {
        fun viewType(): RecyclerViewType
    }

    inner class ParentCheckboxRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.CHECKBOX_PARENT
    }

    inner class ChildCheckboxRecyclerItem(val text: String, var isChecked: Boolean) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.CHECKBOX_CHILD
    }

    inner class ParentChronometerRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.CHRONOMETER_PARENT
    }

    inner class ChildChronometerRecyclerItem(val text: String, val millisecond: Long) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.CHRONOMETER_CHILD
    }

    inner class ParentRatingBarRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.RATING_BAR_PARENT
    }

    inner class ChildRatingBarRecyclerItem(val text: String, val rating: Float) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.RATING_BAR_CHILD
    }
}
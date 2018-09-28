package expandable.recycler.view

import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import expandable.recycler.view.databinding.ItemChildChronometerBinding
import expandable.recycler.view.databinding.ItemChildImageBinding
import expandable.recycler.view.databinding.ItemChildQuickContactBadgeBinding
import expandable.recycler.view.databinding.ItemParentBinding

class ExpandableRecyclerAdapter(context: Context, private val onExpanded: (position: Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val recyclerItems = mutableListOf<RecyclerItem>()

    private val inflater = LayoutInflater.from(context)

    private var isExpandedImage = false
    private var isExpandedChronometer = false
    private var isExpandedQuickContactBadge = false

    private val imageRecyclerItems = mutableListOf<ChildImageRecyclerItem>()
    private val chronometerRecyclerItems = mutableListOf<ChildChronometerRecyclerItem>()
    private val quickContactBadgeRecyclerItems = mutableListOf<ChildQuickContactBadgeRecyclerItem>()

    enum class RecyclerViewType(val value: Int) {
        IMAGE_PARENT(value = 1000),
        IMAGE_CHILD(value = 1001),
        CHRONOMETER_PARENT(value = 2000),
        CHRONOMETER_CHILD(value = 2001),
        QUICK_CONTACT_BADGE_PARENT(value = 3000),
        QUICK_CONTACT_BADGE_CHILD(value = 3001);
    }

    init {
        recyclerItems.also {
            it.add(ParentImageRecyclerItem(text = "Pure Eyes\n純粋さを、捨てない。"))
            it.add(ParentChronometerRecyclerItem(text = "Urban Cowgirl\n“私”で、生きてゆく。"))
            it.add(ParentQuickContactBadgeRecyclerItem(text = "Mystic Journey\n旅を、やめない。"))
        }

        imageRecyclerItems.addAll(listOf(
                ChildImageRecyclerItem(text = "瞳を閉じて", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "ジャコビニ彗星の日", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "スラバヤ通りの妹へ", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "雨の街を", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "緑の町に舞い降りて", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "セシルの週末", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "September Blue Moon", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "まずはどこへ行こう", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "ただわけもなく", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "海に来て", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "Summer Junction", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "きっと言える", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "Midnight Scarecrow", drawableRes = R.mipmap.ic_launcher),
                ChildImageRecyclerItem(text = "Autumn Park", drawableRes = R.mipmap.ic_launcher_round),
                ChildImageRecyclerItem(text = "雪だより", drawableRes = R.mipmap.ic_launcher)))

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

        quickContactBadgeRecyclerItems.addAll(listOf(
                ChildQuickContactBadgeRecyclerItem(text = "満月のフォーチュン"),
                ChildQuickContactBadgeRecyclerItem(text = "破れた恋の繕し方教えます"),
                ChildQuickContactBadgeRecyclerItem(text = "砂の惑星"),
                ChildQuickContactBadgeRecyclerItem(text = "朝陽の中で微笑んで"),
                ChildQuickContactBadgeRecyclerItem(text = "輪舞曲"),
                ChildQuickContactBadgeRecyclerItem(text = "ツバメのように"),
                ChildQuickContactBadgeRecyclerItem(text = "シャンソン"),
                ChildQuickContactBadgeRecyclerItem(text = "霧の中の影"),
                ChildQuickContactBadgeRecyclerItem(text = "AVALON"),
                ChildQuickContactBadgeRecyclerItem(text = "BABYLON"),
                ChildQuickContactBadgeRecyclerItem(text = "きみなき世界"),
                ChildQuickContactBadgeRecyclerItem(text = "Man in the Moon"),
                ChildQuickContactBadgeRecyclerItem(text = "SATURDAY NIGHT ZOMBIES"),
                ChildQuickContactBadgeRecyclerItem(text = "無限の中の一度"),
                ChildQuickContactBadgeRecyclerItem(text = "July")))
    }

    override fun getItemViewType(position: Int): Int = recyclerItems[position].viewType().value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when (viewType) {
                RecyclerViewType.IMAGE_PARENT.value -> ParentImageViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_parent, parent, false))
                RecyclerViewType.IMAGE_CHILD.value -> ChildImageViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_child_image, parent, false))
                RecyclerViewType.CHRONOMETER_PARENT.value -> ParentChronometerViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_parent, parent, false))
                RecyclerViewType.CHRONOMETER_CHILD.value -> ChildChronometerViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_child_chronometer, parent, false))
                RecyclerViewType.QUICK_CONTACT_BADGE_PARENT.value -> ParentQuickContactBadgeViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_parent, parent, false))
                else -> ChildQuickContactBadgeViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_child_quick_contact_badge, parent, false))
            }

    override fun getItemCount(): Int = recyclerItems.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is ParentImageViewHolder -> viewHolder.bind(recyclerItems[position] as ParentImageRecyclerItem)
            is ChildImageViewHolder -> viewHolder.bind(recyclerItems[position] as ChildImageRecyclerItem)
            is ParentChronometerViewHolder -> viewHolder.bind(recyclerItems[position] as ParentChronometerRecyclerItem)
            is ChildChronometerViewHolder -> viewHolder.bind(recyclerItems[position] as ChildChronometerRecyclerItem)
            is ParentQuickContactBadgeViewHolder -> viewHolder.bind(recyclerItems[position] as ParentQuickContactBadgeRecyclerItem)
            is ChildQuickContactBadgeViewHolder -> viewHolder.bind(recyclerItems[position] as ChildQuickContactBadgeRecyclerItem)
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

    inner class ParentImageViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParentImageRecyclerItem) {
            binding.textView.text = item.text

            itemView.setOnClickListener {
                if (isExpandedImage) {
                    removeRecyclerItems(RecyclerViewType.IMAGE_CHILD)
                } else {
                    addRecyclerItems(RecyclerViewType.IMAGE_PARENT, imageRecyclerItems)

                    onExpanded.invoke(findFirstPosition(RecyclerViewType.IMAGE_PARENT)!!)
                }

                isExpandedImage = !isExpandedImage
                rotateArrow()
            }

            rotateArrow()
        }

        private fun rotateArrow() {
            binding.arrow.rotation = if (isExpandedImage) 180f else 0f
        }
    }

    inner class ChildImageViewHolder(private val binding: ItemChildImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChildImageRecyclerItem) {
            binding.textView.text = item.text
            binding.imageView.setImageResource(item.drawableRes)
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

    inner class ParentQuickContactBadgeViewHolder(private val binding: ItemParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParentQuickContactBadgeRecyclerItem) {
            binding.textView.text = item.text

            itemView.setOnClickListener {
                if (isExpandedQuickContactBadge) {
                    removeRecyclerItems(RecyclerViewType.QUICK_CONTACT_BADGE_CHILD)
                } else {
                    addRecyclerItems(RecyclerViewType.QUICK_CONTACT_BADGE_PARENT, quickContactBadgeRecyclerItems)

                    onExpanded.invoke(findFirstPosition(RecyclerViewType.QUICK_CONTACT_BADGE_PARENT)!!)
                }

                isExpandedQuickContactBadge = !isExpandedQuickContactBadge
                rotateArrow()
            }

            rotateArrow()
        }

        private fun rotateArrow() {
            binding.arrow.rotation = if (isExpandedQuickContactBadge) 180f else 0f
        }
    }

    inner class ChildQuickContactBadgeViewHolder(private val binding: ItemChildQuickContactBadgeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChildQuickContactBadgeRecyclerItem) {
            binding.textView.text = item.text
            binding.quickContactBadge.setImageToDefault()
            binding.quickContactBadge.assignContactFromPhone("0120-000-000", true)
        }
    }

    interface RecyclerItem {
        fun viewType(): RecyclerViewType
    }

    inner class ParentImageRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.IMAGE_PARENT
    }

    inner class ChildImageRecyclerItem(val text: String, @DrawableRes val drawableRes: Int) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.IMAGE_CHILD
    }

    inner class ParentChronometerRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.CHRONOMETER_PARENT
    }

    inner class ChildChronometerRecyclerItem(val text: String, val millisecond: Long) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.CHRONOMETER_CHILD
    }

    inner class ParentQuickContactBadgeRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.QUICK_CONTACT_BADGE_PARENT
    }

    inner class ChildQuickContactBadgeRecyclerItem(val text: String) : RecyclerItem {
        override fun viewType(): RecyclerViewType = RecyclerViewType.QUICK_CONTACT_BADGE_CHILD
    }
}
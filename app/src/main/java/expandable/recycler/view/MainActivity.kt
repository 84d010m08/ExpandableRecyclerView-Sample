package expandable.recycler.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSmoothScroller
import expandable.recycler.view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = ExpandableRecyclerAdapter(this) { position ->
            // https://stackoverflow.com/a/43505830
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference() = SNAP_TO_START
            }
            smoothScroller.targetPosition = position
            binding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }

        // ItemDecoration を追加すると展開した直後に区切り線が見えてしまう
        //it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
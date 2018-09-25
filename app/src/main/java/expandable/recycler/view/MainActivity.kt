package expandable.recycler.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearSmoothScroller
import expandable.recycler.view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.adapter = ExpandableRecyclerAdapter(this) { position ->
            // https://stackoverflow.com/a/43505830
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int {
                    return LinearSmoothScroller.SNAP_TO_START
                }
            }
            smoothScroller.targetPosition = position
            binding.recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }

        // ItemDecoration を追加すると展開した直後に区切り線が見えてしまう
        //it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }
}
package kz.kuz.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainFragment : Fragment() {
    private lateinit var mMainRecyclerView: RecyclerView
    private lateinit var mAdapter: MainAdapter

//    private var positionToUpdate: Int = 1
    private inner class Item {
        var mTitle: String? = null
        var mPart1: String? = null
        var mPart2: String? = null
    }

    private val items: MutableList<Item> = ArrayList()

    // методы фрагмента должны быть открытыми
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        activity?.setTitle(R.string.toolbar_title)
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mMainRecyclerView = view.findViewById(R.id.main_recycler_view)
        mMainRecyclerView.layoutManager = LinearLayoutManager(activity)
        // layoutManager можно определить в самом XML
        for (i in 0..19) {
            val item = Item()
            item.mTitle = "Title #" + (i + 1)
            item.mPart1 = "Part1 #" + (i + 1)
            item.mPart2 = "Part2 #" + (i + 1)
            items.add(item)
        }
        mAdapter = MainAdapter(items)
        mMainRecyclerView.adapter = mAdapter
        return view
    }

    private inner class MainHolder(inflater: LayoutInflater, parent: ViewGroup?) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)),
            View.OnClickListener {
        private val mTitleTextView: TextView
        private val mPart1TextView: TextView
        private val mPart2TextView: TextView
        private lateinit var mItem: Item
        fun bind(item: Item) {
            mItem = item
            mTitleTextView.text = mItem.mTitle
            mPart1TextView.text = mItem.mPart1
            mPart2TextView.text = mItem.mPart2
        }

        override fun onClick(view: View) {
//            Toast.makeText(activity, mItem.mTitle + " clicked!", Toast.LENGTH_SHORT).show()
            mMainRecyclerView.adapter?.notifyItemMoved(adapterPosition, 0)
            mMainRecyclerView.scrollToPosition(0)
        }

        init {
            itemView.setOnClickListener(this) // реализуется слушатель на нажатие
            mTitleTextView = itemView.findViewById(R.id.item_title)
            mPart1TextView = itemView.findViewById(R.id.item_part1)
            mPart2TextView = itemView.findViewById(R.id.item_part2)
        }
    }

    private inner class MainAdapter(private val mItems: List<Item>) : RecyclerView.Adapter<MainHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val layoutInflater = LayoutInflater.from(activity)
            return MainHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
//            positionToUpdate = position;
            val item = mItems[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int {
            return mItems.size
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.notifyDataSetChanged() // обновление в случае изменений в списке
//        mAdapter.notifyItemChanged(positionToUpdate); // для обновления одной позиции
    }
}
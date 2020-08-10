
package ${packageName}

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.${itemLayout}.view.*

class ${itemAdapterClass} : RecyclerView.Adapter<${itemAdapterClass}.ViewHolder>() {

    private val items: ArrayList<DummyData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.${itemLayout}, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setRecyclerData(data: List<DummyData>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dummyData: DummyData) {
            itemView.tv_item.text = dummyData.data
        }
    }

    data class DummyData(
        val data: String
    )
}
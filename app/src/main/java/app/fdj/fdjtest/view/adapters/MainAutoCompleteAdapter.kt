package app.fdj.fdjtest.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.fdj.fdjtest.R
import app.fdj.fdjtest.entity.League
import kotlinx.android.synthetic.main.item_main_autocomplete.view.*

class MainAutoCompleteAdapter(private var data: List<League>?) :
    RecyclerView.Adapter<MainAutoCompleteAdapter.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvMainAutocomplete: TextView? = item.tv_main_autocomplete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_main_autocomplete, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            holder.tvMainAutocomplete?.text = it[position].name
        }
    }

    fun updateData(data: List<League>) {
        this.data = data
        this.notifyDataSetChanged()
    }
}
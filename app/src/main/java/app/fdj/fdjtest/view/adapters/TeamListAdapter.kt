package app.fdj.fdjtest.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.fdj.fdjtest.R
import app.fdj.fdjtest.entity.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_team.view.*

class TeamListAdapter(private val listener: (Team?) -> Unit, private var dataList: List<Team>?) : RecyclerView.Adapter<TeamListAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val image: ImageView? = item.img_league
        val tvId: TextView? = item.tv_id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_team, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId?.text = dataList?.let { it[position].id.toString() }
        Picasso.get().load(dataList?.let { it[position].badge }).into(holder.image)
        holder.itemView.setOnClickListener { listener(dataList?.get(position)) }
    }

    fun updateData(data: List<Team>) {
        this.dataList = data
        this.notifyDataSetChanged()
    }
}
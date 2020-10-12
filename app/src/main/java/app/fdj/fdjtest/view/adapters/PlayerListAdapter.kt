package app.fdj.fdjtest.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.fdj.fdjtest.R
import app.fdj.fdjtest.entity.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerListAdapter(private var data: List<Player>?) : RecyclerView.Adapter<PlayerListAdapter.ViewHolder>() {

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvName: TextView? = item.tv_name
        val tvPosition: TextView? = item.tv_position
        val tvBornAt: TextView? = item.tv_bornAt
        val imgPlayer: ImageView? = item.img_player
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return ViewHolder(item)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data?.let {
            holder.tvName?.text = it[position].name
            holder.tvPosition?.text = it[position].position
            holder.tvBornAt?.text = it[position].bornAt
            Picasso.get().load(it[position].thumb).into(holder.imgPlayer)
        }
    }

    fun updateData(data: List<Player>) {
        this.data = data
        this.notifyDataSetChanged()
    }
}

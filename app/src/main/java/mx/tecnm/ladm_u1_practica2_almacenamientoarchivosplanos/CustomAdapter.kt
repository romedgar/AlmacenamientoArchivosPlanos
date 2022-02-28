package mx.tecnm.ladm_u1_practica2_almacenamientoarchivosplanos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(private val list: ArrayList<String>,  itemListener: onItemClickListener): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var myListener : onItemClickListener = itemListener

    fun setOnClickListener(listener: onItemClickListener){
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v,myListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val plate = list[i]
        var plates = plate.split(" ")
        holder.name.text = plates[0]
        holder.category.text = plates[1]
        when (plates[1].lowercase()){
            "asian"->{holder.image.setImageResource(R.drawable.asian)}
            "mexican"->{holder.image.setImageResource(R.drawable.mexican)}
            "fast"->{holder.image.setImageResource(R.drawable.fast)}
            "sea"->{holder.image.setImageResource(R.drawable.seafood)}
            else->{
                holder.image.setImageResource(R.drawable.notfound)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(item: View, listener: onItemClickListener):RecyclerView.ViewHolder(item){
        var name: TextView
        var category: TextView
        var image: ImageView
        init {
            image = item.findViewById(R.id.plate_image)
            name = item.findViewById(R.id.plate_name)
            category = item.findViewById(R.id.plate_category)
            item.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
}
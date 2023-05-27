package pt.ismai.fytmproject.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ismai.fytmproject.R

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        val user : User = userList[position]
        holder.nickName.text = user.nickName
        holder.idade.text = user.idade
        holder.periodo.text = user.periodo

    }
    override fun getItemCount(): Int {
        return userList.size
    }

    public class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nickName : TextView = itemView.findViewById(R.id.tvnickName)
        val idade : TextView = itemView.findViewById(R.id.tvage)
        val periodo : TextView = itemView.findViewById(R.id.tvperiodo)
    }
}
package com.example.forhealthfullapp.bluetooth
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.forhealthfullapp.Paireddeviceslist
import com.example.forhealthfullapp.R

internal class PairedDeviceHolderAdapter(private var deviceList: List<ModelForPairedDevices>, private val listener: Paireddeviceslist) :
        RecyclerView.Adapter<PairedDeviceHolderAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }

        var devicename: TextView = view.findViewById(R.id.device_name)
        var deviceaddress: TextView = view.findViewById(R.id.device_address)

    }

    interface OnItemClickListener{

        fun onItemClick(position: Int){

        }

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cardviewforpaireddevices, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = deviceList[position]
        holder.devicename.text = movie.getName()
        holder.deviceaddress.text = movie.getAddress()

    }
    override fun getItemCount(): Int {
        return deviceList.size
    }
}

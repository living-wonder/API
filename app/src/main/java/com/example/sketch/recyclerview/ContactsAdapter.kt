package com.example.sketch.recyclerview
import com.example.sketch.R
import android.widget.*
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import android.content.Context

/**
 * Created by mac on 21/01/24.
 */
class ContactsAdapter(private val contactsList:ArrayList<Contacts>):RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    class ContactsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val icon:ImageView=itemView.findViewById(R.id.iv_contactIcon)
        val contactname:TextView=itemView.findViewById(R.id.tv_contactName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.contact_layout,parent,false)
        return ContactsViewHolder(view)

    }

    override fun getItemCount(): Int {
          return contactsList.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contacts =contactsList[position]
        holder.icon.setImageResource(contacts.image)
        holder.contactname.text=contacts.name

    }
}
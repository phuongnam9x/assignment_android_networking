package com.fpoly.code4fun.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assigment.R
import com.example.assigment.data.model.Product
import kotlinx.android.synthetic.main.item_recycler_product.view.*

class HomeAdapter(private val fragment: Fragment) :
    ListAdapter<Product, HomeAdapter.ViewHolder>(ProductDiffUtilCallback()) {
    var onItemClick: (Product) -> Unit = { _ -> }
    var onDelete: (String) -> Unit = { _ -> }

    override fun getItemViewType(position: Int) = R.layout.item_recycler_product

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view=layoutInflater.inflate(R.layout.item_recycler_product,parent,false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindData(getItem(position))
    }

    override fun submitList(items: List<Product>?) {
        super.submitList(items ?: emptyList())
    }

    inner class ViewHolder(
        private val view: View,
        private var onItemClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun onBindData(itemData: Product) {
            itemView.run{
                productTitleTextView.text=itemData.title
                productDeceptionTextView.text=itemData.deception
                productPrimeTextView.text=itemData.price  +  "  VND"
                Glide
                    .with(this.context)
                    .load("http://192.168.1.53:5000/public/image/"+itemData.imagePath)
                    .centerCrop()
                    .placeholder(android.R.color.black)
                    .into(productImageView);
            }
            itemView.setOnClickListener {
                onItemClick(itemData)
            }
            itemView.deleteProductButton.setOnClickListener {
              onDelete(itemData.id)
            }
        }
    }

    class ProductDiffUtilCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
            oldItem.id == newItem.id
    }
}
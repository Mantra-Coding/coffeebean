package com.example.coffeebean.orders.list


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeebean.coffee.success.list.OrderDiffUtils
import com.example.coffeebean.coffee.success.list.OrderViewHolder
import com.example.coffeebean.database.entities.Product
import com.example.coffeebean.databinding.OrderDayItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

const val TIME_VIEW = 0
const val ITEM_VIEW = 1

class OrdersAdapter() : ListAdapter<Product, RecyclerView.ViewHolder>(OrderDiffUtils()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    @SuppressLint("SimpleDateFormat")
    fun createList(productsList: List<Product>) {
        adapterScope.launch {

            var secondTime: String
            var firstTime: String
            val list = productsList.toMutableList()

            if (list.isNotEmpty()) {
                list.add(0, list[0])

                for (i in 0..list.lastIndex) {

                    if (i > 0) {

                        firstTime =
                            SimpleDateFormat("dd/MM/yyyy").format(list[i].currentDayInMillis)
                                .toString()

                        secondTime =
                            SimpleDateFormat("dd/MM/yyyy").format(list[i - 1].currentDayInMillis)
                                .toString()

                        if (firstTime != secondTime)
                            list.add(i, list[i])

                    }
                }
            }
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TIME_VIEW -> TimeViewHolder.from(parent)
            ITEM_VIEW -> OrderViewHolder.from(parent)
            else -> throw ClassCastException("UNKNOWN VIEWHOLDER")
        }
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val firstTime =
            SimpleDateFormat("dd/MM/yyyy").format(item.currentDayInMillis).toString()
        when (holder) {
            is OrderViewHolder -> holder.bind(item)

            is TimeViewHolder -> holder.bind(firstTime)
        }

    }


    @SuppressLint("SimpleDateFormat")
    override fun getItemViewType(position: Int): Int {


        return if (position == 0) {
            TIME_VIEW
        } else {
            val itemFirst = getItem(position)
            val secondItem = getItem(position - 1)

            val firstTime =
                SimpleDateFormat("dd/MM/yyyy").format(itemFirst.currentDayInMillis).toString()
            val secondTime =
                SimpleDateFormat("dd/MM/yyyy").format(secondItem.currentDayInMillis).toString()

            if (position > 0 && firstTime == secondTime)
                ITEM_VIEW
            else
                if (position == currentList.lastIndex)
                    ITEM_VIEW
                else
                    TIME_VIEW
        }
    }
}

class TimeViewHolder private constructor(val binding: OrderDayItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(time: String) {
        binding.date = time
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TimeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = OrderDayItemBinding.inflate(inflater, parent, false)
            return TimeViewHolder(binding)
        }
    }
}
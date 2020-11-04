package com.example.coffeebean.database.entities

import androidx.room.*

@Entity(tableName = "table_product", foreignKeys = [ForeignKey(
    entity = User::class,
    parentColumns = ["userId"],
    childColumns = ["userId"],
    onDelete = ForeignKey.RESTRICT,
    onUpdate = ForeignKey.CASCADE)],
    indices = [Index(value = ["product_name", "product_description", "product_amount", "product_price", "userId"])]
)
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    var productId: Long = 0L,

    @ColumnInfo(name = "product_name")
    var name: String,

    @ColumnInfo(name = "product_description")
    var description: String,

    @ColumnInfo(name = "product_amount")
    var amount: Int,

    @ColumnInfo(name = "product_price")
    var price: Double,

    @ColumnInfo(name = "userId", index = true)
    var userId: Long,

    @ColumnInfo(name = "image_id")
    var imageId: Int,

    @ColumnInfo(name = "day_order")
    var currentDayInMillis: Long = System.currentTimeMillis()
){

}
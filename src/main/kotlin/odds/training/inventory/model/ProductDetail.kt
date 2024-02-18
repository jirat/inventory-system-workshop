package odds.training.inventory.model

data class ProductDetail(
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)
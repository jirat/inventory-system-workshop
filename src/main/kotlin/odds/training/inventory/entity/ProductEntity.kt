package odds.training.inventory.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("product")
data class ProductEntity(
    @Id
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)

package odds.training.inventory.repository

import odds.training.inventory.entity.ProductEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductInventoryRepository : MongoRepository<ProductEntity, String> {
    fun findByName(name: String): Optional<ProductEntity>
}
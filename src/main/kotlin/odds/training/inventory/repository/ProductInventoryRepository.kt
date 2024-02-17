package odds.training.inventory.repository

import odds.training.inventory.entity.ProductEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductInventoryRepository : MongoRepository<ProductEntity, String>
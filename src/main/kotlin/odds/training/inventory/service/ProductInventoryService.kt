package odds.training.inventory.service

import odds.training.inventory.model.Product
import odds.training.inventory.repository.ProductInventoryRepository
import org.springframework.stereotype.Service

@Service
class ProductInventoryService(
    private val productInventoryRepository: ProductInventoryRepository
) {
    fun getAllProducts(): List<Product> =
        productInventoryRepository.findAll()
            .map {
                Product(
                    it.id,
                    it.name,
                )
            }.toList()
}
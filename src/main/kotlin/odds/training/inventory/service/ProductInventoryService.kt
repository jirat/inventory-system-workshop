package odds.training.inventory.service

import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.model.Product
import odds.training.inventory.model.ProductDetail
import odds.training.inventory.repository.ProductInventoryRepository
import org.springframework.stereotype.Service
import java.util.*

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

    fun addNewProduct(productDetail: ProductDetail): String {
        // Guard Cause
        if (productDetail.stock < 0) {
            return "Stock cannot be negative"
        }

        if (productDetail.price < 0.0) {
            return "Price cannot be negative"
        }

        val checkDuplicate = productInventoryRepository.findByName(productDetail.name)
        if (checkDuplicate.isPresent) {
            return "Product name cannot be duplicate"
        }

        //สร้าง entity สำหรับ save เข้า database
        val productEntity = ProductEntity(
            UUID.randomUUID().toString(),
            productDetail.name,
            productDetail.description,
            productDetail.price,
            productDetail.stock
        )
        // save เข้า database
        val saveEntity = productInventoryRepository.save(productEntity)

        // ส่ง id ออกไป
        return saveEntity.id
    }
}
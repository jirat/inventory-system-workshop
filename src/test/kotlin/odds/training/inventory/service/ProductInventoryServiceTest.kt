package odds.training.inventory.service

import io.mockk.every
import io.mockk.mockk
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.model.Product
import odds.training.inventory.repository.ProductInventoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProductInventoryServiceTest {
    private val productInventoryRepository: ProductInventoryRepository = mockk()
    private val productInventoryService = ProductInventoryService(productInventoryRepository)

    @Nested
    inner class GetAllProduct {
        @Test
        fun `should return empty list when there is no product in inventory`() {
            //Given
            val expect = emptyList<Product>()
            every { productInventoryRepository.findAll() } returns emptyList()

            //When
            val result = productInventoryService.getAllProducts()

            //Then
            assertEquals(expect, result)
        }

        @Test
        fun `should return product list when has product in inventory`() {
            //Given
            val expect = listOf(Product("1", "item A"))
            every { productInventoryRepository.findAll() } returns listOf(
                ProductEntity(
                    "1",
                    "item A",
                    "A",
                    200.32,
                    10
                )
            )

            //When
            val result = productInventoryService.getAllProducts()

            //Then
            assertEquals(expect, result)
        }
    }
}
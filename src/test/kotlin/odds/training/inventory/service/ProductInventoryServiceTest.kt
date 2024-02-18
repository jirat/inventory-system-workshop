package odds.training.inventory.service

import io.mockk.every
import io.mockk.mockk
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.model.Product
import odds.training.inventory.model.ProductDetail
import odds.training.inventory.repository.ProductInventoryRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

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

    @Nested
    inner class AddProductTest {
        @Test
        fun `should return id when successfully add the product to inventory`() {
            //Given
            val mockEntity = ProductEntity(
                "mockId",
                "newly added product",
                "description of the product",
                10.0,
                500
            )
            every {
                productInventoryRepository.save(any())
            } returns mockEntity
            every {
                productInventoryRepository.findByName(any())
            } returns Optional.empty()

            //When
            val actual = productInventoryService.addNewProduct(
                ProductDetail("newly added product",
                    "description of the product",
                    10.0,
                    500 )
            )

            //Then
            assertEquals("mockId", actual)
        }

        @Test
        fun `should return cannot be negative when trying to add negative stock`() {
            // Given
            val inputProduct = ProductDetail("newly added product",
                "description of the product",
                10.0,
                -500 )

            //When
            val actual = productInventoryService.addNewProduct(
                inputProduct
            )

            //Then
            assertEquals("Stock cannot be negative", actual)
        }

        @Test
        fun `should return cannot be duplicate when trying to add exising product name`() {
            // Given
            val inputProduct = ProductDetail("newly added product",
                "description of the product",
                10.0,
                500 )
            every { productInventoryRepository.findByName("newly added product") } returns
                    Optional.of(
                        ProductEntity("mockId",
                            "newly added product",
                            "description of the product",
                            10.0,
                            500 )
                    )

            //When
            val actual = productInventoryService.addNewProduct(
                inputProduct
            )

            //Then
            assertEquals("Product name cannot be duplicate", actual)
        }

        @Test
        fun `should return cannot be negative when trying to add negative price`() {
            // Given
            val inputProduct = ProductDetail("newly added product",
                "description of the product",
                -10.0,
                500 )

            //When
            val actual = productInventoryService.addNewProduct(
                inputProduct
            )

            //Then
            assertEquals("Price cannot be negative", actual)
        }
    }
}
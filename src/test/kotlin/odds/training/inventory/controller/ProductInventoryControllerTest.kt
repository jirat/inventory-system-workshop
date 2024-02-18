package odds.training.inventory.controller

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.model.ProductDetail
import odds.training.inventory.repository.ProductInventoryRepository
import odds.training.inventory.service.ProductInventoryService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class ProductInventoryControllerTest {

    private val repository: ProductInventoryRepository = mockk()
    private val service = ProductInventoryService(repository)
    private val controller = ProductInventoryController(service)

    @Test
    fun `should return id when successfully added new product`() {
        // Given
        every { repository.save(any()) } returns ProductEntity(
            "mockIntegrationId",
            "mock product name",
            "mock product description",
            100.0,
            10
        )
        every { repository.findByName(any()) } returns Optional.empty()

        // When
        val actual = controller.addProduct(ProductDetail(
            "mock product name",
            "mock product description",
            100.0,
            10
        ))

        // Then
        assertEquals("mockIntegrationId", actual)
        verify(exactly = 1) { repository.save(any()) }
    }

}
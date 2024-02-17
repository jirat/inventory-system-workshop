package odds.training.inventory.service

import io.mockk.every
import io.mockk.mockk
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.repository.ProductInventoryRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ProductInventoryServiceTestMockK {

    private val productInventoryRepository: ProductInventoryRepository = mockk()

    private val productInventoryService = ProductInventoryService(productInventoryRepository)

    @Test
    fun `should return all products if there are product in inventory`() {
        every { productInventoryRepository.findAll() } returns
            listOf(
                ProductEntity(
                    ObjectId("65d061e1ef588b72bd31f2ba"),
                    "mockProduct",
                    "mockDescription",
                    100.0,
                    100
                )
            )

        val actual = productInventoryService.getAllProducts()

        assertEquals(1, actual.size)
        assertEquals("mockProduct", actual[0].name)
    }

    @Test
    fun `should return empty list if there are no product in inventory`() {
        every { productInventoryRepository.findAll() } returns listOf()

        val actual = productInventoryService.getAllProducts()

        assertEquals(0, actual.size)
    }
}
package odds.training.inventory.controller

import io.mockk.every
import io.mockk.mockk
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.repository.ProductInventoryRepository
import odds.training.inventory.service.ProductInventoryService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class ProductInventoryControllerTestWithoutSpringBootTest {

    private val productInventoryRepository: ProductInventoryRepository = mockk()

    private val productInventoryService = ProductInventoryService(productInventoryRepository)

    private val controller = ProductInventoryController(productInventoryService)

    @Test
    fun `should return a list of product if the inventory is not empty`() {
        every { productInventoryRepository.findAll() } returns listOf(
            ProductEntity(
                ObjectId("65d061e1ef588b72bd31f2ba"),
                "mock product name 1",
                "mock product 1 description",
                100.0,
                100
            ),
            ProductEntity(
                ObjectId("65d234e1ef588b72bd31f3ca"),
                "mock product name 2",
                "mock product 2 description",
                200.0,
                500
            ),
        )

        val actual = controller.getAllProductFromInventory()

        assertEquals(2, actual.size)
    }

    @Test
    fun `should return an empty list of product if the inventory is empty`() {
        every { productInventoryRepository.findAll() } returns listOf()

        val actual = controller.getAllProductFromInventory()

        assertEquals(0, actual.size)
    }
}
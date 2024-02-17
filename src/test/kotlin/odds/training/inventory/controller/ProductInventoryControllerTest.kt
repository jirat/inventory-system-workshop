package odds.training.inventory.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.repository.ProductInventoryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
class ProductInventoryE2ETest {
    companion object {
        @Container
        val mongoContainer = MongoDBContainer(DockerImageName.parse("mongo:4.4.6"))

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { mongoContainer.replicaSetUrl }
        }
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var productInventoryRepository: ProductInventoryRepository

    private val mapper = jacksonObjectMapper().findAndRegisterModules()

    @Test
    fun `Should get all product`() {

        mockMvc.perform(get("/inventory/all"))
            .andExpect(status().isOk)
            .andExpect(content().string("[]"))
    }

    @Test
    fun `Should get all product when data not empty`() {
        productInventoryRepository.save(
            ProductEntity(
                id = "65d0ed092132f7cdeb079e14",
                name = "A",
                description = "a",
                price = 200.0,
                stock = 10
            )
        )


        mockMvc.perform(get("/inventory/all"))
            .andExpect(status().isOk)
            .andExpect(content().string("[{\"id\":\"65d0ed092132f7cdeb079e14\",\"name\":\"A\"}]"))
            .andExpect(jsonPath("$[0].name").value("A"))
            .andExpect(jsonPath("$.length()").value(1))
    }
}
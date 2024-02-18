package odds.training.inventory.controller

import com.fasterxml.jackson.databind.ObjectMapper
import odds.training.inventory.entity.ProductEntity
import odds.training.inventory.model.ProductDetail
import odds.training.inventory.repository.ProductInventoryRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@AutoConfigureMockMvc
@SpringBootTest
class ProductInventoryControllerIntegrationTest(@Autowired val mockMvc: MockMvc) {

    @MockBean
    private lateinit var repository: ProductInventoryRepository

    val mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()

    @Test
    fun `should return id when successfully added new product`() {
        val productDetail = ProductDetail(
            "mock product name",
            "mock product description",
            100.0,
            10
        )

        `when`(repository.save(any())).thenReturn(
            ProductEntity(
                "mockIntegrationId",
                "mock product name",
                "mock product description",
                100.0,
                10
            )
        )

        `when`(repository.findByName(anyString())).thenReturn(Optional.empty())

        mockMvc.perform(
            post("/inventory")
                .content(mapper.writeValueAsString(productDetail))
                .contentType("application/json")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").value("mockIntegrationId"))
    }

}
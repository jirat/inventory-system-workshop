package odds.training.inventory.controller

import odds.training.inventory.model.ProductDetail
import odds.training.inventory.service.ProductInventoryService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductInventoryController(
    private val service: ProductInventoryService
) {

    @GetMapping("inventory/all")
    fun getAllProductFromInventory() =
        service.getAllProducts()

    @GetMapping("inventory/{productId}")
    fun getProductById(@PathVariable productId: String) {

    }

    @PostMapping("inventory")
    fun addProduct(@RequestBody productDetail: ProductDetail): String {
        return service.addNewProduct(productDetail)
    }

    @PutMapping("inventory/{productId}")
    fun updateProduct(@PathVariable productId: String,
                      @RequestBody productDetail: ProductDetail) {

    }

    @DeleteMapping("inventory/{productId}")
    fun deleteProduct(@PathVariable productId: String) {

    }
}

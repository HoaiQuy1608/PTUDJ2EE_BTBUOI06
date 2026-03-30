package phattrienungdungvoij2ee.baitap4_qlsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdungvoij2ee.baitap4_qlsp.model.Product;
import phattrienungdungvoij2ee.baitap4_qlsp.service.CategoryService;
import phattrienungdungvoij2ee.baitap4_qlsp.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> productList = productService.getAllProducts();
        model.addAttribute("listproduct", productList);
        return "product/products";
    }

    @GetMapping("/create")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "product/create";
    }

    @PostMapping("/create")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageProduct") MultipartFile imageProduct) {
        productService.handleImageUpload(product, imageProduct);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "product/edit";
        }
        return "redirect:/products";
    }

    @PostMapping("/edit")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("imageProduct") MultipartFile imageProduct) {
        Product existingProduct = productService.getProductById(product.getId());
        if (existingProduct != null) {
            if (!imageProduct.isEmpty()) {
                productService.handleImageUpload(product, imageProduct);
            } else {
                product.setImage(existingProduct.getImage());
            }
            productService.saveProduct(product);
        }
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
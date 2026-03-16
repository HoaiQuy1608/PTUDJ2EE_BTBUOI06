package phattrienungdungvoij2ee.baitap4_qlsp.service;

import java.nio.file.Path; // Đã sửa đổi dòng này
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phattrienungdungvoij2ee.baitap4_qlsp.model.Product;
import phattrienungdungvoij2ee.baitap4_qlsp.repository.CategoryRepository;
import phattrienungdungvoij2ee.baitap4_qlsp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public void handleImageUpload(Product product, MultipartFile imageProduct) {
        if (imageProduct != null && !imageProduct.isEmpty()) {
            try {
                Path dirImages = Paths.get("static/images");
                if (!Files.exists(dirImages)) {
                    Files.createDirectories(dirImages);
                }
                String newFileName = UUID.randomUUID() + "_" + imageProduct.getOriginalFilename();
                Path pathFileUpload = dirImages.resolve(newFileName);
                Files.copy(imageProduct.getInputStream(), pathFileUpload, StandardCopyOption.REPLACE_EXISTING);

                product.setImage(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

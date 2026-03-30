package phattrienungdungvoij2ee.baitap4_qlsp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phattrienungdungvoij2ee.baitap4_qlsp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

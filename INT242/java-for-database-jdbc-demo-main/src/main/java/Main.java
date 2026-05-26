import dao.imp.ProductDao;
import entities.Product;

import java.sql.SQLException;
import java.util.List;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();

        System.out.println("--- Testing findMany() ---");
        try {

            // get all product
            List<Product> products = productDao.findMany();
            if (products.isEmpty()) {
                System.out.println("No products found or table is empty.");
            } else {
                System.out.println("Found " + products.size() + " products:");
                for(Product row_rsProduct : products) {
                    System.out.println(row_rsProduct.toString());
                }
            }

            // get product by id
            Integer productId = 1;
            Optional<Product> prod = productDao.findById(productId);
            if(prod.isPresent()) {
                System.out.println(prod.get());
            }else {
                System.out.println("Not found product id 1");
            }

            // add product
            Product newProduct = new Product(null, "Apple Phone", 30);
            boolean insertSuccess = productDao.save(newProduct);
            if(insertSuccess) {
                // java runtime จะไปดูให้เองว่ามี toString แล้วมันไปเรียกให้
                System.out.println("inserted!!" + newProduct);
            }else {
                System.out.println("Can't insert!! " + newProduct);
            }

            // update product
            Product product02 = new Product(null, "Computer", 100);
            boolean updateSuccess = productDao.update(1, product02);
            if(updateSuccess) {
                System.out.println("updated!! " + newProduct);
            }else {
                System.out.println("can't update!! " + newProduct);
            }


        } catch (SQLException e) {
            System.err.println("Database issue: " + e.getMessage());
            // e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error while calling findMany(): " + e.getMessage());
        }
    }
}

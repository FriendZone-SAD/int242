package dao.imp;

import entities.Product;
import dao.interfaces.JdbcDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.Connection;

import utils.jdbc.ConnectionFactory;


public class ProductDao implements JdbcDao<Product, Integer> {
    @Override
    public Optional<Product> findById(Integer id) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products WHERE id = ?");

        // Parameter Binding
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
            return Optional.of(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("stock")
            ));
        }

        return Optional.empty();
    }

    @Override
    public List<Product> findMany() throws Exception {
            List<Product> prods = new ArrayList<>();
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM products");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Product prod = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("stock"));
                prods.add(prod);
            }

        return prods;
    }

    @Override
    public boolean save(Product productData) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);
            // บอกให้ Database ส่งค่า ID ที่เพิ่งสร้างใหม่คืนมาให้ หลังจากที่มันเขียนข้อมูลลง Disk
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO `products`(`name`, `stock`) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            // Parameter Binding
            stmt.setString(1, productData.getName());
            stmt.setInt(2, productData.getStock());

            int rowsAffected = stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                productData.setId(generatedKeys.getInt(1));
            }

            conn.commit();
            return rowsAffected > 0;
        }catch(Exception e) {
            if(conn == null) {
                throw e;
            }
            conn.rollback();
            throw e;
        }
    }

    @Override
    public boolean update(Integer productId, Product prod) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement("UPDATE products SET name = ?, stock = ? WHERE id = ?");

            // Parameter Binding
            stmt.setString(1, prod.getName());
            stmt.setInt(2, prod.getStock());
            stmt.setInt(3, productId);

            int rowAffected = stmt.executeUpdate();
            conn.commit();
            return rowAffected > 0;

        }catch(Exception e) {
            if(conn == null) {
                throw e;
            }
            conn.rollback();
            throw e;
        }
    }

}

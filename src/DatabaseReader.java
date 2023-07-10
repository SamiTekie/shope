import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseReader {
    private static final String URL = "jdbc:mysql://localhost:3306/shope";
    private static final String USERNAME = "root";
    private static final String PASSWORD = null;

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Read products and categories from the database
            List<Product> products = readProductsFromDatabase();
            List<Product_Category> categories = readCategoriesFromDatabase();

            // Print products from the Product table
            System.out.println("--------------- Data from Product table ---------------");
            for (Product product : products) {
                System.out.println(product.getId() + "-" + product.getProductName() + "-" +
                        product.getProductPrice() + "-" + product.getProductDescription());
            }
            System.out.println("-------------------------------------------------------");

            // Print categories from the Product_Category table
            System.out.println("------------ Data from Product_Category table ------------");
            for (Product_Category category : categories) {
                System.out.println(category.getProductCategoryId() + "-" +
                        category.getProductCategoryName());
            }

            // Example usage of insertion, updating, and deleting methods
            insertProductIntoDatabase("headwear", 9.99, "clothing that is worn on the head.", 1);

            updateProductPriceInDatabase(products.get(5).getId(), 9.99);

            deleteProductFromDatabase(products.get(products.size() - 1).getId());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Product> readProductsFromDatabase() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT p.product_id, p.product_name, p.product_price, p.product_description, c.product_category_name " +
                    "FROM product p " +
                    "INNER JOIN product_category c ON p.product_category_id = c.product_category_id";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                String description = resultSet.getString("product_description");
                String categoryName = resultSet.getString("product_category_name");

                Product product = new Product();
                product.setId(productId);
                product.setProductName(name);
                product.setProductPrice(price);
                product.setProductDescription(description);
                product.setProductCategoryName(categoryName);

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    private static List<Product_Category> readCategoriesFromDatabase() {
        List<Product_Category> categories = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM product_category");

            while (resultSet.next()) {
                int id = resultSet.getInt("product_category_id");
                String name = resultSet.getString("product_category_name");

                Product_Category category = new Product_Category(id, name);
                categories.add(category);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    private static void insertProductIntoDatabase(String productName, double productPrice, String productDescription, int categoryId) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO product (product_name, product_price, product_description, product_category_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, productName);
            statement.setDouble(2, productPrice);
            statement.setString(3, productDescription);
            statement.setInt(4, categoryId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product inserted successfully.");
            } else {
                System.out.println("Failed to insert product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProductPriceInDatabase(int productId, double newPrice) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE product SET product_price = ? WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, newPrice);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product updated successfully.");
            } else {
                System.out.println("Failed to update product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteProductFromDatabase(int productId) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM product WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
            } else {
                System.out.println("Failed to delete product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

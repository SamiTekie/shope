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

            // Declare two lists: products and categories
            List<Product> products = readProductsFromDatabase();
            List<Product_Category> categories = readCategoriesFromDatabase();

            // Loop through the products list and print the product name and price.
            System.out.println("---------------data from Product table---------------");
            for (Product product : products) {
                System.out.println(product.getId() + "-" + product.getProductName() + "-" +
                        product.getProductPrice() + "-" + product.getProductDescription() +
                        "-" + product.getP());
            }
            System.out.println("-----------------------------------------------------------------------------");

            System.out.println("---------------data from Product_Category table---------------");
            // Loop through the categories list and print the category names.
            for (Product_Category category : categories) {
                System.out.println(category.getProduct_category_id() + "-" +
                        category.getProduct_category_name());
            }

            // Example usage of insertion, updating, and deleting methods
            Product newProduct = new Product();
            newProduct.setProductName("headwear");
            newProduct.setProductPrice(9.99);
            newProduct.setProductDescription("clothing that are worn on the head.");
            newProduct.setP(getCategoryById(1));

            insertProductIntoDatabase(newProduct);

            Product productToUpdate = products.get(5);
            productToUpdate.setProductPrice(9.99);
            updateProductInDatabase(productToUpdate);

            Product productToDelete = products.get(products.size() - 1);
            deleteProductFromDatabase(productToDelete);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Product> readProductsFromDatabase() {
        List<Product> products = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM product";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                String description = resultSet.getString("product_description");
                int categoryId = resultSet.getInt("product_category_id");

                Product_Category category = getCategoryById(categoryId);

                Product product = new Product();
                product.setId(productId);
                product.setProductName(name);
                product.setProductPrice(price);
                product.setProductDescription(description);
                product.setP(category);

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

    private static Product_Category getCategoryById(int categoryId) {
        Product_Category category = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM product_category WHERE product_category_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, categoryId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("product_category_id");
                String name = resultSet.getString("product_category_name");

                category = new Product_Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    private static void insertProductIntoDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO product (product_name, product_price," +
                    " product_description, product_category_id)" +
                    " VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getProductPrice());
            statement.setString(3, product.getProductDescription());
            statement.setInt(4, product.getP().getProduct_category_id());

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


    private static void updateProductInDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE product SET product_price = ? WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, product.getProductPrice());
            statement.setInt(2, product.getId());

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

    private static void deleteProductFromDatabase(Product product) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String sql = "DELETE FROM product WHERE product_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getId());

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

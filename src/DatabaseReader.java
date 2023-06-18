import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseReader {
    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver class
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Declare two lists: products and categories
            List<Product> products = readProductsFromDatabase();
            List<Product_Category> categories = readCategoriesFromDatabase();

            // Loop through the products list and print the product name and price.
            for (Product product : products) {
                System.out.println(product.getProduct_name() + "-" + product.getProduct_price());
            }

            // Loop through the categories list and print the category names.
            for (Product_Category category : categories) {
                System.out.println(category.getProduct_category_name());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private static List<Product> readProductsFromDatabase() {
        List<Product> products = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/shope";
        String username = "root";
        String password = null;

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM product";
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                int categoryId = resultSet.getInt("product_category_id");

                Product_Category category = getCategoryById(categoryId);

                Product product = new Product();
                product.setId(productId);
                product.setName(name);
                product.setPrice(price);
                product.setCategory(category);

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    // Define a private method readCategoriesFromDatabase that returns a list of Product_Category objects.
    private static List<Product_Category> readCategoriesFromDatabase() {
        List<Product_Category> categories = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/shope";
        String username = "root";
        String password = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
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

    // Define a private method getCategoryById that retrieves a category by its ID from the database
    // and returns an instance of Product_Category.
    private static Product_Category getCategoryById(int categoryId) {
        Product_Category category = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String url = "jdbc:mysql://localhost:3306/shope";
        String username = "root";
        String password = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM product_category WHERE product_category_id = ?";  // Corrected column name
            statement = connection.prepareStatement(query);
            statement.setInt(1, categoryId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("product_category_id");  // Corrected column name
                String name = resultSet.getString("product_category_name");

                category = new Product_Category(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return category;
    }
}

/* Here are the steps and actions taken in the provided code:

1. Import the necessary classes: `java.sql.*` for database connectivity and `java.util.ArrayList` and
`java.util.List` for managing lists.

2. Define the `DatabaseReader` class.

3. Define the `main` method which serves as the entry point of the program.
   - Inside the `main` method:
     - Load the MySQL JDBC driver class by calling `Class.forName("com.mysql.cj.jdbc.Driver")`.
     - Declare two lists: `products` and `categories` to store the retrieved data from the database.
     - Call the `readProductsFromDatabase` method to retrieve the products and assign the result to the
     `products` list.
     - Call the `readCategoriesFromDatabase` method to retrieve the categories and assign the result to the
     `categories` list.
     - Iterate over the `products` list and print the product name and price.
     - Iterate over the `categories` list and print the category names.

4. Define the `readProductsFromDatabase` method, which retrieves products from the database and
returns a list of `Product` objects.
   - Inside the method:
     - Create an empty `products` list to store the retrieved products.
     - Establish a database connection using the provided URL, username, and password.
     - Prepare a SQL statement with a parameter placeholder to retrieve a specific product by its ID.
     - Set the value for the parameter placeholder using the `setId` method of the prepared statement.
     - Execute the query and obtain the result set.
     - Iterate over the result set and extract the product information.
     - Create a `Product` object and set its attributes.
     - Add the product to the `products` list.
     - Close the result set, statement, and connection.
     - Catch any `SQLException` that may occur and print the stack trace.
     - Return the `products` list.

5. Define the `readCategoriesFromDatabase` method, which retrieves categories from the database and returns
a list of `Product_Category` objects.
   - Inside the method:
     - Create an empty `categories` list to store the retrieved categories.
     - Establish a database connection using the provided URL, username, and password.
     - Create a statement to execute the SQL query to retrieve all categories.
     - Execute the query and obtain the result set.
     - Iterate over the result set and extract the category information.
     - Create a `Product_Category` object and add it to the `categories` list.
     - Close the result set and statement.
     - Catch any `SQLException` that may occur and print the stack trace.
     - Return the `categories` list.

6. Define the `getCategoryById` method, which retrieves a category by its ID from the database and
returns an instance of `Product_Category`.
   - Inside the method:
     - Initialize the `category` variable to `null`.
     - Establish a database connection using the provided URL, username, and password.
     - Prepare a SQL statement with a parameter placeholder to retrieve a specific category by its ID.
     - Set the value for the parameter placeholder using the `setId` method of the prepared statement.
     - Execute the query and obtain the result set.
     - If the result set has a next row, extract the category information and create a `Product_Category` object.
     - Close the result set, statement, and connection.
     - Catch any `SQLException` that may occur and print the stack trace.
     - Return the `category` object.

Note: The code assumes a MySQL database with specific table names and column names. Make sure to adjust them
accordingly to match your database structure.*/
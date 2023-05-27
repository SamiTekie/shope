import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/shope";
        String username ="root";
        String password =null;

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
          // A statement object to execute SQL queries
            Statement statement = connection.createStatement();

            // Execute the SELECT query
            String sql = "SELECT * FROM product_category";
            ResultSet resultSet = statement.executeQuery(sql);

            // Step 4: Process the result set
            while (resultSet.next()) {
                // Access individual columns using column names or indices
                int column1 = resultSet.getInt("product_category_id");
                String column2 = resultSet.getString("product_category_name");

                // Do something with the data (e.g., print it)
                System.out.println("Column 1: " + column1);
                System.out.println("Column 2: " + column2);
                System.out.println();
            }
           // Insert operation
           Statement insertStatement = connection.createStatement();
           String insertQuery = "INSERT INTO product (product_name,product_description,product_price,product_category_id )" +
                    " VALUES ('UnderArmer ', 'NUnder armer  2023',55.8,1)";
           insertStatement.executeUpdate(insertQuery);
            System.out.println("Insert operation successful.");

            // Update operation
           Statement updateStatement = connection.createStatement();
          String updateQuery = "UPDATE product SET product_name = 'Adidas' WHERE product_category_id  =2";
            updateStatement.executeUpdate(updateQuery);
           System.out.println("Update operation successful.");

            // Delete operation
            Statement deleteStatement = connection.createStatement();
            String deleteQuery = "DELETE FROM product WHERE product_Id   =8";
            deleteStatement.executeUpdate(deleteQuery);
            System.out.println("Delete operation successful.");

            // Close the result set and statement
            resultSet.close();
            statement.close();
            // Close the statements and connection
            insertStatement.close();
           updateStatement.close();
            deleteStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
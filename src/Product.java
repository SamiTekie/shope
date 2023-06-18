import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String product_name;
    private double product_price;
    private String product_description;
    private int product_category_id;


    public void setName(String name) {
    }

    public void setPrice(double price) {
    }

    public void setCategory(Product_Category category) {
    }
}

import lombok.*;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {
    private int id;
    private String productName;
    private double productPrice;
    private String productDescription;
    private int productCategoryId;

    private Product_Category p;

}

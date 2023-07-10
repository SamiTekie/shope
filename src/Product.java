import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int id;
    private String productName;
    private double productPrice;
    private String productDescription;
    private int productCategoryId;
    private String productCategoryName;
}

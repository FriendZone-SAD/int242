package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // สำหรับสร้าง Object เปล่าๆ แล้วค่อย set ค่า
public class Product {
    private Integer id;
    private String name;
    private Integer stock;
}
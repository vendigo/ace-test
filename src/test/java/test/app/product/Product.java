package test.app.product;

import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @Column
    @Parsed
    private Long id;
    @Column
    @Parsed
    private String name;
    @Column
    @Parsed
    private String description;
    @Column
    @Parsed
    @Format(formats = "#0.00", options = "decimalSeparator=.")
    private Double price;
    @Column
    @Parsed
    @Format(formats = "yyyy-MM-dd")
    private Date insertDate;
    @Column
    @Parsed
    @Format(formats = "yyyy-MM-dd HH:mm")
    private Date lastUpdateTime;
}

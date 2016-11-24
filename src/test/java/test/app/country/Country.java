package test.app.country;

import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Country {
    @Id
    @Column
    @Parsed
    private String name;
    @Column
    @Parsed
    private String capital;
    @Column
    @Parsed
    private String region;
    @Column
    @Parsed
    private String currency;
    @Column
    @Parsed
    private Long area;
    @Column
    @Parsed
    @Format(formats = "yyyy-MM-dd")
    private Date independenceDay;
}

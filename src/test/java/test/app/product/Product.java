package test.app.product;

import com.google.common.base.MoreObjects;
import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
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
    @Format(formats = "yyyy-MM-dd")
    private Date lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(price, product.price) &&
                Objects.equals(insertDate, product.insertDate) &&
                Objects.equals(lastUpdateTime, product.lastUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, insertDate, lastUpdateTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("price", price)
                .add("insertDate", insertDate)
                .add("lastUpdateTime", lastUpdateTime)
                .toString();
    }
}

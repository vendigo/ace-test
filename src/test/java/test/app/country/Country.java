package test.app.country;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Country {
    @Id
    @Column
    private String name;
    @Column
    private String capital;
    @Column
    private String region;
    @Column
    private String currency;
    @Column
    private Long area;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name) &&
                Objects.equals(capital, country.capital) &&
                Objects.equals(region, country.region) &&
                Objects.equals(currency, country.currency) &&
                Objects.equals(area, country.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capital, region, currency, area);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("capital", capital)
                .add("region", region)
                .add("currency", currency)
                .add("area", area)
                .toString();
    }
}

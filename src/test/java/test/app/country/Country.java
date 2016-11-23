package test.app.country;

import com.google.common.base.MoreObjects;
import com.univocity.parsers.annotations.Format;
import com.univocity.parsers.annotations.Parsed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
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

    public Date getIndependenceDay() {
        return independenceDay;
    }

    public void setIndependenceDay(Date independenceDay) {
        this.independenceDay = independenceDay;
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
                Objects.equals(area, country.area) &&
                Objects.equals(independenceDay, country.independenceDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capital, region, currency, area, independenceDay);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("capital", capital)
                .add("region", region)
                .add("currency", currency)
                .add("area", area)
                .add("independenceDay", independenceDay)
                .toString();
    }
}

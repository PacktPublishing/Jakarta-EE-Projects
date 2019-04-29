package org.jakartaeeprojects.ads.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.net.URL;
import java.util.Objects;

@Entity
@NamedQuery(name = "Product.findAll", query = "SELECT a FROM ProductAd a")
public class ProductAd {

    @GeneratedValue
    @Id
    private Long id;

    private String link;

    private String desc;

    private double score;

    private String category;

    public ProductAd() {
    }

    public static ProductAd create(String link, String desc, double score, String category) {
        ProductAd ad = new ProductAd();
        ad.link = link;
        ad.desc = desc;
        ad.score = score;
        ad.category = category;

        return ad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAd productAd = (ProductAd) o;
        return id.equals(productAd.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

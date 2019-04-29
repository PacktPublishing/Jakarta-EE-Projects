package org.jakartaeeprojects.catalog.entity;

import org.jakartaeeprojects.catalog.control.Ad;

import java.math.BigDecimal;
import java.util.List;

public class BookDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private String author;
    private BookStatus status;
    private long reviewCount;
    private String isbn10;
    private String isbn13;
    private List<String> reviews;
    private String category;
    private List<Ad> adList;

    public BookDto(Long id,
                   String title,
                   BigDecimal price,
                   String author,
                   BookStatus status,
                   String isbn10,
                   String isbn13,
                   long reviewCount,
                   String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.author = author;
        this.status = status;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.reviewCount = reviewCount;
        this.category = category;
    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.author = book.getAuthor().getFirstName()
                + " " + book.getAuthor().getLastName();
        this.isbn10 = book.getIsbn().getIsbn10();
        this.isbn13 = book.getIsbn().getIsbn13();
        this.status = book.getStatus();
        this.category = book.getCategory();
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Long getId() {
        return id;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Ad> getAdList() {
        return adList;
    }

    public void setAdList(List<Ad> adList) {
        this.adList = adList;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", author='" + author + '\'' +
                ", status=" + status +
                ", reviewCount=" + reviewCount +
                ", isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", reviews=" + reviews +
                ", category='" + category + '\'' +
                '}';
    }
}

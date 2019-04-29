package org.jakartaeeprojects.catalog.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    @PositiveOrZero
    private BigDecimal price;

    private String category;

    @PastOrPresent
    private LocalDate publishedDate;

    @Embedded
    private Isbn isbn;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.OUT_OF_STOCK;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    //FetchType.LAZY as the default for all to-many associations
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<BookReview> reviews = new ArrayList<>();

    public Book() {
    }

    public Book(Long id) {
        this.id = id;
    }

    public void addReview(BookReview review) {
        this.reviews.add(review);
        review.setBook(this);
    }

    public void removeReview(BookReview review) {
        this.reviews.remove(review);
        review.setBook(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public void setIsbn(Isbn isbn) {
        this.isbn = isbn;
    }

    public List<BookReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<BookReview> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}

package org.jakartaeeprojects.catalog.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Isbn implements Serializable {

    private String isbn10;
    private String isbn13;

    public static Isbn create(String isbn10, String isbn13) {
        Isbn isbn = new Isbn();
        isbn.setIsbn10(isbn10);
        isbn.setIsbn13(isbn13);
        return isbn;
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
}

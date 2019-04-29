package org.jakartaeeprojects.catalog.control;

import org.jakartaeeprojects.catalog.entity.Book;
import org.jakartaeeprojects.catalog.entity.Isbn;

public class IsbnGenerator {

    public static void applyIsbn(Book book) {
        Isbn isbn = new Isbn();
        long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;

        isbn.setIsbn10("" + number);
        number = (long) Math.floor(Math.random() * 9_000_000_000_000L) + 1_000_000_000_000L;
        isbn.setIsbn13("" + number);

        book.setIsbn(isbn);
    }
}

package org.jakartaeeprojects.catalog.control;

import org.jakartaeeprojects.catalog.entity.Book;
import org.jakartaeeprojects.catalog.entity.BookAddRequest;

public class BookRequestMapper {

    public static Book toBook(BookAddRequest request) {
        Book b = new Book();
        b.setTitle(request.getTitle());
        b.setCategory(request.getCategory());
        b.setPrice(request.getPrice());
        b.setStatus(request.getStatus());
        b.setPublishedDate(request.getPublishedDate());

        return b;
    }
}

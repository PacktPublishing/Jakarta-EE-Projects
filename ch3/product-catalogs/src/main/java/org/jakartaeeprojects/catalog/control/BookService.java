package org.jakartaeeprojects.catalog.control;

import org.jakartaeeprojects.catalog.entity.Book;
import org.jakartaeeprojects.catalog.entity.BookDto;
import org.jakartaeeprojects.catalog.entity.BookReview;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Stateless
public class BookService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Use DTO Projection for read-only data
     */
    public List<BookDto> getBooksDto(int start, int limit) {
        return em.createQuery("SELECT new org.jakartaeeprojects.catalog.entity.BookDto(" +
                " b.id, " +
                " b.title, " +
                " b.price, " +
                " b.author.firstName, " +
                " b.status, " +
                " b.isbn.isbn10, " +
                " b.isbn.isbn13, " +
                " count(review.id), " +
                " b.category " +
                " ) " +
                " from Book b left join b.reviews review group by b", BookDto.class)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public Optional<BookDto> getBookAndReviews(long id) {
        Optional<Book> targetBook = getBook(id);
        if (targetBook.isPresent()) {
            BookDto dto = new BookDto(targetBook.get());
            buildReviews(dto, targetBook.get().getReviews().stream());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    private void buildReviews(BookDto dto, Stream<BookReview> reviewStream) {
        List<String> reviewComments = reviewStream
                .map(BookReview::getReview)
                .collect(toList());
        dto.setReviewCount(reviewComments.size());
        dto.setReviews(reviewComments);
    }

    public Optional<Book> getBook(long id) {
        Book found = em.find(Book.class, id);
        return found != null ? Optional.of(found) : Optional.empty();
    }

    public void addReview(@Valid Book book, String comment, String by) {
        book.addReview(new BookReview(comment, by));
        this.em.merge(book);
    }

}

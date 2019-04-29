package org.jakartaeeprojects.catalog;

import org.jakartaeeprojects.catalog.entity.Author;
import org.jakartaeeprojects.catalog.entity.Book;
import org.jakartaeeprojects.catalog.entity.BookDto;
import org.jakartaeeprojects.catalog.entity.BookReview;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BookServiceJPATest {
    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void init() {
        emf = Persistence.createEntityManagerFactory("catalogsPUtest");
        em = emf.createEntityManager();
    }

    @After
    public void close() {
        em.close();
        emf.close();
    }

    @Test
    public void saveBookAndAuthor() {
        Author author = new Author();
        author.setFirstName("Prashant");
        author.setEmail("prashantp@jakartaee-book.org");


        em.getTransaction().begin();
        em.persist(author);
        em.getTransaction().commit();

        Book b = new Book();
        b.setAuthor(author);
        b.setCategory("test");
        b.setTitle("Sample");
        b.setPublishedDate(LocalDate.now());

        b.addReview(new BookReview("hello 1"));
        b.addReview(new BookReview("hello 2"));

        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();

        b = new Book();
        b.setAuthor(author);
        b.setCategory("test");
        b.setTitle("Sample");
        b.setPublishedDate(LocalDate.now());

        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();

        List<BookDto> list = em.createQuery("SELECT new org.jakartaeeprojects.catalog.entity.BookDto(" +
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
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
        System.out.println("Results here " + list.size());
        list.forEach(System.out::println);

        Book book = em.find(Book.class,2L);

        List<String> reviewComments = book.getReviews().stream()
                .map(BookReview::getReview)
                .collect(toList());
        System.out.println("review only");
        reviewComments.forEach(System.out::println);
    }

    @Test
    public void saveReview() {

        Author author = new Author();
        author.setFirstName("Prashant");
        author.setEmail("prashantp@jakartaee-book.org");

        em.getTransaction().begin();
        em.persist(author);
        em.getTransaction().commit();

        Book book = new Book();

        book.setAuthor(author);
        book.setCategory("test");
        book.setTitle("Sample");
        book.setPublishedDate(LocalDate.now());

        BookReview review1 = new BookReview("Review 1");
        BookReview review2 = new BookReview("Review 2");
        book.addReview(review1);
        book.addReview(review2);

        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();

    }
}

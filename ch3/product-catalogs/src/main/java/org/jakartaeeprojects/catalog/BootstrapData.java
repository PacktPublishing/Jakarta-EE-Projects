package org.jakartaeeprojects.catalog;

import org.jakartaeeprojects.catalog.entity.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Startup
@Singleton
public class BootstrapData {

    private static final String[] comments = {"Awesome book", "Good choice", "Too technical"};

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        Author author = createAuthor("Prashant", "Padmanabhan", "prashantp@xyz.org");

        author.setBooks(buildBookWithReviews(author,
                "Java EE 8 and Angular",
                "Jakarta EE Projects",
                "Docker next"));
        em.merge(author);

        author = createAuthor("Sujit", "Kumar", "sujit.kumar@xyz.org");

        author.setBooks(buildBookWithReviews(author,
                "BPM workflow",
                "BPM working internals"));
        em.merge(author);
    }

    private Author createAuthor(String first, String last, String email) {
        Author author = new Author();
        author.setFirstName(first);
        author.setLastName(last);
        author.setEmail(email);
        em.persist(author);
        return author;
    }

    private List<Book> buildBookWithReviews(Author byAuthor, String... titles) {
        List<Book> list = new ArrayList<>();
        for (String title : titles) {
            Book book = new Book();
            book.setAuthor(byAuthor);
            book.setTitle(title);

            int rand = (int) (Math.random() * 9) + 1;

            book.setPublishedDate(LocalDate.now().minusYears(rand));
            book.setStatus(new Random().nextBoolean() ? BookStatus.AVAILABLE : BookStatus.OUT_OF_STOCK);
            book.setPrice(BigDecimal.valueOf(rand * 100));
            book.setCategory("Programming");
            buildReview(book, comments);
            book.setIsbn(Isbn.create("153941200" + rand, "978153941200" + rand));

            list.add(book);
        }
        return list;
    }

    private void buildReview(Book book, String... comments) {
        Arrays.asList(comments).stream()
                .filter(c -> new Random().nextBoolean())
                .map(BookReview::new)
                .forEach(book::addReview);
    }

}

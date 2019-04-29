package org.jakartaeeprojects.catalog.control;

import org.jakartaeeprojects.catalog.entity.Author;
import org.jakartaeeprojects.catalog.entity.Book;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Stateless
public class AuthorService {

    @PersistenceContext
    private EntityManager em;

    public List<Author> getAuthors(int start, int limit) {
        return em.createQuery("SELECT a from Author a", Author.class)
                .setFirstResult(start)
                .setMaxResults(limit)
                .getResultList();
    }

    public Optional<Author> getAuthor(long id) {
        Author found = em.find(Author.class, id);
        return found != null ? Optional.of(found) : Optional.empty();
    }

    public Optional<Author> getAuthorByEmail(String email) {
        try {
            TypedQuery<Author> authorQuery = em.createQuery("select a from Author a WHERE a.email = :email",
                    Author.class);
            authorQuery.setParameter("email", email);
            return Optional.of(authorQuery.getSingleResult());
        } catch (NoResultException nre) {
        }
        return Optional.empty();
    }

    public Book addBook(Author author, @Valid Book book, Consumer<Book> isbnGenerator) {
        isbnGenerator.accept(book);
        this.em.persist(book);

        return book;
    }
}

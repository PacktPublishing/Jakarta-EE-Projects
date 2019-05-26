package org.jakartaeeprojects.catalog.boundary;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.jakartaeeprojects.catalog.control.*;
import org.jakartaeeprojects.catalog.entity.Author;
import org.jakartaeeprojects.catalog.entity.Book;
import org.jakartaeeprojects.catalog.entity.BookAddRequest;
import org.jakartaeeprojects.catalog.entity.BookDto;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    @Inject
    private Logger logger;

    @Inject
    @ConfigProperty(name = "page.limit", defaultValue = "10")
    private int pageLimit;

    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    @Inject
    private AdClient client;

//    @Inject
//    @RestClient
//    private AdResourceService client;

    @Counted(name = "bookAccessCount",
            absolute = true,
            monotonic = true,
            description = "Number of times the list of books is requested")
    @GET
    public Response getBooks(@DefaultValue("0") @QueryParam("start") @PositiveOrZero int start,
                             @QueryParam("limit") int limit) {
        limit = limit > 0 ? limit : pageLimit;

        logger.log(Level.INFO, String.format(
                "fetching from start %d to limit %d", start, limit));

        List<BookDto> books = bookService.getBooksDto(start, limit);

        return Response.ok(books)
                .build();
    }

    @GET
    @Path("{id}")
    public Response getBook(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Looking for book {0}", id);

        Optional<BookDto> targetBook = this.bookService.getBookAndReviews(id);
        if (targetBook.isPresent()) {
            logger.log(Level.INFO, "Fetching ads for book {0}", id);
            List<Ad> ads = this.client.getAds(targetBook.get().getCategory());
            BookDto dto = targetBook.get();
            dto.setAdList(ads);

            return Response.ok(dto).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .header("reason", "No book found by " + id)
                .build();
    }

    @Timed(name = "booksProcessed",
            description = "The timings of add book method.",
            absolute = true)
    @POST
    public Response add(@Valid BookAddRequest request,
                        @Context UriInfo uriInfo) {

        Book bookToSave = BookRequestMapper.toBook(request);
        Author booksAuthor = mapBookToAuthor(request.getAuthor().getEmail(), bookToSave);

        Book savedBook = this.authorService.addBook(
                booksAuthor,
                bookToSave,
                IsbnGenerator::applyIsbn);

        URI bookUri = uriInfo.getAbsolutePathBuilder()
                .path(savedBook.getId() + "")
                .build();

        return Response.accepted(savedBook.getTitle())
                .location(bookUri)
                .build();
    }

    private Author mapBookToAuthor(String email, Book book) {
        Optional<Author> author = authorService.getAuthorByEmail(email);
        if (!author.isPresent()) {
            throw new IllegalArgumentException("No such author");
        }

        Author theAuthor = author.get();
        theAuthor.addBook(book);
        return theAuthor;
    }

    @PUT
    @Path("{id}")
    public Response updateReviews(
            @PathParam("id") Long id,
            @QueryParam("comment") @NotBlank String comment,
            @DefaultValue("Anonymous") @QueryParam("by") String by) {
        Optional<Book> toUpdate = this.bookService.getBook(id);
        if (!toUpdate.isPresent()) {
            throw new IllegalArgumentException("No such book");
        }

        this.bookService.addReview(toUpdate.get(), comment, by);

        return Response.ok().build();
    }

}
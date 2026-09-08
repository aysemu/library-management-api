package com.example.library.service;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.dto.BookUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    BookResponse createBook(BookCreateRequest request);
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    BookResponse getBookByTitle(String title);
    BookResponse updateBook(Long id, BookUpdateRequest request);
    void deleteBook(Long id);
    List<BookResponse> searchBooksByTitle(String keyword);
    List<BookResponse> getBooksByMinAge(Long minAge);
    List<BookResponse> getBooksByAuthorAndMinAge(String author, Long minAge);
    Page<BookResponse> getBooksPublishedAfter(LocalDate date, Pageable pageable);
    Long countBooksByAuthor(String author);
    boolean existsBookByAuthor(String author);
}

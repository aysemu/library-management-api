package com.example.yenideen.controller;

import com.example.yenideen.dto.BookCreateRequest;
import com.example.yenideen.dto.BookResponse;
import com.example.yenideen.dto.BookUpdateRequest;
import com.example.yenideen.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookCreateRequest request) {
        BookResponse response = bookService.createBook(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/search/title")
    public ResponseEntity<BookResponse> getBookByTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @GetMapping("/search/keyword")
    public ResponseEntity<List<BookResponse>> searchBooksByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBooksByTitle(keyword));
    }

    @GetMapping("/search/age")
    public ResponseEntity<List<BookResponse>> getBooksByMinAge(@RequestParam Long minAge) {
        return ResponseEntity.ok(bookService.getBooksByMinAge(minAge));
    }

    @GetMapping("/search/author-age")
    public ResponseEntity<List<BookResponse>> getBooksByAuthorAndMinAge(
            @RequestParam String author,
            @RequestParam Long minAge) {
        return ResponseEntity.ok(bookService.getBooksByAuthorAndMinAge(author, minAge));
    }

    @GetMapping("/search/published-after")
    public ResponseEntity<Page<BookResponse>> getBooksPublishedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksPublishedAfter(date, pageable));
    }

    @GetMapping("/stats/count")
    public ResponseEntity<Long> countBooksByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.countBooksByAuthor(author));
    }

    @GetMapping("/stats/exists")
    public ResponseEntity<Boolean> existsBookByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.existsBookByAuthor(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestBody BookUpdateRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

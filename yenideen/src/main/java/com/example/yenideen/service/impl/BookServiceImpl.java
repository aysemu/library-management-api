package com.example.yenideen.service.impl;

import com.example.yenideen.dto.BookCreateRequest;
import com.example.yenideen.dto.BookResponse;
import com.example.yenideen.dto.BookUpdateRequest;
import com.example.yenideen.entity.Book;
import com.example.yenideen.exception.ResourceNotFoundException;
import com.example.yenideen.mapper.BookMapper;
import com.example.yenideen.repository.BookRepository;
import com.example.yenideen.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        Book book = BookMapper.toEntity(request);
        Book savedBook = bookRepository.save(book);
        return BookMapper.toResponse(savedBook);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return BookMapper.toResponse(book);
    }

    @Override
    public BookResponse getBookByTitle(String title) {
        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with title: " + title));
        return BookMapper.toResponse(book);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        BookMapper.updateEntityFromDto(request, book);
        Book updatedBook = bookRepository.save(book);
        return BookMapper.toResponse(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponse> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContaining(keyword).stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByMinAge(Long minAge) {
        return bookRepository.findByAgeGreaterThanEqualOrderByAgeAsc(minAge).stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByAuthorAndMinAge(String author, Long minAge) {
        return bookRepository.findByAuthorAndAgeGreaterThan(author, minAge).stream()
                .map(BookMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookResponse> getBooksPublishedAfter(LocalDate date, Pageable pageable) {
        return bookRepository.findByPublishDateAfter(date, pageable)
                .map(BookMapper::toResponse);
    }

    @Override
    public Long countBooksByAuthor(String author) {
        return bookRepository.countByAuthor(author);
    }

    @Override
    public boolean existsBookByAuthor(String author) {
        return bookRepository.existsByAuthor(author);
    }
}

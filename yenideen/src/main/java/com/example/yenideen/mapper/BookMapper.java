package com.example.yenideen.mapper;

import com.example.yenideen.dto.BookCreateRequest;
import com.example.yenideen.dto.BookResponse;
import com.example.yenideen.dto.BookUpdateRequest;
import com.example.yenideen.entity.Book;

public class BookMapper {

    public static Book toEntity(BookCreateRequest request) {
        if (request == null) {
            return null;
        }
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setAge(request.getAge());
        book.setPublishDate(request.getPublishDate());
        return book;
    }

    public static BookResponse toResponse(Book book) {
        if (book == null) {
            return null;
        }
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .age(book.getAge())
                .publishDate(book.getPublishDate())
                .build();
    }

    public static void updateEntityFromDto(BookUpdateRequest request, Book book) {
        if (request == null || book == null) {
            return;
        }
        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getAge() != null) {
            book.setAge(request.getAge());
        }
        if (request.getPublishDate() != null) {
            book.setPublishDate(request.getPublishDate());
        }
    }
}

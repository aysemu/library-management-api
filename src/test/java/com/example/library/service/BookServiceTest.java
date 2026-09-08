package com.example.library.service;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.dto.BookUpdateRequest;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.repository.BookRepository;
import com.example.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "Clean Code", "Robert C. Martin", 15L, LocalDate.of(2008, 7, 11));
    }

    @Test
    @DisplayName("Should create a new book successfully")
    void createBook_Success() {
        BookCreateRequest request = new BookCreateRequest("Clean Code", "Robert C. Martin", 15L, LocalDate.of(2008, 7, 11));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse response = bookService.createBook(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Clean Code");
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should return book by id")
    void getBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponse response = bookService.getBookById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Clean Code");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when book id does not exist")
    void getBookById_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Book not found with id: 99");
    }

    @Test
    @DisplayName("Should return all books")
    void getAllBooks_Success() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookResponse> books = bookService.getAllBooks();

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Robert C. Martin");
    }

    @Test
    @DisplayName("Should update an existing book")
    void updateBook_Success() {
        BookUpdateRequest updateRequest = new BookUpdateRequest("Clean Code 2nd Ed", "Robert C. Martin", 16L, LocalDate.of(2008, 7, 11));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponse response = bookService.updateBook(1L, updateRequest);

        assertThat(response).isNotNull();
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Should delete book by id")
    void deleteBook_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }
}

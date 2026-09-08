package com.example.yenideen.controller;

import com.example.yenideen.dto.BookCreateRequest;
import com.example.yenideen.dto.BookResponse;
import com.example.yenideen.exception.ResourceNotFoundException;
import com.example.yenideen.security.CustomUserDetailsService;
import com.example.yenideen.security.JwtTokenProvider;
import com.example.yenideen.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private JwtTokenProvider jwtTokenProvider;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("GET /api/v1/books - Should return list of books")
    void getAllBooks_ShouldReturnList() throws Exception {
        BookResponse bookResponse = new BookResponse(1L, "Clean Code", "Robert C. Martin", 15L, LocalDate.of(2008, 7, 11));
        when(bookService.getAllBooks()).thenReturn(List.of(bookResponse));

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[0].author").value("Robert C. Martin"));
    }

    @Test
    @DisplayName("GET /api/v1/books/{id} - Should return book when exists")
    void getBookById_ShouldReturnBook() throws Exception {
        BookResponse bookResponse = new BookResponse(1L, "Clean Code", "Robert C. Martin", 15L, LocalDate.of(2008, 7, 11));
        when(bookService.getBookById(1L)).thenReturn(bookResponse);

        mockMvc.perform(get("/api/v1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @DisplayName("POST /api/v1/books - Should create book")
    void createBook_ShouldReturnCreated() throws Exception {
        BookCreateRequest request = new BookCreateRequest("Clean Code", "Robert C. Martin", 15L, LocalDate.of(2008, 7, 11));
        BookResponse response = new BookResponse(1L, "Clean Code", "Robert C. Martin", 15L, LocalDate.of(2008, 7, 11));

        when(bookService.createBook(any(BookCreateRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @DisplayName("POST /api/v1/books - Should return 400 Bad Request when validation fails")
    void createBook_InvalidRequest_ShouldReturn400() throws Exception {
        BookCreateRequest invalidRequest = new BookCreateRequest("", "", -1L, null);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/books/{id} - Should return 404 Not Found when book does not exist")
    void getBookById_NotFound_ShouldReturn404() throws Exception {
        when(bookService.getBookById(99L)).thenThrow(new ResourceNotFoundException("Book not found with id: 99"));

        mockMvc.perform(get("/api/v1/books/99"))
                .andExpect(status().isNotFound());
    }
}

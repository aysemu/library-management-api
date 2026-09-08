package com.example.library.controller;

import com.example.library.dto.BookLoanRequest;
import com.example.library.dto.BookLoanResponse;
import com.example.library.service.BookLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class BookLoanController {

    private final BookLoanService bookLoanService;

    @PostMapping("/borrow")
    public ResponseEntity<BookLoanResponse> borrowBook(@Valid @RequestBody BookLoanRequest request) {
        BookLoanResponse response = bookLoanService.borrowBook(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{loanId}/return")
    public ResponseEntity<BookLoanResponse> returnBook(@PathVariable Long loanId) {
        BookLoanResponse response = bookLoanService.returnBook(loanId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookLoanResponse>> getLoansByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(bookLoanService.getLoansByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<List<BookLoanResponse>> getAllLoans() {
        return ResponseEntity.ok(bookLoanService.getAllLoans());
    }
}

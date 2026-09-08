package com.example.library.service;

import com.example.library.dto.BookLoanRequest;
import com.example.library.dto.BookLoanResponse;

import java.util.List;

public interface BookLoanService {
    BookLoanResponse borrowBook(BookLoanRequest request);
    BookLoanResponse returnBook(Long loanId);
    List<BookLoanResponse> getLoansByUserId(Long userId);
    List<BookLoanResponse> getAllLoans();
}

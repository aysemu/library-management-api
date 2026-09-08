package com.example.yenideen.service;

import com.example.yenideen.dto.BookLoanRequest;
import com.example.yenideen.dto.BookLoanResponse;

import java.util.List;

public interface BookLoanService {
    BookLoanResponse borrowBook(BookLoanRequest request);
    BookLoanResponse returnBook(Long loanId);
    List<BookLoanResponse> getLoansByUserId(Long userId);
    List<BookLoanResponse> getAllLoans();
}

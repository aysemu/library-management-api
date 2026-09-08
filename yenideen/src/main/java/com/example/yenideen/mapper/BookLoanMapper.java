package com.example.yenideen.mapper;

import com.example.yenideen.dto.BookLoanResponse;
import com.example.yenideen.entity.BookLoan;

public class BookLoanMapper {

    public static BookLoanResponse toResponse(BookLoan loan) {
        if (loan == null) {
            return null;
        }
        return BookLoanResponse.builder()
                .id(loan.getId())
                .userId(loan.getUser() != null ? loan.getUser().getId() : null)
                .userFullName(loan.getUser() != null ? loan.getUser().getFullName() : null)
                .bookId(loan.getBook() != null ? loan.getBook().getId() : null)
                .bookTitle(loan.getBook() != null ? loan.getBook().getTitle() : null)
                .borrowDate(loan.getBorrowDate())
                .dueDate(loan.getDueDate())
                .returnDate(loan.getReturnDate())
                .status(loan.getStatus())
                .build();
    }
}

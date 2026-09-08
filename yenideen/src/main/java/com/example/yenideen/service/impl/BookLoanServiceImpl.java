package com.example.yenideen.service.impl;

import com.example.yenideen.dto.BookLoanRequest;
import com.example.yenideen.dto.BookLoanResponse;
import com.example.yenideen.entity.Book;
import com.example.yenideen.entity.BookLoan;
import com.example.yenideen.entity.User;
import com.example.yenideen.exception.ResourceNotFoundException;
import com.example.yenideen.mapper.BookLoanMapper;
import com.example.yenideen.repository.BookLoanRepository;
import com.example.yenideen.repository.BookRepository;
import com.example.yenideen.repository.UserRepository;
import com.example.yenideen.service.BookLoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookLoanResponse borrowBook(BookLoanRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + request.getBookId()));

        bookLoanRepository.findByBookIdAndReturnDateIsNull(book.getId())
                .ifPresent(activeLoan -> {
                    throw new IllegalStateException("Book is currently borrowed and not yet returned.");
                });

        int loanDays = (request.getLoanDays() != null && request.getLoanDays() > 0) ? request.getLoanDays() : 14;

        BookLoan loan = BookLoan.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(loanDays))
                .status("BORROWED")
                .build();

        BookLoan savedLoan = bookLoanRepository.save(loan);
        return BookLoanMapper.toResponse(savedLoan);
    }

    @Override
    @Transactional
    public BookLoanResponse returnBook(Long loanId) {
        BookLoan loan = bookLoanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Book loan record not found with id: " + loanId));

        if (loan.getReturnDate() != null) {
            throw new IllegalStateException("Book has already been returned.");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");
        BookLoan updatedLoan = bookLoanRepository.save(loan);
        return BookLoanMapper.toResponse(updatedLoan);
    }

    @Override
    public List<BookLoanResponse> getLoansByUserId(Long userId) {
        return bookLoanRepository.findByUserId(userId).stream()
                .map(BookLoanMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLoanResponse> getAllLoans() {
        return bookLoanRepository.findAll().stream()
                .map(BookLoanMapper::toResponse)
                .collect(Collectors.toList());
    }
}

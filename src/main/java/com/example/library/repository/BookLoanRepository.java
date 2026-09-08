package com.example.library.repository;

import com.example.library.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findByUserId(Long userId);
    List<BookLoan> findByBookId(Long bookId);
    Optional<BookLoan> findByBookIdAndReturnDateIsNull(Long bookId);
}

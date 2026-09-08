package com.example.yenideen;

import com.example.yenideen.entity.Book;
import com.example.yenideen.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            List<Book> exampleBooks = new ArrayList<>();
            exampleBooks.add(new Book(null, "Clean Code", "Robert C. Martin", 11L, LocalDate.parse("2008-07-11")));
            exampleBooks.add(new Book(null, "Clean Agile", "Robert C. Martin", 0L, LocalDate.parse("2019-09-12")));
            exampleBooks.add(new Book(null, "Agile Software Development", "Robert C. Martin", 17L, LocalDate.parse("2002-10-25")));
            exampleBooks.add(new Book(null, "Code Complete 2", "Steve McConnell", 26L, LocalDate.parse("1993-05-30")));
            exampleBooks.add(new Book(null, "Essential Scrum", "Kenneth S. Rubin", 7L, LocalDate.parse("2012-07-20")));
            exampleBooks.add(new Book(null, "Design Patterns", "Gang of Four", 25L, LocalDate.parse("1994-10-01")));
            exampleBooks.add(new Book(null, "Domain Driven Design", "Eric Evans", 16L, LocalDate.parse("2003-08-30")));
            exampleBooks.add(new Book(null, "Test Driven Development", "Kent Beck", 17L, LocalDate.parse("2002-11-18")));
            exampleBooks.add(new Book(null, "Refactoring", "Kent Beck", 7L, LocalDate.parse("2012-03-09")));
            exampleBooks.add(new Book(null, "Extreme Programming Explained", "Kent Beck", 15L, LocalDate.parse("2004-11-26")));

            bookRepository.saveAll(exampleBooks);
        }
    }
}

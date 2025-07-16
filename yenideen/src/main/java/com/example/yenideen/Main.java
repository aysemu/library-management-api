package com.example.yenideen;

import com.example.yenideen.repository.BookRepository;
import com.example.yenideen.entity.Book; // Book entity importu eklemeyi unutma
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Main.class, args);
        BookRepository bookRepository = applicationContext.getBean(BookRepository.class);


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

        System.out.println(" 1. Title 'Domain Driven Design' olan kitap:");
        System.out.println(bookRepository.findByTitle("Domain Driven Design"));

        System.out.println("\n 2. Yaşı 15 ve üzeri olan kitaplar (yaşa göre artan sıralı):");
        bookRepository.findByAgeGreaterThanEqualOrderByAgeAsc(15L)
                .forEach(System.out::println);

        System.out.println("\n 3. 2000 yılından sonra yayımlanan kitaplar (5'erli sayfalarda, 6. sayfa):");
        bookRepository.findByPublishDateAfter(LocalDate.of(2000,1,1), PageRequest.of(5,2))
                .forEach(System.out::println);

        System.out.println("\n 4. Başlığında 'Clean' geçen kitaplar:");
        bookRepository.findByTitleContaining("Clean")
                .forEach(System.out::println);

        System.out.println("\n 5. Robert C. Martin tarafından yazılmış ve yaşı 10'dan büyük kitaplar:");
        bookRepository.findByAuthorAndAgeGreaterThan("Robert C. Martin",10L)
                .forEach(System.out::println);

        System.out.println("\n 6. Kent Beck'e ait kitap sayısı:");
        System.out.println(bookRepository.countByAuthor("Kent Beck"));

        System.out.println("\n 7. Martin Fowler'a ait kitap var mı?");
        System.out.println(bookRepository.existsByAuthor("Martin Fowler") ? "Evet, var." : "Hayır, yok.");

    }
}

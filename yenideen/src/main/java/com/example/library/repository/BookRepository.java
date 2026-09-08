package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select u from Book u where u.title = :title")
    Optional<Book> findByTitle(@Param("title") String title);

    @Query("select u from Book u where u.age >= :age order by u.age asc")
    List<Book> findByAgeGreaterThanEqualOrderByAgeAsc(@Param("age") Long age);

    @Query("select u from Book u where u.publishDate > :date")
    Page<Book> findByPublishDateAfter(@Param("date") LocalDate date, Pageable pageable);

    @Query("select u from Book u where u.title like %:keyword%")
    List<Book> findByTitleContaining(@Param("keyword") String keyword);

    @Query("select u from Book u where u.author = :author and u.age > :age")
    List<Book> findByAuthorAndAgeGreaterThan(@Param("author") String author, @Param("age") Long age);

    @Query("select count(u) from Book u where u.author = :author")
    Long countByAuthor(@Param("author") String author);

    @Query("select case when count(u) > 0 then true else false end from Book u where u.author = :author")
    boolean existsByAuthor(@Param("author") String author);
}


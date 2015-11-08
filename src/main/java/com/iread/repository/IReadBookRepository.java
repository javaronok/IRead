package com.iread.repository;

import com.iread.model.IReadBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReadBookRepository extends JpaRepository<IReadBook, Long> {
}

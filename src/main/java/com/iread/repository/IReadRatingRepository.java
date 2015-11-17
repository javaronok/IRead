package com.iread.repository;

import com.iread.model.IReadBook;
import com.iread.model.IReadRating;
import com.iread.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IReadRatingRepository extends JpaRepository<IReadRating, Long> {
    public List<IReadRating> findByUser(User user);
    public List<IReadRating> findByUserAndBook(User user, IReadBook book);
}

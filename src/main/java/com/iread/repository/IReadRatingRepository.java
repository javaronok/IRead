package com.iread.repository;

import com.iread.model.IReadRating;
import com.iread.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IReadRatingRepository extends CrudRepository<IReadRating, Long> {
    public List<IReadRating> findByUser(User user);
}

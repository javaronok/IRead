package com.iread.repository;

import com.iread.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUsername(String userName);
    List<User> findByUsernameAndEnabled(String userName, boolean enabled);
    Page<User> findByUsernameLikeIgnoreCaseAndEnabled(String term, boolean enabled, Pageable pageable);
}

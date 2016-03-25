package com.iread.service;

import com.iread.form.UserAddBookForm;
import com.iread.model.IReadBook;
import com.iread.model.IReadTag;
import com.iread.repository.IReadBookRepository;
import com.iread.repository.IReadTagRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IReadTagService {
    @Autowired
    IReadTagRepository repository;

    @Transactional
    public List<IReadTag> listTags() {
        List<IReadTag> tags = repository.findAll();
        return tags;
    }

}

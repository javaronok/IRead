package com.iread.service;

import com.iread.model.IReadBook;
import com.iread.repository.IReadBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IReadBooksService {

    @Autowired
    IReadBookRepository repository;

    public List<IReadBook> listBooks() {
        return repository.findAll();
    }
}

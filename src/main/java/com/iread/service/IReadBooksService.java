package com.iread.service;

import com.iread.form.UserAddBookForm;
import com.iread.model.IReadBook;
import com.iread.model.User;
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

    public IReadBook saveBook (UserAddBookForm form) {
        IReadBook book = new IReadBook();
        book.setBookName(form.getBookName());
        book.setBookAuthor(form.getBookAuthor());
        book.setPublicationDate(form.getBookYear());
        book.setAnnotation(form.getBookAnnotation());

        repository.save(book);
        return book;
    }
}

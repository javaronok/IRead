package com.iread.service;

import com.iread.form.UserAddBookForm;
import com.iread.model.IReadBook;
import com.iread.model.User;
import com.iread.repository.IReadBookRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IReadBooksService {

    @Autowired
    IReadBookRepository repository;

    @Autowired
    private RatingService ratingService;

    @Transactional
    public List<IReadBook> listBooks() {
        List<IReadBook> books = repository.findAll();
        initAll(books);
        return books;
    }

    private void initAll(List<IReadBook> books) {
        for (IReadBook book : books) {
            initFull(book);
        }
    }

    @Transactional
    public IReadBook load(Long id) {
        IReadBook one = repository.findOne(id);
        initFull(one);
        return one;
    }

    public void initFull(IReadBook book) {
        Hibernate.initialize(book.getTags());
    }

    public IReadBook saveBook(UserAddBookForm form, User user) {
        IReadBook book = new IReadBook();
        book.setBookName(form.getBookName());
        book.setAuthorLastName(form.getAuthorLastName());
        book.setAuthorFirstName(form.getAuthorFirstName());
        book.setAuthorPatronymic(form.getAuthorPatronymic());
        book.setPublicationDate(form.getBookYear());
        book.setAnnotation(form.getBookAnnotation());
        book.setCover(form.getCoverFileUid());
        book.setTags(form.getTags());
        book.setOwner(user);

        repository.save(book);
        return book;
    }

    public void delete(IReadBook book)  {
        repository.delete(book);
        ratingService.deleteRatingsByBook(book);
    }

    public IReadBook updateBook(UserAddBookForm form, User user) {
        IReadBook book = load(form.getId());

        book.setBookName(form.getBookName());
        book.setAuthorLastName(form.getAuthorLastName());
        book.setAuthorFirstName(form.getAuthorFirstName());
        book.setAuthorPatronymic(form.getAuthorPatronymic());
        book.setPublicationDate(form.getBookYear());
        book.setAnnotation(form.getBookAnnotation());
        book.setTags(form.getTags());
        book.setOwner(user);

        return repository.save(book);
    }
}

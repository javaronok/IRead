package com.iread.security;

import com.iread.form.UserSignUpForm;
import com.iread.model.User;
import com.iread.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User findFull(Long id) {
        return userRepository.findOne(id);
    }

    public boolean existsUser(String userName) {
        return userRepository.findByUsername(userName).size() > 0;
    }

    public List<User> findByUserNameAndEnabled(String userName, boolean enabled) {
        return userRepository.findByUsernameIgnoreCaseAndEnabled(userName, enabled);
    }

    public User getUserByName(String userName) {
        List<User> users = findByUserNameAndEnabled(userName, true);
        if (users.size() == 1) {
            return users.get(0);
        } else {
            throw new IllegalStateException(" User not found");
        }
    }

    public List<User> listAllFull() {
        List<User> result = new ArrayList<>();
        Iterable<User> all = userRepository.findAll();

        for (User user : all) {
            result.add(user);
        }

        return result;
    }

    public User load(Long id) {
        return userRepository.findOne(id);
    }

    public Page<User> findBySelectedName(String selectedUserName, Pageable pageable) {
        String term = "%" + StringUtils.defaultString(selectedUserName) + "%";
        return userRepository.findByUsernameLikeIgnoreCaseAndEnabled(term, true, pageable);
    }

    @Transactional(readOnly = false)
    public User saveNewUserForm(UserSignUpForm form) {
        if (form == null || form.getLogin() == null || form.getPasswd() == null || form.getPasswdRepeat() == null ||
                !form.getPasswd().equals(form.getPasswdRepeat()))
        {
            throw new IllegalArgumentException("Проверьте введённые данные");
        }

        String encodedPassword = passwordEncoder.encode(form.getPasswd());

        return saveNewUser(form.getLogin(), encodedPassword, form.getFirstName(), form.getLastName());
    }

    public User saveNewUser(String login, String password, String firstName, String lastName) {
        User user = new User();
        user.setUsername(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEnabled(true);

        userRepository.save(user);
        return user;
    }

}

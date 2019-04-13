package com.goaleaf.services.servicesImpl;

import com.goaleaf.entities.User;
import com.goaleaf.entities.viewModels.accountsAndAuthorization.EditImageViewModel;
import com.goaleaf.entities.viewModels.accountsAndAuthorization.EditUserViewModel;
import com.goaleaf.repositories.RoleRepository;
import com.goaleaf.services.UserService;
import com.goaleaf.validators.exceptions.accountsAndAuthorization.BadCredentialsException;
import com.goaleaf.validators.exceptions.accountsAndAuthorization.EmailExistsException;
import com.goaleaf.validators.exceptions.accountsAndAuthorization.LoginExistsException;
import com.goaleaf.entities.viewModels.accountsAndAuthorization.RegisterViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.goaleaf.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Iterable<User> listAllUsersPaging(Integer pageNr, Integer howManyOnPage) {
        return userRepository.findAll(new PageRequest(pageNr, howManyOnPage));
    }

    @Override
    public Iterable<User> listAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void removeUser(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public Boolean checkIfExists(Integer id) {
        if (userRepository.checkIfExists(id) > 0)
            return true;
        else
            return false;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(RegisterViewModel register)
            throws EmailExistsException, LoginExistsException {


        User user = new User();
        user.setLogin(register.login);
        user.setUserName(register.userName);
        user.setPassword(register.password);
        user.setEmailAddress(register.emailAddress);
        user.setImageName("def_goaleaf_avatar.png");
        return userRepository.save(user);
    }

    public void updateUser(EditUserViewModel model) throws BadCredentialsException {
        if (findById(model.id) != null) {
            User updatedUser = findById(model.id);
            updatedUser.setUserName(model.userName);

            if (bCryptPasswordEncoder.matches(model.oldPassword, userRepository.findById(model.id).getPassword())) {
                updatedUser.setEmailAddress(model.emailAddress);
                if (model.newPassword.equals(model.matchingNewPassword))
                    updatedUser.setPassword(bCryptPasswordEncoder.encode(model.newPassword));
                else
                    throw new BadCredentialsException("Passwords are not equal!");
            } else {
                throw new BadCredentialsException("Wrong Password!");
            }

            userRepository.save(updatedUser);
        }
    }

    public void updateUserImage(EditImageViewModel model) {
        User updatedUser = findById(model.id);

        updatedUser.setImageName(model.imageName);

        saveUser(updatedUser);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByEmailAddress(String email) {
        return userRepository.findByEmailAddress(email);
    }
}

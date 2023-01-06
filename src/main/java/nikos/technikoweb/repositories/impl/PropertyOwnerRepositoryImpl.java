/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nikos.technikoweb.repositories.impl;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import nikos.technikoweb.exceptions.PropertyOwnerException;
import nikos.technikoweb.exceptions.codes.PropertyOwnerExceptionCodes;
import nikos.technikoweb.model.PropertyOwner;
import nikos.technikoweb.model.User;
import nikos.technikoweb.repository.PropertyOwnerRepository;

/**
 *
 * @author legeo
 */
public class PropertyOwnerRepositoryImpl extends RepositoryImpl<PropertyOwner, Long> implements PropertyOwnerRepository {
   
    @Override
    @Transactional
    public int create(User t) {
        em.persist(t);
        return (int) t.getId();
    }

 

    @Override
    @Transactional
    public Optional<User> read(int id) {
        User user = em.find(User.class, id);
        return user != null && user.isActive() ? Optional.of(user) : Optional.empty();
    }

 

    @Override
    @Transactional
    public List<User> read() {
        return em.createQuery("from User", User.class).getResultList();
    }

 

    @Override
    @Transactional
    public boolean safeDelete(int id) {
        User user = em.find(User.class, id);
        if (user == null) {
            return false;
        }
        user.setActive(false);
        em.persist(user);
        return true;
    }

 

    @Override
    @Transactional
    public User validateLogin(String username, String password) throws PropertyOwnerException {
        Optional<User> userOptional = readOwnerByUsername(username);
        if (userOptional.isEmpty()) {
            throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_USERNAME_NOT_FOUND);
        }
        User user = userOptional.get();
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new PropertyOwnerException(PropertyOwnerExceptionCodes.OWNER_PASSWORD_INCORRECT);
    }

 

    @Override
    @Transactional
    public Optional<User> readOwnerByUsername(String username) throws PropertyOwnerException {
        List<User> propertyList = em.createQuery("select o from User o where o.username = :username").setParameter("username", username).getResultList();
        User user=null;
        if (!propertyList.isEmpty()) {
           user = propertyList.get(0);
        }
        return optionalOfUser(user);
    }

 

   
    @Transactional
    public boolean update(User t, int id) {
        User oldUser = em.find(User.class, id);
        if (oldUser == null || !oldUser.isActive()) {
            return false;
        }
        oldUser.setAddress(t.getAddress());
        oldUser.setPassword(t.getPassword());
        oldUser.setEmail(t.getEmail());
        updateUser(oldUser);
        return true;
    }

 

    /**
     * update the user
     * @param user 
     */
    private void updateUser(User user) {
        em.persist(user);
    }

 

    
    @Transactional
    public boolean delete(int id) {
        User user = em.find(User.class, id);
        if (user == null) {
            return false;
        }
        em.remove(user);
        return true;

 

    }

 

    @Override
    @Transactional
    public Optional<User> readOwnerByEmail(String email) {
        List<User> userList = em.createQuery("select u from User u where u.email = :email").setParameter("email", email).getResultList();
        User user = null;
        if (!userList.isEmpty()) {
            user = userList.get(0);
        }
        return optionalOfUser(user);
    }

 

    @Override
    @Transactional
    public Optional<User> readOwnerByVat(String vat) {
        List<User> userList = em.createQuery("select u from User u where u.vat = :vat").setParameter("vat", vat).getResultList();
        User user = null;
        if (!userList.isEmpty()) {
            user = userList.get(0);
        }
        return optionalOfUser(user);
    }

 

    /**
     * get the optional of the user
     * @param user
     * @return 
     */
    private Optional<User> optionalOfUser(User user) {
        return user != null && user.isActive() ? Optional.of(user) : Optional.empty();
    }

}

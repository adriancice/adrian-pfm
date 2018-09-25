package com.adrian.blog.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.adrian.blog.model.User;

/**
 * The UserService interface
 *
 * @author Adrian Paul
 * @version 1.0 Date 14/09/2018.
 */
public interface IUserService {

	User save(User user);

	Boolean delete(int id);

	User update(User user);

	User findById(int id);

	User findByUserName(String username);

	User findByEmail(String email);

	Collection<User> findAll();

	Page<User> findAll(Pageable pageable);
}

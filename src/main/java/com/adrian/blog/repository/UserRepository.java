package com.adrian.blog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.adrian.blog.model.User;

/**
 * The UserRepository class
 *
 * @author Adrian Paul
 * @version 1.0 Date 14/09/2018
 */
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

	User findByUsername(String username);

	User findByEmail(String email);
}
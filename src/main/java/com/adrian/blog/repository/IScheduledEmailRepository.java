package com.adrian.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adrian.blog.model.ScheduledEmail;

@Repository("scheduledEmailRepository")
public interface IScheduledEmailRepository extends JpaRepository<ScheduledEmail, Integer> {

	ScheduledEmail findByEmail(String email);

}

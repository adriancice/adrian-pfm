package com.adrian.blog.service;

import java.util.Collection;

import com.adrian.blog.model.ScheduledEmail;

public interface IScheduledEmailService {

	ScheduledEmail save(ScheduledEmail scheduledEmail);

	void delete(String email);

	Collection<ScheduledEmail> findAll();

	Boolean existEmail(String email);

}

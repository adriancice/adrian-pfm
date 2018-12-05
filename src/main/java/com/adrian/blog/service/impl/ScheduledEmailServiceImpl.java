package com.adrian.blog.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adrian.blog.model.ScheduledEmail;
import com.adrian.blog.repository.IScheduledEmailRepository;
import com.adrian.blog.service.IScheduledEmailService;

@Service
public class ScheduledEmailServiceImpl implements IScheduledEmailService {

	@Autowired
	private IScheduledEmailRepository scheduledEmailRepository;

	@Override
	public ScheduledEmail save(ScheduledEmail scheduledEmail) {
		return scheduledEmailRepository.save(scheduledEmail);
	}

	@Override
	public Collection<ScheduledEmail> findAll() {
		Iterable<ScheduledEmail> itr = scheduledEmailRepository.findAll();
		return (Collection<ScheduledEmail>) itr;
	}

	@Override
	public Boolean existEmail(String email) {
		if (scheduledEmailRepository.findByEmail(email) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(String email) {
		ScheduledEmail se = scheduledEmailRepository.findByEmail(email);
		scheduledEmailRepository.delete(se);
	}

}

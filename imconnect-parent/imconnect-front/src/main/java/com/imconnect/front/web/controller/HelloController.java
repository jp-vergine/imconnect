package com.imconnect.front.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.imconnect.core.repositories.user.UserRepository;
import com.imconnect.front.service.SecUserDetails;


@Controller
public class HelloController {

	private Logger logger = LoggerFactory.getLogger(HelloController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = { "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security");
		model.addObject("message", "mongo users: " + userRepository.countAllUsers());
		model.setViewName("hello");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		
		Authentication auth = (Authentication) SecurityContextHolder.getContext().getAuthentication();
		Object myUser = (auth != null) ? auth.getPrincipal() :  null;
		if (myUser instanceof SecUserDetails) {
			SecUserDetails user = (SecUserDetails) myUser;    
			logger.debug("Access to admin page with account: " + user.getUsername());
		}
		
		logger.error("Ceci est un test de log");
		
		
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security");
		model.addObject("message", "This is protected page - Admin Page!");
		model.setViewName("admin");

		return model;

	}

	@RequestMapping(value = "/dba**", method = RequestMethod.GET)
	public ModelAndView dbaPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Hello World");
		model.addObject("message", "This is protected page - Database Page!");
		model.setViewName("admin");

		return model;

	}
}
package com.hyman.schedule.master.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyman.schedule.master.service.TestService;


@Controller
public class IndexController {
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired TestService testService;
		
	@RequestMapping(value = "index",method =RequestMethod.GET)
	public void index(@RequestParam(value="name",required=false) String name,HttpServletRequest request,Model model) {
		
		if(null!=name && name.length()>0){						
			model.addAttribute("uname", name);
		} else {
			model.addAttribute("uname", testService.hellworld());
		}
		
		LOG.info("--you call spring mvc /index");
	}
}
/**
 * 
 */
package com.neu.msd.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.UserService;

/**
 * @author Harsh
 *
 */
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/redirectToDiagnostic.action", method=RequestMethod.GET)
	public String redirectToDiagnostic(Model model){
		
		try {
			List<AdminActivityAnswer> activityAnswers = userService.getDiagnosticQuestions();
			model.addAttribute("activityAnswers", activityAnswers);
			return "diagnostic";
		} catch (UserException e) {
			return "errorPage";
		} catch (AdminException e) {
			return "errorPage";
		}
	}
	@RequestMapping(value="/loadUserTopicsPage.action", method=RequestMethod.GET)
	public String loadTopicsOfUser(HttpSession session, Model model){
		try{
			User user = new User();
			user = (User) session.getAttribute("user");
			if (user.isDiagnosticTaken()== false){
				return redirectToDiagnostic(model);
			}
			else{
				List<Topic> topics = new ArrayList<Topic>();
				topics = userService.getTopicsOfUser(user);
				model.addAttribute("topics", topics);
			}
			
		}
		catch (UserException ex){
			return "error";
		}
		return "topics";
	}
	

}

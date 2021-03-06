/**

 * @author SANIL AND VINAY 

 */

package com.neu.msd.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.Mother;

import com.neu.msd.entities.MotherRegistration;

import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.UserType;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.AdminService;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.dao.AdminDao;
import com.neu.msd.entities.Activity;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class AdminTest {
	
	private static int deleteId;
	
	@Autowired
	AdminService adminService;

	
	@Test
	public void test_createDiagnosticQuestions() throws AdminException{

		String ques = "I should not be there";
		Answer a1 = new Answer();
		a1.setAnswerText("I am an Created answer and I should not be ther");
		a1.setOrderNo(1);
		a1.setIsCorrect(false);
		a1.setIsRightanswer(false);

		Answer a2 = new Answer();
		a2.setAnswerText("I am an Created answer 2 and I should not be ther");
		a2.setOrderNo(2);
		a2.setIsCorrect(true);
		a2.setIsRightanswer(false);
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);
		
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		deleteId = lastActivityId;
		assertEquals(true, lastActivityId > 0);
					
	}
	
	@Test
	public void test_createDiagnosticQuestionsInput() throws AdminException{
		
		String ques = null;
		ArrayList<Answer> answers = new ArrayList<Answer>();
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		assertEquals(lastActivityId, 0);
		
	}
	
	@Test
	public void test_createDiagnosticQuestionsInput2() throws AdminException{
		
		String ques = "A valid string";
		ArrayList<Answer> answers = null;
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		assertEquals(lastActivityId, 0);
		
	}
	
	@Test
	public void test_updateDiagnosticQuestion() throws AdminException{
		

		String ques = "Text Created Questions for update test and I should not be there";
		Answer a1 = new Answer();
		a1.setAnswerText("I am an Created answer for update test and I should not be there");
		a1.setOrderNo(1);
		a1.setIsCorrect(false);
		a1.setIsRightanswer(false);

		Answer a2 = new Answer();
		a2.setAnswerText("I am an Created answer 2 for update test and I should not be there");
		a2.setOrderNo(2);
		a2.setIsCorrect(true);
		a2.setIsRightanswer(false);
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);
		
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+lastActivityId);
		
		
		Activity activity = new Activity();
		activity.setId(lastActivityId);
		activity.setActivityText("I am the updated question and I should be there");
		ActivityTemplate activityTemplate = new ActivityTemplate();
		activityTemplate.setId(3);
		activity.setActivityTemplate(activityTemplate);
		
		
		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);

		a1 = new Answer();
		a1.setAnswerText("I am an updated answer 1 and I should be there");
		a1.setOrderNo(1);
		a1.setIsCorrect(false);
		a1.setIsRightanswer(false);

		a2 = new Answer();
		a2.setAnswerText("I am an updated answer 2 and I should be there");
		a2.setOrderNo(2);
		a2.setIsCorrect(true);
		a2.setIsRightanswer(false);
		
		ArrayList<Answer> adminAnswers = new ArrayList<Answer>();
		adminAnswers.add(a1);
		adminAnswers.add(a2);
		
				
		adminActivityAnswer.setAnswers(adminAnswers);
			
		int numRowsAffected = adminService.updateDiagnosticQuestion(adminActivityAnswer);
		assertEquals(true, numRowsAffected > 0);		
	}
	
	@Test
	public void test_updateDiagnosticQuestionInput() throws AdminException{
									
		int numRowsAffected = adminService.updateDiagnosticQuestion(null);
		assertEquals(true, numRowsAffected == 0);		
	}
	
	
	
	@Test
	public void test_getDiagnosticList(){
	
		List<AdminActivityAnswer> diagnosticQuestionList= adminService.getDiagnosticQuestions();
		assertEquals(true, diagnosticQuestionList != null);
	
	}

	@Test
	public void test_checkDiagnosticListLength(){

		List<AdminActivityAnswer> diagnosticQuestionList= adminService.getDiagnosticQuestions();
		assertEquals(true, diagnosticQuestionList.size() > 0);
	
	}
	
	@Test
	public void test_deleteDiagnosticQuestion() throws AdminException{
				
		int numRowsAffected = adminService.deleteDiagnosticQuestion(deleteId);
		assertEquals(true, numRowsAffected > 0);
		
	}
	
	@Test
	public void test_deleteDiagnosticQuestionInput() throws AdminException{
				
		int numRowsAffected = adminService.deleteDiagnosticQuestion(-5);
		assertEquals(false, numRowsAffected > 0);
		
	}
	
	
	
	
}

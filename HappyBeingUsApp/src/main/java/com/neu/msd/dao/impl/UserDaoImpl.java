/**
 * 
 */
package com.neu.msd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.neu.msd.dao.AdminDao;
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.exception.UserException;

/**
 * @author Harsh
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	AdminDao adminDao;

	Map<Integer, ActivityTemplate> activityTemplateMap = new HashMap<Integer, ActivityTemplate>();
	Map<Integer, ActivityType> activityTypeMap = new HashMap<Integer, ActivityType>();

	public int getDiagnosticType() throws UserException {

		try {

			Connection connection = dataSource.getConnection();
			String sql = "select activity_type_id from activity_type where activity_type_desc = 'Diagnostic'";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("activity_type_id");
			}

			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}
	}

	public List<Activity> getActivitiesByType(int activityType) throws UserException {

		try {
			adminDao.loadActivityTemplate(activityTemplateMap);
			adminDao.loadActivityType(activityTypeMap);
		} catch (AdminException e) {
			e.printStackTrace();
		}

		List<Activity> activities = new ArrayList<Activity>();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity where activity_type_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityType);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Activity activity = new Activity();
				activity.setId(rs.getInt("activity_id"));
				activity.setActivityType(activityTypeMap.get(activityType));
				activity.setActivityTemplate(activityTemplateMap.get(rs.getInt("activity_template_id")));
				activity.setActivityText(rs.getString("activity_text"));
				activity.setOrderNo(rs.getInt("order_no"));
				activities.add(activity);
			}
			return activities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}
	}

	private int getNextScoreId() throws AuthenticationException {
		try {
			Connection connection = dataSource.getConnection();

			String sql = "SELECT MAX(score_id) AS score_id FROM score";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) + 1;
			}

			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	}

	public Answer getAnswerById(int answerId) throws UserException {

		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from answer where answer_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, answerId);

			ResultSet rs = stmt.executeQuery();

			Answer answer = new Answer();
			while (rs.next()) {

				answer.setId(rs.getInt("answer_id"));
				answer.setAnswerText(rs.getString("answer_desc"));
				answer.setOrderNo(rs.getInt("order_no"));
			}
			return answer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}
	}

	@Override
	public void addscoreforuser(User user, double score) {
		// TODO Auto-generated method stub
		try {
			int sco=(int)score;
			Connection connection = dataSource.getConnection();
			String sql = "select score_range from score where usertype = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1,2 );
			ResultSet rs = stmt.executeQuery();
			List<Integer> score_range=new ArrayList<Integer>();
			while (rs.next()) {

				score_range.add(rs.getInt("score_range"));
			
			}
			int score_id=1;
		 for(Integer r:score_range)
		 {
			 if (sco<=r)
			 {
				 break;
			 }
			 score_id++;
		 }
		 
			sql = "select version_id from version_score where score_id= ?";
		    stmt = connection.prepareStatement(sql);
			stmt.setInt(1,score_id);
			rs = stmt.executeQuery();
			int version_id = 0;
			while (rs.next()) {

				version_id=rs.getInt("version_id");
			
			}

			sql = "update user set is_diagnostic_taken = ?, version_id = ?, score= ? where user_id = ?";
			PreparedStatement stmt3 = connection.prepareStatement(sql);
			stmt3.setInt(1, 1);
			stmt3.setInt(2, version_id);
			stmt3.setInt(3, sco);
			stmt3.setInt(4, user.getId());
			int records = stmt3.executeUpdate();
			System.out.println("No. of records inserted: " + records);
			 
			sql = "select topic_id from version_topic where version_id= ?";
		    stmt = connection.prepareStatement(sql);
			stmt.setInt(1,version_id);
			rs = stmt.executeQuery();
			List<Integer> topics=new ArrayList<Integer>();
			while (rs.next()) {

				topics.add(rs.getInt("topic_id"));
			
			}
                for(Integer topic_id:topics)
                {
                	sql = "insert into user_topic_status (user_id, topic_id, topic_status_id) "
        					+ " values (?, ?, ?)";
        			stmt = connection.prepareStatement(sql );
        			
        			stmt.setInt(1, user.getId());
        			stmt.setInt(2, topic_id);
        			stmt.setInt(3,1);
        			
        			records = stmt.executeUpdate();
        			
        			System.out.println("No. of records inserted: "+records);
                }
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new AuthenticationException(e);
			} catch (AuthenticationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	@Override
	public Integer[] getweigh() throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = dataSource.getConnection();
		String sql = "Select * from activity_score";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Integer> weighlist=new ArrayList<Integer>();
		while (rs.next()) {

			weighlist.add(rs.getInt("score"));
		
		}
		Integer[] weighs=weighlist.toArray(new Integer[weighlist.size()]);
		
		
		
		return weighs;
	}

}

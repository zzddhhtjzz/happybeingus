/**
 * 
 */
package com.neu.msd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.msd.dao.AdminDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
@Service
public class AdminServiceImpl implements AdminServie {

	@Autowired
	private AdminDao adminDao;
	
	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#loadTopics()
	 */
	public List<Topic> loadTopics() throws AdminException {
		
		List<Topic> allTopics = adminDao.loadTopics();
		
		loadTopicsWithActivityContainers(allTopics);
		
		return allTopics;
	}

	/**
	 * @param allTopics
	 * @throws AdminException
	 */
	private void loadTopicsWithActivityContainers(List<Topic> allTopics) throws AdminException {
			
		for(Topic topic : allTopics){
			List<ActivityContainer> activityContainers = adminDao.loadActivityContainersByTopicId(topic.getId());
			topic.setActivityContainers(activityContainers);
//			loadActivityContainersWithActivities(activityContainers);
		}
	}

	/**
	 * @param activityContainers
	 * @throws AdminException
	 */
	private void loadActivityContainersWithActivities(List<ActivityContainer> activityContainers) throws AdminException {

		for(ActivityContainer activityContainer : activityContainers){
			List<Activity> activities = adminDao.loadActivitiesByActivityContainerId(activityContainer.getActivityContainerId());
			activityContainer.setActivities(activities);
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#getActivityContainerById(int)
	 */
	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException {
		
		return adminDao.loadActivityContainerById(activityContainerId);
	}

}
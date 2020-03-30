
package com.lin.imissyou.services;

import com.lin.imissyou.model.Activity;
import com.lin.imissyou.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public Activity getByName(String name) {
        return activityRepository.findByName(name);
    }

//    public List<Activity> getByCategoryId(Long categoryId){
//        Date date = new Date();
//        List<Activity> activities = activityRepository
//                .findByStartTimeLessThanAndEndTimeGreaterThanAndCategoryListId(
//                        date, date, categoryId);
//        return activities;
//    }
}

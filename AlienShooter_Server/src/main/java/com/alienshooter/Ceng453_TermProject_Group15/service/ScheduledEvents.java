package com.alienshooter.Ceng453_TermProject_Group15.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledEvents {


    @Autowired
    private LeaderboardWeeklyService leaderboard_weeklyService;


    /*This runs itself every 1 hour(3600000 ms) and it cleans the scores if they are posted more than 7 days ago */
    @Scheduled(fixedRate = 3600000)
    public void scheduled_event1()
    {
        Date date_now = new Date();
        java.sql.Date sql_date_now = new java.sql.Date(date_now.getTime());
        System.out.println(sql_date_now);
        leaderboard_weeklyService.delete_score_weekly(sql_date_now);
    }


}

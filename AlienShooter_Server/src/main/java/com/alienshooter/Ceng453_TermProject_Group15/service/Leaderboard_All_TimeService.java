package com.alienshooter.Ceng453_TermProject_Group15.service;

import com.alienshooter.Ceng453_TermProject_Group15.model.Leaderboard_All_Time;
import com.alienshooter.Ceng453_TermProject_Group15.repository.Leaderboard_All_Time_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service("Leaderboard_All_TimeService")
public class Leaderboard_All_TimeService {

    private Leaderboard_All_Time_Repository leaderboard_all_time_repository;

    @Autowired
    public Leaderboard_All_TimeService(Leaderboard_All_Time_Repository leaderboard_all_time_repository) {
        this.leaderboard_all_time_repository = leaderboard_all_time_repository;
    }

    /*Score update is implemented by using query in repository */
    public void update_score(long id, long score, Date date){
        leaderboard_all_time_repository.score_update(id,score,date);
    }

    /*Get by id is implemented by using query in repository */
    public Leaderboard_All_Time get_by_username(long id)
    {
        return leaderboard_all_time_repository.get_by_userid(id);
    }

    /*Gets the leaderboard. It was already implemented in jpaRepository interface */
    public List<Leaderboard_All_Time> get_all_time_leaderboard()
    {
        return leaderboard_all_time_repository.findAll();
    }

    /*Saves the score. It was already implemented in jpaRepository interface */
    public void save_score(Leaderboard_All_Time leaderboard) {
        leaderboard_all_time_repository.save(leaderboard);
    }

}
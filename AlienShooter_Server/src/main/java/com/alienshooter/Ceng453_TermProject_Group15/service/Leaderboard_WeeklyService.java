package com.alienshooter.Ceng453_TermProject_Group15.service;

import com.alienshooter.Ceng453_TermProject_Group15.model.Leaderboard_Weekly;
import com.alienshooter.Ceng453_TermProject_Group15.repository.Leaderboard_Weekly_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service("Leaderboard_WeeklyService")
public class Leaderboard_WeeklyService {

    private Leaderboard_Weekly_Repository leaderboard_weekly_repository;

    @Autowired
    public Leaderboard_WeeklyService(Leaderboard_Weekly_Repository leaderboard_weekly_repository) {
        this.leaderboard_weekly_repository = leaderboard_weekly_repository;
    }

    /*Updates the score of player according to the query in repository*/
    public void update_score(long id, long score, Date date){
        leaderboard_weekly_repository.score_update(id,score,date);
    }

    /*Implemented get by id*/
    public Leaderboard_Weekly get_by_username(long id)
    {
        return leaderboard_weekly_repository.get_by_userid(id);
    }

    /*This is the function call of the scheduled event of cleaning weekly leaderboard */
    void delete_score_weekly(Date date)
    {
        leaderboard_weekly_repository.delete_score_weekly(date);
    }

    /*Gets all scores in Leaderboard_Weekly, already in jpaRepository */
    public List<Leaderboard_Weekly> get_weekly_leaderboard()
    {
        return leaderboard_weekly_repository.findAll();
    }

    /*Saves the score in Leaderboard_Weekly, already in jpaRepository */
    public void save_score(Leaderboard_Weekly leaderboard) {
        leaderboard_weekly_repository.save(leaderboard);
    }

}

package com.alienshooter.Ceng453_TermProject_Group15.service;

import com.alienshooter.Ceng453_TermProject_Group15.model.LeaderboardWeekly;
import com.alienshooter.Ceng453_TermProject_Group15.repository.LeaderboardWeeklyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service("Leaderboard_WeeklyService")
public class LeaderboardWeeklyService {

    private LeaderboardWeeklyRepository leaderboard_weekly_repository;

    @Autowired
    public LeaderboardWeeklyService(LeaderboardWeeklyRepository leaderboard_weekly_repository) {
        this.leaderboard_weekly_repository = leaderboard_weekly_repository;
    }

    /*Updates the score of player according to the query in repository*/
    public void update_score(long id, long score, Date date){
        leaderboard_weekly_repository.score_update(id,score,date);
    }

    /*Implemented get by id*/
    public LeaderboardWeekly get_by_username(long id)
    {
        return leaderboard_weekly_repository.get_by_userid(id);
    }

    /*This is the function call of the scheduled event of cleaning weekly leaderboard */
    void delete_score_weekly(Date date)
    {
        leaderboard_weekly_repository.delete_score_weekly(date);
    }

    /*Gets all scores in LeaderboardWeekly, already in jpaRepository */
    public List<LeaderboardWeekly> get_weekly_leaderboard()
    {
        return leaderboard_weekly_repository.findAll();
    }

    /*Saves the score in LeaderboardWeekly, already in jpaRepository */
    public void save_score(LeaderboardWeekly leaderboard) {
        leaderboard_weekly_repository.save(leaderboard);
    }

}

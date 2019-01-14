package com.alienshooter.Ceng453_TermProject_Group15.service;

import com.alienshooter.Ceng453_TermProject_Group15.model.LeaderboardAllTime;
import com.alienshooter.Ceng453_TermProject_Group15.repository.LeaderboardAlltimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.List;

@Service("Leaderboard_All_TimeService")
public class LeaderboardAlltimeService {

    private LeaderboardAlltimeRepository leaderboard_alltime_repository;

    @Autowired
    public LeaderboardAlltimeService(LeaderboardAlltimeRepository leaderboard_alltime_repository) {
        this.leaderboard_alltime_repository = leaderboard_alltime_repository;
    }

    /*Score update is implemented by using query in repository */
    public void update_score(long id, long score, Date date){
        leaderboard_alltime_repository.score_update(id,score,date);
    }

    /*Get by id is implemented by using query in repository */
    public LeaderboardAllTime get_by_username(long id)
    {
        return leaderboard_alltime_repository.get_by_userid(id);
    }

    /*Gets the leaderboard. It was already implemented in jpaRepository interface */
    public List<LeaderboardAllTime> get_all_time_leaderboard()
    {
        return leaderboard_alltime_repository.findAll();
    }

    /*Saves the score. It was already implemented in jpaRepository interface */
    public void save_score(LeaderboardAllTime leaderboard) {
        leaderboard_alltime_repository.save(leaderboard);
    }

}
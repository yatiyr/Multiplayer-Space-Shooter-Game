package com.alienshooter.Ceng453_TermProject_Group15.repository;

import com.alienshooter.Ceng453_TermProject_Group15.model.LeaderboardWeekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;

@Repository
public interface LeaderboardWeeklyRepository extends JpaRepository<LeaderboardWeekly,Long>{

    /*
    This updates LeaderboardAllTime table in our database.
    When a player's score is updated it updates the leaderboard accordingly.
     */
    @Transactional
    @Modifying
    @Query("UPDATE LeaderboardWeekly SET date = :date,score = :score WHERE userid = :id")
    void score_update(@Param("id") long id,@Param("score") long score,@Param("date") Date date);

    /*
    This query is scheduled in ScheduledEvents.java class.It is for cleaning the weekly leaderboard
    according to current time.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM LeaderboardWeekly WHERE DATEDIFF(:date, date ) >= 7 OR DATEDIFF(date, :date ) >=7 ")
    void delete_score_weekly(@Param("date") Date date);

    /*
    This gets the user's score according to the id given.
     */
    @Query("SELECT u FROM LeaderboardWeekly u WHERE userid = :id")
    LeaderboardWeekly get_by_userid(@Param("id") long id);

}

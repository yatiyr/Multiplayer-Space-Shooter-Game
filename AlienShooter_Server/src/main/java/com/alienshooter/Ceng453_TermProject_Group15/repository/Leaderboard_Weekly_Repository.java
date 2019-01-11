package com.alienshooter.Ceng453_TermProject_Group15.repository;

import com.alienshooter.Ceng453_TermProject_Group15.model.Leaderboard_Weekly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;

@Repository
public interface Leaderboard_Weekly_Repository extends JpaRepository<Leaderboard_Weekly,Long>{

    /*
    This updates Leaderboard_All_Time table in our database.
    When a player's score is updated it updates the leaderboard accordingly.
     */
    @Transactional
    @Modifying
    @Query("UPDATE Leaderboard_Weekly SET date = :date,score = :score WHERE userid = :id")
    void score_update(@Param("id") long id,@Param("score") long score,@Param("date") Date date);

    /*
    This query is scheduled in Scheduled_events.java class.It is for cleaning the weekly leaderboard
    according to current time.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Leaderboard_Weekly WHERE DATEDIFF(:date, date ) >= 7 OR DATEDIFF(date, :date ) >=7 ")
    void delete_score_weekly(@Param("date") Date date);

    /*
    This gets the user's score according to the id given.
     */
    @Query("SELECT u FROM Leaderboard_Weekly u WHERE userid = :id")
    Leaderboard_Weekly get_by_userid(@Param("id") long id);

}

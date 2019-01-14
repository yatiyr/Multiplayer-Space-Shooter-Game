package com.alienshooter.Ceng453_TermProject_Group15.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity // This tells Hibernate to make a table out of this class
@Table(name="LeaderboardWeekly")
public class LeaderboardWeekly {

    /*
    Leaderboard weekly class is very similar to leaderboard_all_time class
    However, every hour, server checks the dates of the scores whether they
    have passed 7 days and if so, those scores are deleted from the leaderboard.
    This scheduled function is implemented in ScheduledEvents.java inside service
    folder.
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="userid",insertable = true, updatable = true)
    private long userid;


    /*
    This property is a foreign key which takes the id from the user entity.
    If that user is deleted, that user's highscore is also deleted.
     */
    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="userid",insertable = false, updatable = false)
    private User user;

    @Column(name="username")
    @NotNull
    private String username;

    @Column(name="score")
    @NotNull
    private long score;

    @Column(name="date")
    @NotNull
    private Date date;

    public int getId() {
        return id;
    }

    public long getUserId(){
        return this.userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public LeaderboardWeekly(long user_id, long score_, Date date, String username)
    {
        this.score = score_;
        this.userid = user_id;
        this.date = date;
        this.username = username;
    }

    public LeaderboardWeekly() { }

    public void setId(int id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        score = score;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
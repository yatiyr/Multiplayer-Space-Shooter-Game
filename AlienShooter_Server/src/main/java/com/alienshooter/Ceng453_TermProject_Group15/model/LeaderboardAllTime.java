package com.alienshooter.Ceng453_TermProject_Group15.model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity // This tells Hibernate to make a table out of this class
@Table(name="LeaderboardAllTime")
public class LeaderboardAllTime {

    /*
    LeaderboardAllTime class holds the scores of players
    The dates when those scores made and the id of the player
    who scored.
     */

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;


    @Column(name="userid",insertable = true, updatable = true)
    private long user_id;

    /*
    This is a foreign key referencing User class' id.
    If the user is deleted, its corresponding row in
    LeaderboardAllTime table is deleted.
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
    private long score_;

    @Column(name="date")
    @NotNull
    private Date date;

    public int getId() {
        return id;
    }

    public long getUserId(){
        return this.user_id;
    }


    public void setUser_id(long user_id)
    {
        this.user_id = user_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getScore() {
        return score_;
    }

    public String getUsername() { return this.username; }

    public void setScore(long score) {
        this.score_ = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
package com.alienshooter.Ceng453_TermProject_Group15.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity // This tells Hibernate to make a table out of this class
@Table(name="USER")
public class USER {

    @Id
    @Column(name="UserID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long userid;     /* This is the primary key of our table */

    @Column(name = "UserName")
    @NotEmpty(message = "*You need to provide your name")
    @NotBlank
    private String name;

    @Column(name="Password")
    @Length(min = 6,message = "*Your Password is really bad.Make it have at least 6 characters")
    @NotEmpty(message = "*You have to provide your password")
    @NotBlank
    private String password;


    public long getId() {
        return userid;
    }


    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public String getPassword() {
        return this.password;
    }

}
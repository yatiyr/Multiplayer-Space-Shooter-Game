package com.alienshooter.Ceng453_TermProject_Group15;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableScheduling
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Value("${spring.queries.users-query}")
    private String usersQuery;



/*
This is for ensuring the security of the web application.
 */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/sign_in").permitAll()
                .antMatchers("/update_score").permitAll()
                .antMatchers("/update_score_alltime").permitAll()
                .antMatchers("/update_score_weekly").permitAll()
                .antMatchers("/update_password").permitAll()
                .antMatchers("/update_name").permitAll()
                .antMatchers("/get_weekly_leaderboard").permitAll()
                .antMatchers("/get_all_time_leaderboard").permitAll()
                .antMatchers("/delete_user").permitAll()
                .anyRequest().authenticated()
                .and();
    }

}


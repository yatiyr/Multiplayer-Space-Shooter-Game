package com.alienshooter.Ceng453_TermProject_Group15;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.alienshooter.Ceng453_TermProject_Group15.model.USER;
import com.alienshooter.Ceng453_TermProject_Group15.service.USERService;

import com.alienshooter.Ceng453_TermProject_Group15.model.Leaderboard_All_Time;
import com.alienshooter.Ceng453_TermProject_Group15.service.Leaderboard_All_TimeService;

import com.alienshooter.Ceng453_TermProject_Group15.model.Leaderboard_Weekly;
import com.alienshooter.Ceng453_TermProject_Group15.service.Leaderboard_WeeklyService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private USERService userService;

    @Autowired
    private Leaderboard_All_TimeService leaderboard_all_timeService;

    @Autowired
    private Leaderboard_WeeklyService leaderboard_weeklyService;

    public LoginController(USERService userService,Leaderboard_All_TimeService leaderboard_all_timeService,Leaderboard_WeeklyService leaderboard_weeklyService)
    {
        this.userService = userService;
        this.leaderboard_weeklyService = leaderboard_weeklyService;
        this.leaderboard_all_timeService = leaderboard_all_timeService;
    }

    /*
    Return type: json-status and message

    Takes the username and checks whether that user exists
    if exists, then delete it.

    Parameter :Json  @RequestBody
     */
    @RequestMapping(value="/delete_user",method = RequestMethod.POST)
    public ResponseEntity delete_user(@RequestBody USER user) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        USER user_exists = userService.findByName(user.getName());
        if(user_exists == null)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("this user already doesn't exist");
        }

        String_Encrypt_Decryptor encrypt_decryptor = new String_Encrypt_Decryptor(user.getPassword(),0);
        String password = encrypt_decryptor.getPassphrase_enc();
        if(password.equals(user_exists.getPassword()))
        {
            System.out.println(user.getId());
            userService.delete_user(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("user is successfully deleted");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("wrong password");
    }

    /*
    Returns the whole list of all_time_leaderboard in json to the client.
     */
    @RequestMapping(value="/get_all_time_leaderboard",method = RequestMethod.GET)
    public ResponseEntity<List<Leaderboard_All_Time>> get_all_time_leaderboard()
    {
        List<Leaderboard_All_Time> lb_list = leaderboard_all_timeService.get_all_time_leaderboard();

        return new ResponseEntity<>(lb_list,HttpStatus.OK);
    }

    /*
    Returns the whole list of weeklt_leaderboard in json to the client.
     */
    @RequestMapping(value="/get_weekly_leaderboard",method = RequestMethod.GET)
    public ResponseEntity<List<Leaderboard_Weekly>> get_weekly_leaderboard()
    {
        List<Leaderboard_Weekly> lb_list = leaderboard_weeklyService.get_weekly_leaderboard();

        return new ResponseEntity<>(lb_list,HttpStatus.OK);

    }

    /*
    Updates the name and returns the specified json message and status.
    Parameter: json @RequestBody
     */
    @RequestMapping(value="/update_name",method=RequestMethod.POST)
    public ResponseEntity update_name(@RequestBody USER user) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        USER user_exists = userService.findByName(user.getName());
        USER real_user = userService.findById(user.getId());
        if(user_exists == null)
        {
            String_Encrypt_Decryptor encrypt_decryptor = new String_Encrypt_Decryptor(user.getPassword(),0);
            String password = encrypt_decryptor.getPassphrase_enc();
            if(password.equals(real_user.getPassword())) {
                userService.username_update(user);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("name successfully updated");
            }
            else
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("wrong password");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("this name already exists");

    }

    /*Updates the password of the user specified in @Request Body
    and returns the specified json message and status.
     */
    @RequestMapping(value="/update_password",method=RequestMethod.POST)
    public ResponseEntity update_password(@RequestBody USER user) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        USER user_exists = userService.findByName(user.getName());
        //USER real_user = userService.findById(user.getId());
        if(user_exists != null)
        {
                userService.password_update(user);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("password successfully updated");

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("this user doesn't even exist");
    }

    /*
    Return type: json response
    Parameter: json @RequestBody

    Updates the score of the player.
     */
    /*@RequestMapping(value="/update_score",method=RequestMethod.POST)
    public ResponseEntity update_score(@RequestBody Leaderboard_All_Time leaderboard)
    {
        Leaderboard_Weekly leaderboard_weekly = new Leaderboard_Weekly(leaderboard.getUserId(),leaderboard.getScore(),leaderboard.getDate(),leaderboard.getUsername());
        Leaderboard_All_Time all_time_exists = leaderboard_all_timeService.get_by_username(leaderboard.getUserId());
        Leaderboard_Weekly weekly_exists = leaderboard_weeklyService.get_by_username(leaderboard.getUserId());

        if(all_time_exists != null && weekly_exists !=null)
        {
            leaderboard_all_timeService.update_score(leaderboard.getUserId(),leaderboard.getScore(),leaderboard.getDate());
            leaderboard_weeklyService.update_score(leaderboard.getUserId(),leaderboard.getScore(), leaderboard.getDate());
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Leaderboard(s) updated");
        }
        if(all_time_exists != null && weekly_exists == null)
        {
            leaderboard_all_timeService.update_score(leaderboard.getUserId(),leaderboard.getScore(),leaderboard.getDate());
            leaderboard_weeklyService.save_score(leaderboard_weekly);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("New Score added to weekly table and all time table updated");
        }

        leaderboard_all_timeService.save_score(leaderboard);
        leaderboard_weeklyService.save_score(leaderboard_weekly);
        return ResponseEntity.status(HttpStatus.OK)
                .body("New Score added");

    } */

    /*
    Return type: json response
    Parameter: json @RequestBody

    Updates the score of the player for only all_time leaderboard.
     */
    @RequestMapping(value="/update_score_alltime",method=RequestMethod.POST)
    public ResponseEntity update_score_alltime(@RequestBody Leaderboard_All_Time leaderboard)
    {
        Leaderboard_All_Time all_time_exists = leaderboard_all_timeService.get_by_username(leaderboard.getUserId());

        if(all_time_exists != null)
        {
            if(leaderboard.getScore() > all_time_exists.getScore()) {
                leaderboard_all_timeService.update_score(leaderboard.getUserId(), leaderboard.getScore(), leaderboard.getDate());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Leaderboard(s) updated");
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Leaderboard could not updated");
            }
        }
        leaderboard_all_timeService.save_score(leaderboard);
        return ResponseEntity.status(HttpStatus.OK)
                .body("New Score added");
    }

    /*
    Return type: json response
    Parameter: json @RequestBody

    Updates the score of the player for only weekly leaderboard.
    */
    @RequestMapping(value="/update_score_weekly",method=RequestMethod.POST)
    public ResponseEntity update_score_weekly(@RequestBody Leaderboard_All_Time leaderboard)
    {
        Leaderboard_Weekly leaderboard_weekly = new Leaderboard_Weekly(leaderboard.getUserId(),leaderboard.getScore(),leaderboard.getDate(),leaderboard.getUsername());
        Leaderboard_Weekly weekly_exists = leaderboard_weeklyService.get_by_username(leaderboard.getUserId());

        if(weekly_exists !=null)
        {
            if(leaderboard.getScore() > weekly_exists.getScore()) {
                leaderboard_weeklyService.update_score(leaderboard.getUserId(), leaderboard.getScore(), leaderboard.getDate());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Leaderboard(s) updated");
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Leaderboard(s) could not updated");
            }

        }

        leaderboard_weeklyService.save_score(leaderboard_weekly);
        return ResponseEntity.status(HttpStatus.OK)
                .body("New Score added");

    }



    /*
    Return type: If the authentication is ok, it returns all the information of the specified user and httpstatus ok
    if it is not ok, then it returns a null user and httpstatus bad request.
     */
    @RequestMapping(value="/sign_in",method = RequestMethod.POST)
    public ResponseEntity<USER> sign_in(@RequestBody USER user) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {


        //USER user_r = null;
        USER user_exists = userService.findByName(user.getName());
        if(user_exists != null)
        {
            String_Encrypt_Decryptor encrypt_decryptor = new String_Encrypt_Decryptor(user.getPassword(),0);
            String password = encrypt_decryptor.getPassphrase_enc();
            if(password.equals(user_exists.getPassword()))
            {
                return new ResponseEntity<>(user_exists, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
    }


    /*
    Return type: json response
    Parameter : json @ResponseBody user
    It takes the user's name and password,
    encrypts the password and saves it
    if the password is more than 6 characters and
    the user name is unique.
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody USER user) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {

        USER userExists = userService.findByName(user.getName());
        if (userExists != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("There is already a user with that name");
        }
        if (user.getPassword().length() < 6) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Your password needs to be at least 6 characters length");
        } else {
            userService.saveUser(user);
        }
        return ResponseEntity.status(HttpStatus.OK)
                             .body("Registration successful");
    }

}

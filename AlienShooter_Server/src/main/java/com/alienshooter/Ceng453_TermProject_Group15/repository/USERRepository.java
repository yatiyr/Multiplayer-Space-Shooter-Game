package com.alienshooter.Ceng453_TermProject_Group15.repository;

import com.alienshooter.Ceng453_TermProject_Group15.model.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface USERRepository extends JpaRepository<USER,Long>{
    USER findByName(String username);


    /*
    This updates the name of the user.
    The conditions are checked in controller.
     */
    @Transactional
    @Modifying
    @Query("UPDATE USER SET name = :username WHERE userid = :userid")
    void username_update(@Param("userid") long id, @Param("username") String username);

    /*
    This updates the password of the user.
    The conditions are checked in controller.
     */
    @Transactional
    @Modifying
    @Query("UPDATE USER SET password = :password WHERE userid = :userid")
    void password_update(@Param("userid") long id, @Param("password") String password);

    /*
    This deletes the user according to the id given.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM USER WHERE userid = :userid")
    void delete_user(@Param("userid") long id);

}

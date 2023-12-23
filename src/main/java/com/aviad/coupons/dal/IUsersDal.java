package com.aviad.coupons.dal;

import com.aviad.coupons.beans.SuccessfulLoginDetails;
import com.aviad.coupons.beans.UserLoginData;
import com.aviad.coupons.dto.User;
import com.aviad.coupons.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUsersDal extends CrudRepository<UserEntity, Long> {
    @Query("SELECT new com.aviad.coupons.beans.SuccessfulLoginDetails(u.id, u.userType, u.company.id) " +
            "FROM UserEntity u WHERE u.username = :#{#userLoginData.username} AND u.password = :#{#userLoginData.password}")
    SuccessfulLoginDetails login(@Param("userLoginData") UserLoginData userLoginData);

    @Query("SELECT new com.aviad.coupons.dto.User (u.id, u.username, u.email, u.userType, u.company.id)" +
            "FROM UserEntity u")
    List<User> getUsers();

    @Query("SELECT new com.aviad.coupons.dto.User (u.id, u.username, u.email, u.userType, u.company.id)" +
            "FROM UserEntity u WHERE u.id = :id")
    User getUser(@Param("id") int id);

    @Query("SELECT new com.aviad.coupons.dto.User (u.id, u.username, u.email, u.userType, u.company.id)" +
            "FROM UserEntity u WHERE u.company.id = :id")
    List<User> getUsersByCompanyId(@Param("id") int id);

    @Query("SELECT password From UserEntity u WHERE u.id = :id")
    String getPasswordByUserId(@Param("id") int id);
}

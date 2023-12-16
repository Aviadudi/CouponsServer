package com.aviad.coupons.logic;


import com.aviad.coupons.beans.SuccessfulLoginDetails;
import com.aviad.coupons.beans.UserLoginData;
import com.aviad.coupons.dal.IUsersDal;
import com.aviad.coupons.dto.User;
import com.aviad.coupons.entities.UserEntity;
import com.aviad.coupons.enums.ErrorType;
import com.aviad.coupons.enums.UserType;
import com.aviad.coupons.exceptions.ApplicationException;
import com.aviad.coupons.utils.EncryptUtils;
import com.aviad.coupons.utils.JWTUtils;
import com.aviad.coupons.utils.TokenDecodedDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserLogic {
    private IUsersDal usersDal;

    public UserLogic(IUsersDal usersDal) {
        this.usersDal = usersDal;
    }

    public void addUser(User user) throws ApplicationException {
        validateUserData(user);
        String originalPassword = user.getPassword();
        String encryptedPassword = EncryptUtils.encryptPassword(originalPassword);
        user.setPassword(encryptedPassword);
        UserEntity userEntity = new UserEntity(user);
        this.usersDal.save(userEntity);
    }

    public User getUser(int id) throws ApplicationException {
        return this.usersDal.getUser(id);
    }

    public void deleteUser(long id) throws ApplicationException {
        this.usersDal.deleteById(id);
    }

    public void updateUser(User user, String token) throws ApplicationException {
        validateUserData(user);

        String originalPassword = user.getPassword();
        String encryptedPassword = EncryptUtils.encryptPassword(originalPassword);
        user.setPassword(encryptedPassword);

        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        user.setId(successfulLoginDetails.getId());
        user.setUserType(successfulLoginDetails.getUserType());
        user.setCompanyId(successfulLoginDetails.getCompanyId());

        UserEntity userEntity = new UserEntity(user);
        this.usersDal.save(userEntity);
    }

    public List<User> getAllUsers(String token) throws ApplicationException {
        if (!isAdmin(token)) {
            return Collections.emptyList();
        }
        return this.usersDal.getUsers();
    }

    public List<User> getUsersByCompanyId(int id, String token) throws ApplicationException {
        // Validate that user from type Company can only request to see the users from his own company
        if (isCompany(token)){
            int userId = TokenDecodedDataUtil.decodedCompanyId(token);
            id = userId;
        }
        return this.usersDal.getUsersByCompanyId(id);
    }

    public String login(UserLoginData userLoginData) throws Exception {
        validateUserName(userLoginData.getUsername());
        validateUserPassword(userLoginData.getPassword());

        String originalPassword = userLoginData.getPassword();
        String encryptedPassword = EncryptUtils.encryptPassword(originalPassword);
        userLoginData.setPassword(encryptedPassword);

        SuccessfulLoginDetails successfulLoginDetails = this.usersDal.login(userLoginData);
        if (successfulLoginDetails == null) {
            throw new ApplicationException(ErrorType.FAILED_LOGIN);
        }
        String token = JWTUtils.createJWT(successfulLoginDetails);
        return token;
    }

    private void validateUserData(User user) throws ApplicationException {
        validateUserName(user.getUsername());
        validateUserPassword(user.getPassword());
        validateUserEmail(user.getEmail());
    }


    private void validateUserPassword(String password) throws ApplicationException {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,12}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        if (password == null) {
            throw new ApplicationException(ErrorType.INVALID_PASSWORD);
        }
        if (!pattern.matcher(password).matches()) {
            throw new ApplicationException(ErrorType.INVALID_PASSWORD);
        }
    }

    private void validateUserEmail(String email) throws ApplicationException {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null || email.isEmpty()) {
            throw new ApplicationException(ErrorType.EMAIL_NOT_PROVIDED);
        }
        if (!pattern.matcher(email).matches()) {
            throw new ApplicationException(ErrorType.INVALID_EMAIL);
        }
    }

    private void validateUserName(String username) throws ApplicationException {
        if (username.length() < 4) {
            throw new ApplicationException(ErrorType.USERNAME_TOO_SHORT);
        }
        if (username.length() > 15) {
            throw new ApplicationException(ErrorType.USERNAME_TOO_LONG);
        }
    }

    public User getUserByToken(String token) throws ApplicationException {
        SuccessfulLoginDetails successfulLoginDetails;
        try {
            String tokenWithoutBearer = JWTUtils.getTokenWithoutBearer(token);
            Claims claims = JWTUtils.decodeJWTClaims(tokenWithoutBearer);
            ObjectMapper objectMapper = new ObjectMapper();
            successfulLoginDetails = objectMapper.readValue(claims.getSubject(), SuccessfulLoginDetails.class);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getErrorMessage(), e);
        }
        int userId = successfulLoginDetails.getId();
        User user = getUser(userId);
        return user;
    }
    private boolean isAdmin(String token) throws ApplicationException {
        UserType userType = TokenDecodedDataUtil.decodedUserType(token);
        return userType.equals(UserType.ADMIN);
    }
    private boolean isCompany(String token) throws ApplicationException {
        UserType userType = TokenDecodedDataUtil.decodedUserType(token);
        return userType.equals(UserType.COMPANY);
    }
}

package com.authservice.auth.util;


import com.authservice.auth.dao.AuthRequest;
import com.authservice.auth.model.UserData;

public class ValueMapper {


    public static UserData convertDtoToEntity(AuthRequest userRequest)
    {
       UserData userData=new UserData();
       userData.setName(userRequest.getName());
       userData.setEmail(userRequest.getEmail());
       userData.setPassword(userRequest.getPassword());
       userData.setAge(userRequest.getAge());
       return userData;
    }



}

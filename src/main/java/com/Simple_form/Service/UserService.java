package com.Simple_form.Service;

import com.Simple_form.Model.UserModel;
import com.Simple_form.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserModel registerUser(String username , String password,String email) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }
        else{
            UserModel userModel = new UserModel();
            userModel.setUsername(username);
            userModel.setPassword(password);
            userModel.setEmail(email);
            return userRepository.save(userModel);
        }
    }

    public UserModel authentication(String username , String password){
        return userRepository.findByUsernameAndPassword(username, password).orElse(null);
    }
}

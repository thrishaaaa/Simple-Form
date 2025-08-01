package com.Simple_form.Repository;

import com.Simple_form.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findByUsernameAndPassword(String username, String password);
}

package com.product.user.repos;

import com.product.user.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<user,Long> {

    user getOneUser(Long id);

}

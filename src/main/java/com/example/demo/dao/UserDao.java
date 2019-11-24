package com.example.demo.dao;


import com.example.demo.pojo.User;
import javafx.concurrent.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

//接口
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByUsername(String username);

//    @Modifying
//    @Transactional
//    @Query(value = "delete from check_product_status where  id not in (:listParam)", nativeQuery = true)
//    void updateBo(@Param("listParam") List<Integer> listParam);


    @Modifying
    @Transactional
    @Query(value = "update user2 u set u.bo = ?2 where u.id = ?1", nativeQuery = true)
    void updateBo(String id,boolean bo);



    @Modifying
    @Transactional
    @Query(value = "update user2 u set u.email = ?2 , u.nickname=?3,u.username=?4 ,u.update_time=?5 where u.id = ?1", nativeQuery = true)
    void updateuser(String id, String email, String nickname, String username, Date a);

//    @Query("update user2 u set u.bo = ?2 where u.id = ?1")
//    User updateBo(String id,boolean bo );
}

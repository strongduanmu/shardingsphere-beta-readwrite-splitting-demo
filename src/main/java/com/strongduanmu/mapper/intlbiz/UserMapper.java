package com.strongduanmu.mapper.intlbiz;

import com.strongduanmu.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    
    int deleteByPrimaryKey(Integer id);
    
    int insert(User record);
    
    User selectByPrimaryKey(Integer id);
    
    List<User> selectAll();
    
    int updateByPrimaryKey(User record);
}
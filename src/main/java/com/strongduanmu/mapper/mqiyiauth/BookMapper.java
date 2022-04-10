package com.strongduanmu.mapper.mqiyiauth;

import com.strongduanmu.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {
    
    int deleteByPrimaryKey(Integer id);
    
    int insert(Book record);
    
    Book selectByPrimaryKey(Integer id);
    
    List<Book> selectAll();
    
    int updateByPrimaryKey(Book record);
}
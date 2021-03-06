package com.strongduanmu.mapper.qiyiauth;

import com.strongduanmu.domain.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookMapper {
    
    int deleteByPrimaryKey(Integer id);
    
    int insert(Book record);
    
    Book selectByPrimaryKey(Integer id);
    
    List<Book> selectAll();
    
    int updateByPrimaryKey(Book record);
    
    int selectLastInsertId();
    
    Long replace(Book record);
}
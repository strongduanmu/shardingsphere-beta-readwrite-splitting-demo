package com.strongduanmu.service.impl;

import java.util.List;

import com.strongduanmu.domain.Book;
import com.strongduanmu.mapper.mqiyiauth.BookMapper;
import com.strongduanmu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    
    @Autowired
    private BookMapper bookMapper;
    
    @Override
    public List<Book> selectAll() {
        return bookMapper.selectAll();
    }
}

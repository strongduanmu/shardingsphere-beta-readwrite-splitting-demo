package com.strongduanmu;

import com.strongduanmu.domain.Book;
import com.strongduanmu.mapper.intlbiz.UserMapper;
import com.strongduanmu.mapper.qiyiauth.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class ReadWriteSplittingTest {
    
    @SpringBootApplication(scanBasePackages = "com.strongduanmu")
    static class InnerConfig {
    }
    
    @Autowired
    BookMapper bookMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Test
    public void testSelectMultiThread() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 3000; i++) {
                try {
                    bookMapper.selectAll();
                } catch (Exception e) {
                    log.info("bookMapper selectAll exception:", e);
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 3000; i++) {
                try {
                    userMapper.selectAll();
                } catch (Exception e) {
                    log.info("userMapper selectAll exception:", e);
                }
            }
        });
        t1.start();
        t2.start();
        Thread.currentThread().join();
    }
    
    @Test
    public void testSelectLastInsertId() {
        for (int i = 0; i < 10; i++) {
            System.out.println(bookMapper.selectLastInsertId());
        }
    }
    
    @Test
    public void testReplace() {
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setAuthor("author" + i);
            book.setName("name" + i);
            book.setPrice(100L + i);
            book.setCreatetime(new Date());
            book.setDescription("description" + i);
            bookMapper.replace(book);
            System.out.println(book.getId());
        }
    }
}

package com.strongduanmu;

import com.strongduanmu.mapper.intlbiz.UserMapper;
import com.strongduanmu.mapper.mqiyiauth.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}

package com.strongduanmu.controller;

import com.strongduanmu.domain.Book;
import com.strongduanmu.domain.Response;
import com.strongduanmu.service.BookService;
import com.strongduanmu.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "/books")
    public Response selectAll() throws Exception {
        List<Book> books = bookService.selectAll();
        return ResponseUtils.success(books);
    }
}

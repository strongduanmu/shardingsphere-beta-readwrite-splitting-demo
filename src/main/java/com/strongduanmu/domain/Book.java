package com.strongduanmu.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public final class Book {
    
    private Long id;
    
    private String author;
    
    private String name;
    
    private Long price;
    
    private Date createtime;
    
    private String description;
}
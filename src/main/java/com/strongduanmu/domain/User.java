package com.strongduanmu.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public final class User {
    
    private Integer id;
    
    private String name;
    
    private Date birthday;
}
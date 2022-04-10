package com.strongduanmu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    
    private String code;
    
    private String message;
    
    private Collection<T> data;
}

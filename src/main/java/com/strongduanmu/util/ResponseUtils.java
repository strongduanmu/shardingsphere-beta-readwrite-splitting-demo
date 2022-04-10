package com.strongduanmu.util;

import com.strongduanmu.domain.Response;
import com.strongduanmu.enums.ResponseEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ResponseUtils {
    
    public static Response success(Object objects) {
        List list = new ArrayList<>();
        if (!(objects instanceof Collection)) {
            list.add(objects);
        } else {
            list = (ArrayList) objects;
        }
        return new Response("0000", "success", list);
    }
    
    public static Response success() {
        return success(new ArrayList<>());
    }
    
    public static Response error(String code, String message) {
        return new Response(code, message, new ArrayList<>());
    }
    
    public static Response error(ResponseEnum responseEnum) {
        return error(responseEnum.getCode(), responseEnum.getMessage());
    }
    
    public static Response error(ResponseEnum responseEnum, String message) {
        return error(responseEnum.getCode(), message);
    }
}

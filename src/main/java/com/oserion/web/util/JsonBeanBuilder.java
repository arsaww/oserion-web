package com.oserion.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oserion.web.bean.Content;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Arsaww on 12/6/2015.
 */
public class JsonBeanBuilder <T>{

    public T createBean(HttpServletRequest r, Class<T> BeanClass) throws IOException {

        // 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(r.getInputStream()));
        String json = br.readLine();

        // 2. initiate jackson mapper
        ObjectMapper mapper = new ObjectMapper();

        // 3. Convert received JSON to Bean
        return mapper.readValue(json, BeanClass);
    }

}

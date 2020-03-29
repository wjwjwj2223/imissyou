package com.lin.imissyou.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.imissyou.exception.http.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenericAndJson {

    private static ObjectMapper mapper;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }

    public static <T> String objectToJson(T o) {
         if (o == null) {
             return null;
         }
         try {
             return GenericAndJson.mapper.writeValueAsString(o);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
             throw new ServerErrorException(9999);
         }
     }

     public static <T> T jsonToObject(String s, TypeReference<T> tr) {
        if (s == null) {
            return null;
        }
         try {
             T t = GenericAndJson.mapper.readValue(s, tr);
             return t;
         } catch (JsonProcessingException e) {
             e.printStackTrace();
             throw  new ServerErrorException(9999);
         }
     }

//     public static <T> List<T> jsonToList(String s) {
//         if (s == null) {
//             return null;
//         }
//         try {
//             return GenericAndJson.mapper.readValue(s, new TypeReference<List<T>>() {
//             });
//         } catch (JsonProcessingException e) {
//             e.printStackTrace();
//             throw  new ServerErrorException(9999);
//         }
//     }

//     public static <T> T jsonToList(String s) {
//         if (s == null) {
//             return null;
//         }
//         try {
//             T t = GenericAndJson.mapper.readValue(s, new TypeReference<T>() {
//             });
//             return t;
//         } catch (JsonProcessingException e) {
//             e.printStackTrace();
//             throw  new ServerErrorException(9999);
//         }
//     }
}

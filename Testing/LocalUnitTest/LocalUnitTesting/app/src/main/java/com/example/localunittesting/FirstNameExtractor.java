package com.example.localunittesting;

public class FirstNameExtractor {

    public static String extractFirstName(String name){
        if (name == null || name == ""){
            return "";
        }
        String[] split = name.split(" ");
        for (String word : split){
            if (!word.isEmpty()){
                return word;
            }
        }
        return null;
    }

}

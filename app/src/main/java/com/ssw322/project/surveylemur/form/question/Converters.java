package com.ssw322.project.surveylemur.form.question;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Set;

import androidx.room.TypeConverter;

//TODO: Remove this along with Room
public class Converters {

    @TypeConverter
    public static Set<Integer> fromString(String s) {
        Type listType = new TypeToken<Set<Integer>>() {}.getType();
        return new Gson().fromJson(s, listType);
    }

    @TypeConverter
    public static String toString(Set<Integer> set) {
        return new Gson().toJson(set);
    }
}

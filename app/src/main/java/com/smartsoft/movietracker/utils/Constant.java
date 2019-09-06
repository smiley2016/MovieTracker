package com.smartsoft.movietracker.utils;

import java.util.ArrayList;

public class Constant {

    public static class HomeFragment{
        public static int COLUMN_NUM = 5;
    }

    public static class Common{
        public static String BASE_URL="https://api.themoviedb.org/3/";
        public static String API_KEY = "221aac16f7f58f1cf0fd5f99ff6e6b60";
        public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        public static final String LANGUAGE = "en-US";
        public static final String SORT_BY = "popularity.desc";
        public static int PAGE = 0;
        public static final boolean INCLUDE_ADULT = false;
        public static final boolean INCLUDE_VIDEO = false;

    }

    public static class Genre{
        public static ArrayList<com.smartsoft.movietracker.model.Genre> genre = new ArrayList<>();
    }
}

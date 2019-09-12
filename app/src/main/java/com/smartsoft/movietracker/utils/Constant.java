package com.smartsoft.movietracker.utils;

import java.util.ArrayList;

public class Constant {

    public static class HomeFragment{
        public static int COLUMN_NUM = 5;
    }

    public static class API {
        public static String BASE_YOUTUBE_URL_FOR_PICTURE = "http://img.youtube.com/vi/";
        public static String YOUTUBE_THUMBNAIL = "/0.jpg";
        public static String BASE_URL="https://api.themoviedb.org/3/";
        public static String API_KEY = "221aac16f7f58f1cf0fd5f99ff6e6b60";
        public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        public static String IMAGE_ORIGINAL_BASE_URL = "https://image.tmdb.org/t/p/original";
        public static final String LANGUAGE = "en-US";
        public static final String SORT_BY = "";
        public static int PAGE = 0;
        public static final boolean INCLUDE_ADULT = false;
        public static final boolean INCLUDE_VIDEO = false;

        public static ArrayList<String> sortList = new ArrayList<>();

    }

    public static class Genre{
        public static ArrayList<com.smartsoft.movietracker.model.genre.Genre> genre = new ArrayList<>();
    }
}

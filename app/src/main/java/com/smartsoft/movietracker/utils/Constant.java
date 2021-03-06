package com.smartsoft.movietracker.utils;


/**
 * This class contains the constant data what are using everywhere in the app
 */

public class Constant {

    /**
     * All the
     * {@link androidx.leanback.widget.HorizontalGridView}
     * {@link androidx.leanback.widget.VerticalGridView}
     * constant variables
     */
    public static class GridView {
        public static int COLUMN_NUM5 = 5;
        public static int COLUMN_NUM7 = 7;
    }

    /**
     * All variables for the {@link retrofit2.Retrofit} query and API
     */
    public static class API {
        public static final String BASE_YOUTUBE_URL_FOR_PICTURE = "http://img.youtube.com/vi/";
        public static final String YOUTUBE_THUMBNAIL = "/0.jpg";
        public static final String BASE_URL = "https://api.themoviedb.org/3/";
        public static final String API_KEY = "221aac16f7f58f1cf0fd5f99ff6e6b60";
        public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
        public static final String IMAGE_ORIGINAL_BASE_URL = "https://image.tmdb.org/t/p/original";
        public static final String LANGUAGE = "en-US";
        public static final boolean INCLUDE_ADULT = false;
        public static final boolean INCLUDE_VIDEO = false;
        public static final int RESPONSE_CODE = 200;

    }

    /**
     * All variables for the sort/order dialog
     */
    public static class Sort {
        public static final String popularity = "popularity";
        public static final String releaseDate = "release_date";
        public static final String originalTitle = "original_title";
        public static final String voteAverage = "vote_average";
    }

    /**
     * @see com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment
     * variables
     */
    public static class MovieNavigationPresenter {
        public static final int offset = 0;
    }

    /**
     * @see com.smartsoft.movietracker.utils.FragmentNavigation
     * variables
     */
    public static class FragmentNavigation {
        static final String DialogFragment = "dialog";
    }
}

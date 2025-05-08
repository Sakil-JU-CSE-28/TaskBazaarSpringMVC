package util;

public class Constants {

    public static class Error {
        public static final String EMPTY_TITLE = "Title cannot be empty";
        public static final String EMPTY_CONTENT = "Content cannot be empty";
        public static final String TITLE_LENGTH_ERROR = "Title length must be between 1 and 20";
        public static final String CONTENT_LENGTH_ERROR = "Content length must be between 1 and 200";
        public static final String USER_NOT_FOUND = "User not found ";
        public static final String USER_ALREADY_EXISTS = "Username is already taken";
        public static final String EMAIL_ALREADY_EXISTS = "Email is already in use";
        public static final String BIDDER_EMPTY= "Bidder cannot be empty";
        public static final String POST_ID_EMPTY= "PostId cannot be empty";
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String BID_ALREADY_ADDED = "Bid already added";
    }

    public static class Limit {
        public static final int MAX_TITLE_LENGTH = 20;
        public static final int MAX_CONTENT_LENGTH = 200;
    }

}

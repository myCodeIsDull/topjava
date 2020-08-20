package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;

public class ErrorInfoTestData {
    public static final TestMatcher<ErrorInfo> ERROR_INFO_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(ErrorInfo.class, "detail");

    public static final String PROFILE_REST_URL = "http://localhost/rest/profile";
    public static final String MEALS_REST_URL = "http://localhost/rest/profile/meals/";
    public static final String ADMIN_REST_URL = "http://localhost/rest/admin/users/";

    public static final ErrorInfo PROFILE_REST_CONTROLLER_REGISTRATION_VALIDATION_ERROR = new ErrorInfo(PROFILE_REST_URL + "/register", ErrorType.VALIDATION_ERROR, "");
    public static final ErrorInfo PROFILE_REST_CONTROLLER_UPDATING_VALIDATION_ERROR = new ErrorInfo(PROFILE_REST_URL, ErrorType.VALIDATION_ERROR, "");
    public static final ErrorInfo PROFILE_REST_CONTROLLER_UPDATE_WITH_EXISTING_MAIL_DATA_ERROR = new ErrorInfo(PROFILE_REST_URL, ErrorType.DATA_ERROR, "");
    public static final ErrorInfo PROFILE_REST_CONTROLLER_EXISTING_USER_DATA_ERROR = new ErrorInfo(PROFILE_REST_URL + "/register", ErrorType.DATA_ERROR, "");

    public static final ErrorInfo ADMIN_REST_CONTROLLER_CREATE_VALIDATION_ERROR = new ErrorInfo(ADMIN_REST_URL, ErrorType.VALIDATION_ERROR, "");
    public static final ErrorInfo ADMIN_REST_CONTROLLER_UPDATING_VALIDATION_ERROR = new ErrorInfo(ADMIN_REST_URL + UserTestData.USER_ID, ErrorType.VALIDATION_ERROR, "");
    public static final ErrorInfo ADMIN_REST_CONTROLLER_UPDATE_WITH_EXISTING_MAIL_DATA_ERROR = new ErrorInfo(ADMIN_REST_URL + UserTestData.USER_ID, ErrorType.DATA_ERROR, "");
    public static final ErrorInfo ADMIN_REST_CONTROLLER_CREATE_EXISTING_USER_DATA_ERROR = new ErrorInfo(ADMIN_REST_URL, ErrorType.DATA_ERROR, "");

    public static final ErrorInfo MEALS_REST_CONTROLLER_CREATE_VALIDATION_ERROR = new ErrorInfo(MEALS_REST_URL, ErrorType.VALIDATION_ERROR, "");
    public static final ErrorInfo MEALS_REST_CONTROLLER_UPDATING_VALIDATION_ERROR = new ErrorInfo(MEALS_REST_URL + MealTestData.MEAL1_ID, ErrorType.VALIDATION_ERROR, "");


}

package project.ks.com.posts.util;

import java.text.SimpleDateFormat;

import project.ks.com.posts.R;

/**
 * Created by Gopal on 03-Oct-16.
 */
public class AppConstant {

    public static final String HTTP_BASE_URL = "http://172.16.3.40:8080/post" ;

    public static final String USER_LIST = "/user/list";

    /*
    *   Host service constatnt
    */
    public static final String HOSTNAME = "http://172.16.3.40:8080";
    public static final String CONTEXTPATH = "/post";


    /*
    *   Service module name
    */

    public static final String USER_MODULE = "/user";
    public static final String TAG_MODULE = "/tag";
    public static final String QUESTION_MODULE = "/question";
    public static final String ANSWER_MODULE = "/answer";
    public static final String POST_MODULE = "/post";
    public static final String LOGIN_MODULE = "/login";

    public static final String ADD = "/add";
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";
    public static final String LIST = "/list";


    /**
     * user mapping
     */
    public static final String USER_REGISTRATION = USER_MODULE + "/add";


    /**
     *  Question mapping
     */

    public static final String TAG_LIST = TAG_MODULE + LIST;
    public static final String QUESTION_LIST_MAPPING = AppConstant.QUESTION_MODULE + AppConstant.LIST;
    public static final String QUESTION_ADD_MAPPING = AppConstant.QUESTION_MODULE + AppConstant.ADD;

    /**
     *  Answer mapping
     */

    public static final String ANSWER_ADD_MAPPING = AppConstant.ANSWER_MODULE + AppConstant.ADD;


    /**
     *  Post Mapping
     */
    public static final String POST_MAPPING = AppConstant.POST_MODULE;

    /*
	 * Default MDY format
	 */
    public static final String DEFAULT_MDY_HHMM_DATETIME_FORMAT = "dd MMM yyyy hh:mm a";
    public static final String DEFAULT_DDMMYYYY_DATE_FORMAT = "dd/MM/yyyy";
    public static final SimpleDateFormat DEFAULT_DMY_DATE_FORMAT = new SimpleDateFormat(AppConstant.DEFAULT_DDMMYYYY_DATE_FORMAT);
    public static final SimpleDateFormat DMY_HHMM_DATE_FORMAT = new SimpleDateFormat(AppConstant.DEFAULT_MDY_HHMM_DATETIME_FORMAT);
    public static final String DEFAULT_YYYYMMDD_DATE_FORMAT = "yyyy-mm-dd";

    /*
	 * Http Header Constant
	 */
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String AUTHORIZATION_TOKEN_HEADER = "Authorization";
    public static final String ACCEPT_HEADER = "Accept";

    public static final String LOGIN_PREFERENCES = "loginPreferences";
    public static final String IS_LOGIN_SKIPED = "isLoginSkiped";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER_JSON = "user";
    public static final String QUESTION_JSON = "question";

    public static String PACKAGE_NAME ;

    public final static Integer[] mThumbIds = {

            R.mipmap.user1,R.mipmap.user2,
            R.mipmap.user3,R.mipmap.user4,
            R.mipmap.user5,R.mipmap.user6,
            R.mipmap.user7,R.mipmap.user8,
            R.mipmap.user9,R.mipmap.user10,
            R.mipmap.user11,R.mipmap.user12,
            R.mipmap.user13,R.mipmap.user14,
            R.mipmap.user15,R.mipmap.user16,
            R.mipmap.user17,R.mipmap.user18,
            R.mipmap.user19,R.mipmap.user20,
            R.mipmap.user21,R.mipmap.user22,
            R.mipmap.user23,R.mipmap.user24,
            R.mipmap.user25,R.mipmap.user26,
            R.mipmap.user27,R.mipmap.user30,
            R.mipmap.user31,R.mipmap.user32,
            R.mipmap.user33,R.mipmap.user34,
            R.mipmap.user35,R.mipmap.user36,
            R.mipmap.user37,R.mipmap.user38,
            R.mipmap.user39,R.mipmap.user40,
            R.mipmap.user41,R.mipmap.user42,
            R.mipmap.user43,R.mipmap.user44,
            R.mipmap.user45,R.mipmap.user46,
            R.mipmap.user47,R.mipmap.user48,
            R.mipmap.user49,R.mipmap.user50,

    };

    public final static String[] mThumbName = {

            "user1","user2",
            "user3","user4",
            "user5","user6",
            "user7","user8",
            "user9","user10",
            "user11","user12",
            "user13","user14",
            "user15","user16",
            "user17","user18",
            "user19","user20",
            "user21","user22",
            "user23","user24",
            "user25","user26",
            "user27","user30",
            "user31","user32",
            "user33","user34",
            "user35","user36",
            "user37","user38",
            "user39","user40",
            "user41","user42",
            "user43","user44",
            "user45","user46",
            "user47","user48",
            "user49","user50"

    };
}

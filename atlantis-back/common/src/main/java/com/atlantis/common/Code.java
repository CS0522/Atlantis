package com.atlantis.common;

public class Code {
    // CRUD code:

    public static final Integer GET_OK = 1001;
    public static final Integer INSERT_OK = 2001;
    public static final Integer UPDATE_OK = 3001;
    public static final Integer DELETE_OK = 4001;
    public static final Integer LOGIN_OK = 5001;
    public static final Integer LOGOUT_OK = 6001;
    public static final Integer UPLOAD_OK = 7001;
    public static final Integer DOWNLOAD_OK = 8001;
    public static final Integer VERIFY_OK = 9001;

    public static final Integer GET_ERR = 1000;
    public static final Integer INSERT_ERR = 2000;
    public static final Integer UPDATE_ERR = 3000;
    public static final Integer DELETE_ERR = 4000;
    public static final Integer LOGIN_ERR = 5000;
    public static final Integer LOGOUT_ERR = 6000;
    public static final Integer STATUS_OFF = 5003;
    public static final Integer UPLOAD_ERR = 7000;
    public static final Integer DOWNLOAD_ERR = 8000;
    public static final Integer VERIFY_ERR = 9000;


    // Exception error code:
    // system = 500x
    // business = 5002
    // exception = 5003

    public static final Integer SYS_ERR = 9001;

    public static final Integer SEV_ERR = 9002;

    public static final Integer EXC_ERR = 9003;
}

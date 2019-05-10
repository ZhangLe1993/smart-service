package com.biubiu.user.core;

/**
 * @author yule.zhang
 * @date 2019/5/9 21:32
 * @email zhangyule1993@sina.com
 * @description 状态码
 */
public class RequestCode {
    public static final int SUCCESS = 200;
    public static final int SUCCESS_CREATED = 201;
    public static final int SUCCESS_ASYNC = 202;
    public static final int SYNTAX_ERROR = 400;
    public static final int UNAUTHORIZED_ERROR = 401;
    public static final int FORBID_ERROR = 403;
    public static final int RES_NOT_FIND_ERROR = 404;
    public static final int RES_IS_DELETE_ERROR = 410;
    public static final int NO_CONTENT_LENGTH_ERROR = 411;
    public static final int MIS_SING_PARAMETER_ERROR = 412;
    public static final int CONTENT_FORBID_ERROR = 415;
    public static final int RANGE_ERROR = 416;
    public static final int SEMANTIC_ERROR = 422;
    public static final int LOCKED_ERROR = 423;
    public static final int SERVER_ERROR = 500;
    public static final int SERVER_UNREALIZED_ERROR = 501;
    public static final int NONE = 0;
    public static final int NOT_ACCEPTABLE = 406;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int METHOD_NOT_ALLOWED = 405;

    public RequestCode() {
    }
}

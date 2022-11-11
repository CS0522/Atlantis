// 用于前后端通信，请求成功和请求失败时返回的代码

const GET_OK = 1001;
const INSERT_OK = 2001;
const UPDATE_OK = 3001;
const DELETE_OK = 4001;
const LOGIN_OK = 5001;
const LOGOUT_OK = 6001;
const UPLOAD_OK = 7001;
const DOWNLOAD_OK = 8001;
const VERIFY_OK = 9001;

const GET_ERR = 1000;
const INSERT_ERR = 2000;
const UPDATE_ERR = 3000;
const DELETE_ERR = 4000;

const LOGIN_ERR = 5000;
const LOGOUT_ERR = 6000;
const STATUS_OFF = 5003;
const UPLOAD_ERR = 7000;
const DOWNLOAD_ERR = 8000;
const VERIFY_ERR = 9000;

// Exception error code:
// system = 500x
// business = 5002
// exception = 5003

const SYS_ERR = 9001;

const SEV_ERR = 9002;

const EXC_ERR = 9003;

export default {
    GET_OK,
    INSERT_OK,
    UPDATE_OK,
    DELETE_OK,
    LOGIN_OK,
    LOGOUT_OK,
    UPLOAD_OK,
    DOWNLOAD_OK,
    VERIFY_OK,

    GET_ERR,
    INSERT_ERR,
    UPDATE_ERR,
    DELETE_ERR,
    LOGIN_ERR,
    LOGOUT_ERR,
    STATUS_OFF,
    UPLOAD_ERR,
    DOWNLOAD_ERR,
    VERIFY_ERR,

    // Exception error code:
    // system  500x
    // business  5002
    // exception  5003

    SYS_ERR,

    SEV_ERR,

    EXC_ERR,

}
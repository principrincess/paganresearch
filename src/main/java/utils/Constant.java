package utils;

public class Constant {

    final public static String BASE_URL = "https://paganresearch.io";
    final public static String LOGIN_URL = BASE_URL + "/login";

    final public static int PAGE_TIMEOUT = 120;


    final public static String USERNAME = "sanjay@proforte.co";
    final public static String PASSWORD = "Proforte@1234";


    final public static String NAME = "NAME";
    final public static String COMPANY = "COMPANY";
    final public static String EMAIL = "EMAIL";
    final public static String PHONE = "PHONE";
    final public static String DESIGNATION = "DESIGNATION";
    final public static String LAST_UPDATED = "LAST_UPDATED";

    final public static String[] COL_NAME = {NAME, COMPANY, EMAIL, PHONE, DESIGNATION, LAST_UPDATED};


    final public static int MAX_DATA_IN_WEBSITE_TABLE = 30;
//    30 = 1 PAGE
    final public static int MAX_DATA_IN_EXCEL_FILE = MAX_DATA_IN_WEBSITE_TABLE * 10;



}

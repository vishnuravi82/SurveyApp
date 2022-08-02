package com.labournet.surveyapp.util;


public class Keys {

    public static final String CONST_CLIENT_ID = "athira";
    public static final String CONST_CLIENT_KEY = "navriti@123";
    //    public static final String CONST_NEW_CLIENT_ID = "L4b0urN3t";
//    public static final String CONST_NEW_CLIENT_KEY = "N304pp5";
  public static final String CONST_S3_BUCKET = "phoenix/";
    //public static final String CONST_S3_BUCKET = "neo_skills/";
    public static final String S3_QA = "qa/";
    //public static final String S3_LIVE = "live/";
    public static final String S3_LIVE = "live/";
   // public static final String S3_LIVE = "uat/";
    public static final String S3_UAT = "uat/";
    public static final String S3_PATH_OJT_IMGS = "OJT/images/";
    public static final String S3_PATH_OJT_AUDIO = "OJT/audio/";
    public static final String S3_PATH_TMA_TRAINER_IMG = "tms/trainer_stage_images/";
    public static final String S3_PATH_TMA_CANDIDATE_IMG = "tms/attendance_images/";
    public static final String S3_PATH_MRE_DOCS = "pheonix_app/docs/";
    public static final String S3_PATH_MRE_IMGS = "neo_app/images/";
    public static final String S3_PATH_MRE_XML_MBL = "neo_app/xml_files/mobilization/";
    public static final String S3_PATH_MRE_XML_REG = "neo_app/xml_files/registration/";
    public static final String S3_PATH_MRE_XML_ENR = "/phoenix/qa/xml/";
    public static final String S3_PATH_QMA_TRAINER_IMG = "QM/trainer_images/";
    public static final String S3_PATH_QMA_IMGS = "QM/images/";
    public static final String S3_PATH_QMA_VIDEOS = "QM/video/";
    public static final String S3_PATH_PTA_CAND_IMG = "PT/images/";
    public static final String S3_PATH_PTA_VIDEOS = "PT/video/";

    public static final String CONTENT_TYPE_PNG = "image/png";
    public static final String CONTENT_TYPE_JPG = "image/jpeg";
    public static final String CONTENT_TYPE_XML = "text/xml";
    public static final String CONTENT_TYPE_PDF = "application/pdf";
    public static final String CONTENT_TYPE_WORD = "application/msword";
    public static final String CONTENT_TYPE_MP3 = "audio/mpeg";
    public static final String CONTENT_TYPE_MP4 = "video/mp4";


    public static final String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public static final String TIMESTAMP_FORMAT2 = "dd MMM yyyy hh:mm aa";

    public static final int MAX_WIDTH = 1024;
    public static final int MAX_HEIGHT = 768;

    //Preferences
    public static final String PREFS_SERVER_MODE = "prefs_server_mode";
    public static final int CONST_LIVE_MODE = 0;
    public static final int CONST_DEMO_MODE = 1;
    public static final int CONST_UAT_MODE = 2;

    public static final String CONST_DUMMY_OTP = "342179";

    public static final String PREFS_APP_VERSION_CODE = "prefs_app_version_code";
    public static final String PREFS_LOGGED_IN = "prefs_logged_in";
    public static final String PREFS_PRACTICE_SELECTED = "prefs_prac_sel";
    public static final String PREFS_COURSE_SELECTED = "prefs_course_sel";
    public static final String PREFS_SECTION_SELECTED = "prefs_section_sel";
    public static final String PREFS_CENTER_SELECTED = "prefs_center_sel";
    public static final String PREFS_USER_ID = "prefs_user_id";
    public static final String PREFS_USER_NAME = "prefs_user_name";
    public static final String PREFS_LATITUDE = "prefs_latitude";
    public static final String PREFS_LONGITUDE = "prefs_longitude";
    public static final String PREFS_PROF_SECT_ID = "prefs_prof_sect_id";
    public static final String PREFS_CAND_ID = "prefs_cand_id";
    public static final String PREFS_WORKFLOW_ID = "prefs_workflow_id";
    public static final String PREFS_ROLE_ID = "prefs_role_id";
    public static final String PREFS_ROLE_ID_LIST = "prefs_role_id_list";
    public static final String PREFS_ROLE_NAME = "prefs_role_name";
    public static final String PREFS_ROLE_NAME_LIST = "prefs_role_name_list";
    public static final String PREFS_MULTIPLE_ROLE = "prefs_multiple_role";
    public static final String PREFS_RELEASE_FLAG = "prefs_release_flag";
    public static final String PREFS_RELEASE_MSG = "prefs_release_msg";
    public static final String PREFS_MOB_TYPE = "prefs_mob_type";
    public static final String PREFS_JOB_ROLE_ID = "prefs_job_role_id";
 public static final String PREFS_HOUSEHOLD_ID = "house_hold_id";

    //Database
    public static final String TBL_PRACTICES = "tbl_practices";
    public static final String TBL_COURSES = "tbl_courses";
    public static final String TBL_CENTERS = "tbl_centers";
    public static final String TBL_PROF_QUES = "tbl_prof_ques";
    public static final String TBL_PROF_OPS = "tbl_prof_ops";
    public static final String TBL_WF_QUES = "tbl_wf_ques";
    public static final String TBL_WF_OPS = "tbl_wf_ops";
    public static final String TBL_STATES = "tbl_states";
    public static final String TBL_DISTRICTS = "tbl_districts";
    public static final String TBL_CONTRACTORS = "tbl_contractors";
    public static final String TBL_BATCHES = "tbl_batches";

    public static final String KEY_CLIENT_ID = "client_id";
    public static final String KEY_CLIENT_KEY = "client_key";
    public static final String KEY_ID = "id";
    public static final String KEY_SL_NO = "sl_no";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_CENTER_NAME = "center_name";
    public static final String KEY_PRACTICE_NAME = "practice_name";
    public static final String KEY_COURSE_NAME = "course_name";
    public static final String KEY_CENTER_ID = "center_id";
    public static final String KEY_PRAC_ID = "prac_id";
    public static final String KEY_FLAG_DEP = "flag_dep";
    public static final String KEY_QUES_ID = "ques_id";
    public static final String KEY_QUES_TXT = "ques_txt";
    public static final String KEY_QUES_TYPE = "ques_type";
    public static final String KEY_IP_TYPE = "ip_type";
    public static final String KEY_IP_LENGTH = "ip_length";
    public static final String KEY_FLAG_MAND = "flag_mand";
    public static final String KEY_CHILD_QUES_ID = "child_ques_id";
    public static final String KEY_IMG_URL = "img_url";
    public static final String KEY_ROW_NUM = "row_num";
    public static final String KEY_OPS_TXT = "ops_txt";
    public static final String KEY_DEP_QUES_ID = "dep_ques_id";
    public static final String KEY_STATE_NAME = "state_name";
    public static final String KEY_DISTRICT_NAME = "district_name";
    public static final String KEY_STATE_ID = "state_id";
    public static final String KEY_CONTRACTOR_NAME = "contractor_name";
    public static final String KEY_CORRECT_OP_ID = "correct_op_id";
    public static final String KEY_DEP_QUES_IDS = "dep_ques_ids";
    public static final String KEY_BATCH_CODE = "batch_code";

    public static final int CAND_STATUS_PENDING = 1;
    public static final int CAND_STATUS_COMPLETED = 2;
    public static final int CAND_STATUS_NOT_ELIGIBLE = 3;
    public static final int CAND_STATUS_NOT_STARTED = 4;

    public static final int SECT_TYPE_ASSIGN_BATCH = 5;

    public static final int QUES_TYPE_SINGLE_CHOICE = 1;
    public static final int QUES_TYPE_MULTIPLE_CHOICE = 2;
    public static final int QUES_TYPE_OPEN_FIELD = 3;
    public static final int QUES_TYPE_UPLOAD_IMAGE = 4;
    public static final int QUES_TYPE_DROPDOWN = 5;
    public static final int QUES_TYPE_DATE = 6;
    public static final int QUES_TYPE_TIME = 7;
    public static final int QUES_TYPE_DATE_TIME = 8;
    public static final int QUES_TYPE_READ_ONLY = 9;
    public static final int QUES_TYPE_STATE = 10;
    public static final int QUES_TYPE_DISTRICT = 11;
    public static final int QUES_TYPE_SHOW_IMAGE = 12;
    public static final int QUES_TYPE_MULTIPLE_UPLOAD = 13;
    public static final int QUES_TYPE_CONTRACTOR = 14;
    public static final int QUES_TYPE_CONTRACTOR_LIST = 15;
    public static final int QUES_TYPE_CONTRACTOR_DETAILS = 16;
    public static final int QUES_TYPE_BATCH_LIST = 17;
    public static final int QUES_TYPE_TITLE = 100;
    public static final int QUES_TYPE_DUMMY = 101;

    public static final int INPUT_TYPE_NUMBER = 1;
    public static final int INPUT_TYPE_SINGLE_TEXT = 2;
    public static final int INPUT_TYPE_MULTI_TEXT = 3;
    public static final int INPUT_TYPE_EMAIL = 4;

    public static final int SECT_STATUS_COMPLETE = 1;
    public static final int SECT_STATUS_PENDING = 2;
    public static final int SECT_STATUS_FAIL = 3;
    public static final int SECT_STATUS_ACTIVE = 4;

    //select and switch role access dialog
    public static final int ROLE_ID_CANDIDATE = 0;
    public static final int ROLE_ID_ADMIN = 1;
    public static final int ROLE_ID_MOBILIZER = 2;
    public static final int ROLE_ID_CENTER_MANAGER = 5;
    public static final int ROLE_ID_TRAINER = 7;
    public static final int ROLE_ID_SYSTEM = 20;
    public static final int ROLE_ID_PLACEMENT_OFFICER = 21;
    public static final int ROLE_ID_COUNSELLOR = 24;
    public static final int ROLE_ID_PROJ_COORDINATOR = 38;

    public static final int QUES_PER_SCREEN = 7;
    public static final String READ_ONLY_DELIM = "\\[@@]";
    /*TMA*/
    public static final int BATCH_STATUS_COMPLETED = 1;
    public static final int BATCH_STATUS_ONGOING = 2;
    public static final int BATCH_STATUS_SCHEDULED = 3;

    public static final int SESSION_STATUS_IN_PROGRESS = 1;
    public static final int SESSION_STATUS_NOT_STARTED = 2;
    public static final int SESSION_STATUS_COMPLETED = 3;

    public static final String SESSION_DELIM = "@@@";
    public static final String TBL_SESSION_PLANS = "tbl_session_plans";
    public static final String TBL_MODULES = "tbl_modules";
    public static final String KEY_SESSION_PLAN_NAME = "session_plan_name";
    public static final String KEY_SESSION_PLAN_ID = "session_plan_id";
    public static final String KEY_MODULE_NAME = "module_name";
    public static final String PREFS_FRESHER = "prefs_fresher";
    public static final String PREFS_CAND_DB_ID = "prefs_cand_db_id";

    /*New NEO*/
//    public static final String CONST_PATH_LIVE_URL_XML="https://neo.labournet.in/XML/"; //get candidate list
//    public static final String CONST_PATH_QA_URL_XML="https://neo-qa.labournet.in/XML/"; //get candidate list
//    public static final String CONST_PATH_LIVE_URL_IMAGE="https://neo.certiplate.com/Data/skills_app_files/img/";
//    public static final String CONST_PATH_QA_URL_IMAGE="https://neo.certiplate.com/Data/skills_app_files_demo/img/";
//    public static final String CONST_PATH_LIVE_URL_FILES="https://neo.certiplate.com/Data/skills_app_files/doc/";
//    public static final String CONST_PATH_QA_URL_FILES="https://neo.certiplate.com/Data/skills_app_files_demo/doc/";
//    public static final String CONST_PATH_URL_SUBMIT_XML="https://neo.certiplate.com/Data/skills_app_files/xml/"; //submitted cand data

    public static final String PREFS_ACTION = "prefs_action";

    public static final String KEY_LATITUDE = "key_latitude";
    public static final String KEY_LONGITUDE = "key_longitude";
    public static final String KEY_TIMESTAMP = "key_timestamp";
    public static final String KEY_CREATED_BY = "key_created_by";
    public static final String KEY_ACTIVITY_STATUS_ID = "key_activity_status_id";
    public static final String TBL_MBL_CAND = "tbl_mbl_cand";//ID,USER ID
    public static final String KEY_CAND_STAGE = "cand_stage";//0,1,2,3(even:draft,odd:complete)
    public static final String KEY_MBL_COMPLETE = "mbl_complete";//0,1
    public static final String KEY_IS_FRESHER = "is_fresher";//0,1
    public static final String KEY_CAND_PIC = "cand_pic";
    public static final String KEY_CAND_SALTN = "cand_saltn";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_MID_NAME = "mid_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_CAND_DOB = "cand_dob";
    public static final String KEY_DOB_ENTERED = "dob_entered";
    public static final String KEY_CAND_AGE = "cand_age";
    public static final String KEY_PRIMARY_MOB = "primary_mob";
    public static final String KEY_MOB_VERIFIED = "mob_verified";
    public static final String KEY_WA_NUM = "wa_num";
    public static final String KEY_SEC_MOB = "sec_mob";
    public static final String KEY_CAND_EMAIL = "cand_email";
    public static final String KEY_EMAIL_VALIDATE = "email_validated";
    public static final String KEY_CAND_GENDER = "cand_gender";
    public static final String KEY_MARITAL_ST = "marital_status";
    public static final String KEY_CAND_CASTE = "cand_caste";
    public static final String KEY_DISABLE_ST = "disable_status";
    public static final String KEY_CAND_RELIGION = "cand_religion";
    public static final String KEY_MOTHER_TONGUE = "mother_tongue";
    public static final String KEY_CAND_OCCUPTN = "cand_occuptn";
    public static final String KEY_ANNUAL_INCOME = "annual_income";
    public static final String KEY_CAND_SOURCE = "cand_source";
    public static final String KEY_INTEREST_COURSE = "interest_course";
    public static final String KEY_CAND_PRODUCT = "cand_product";
    public static final String KEY_PRES_ADDR1 = "pres_addr_one";
    public static final String KEY_PRES_ADDR2 = "pres_addr_two";
    public static final String KEY_PRES_VILLAGE = "pres_village";
    public static final String KEY_PRES_PANCHAYAT = "pres_panchayat";
    public static final String KEY_PRES_TALUK = "pres_taluk";
    public static final String KEY_PRES_PINCODE = "pres_pincode";
    public static final String KEY_PRES_DISTRICT = "pres_district";
    public static final String KEY_PRES_STATE = "pres_state";
    public static final String KEY_PRES_COUNTRY = "pres_country";
    public static final String KEY_ADDR_SAME = "addr_same";
    public static final String KEY_PERM_ADDR1 = "perm_addr_one";
    public static final String KEY_PERM_ADDR2 = "perm_addr_two";
    public static final String KEY_PERM_VILLAGE = "perm_village";
    public static final String KEY_PERM_PANCHAYAT = "perm_panchayat";
    public static final String KEY_PERM_TALUK = "perm_taluk";
    public static final String KEY_PERM_PINCODE = "perm_pincode";
    public static final String KEY_PERM_DISTRICT = "perm_district";
    public static final String KEY_PERM_STATE = "perm_state";
    public static final String KEY_PERM_COUNTRY = "perm_country";
    public static final String TBL_REG_CAND = "tbl_reg_cand";
    public static final String KEY_REG_COMPLETE = "reg_complete";//0,1
    public static final String KEY_AADHAAR_NO = "aadhaar_no";
    public static final String KEY_AADHAAR_COPY = "aadhaar_copy";
    public static final String KEY_ID_TYPE = "id_type";
    public static final String KEY_ID_NUM = "id_num";
    public static final String KEY_ID_COPY = "id_copy";
    public static final String KEY_EMP_TYPE = "emp_type";
    public static final String KEY_PREF_JOB = "pref_job";
    public static final String KEY_YRS_EXP = "yrs_exp";
    public static final String KEY_REL_EXP = "rel_exp";
    public static final String KEY_LAST_CTC = "last_ctc";
    public static final String KEY_PREF_LOC = "pref_loc";
    public static final String KEY_WILL_TRAVEL = "will_travel";
    public static final String KEY_WORK_SHIFT = "work_shift";
    public static final String KEY_BOCW_ID = "bocw_id";
    public static final String KEY_EXPECT_CTC = "expect_ctc";
    public static final String TBL_ENR_CAND = "tbl_enr_cand";
    public static final String TBL_RE_ENR_CAND = "tbl_re_enr_cand";
    public static final String KEY_ENR_COMPLETE = "enr_complete";//0,1
    public static final String KEY_RE_ENR_COMPLETE = "re_enr_complete";//0,1
    public static final String KEY_HIGH_QUALI = "high_quali";
    public static final String KEY_EDN_STREAM = "edn_stream";
    public static final String KEY_INSTI_NAME = "insti_name";
    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_YR_PASS = "yr_pass";
    public static final String KEY_PERCENTAGE = "percentage";
    public static final String KEY_COMP_KNOW = "comp_know";
    public static final String KEY_TECH_KNOW = "tech_know";
    public static final String KEY_MEM_SALTN = "mem_saltn";
    public static final String KEY_MEM_NAME = "mem_name";
    public static final String KEY_MEM_DOB = "mem_dob";
    public static final String KEY_MEM_AGE = "mem_age";
    public static final String KEY_MEM_CONT = "mem_cont";
    public static final String KEY_MEM_EMAIL = "mem_email";
    public static final String KEY_MEM_GENDER = "mem_gender";
    public static final String KEY_MEM_EDN = "mem_edn";
    public static final String KEY_MEM_RELATION = "mem_relation";
    public static final String KEY_MEM_OCCUPTN = "mem_occuptn";
    public static final String KEY_MEM_INCOME = "mem_income";
    public static final String KEY_HOUSE_INCOME = "house_income";
    public static final String KEY_BANK_NAME = "bank_name";
    public static final String KEY_BRANCH_NAME = "branch_name";
    public static final String KEY_IFSC_CODE = "ifsc_code";
    public static final String KEY_ACC_TYPE = "acc_type";
    public static final String KEY_ACC_NUM = "acc_num";
    public static final String KEY_BANK_COPY = "bank_copy";
    public static final String KEY_ASSIGN_BATCH = "assign_batch";
    public static final String KEY_JOB_ROLE_ID="job_role_id";
    public static final String KEY_MOBILIZATION_TYPE = "mob_type"; //default 1
    public static final String KEY_RESULT_PASS = "result_pass"; //default 1
    public static final String KEY_MCL_SCORE = "mcl_score"; //default 0
    public static final String KEY_MCL_AGE = "mcl_age";
    public static final String KEY_ADDR_ADHAR = "addr_adhar";
    public static final String KEY_MEMBER_COUNT = "member_count";
    public static final String KEY_HEAD_HOUSE = "head_house";
    public static final String KEY_READ_LOCAL = "read_local";
    public static final String KEY_HAVE_PHONE = "have_phone";
    public static final String KEY_BUY_PHONE = "buy_phone";
    public static final String KEY_HAVE_BIKE = "have_bike";
    public static final String KEY_OPERATE_PHONE = "operate_phone";
    public static final String KEY_IS_ENTRE = "is_entre";
    public static final String KEY_FAMILY_PERMIT = "family_permit";
    public static final String KEY_MEMBER_SHG = "member_shg";
    public static final String KEY_WILL_SHE = "will_she";
    public static final String KEY_ONLINE_TRAINING = "online_training";
    public static final String KEY_SHARE_TO_LN = "share_to_ln";
    public static final String KEY_CONTRACT_LN = "contract_ln";
    public static final String KEY_BUY_TOOLS = "buy_tools";
    public static final String KEY_ADOPT_DIGITAL = "adopt_digital";
    public static final String KEY_REGISTER_SOCIAL = "register_social";
    public static final String KEY_LOAN_PAST = "loan_past";
    public static final String KEY_ACTIVE_LOAN = "active_loan";
    public static final String KEY_TAKE_LOAN = "take_loan";
    public static final String KEY_HEALTH_INS = "health_ins";
    public static final String KEY_ALLERGIC_DUST = "allergic_dust";
    public static final String KEY_WEAR_PPE = "wear_ppe";
    public static final String KEY_FOLLOW_SAFETY = "follow_safety";
    public static final String KEY_LEGAL_ENQUIRY = "legal_enquiry";
    public static final String KEY_PASS_8 = "pass_eight";
    public static final String KEY_PAST_EXP = "past_exp";
    public static final String KEY_WORK_6HRS = "work_hours";
    public static final String KEY_TRAVEL_PLACE = "travel_place";
    public static final String KEY_HAVE_BANK = "have_bank";
    public static final String KEY_AST_TV = "ast_tv";
    public static final String KEY_AST_FRIDGE = "ast_fridge";
    public static final String KEY_AST_WASH = "ast_wash";
    public static final String KEY_AST_AC = "ast_ac";
    public static final String KEY_AST_CAR = "ast_car";
    public static final String KEY_AST_MED_INS = "ast_med_ins";
    public static final String KEY_AST_LIFE_INS = "ast_life_ins";
    public static final String KEY_AST_KIDS_EDN = "ast_kids_edn";
    public static final String KEY_AST_FARM_LAND = "ast_farm_land";
    public static final String KEY_FARM_ACRE = "farm_acre";
    public static final String KEY_HOUSE_SIZE = "house_size";
    public static final String KEY_AST_OTHERS = "ast_others";
    public static final String KEY_OWN_HOUSE = "own_house";
    public static final String KEY_RATION_CARD = "ration_card";
    public static final String KEY_DELL_EDN_PIC = "dell_edn_pic";
    public static final String KEY_SHE_EDN_PIC = "she_edn_pic";
    public static final String KEY_AGE_PIC = "age_pic";
    public static final String KEY_MOU_PIC = "mou_pic";
    public static final String KEY_DATE_MOU = "date_mou";
    public static final String KEY_DATE_KIT = "date_kit";
    public static final String KEY_ASP_DISTRICT = "asp_district";
    public static final String KEY_INCOME_PROOF = "income_proof";
    public static final String KEY_10_PASS="tenth_pass";
    public static final String KEY_READ_WRITE_LOCAL="read_write_local";
    public static final String KEY_READ_ENGLISH="read_english";
    public static final String KEY_MIN_6_EXP="min_six_exp";
    public static final String KEY_GOOD_COMMUNICATION="good_communication";
    public static final String KEY_UNDERSTAND_COVID="understand_covid";
    public static final String KEY_SERVE_MOBILIZER="serve_mobilizer";
    public static final String KEY_TRAVEL_PHC="travel_phc";
    public static final String KEY_WORK_FULL_TIME="work_full_time";
    public static final String KEY_REPORT_MBL_DATA ="report_mcl_data";
    public static final String KEY_12_PASS="twelve_pass";
    public static final String KEY_WORK_MS_OFFICE="work_ms_office";
    public static final String KEY_SERVE_DEO="serve_deo";
    public static final String KEY_REPORT_DEO_DATA="report_deo_data";
    public static final String KEY_COMPLETE_BSC="complete_bsc";
    public static final String KEY_EXP_NGO="exp_ngo";
    public static final String KEY_EXP_VACCINE="exp_vaccine";
    public static final String KEY_EXP_HOSPITAL="exp_hospital";
    public static final String KEY_SERVE_VACCINATOR="serve_vaccinator";
    public static final String KEY_REPORT_VACCINE_DATA="report_vaccine_data";

    public static final String TBL_NEO_BATCHES = "tbl_neo_batchYes";
    public static final String KEY_NEO_BATCH_CODE = "neo_batch_code";
    public static final String KEY_NEO_BATCH_NAME = "neo_batch_name";
    public static final String KEY_NEO_COURSE_CODE = "neo_course_code";
    public static final String KEY_NEO_COURSE_NAME = "neo_course_name";
    public static final String KEY_NEO_EXT_BATCH_CODE = "neo_ext_batch_code";

    public static final String TBL_CAND_STATUS_HISTORY = "tbl_cand_status_history";//id, userId, lat, lng, t/s
    public static final String KEY_CAND_ID = "candidate_id";
    public static final String KEY_CS_ID = "cs_id";
    public static final String KEY_CS_REASON = "cs_reason";
    public static final String KEY_CS_DATE_TIME = "cs_date_time";
    public static final String KEY_CS_REMARKS = "cs_remarks";
    public static final String KEY_CS_UPLOAD_FLAG = "cs_upload_flag";
    public static final String KEY_CS_NAME = "cs_name";

    public static final int ACTION_MOBILIZATION = 1;
    public static final int ACTION_REGISTRATION = 2;
    public static final int ACTION_ENROLMENT = 3;
    public static final int ACTION_RE_ENROLMENT = 4;
    public static final int ACTION_TRAINING = 5;
    public static final int ACTION_OJT = 6;
    public static final int ACTION_QMA =7;
    public static final int ACTION_PTA =8;

    public static final int MOB_TYPE_REGULAR = 1;
    public static final int MOB_TYPE_SHE = 2;
    public static final int MOB_TYPE_ELECTRONICS_RPL = 3;
    public static final int MOB_TYPE_DELL = 4;
    public static final int MOB_TYPE_NSDC_BAT = 5;

    public static final int JOB_ROLE_COMMUNITY_MOBILIZER=1;
    public static final int JOB_ROLE_DATA_ENTRY_OPERATOR=2;
    public static final int JOB_ROLE_VACCINATOR=3;

    public static final String TBL_FAMILY_MEMBERS = "tbl_family_members";

    public static final String MIME_TYPE_JPG = "jpg";
    public static final String MIME_TYPE_PNG = "png";
    public static final String MIME_TYPE_PDF = "pdf";
    public static final String MIME_TYPE_DOC = "doc";
    public static final String MIME_TYPE_TXT = "txt";

    public static int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    //change in network_security_config and RetrofitClient
    public static String PATH_IMAGE = "/data/";

    public static final int INVALID_MOBILE_ERROR=1;
    public static final int INVALID_EMAIL_ERROR=2;
    public static final int INVALID_MOB_EMAIL_ERROR=3;

}











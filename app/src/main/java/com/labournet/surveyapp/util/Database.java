package com.labournet.surveyapp.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;

import com.labournet.surveyapp.model.CandStatusHistoryModel;
import com.labournet.surveyapp.model.FamilyMemberModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Athira on 11/6/19.
 */
public class Database {

    public static final int DATABASE_VERSION = 14;//modified for v39
    public static final String DATABASE_NAME = "phoenix";
    private static final String TAG = "Database.java";

    private static final String QUERY_TBL_CENTERS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_CENTERS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_USER_ID + " INTEGER, " + Keys.KEY_CENTER_NAME + " TXT)";

    private static final String QUERY_TBL_PRACTICES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_PRACTICES + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_PRACTICE_NAME + " TXT)";

    private static final String QUERY_TBL_COURSES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_COURSES + " (" + Keys.KEY_ID + " INTEGER, " + Keys.KEY_PRAC_ID + " INTEGER, " + Keys.KEY_COURSE_NAME + " TXT, " + Keys.KEY_CENTER_ID + " INTEGER, " + Keys.KEY_SL_NO + " INTEGER PRIMARY KEY AUTOINCREMENT)";

    private static final String QUERY_TBL_PROF_QUES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_PROF_QUES + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_FLAG_DEP + " INTEGER, " + Keys.KEY_QUES_TXT + " TXT, " + Keys.KEY_QUES_TYPE + " INTEGER, " + Keys.KEY_IP_TYPE + " INTEGER, " + Keys.KEY_IP_LENGTH + " INTEGER, " + Keys.KEY_FLAG_MAND + " INTEGER, " + Keys.KEY_CHILD_QUES_ID + " INTEGER, " + Keys.KEY_IMG_URL + " TXT, " + Keys.KEY_ROW_NUM + " INTEGER, " + Keys.KEY_CORRECT_OP_ID + " INTEGER)";

    private static final String QUERY_TBL_PROF_OPS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_PROF_OPS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_QUES_ID + " INTEGER, " + Keys.KEY_OPS_TXT + " TXT, " + Keys.KEY_FLAG_DEP + " INTEGER, " + Keys.KEY_DEP_QUES_ID + " INTEGER)";

    private static final String QUERY_TBL_WF_QUES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_WF_QUES + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_FLAG_DEP + " INTEGER, " + Keys.KEY_QUES_TXT + " TXT, " + Keys.KEY_QUES_TYPE + " INTEGER, " + Keys.KEY_IP_TYPE + " INTEGER, " + Keys.KEY_IP_LENGTH + " INTEGER, " + Keys.KEY_FLAG_MAND + " INTEGER, " + Keys.KEY_ROW_NUM + " INTEGER, " + Keys.KEY_CHILD_QUES_ID + " INTEGER, " + Keys.KEY_IMG_URL + " TXT)";

    private static final String QUERY_TBL_WF_OPS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_WF_OPS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_QUES_ID + " INTEGER, " + Keys.KEY_OPS_TXT + " TXT, " + Keys.KEY_FLAG_DEP + " INTEGER, " + Keys.KEY_DEP_QUES_ID + " INTEGER)";

    private static final String QUERY_TBL_STATES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_STATES + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_STATE_NAME + " TXT)";

    private static final String QUERY_TBL_DISTRICTS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_DISTRICTS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_STATE_ID + " INTEGER, " + Keys.KEY_DISTRICT_NAME + " TXT)";

    private static final String QUERY_TBL_CONTRACTORS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_CONTRACTORS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_CONTRACTOR_NAME + " TXT, " + Keys.KEY_DEP_QUES_IDS + " TXT)";

    private static final String QUERY_TBL_BATCHES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_BATCHES + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_BATCH_CODE + " TXT)";
    private static final String QUERY_TBL_SESSION_PLANS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_SESSION_PLANS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_SESSION_PLAN_NAME + " TXT)";
    private static final String QUERY_TBL_MODULES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_MODULES + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY, " + Keys.KEY_SESSION_PLAN_ID + " INTEGER, " + Keys.KEY_MODULE_NAME + " TXT)";
    /*new neo*/
    private static final String QUERY_TBL_MBL_CAND = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_MBL_CAND + " (" + Keys.KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + Keys.KEY_USER_ID + " INTEGER," + Keys.KEY_CAND_STAGE + " INTEGER," +
            Keys.KEY_MBL_COMPLETE + " INTEGER," + Keys.KEY_IS_FRESHER + " INTEGER," + Keys.KEY_CAND_PIC + " TXT," +
            Keys.KEY_CAND_SALTN + " TXT," + Keys.KEY_FIRST_NAME + " TXT," + Keys.KEY_MID_NAME + " TXT," + Keys.KEY_LAST_NAME +
            " TXT," + Keys.KEY_CAND_DOB + " TXT," + Keys.KEY_DOB_ENTERED + " INTEGER," + Keys.KEY_CAND_AGE + " TXT," +
            Keys.KEY_PRIMARY_MOB + " TXT," + Keys.KEY_MOB_VERIFIED + " INTEGER," + Keys.KEY_SEC_MOB + " TXT," +
            Keys.KEY_CAND_EMAIL + " TXT," + Keys.KEY_CAND_GENDER + " TXT," + Keys.KEY_MARITAL_ST + " TXT," + Keys.KEY_CAND_CASTE
            + " TXT," + Keys.KEY_DISABLE_ST + " TXT," + Keys.KEY_CAND_RELIGION + " TXT," + Keys.KEY_MOTHER_TONGUE + " TXT," +
            Keys.KEY_CAND_OCCUPTN + " TXT," + Keys.KEY_ANNUAL_INCOME + " TXT," + Keys.KEY_CAND_SOURCE + " TXT," +
            Keys.KEY_INTEREST_COURSE + " TXT," + Keys.KEY_CAND_PRODUCT + " TXT," + Keys.KEY_PRES_ADDR1 + " TXT," +
            Keys.KEY_PRES_ADDR2 + " TXT," + Keys.KEY_PRES_VILLAGE + " TXT," + Keys.KEY_PRES_PANCHAYAT + " TXT," +
            Keys.KEY_PRES_TALUK + " TXT," + Keys.KEY_PRES_PINCODE + " TXT," + Keys.KEY_PRES_DISTRICT + " TXT," +
            Keys.KEY_PRES_STATE + " INTEGER," + Keys.KEY_PRES_COUNTRY + " INTEGER," + Keys.KEY_ADDR_SAME + " INTEGER," +
            Keys.KEY_PERM_ADDR1 + " TXT," + Keys.KEY_PERM_ADDR2 + " TXT," + Keys.KEY_PERM_VILLAGE + " TXT," +
            Keys.KEY_PERM_PANCHAYAT + " TXT," + Keys.KEY_PERM_TALUK + " TXT," + Keys.KEY_PERM_PINCODE + " TXT," +
            Keys.KEY_PERM_DISTRICT + " TXT," + Keys.KEY_PERM_STATE + " INTEGER," + Keys.KEY_PERM_COUNTRY + " INTEGER," +
            Keys.KEY_LATITUDE + " TXT," + Keys.KEY_LONGITUDE + " TXT," + Keys.KEY_TIMESTAMP + " TXT," + Keys.KEY_EMAIL_VALIDATE
            + " INTEGER," + Keys.KEY_WA_NUM + " TXT" +
            ")";

    private static final String QUERY_TBL_REG_CAND = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_REG_CAND + " (" +
            Keys.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Keys.KEY_USER_ID + " INTEGER," + Keys.KEY_CAND_STAGE +
            " INTEGER," + Keys.KEY_REG_COMPLETE + " INTEGER," + Keys.KEY_IS_FRESHER + " INTEGER," + Keys.KEY_CAND_PIC +
            " TXT," + Keys.KEY_CAND_SALTN + " TXT," + Keys.KEY_FIRST_NAME + " TXT," + Keys.KEY_MID_NAME + " TXT," +
            Keys.KEY_LAST_NAME + " TXT," + Keys.KEY_CAND_DOB + " TXT," + Keys.KEY_DOB_ENTERED + " INTEGER," +
            Keys.KEY_CAND_AGE + " TXT," + Keys.KEY_PRIMARY_MOB + " TXT," + Keys.KEY_MOB_VERIFIED + " INTEGER," +
            Keys.KEY_SEC_MOB + " TXT," + Keys.KEY_CAND_EMAIL + " TXT," + Keys.KEY_CAND_GENDER + " TXT," + Keys.KEY_MARITAL_ST
            + " TXT," + Keys.KEY_CAND_CASTE + " TXT," + Keys.KEY_DISABLE_ST + " TXT," + Keys.KEY_CAND_RELIGION +
            " TXT," + Keys.KEY_MOTHER_TONGUE + " TXT," + Keys.KEY_CAND_OCCUPTN + " TXT," + Keys.KEY_ANNUAL_INCOME
            + " TXT," + Keys.KEY_CAND_SOURCE + " TXT," + Keys.KEY_INTEREST_COURSE + " TXT," + Keys.KEY_CAND_PRODUCT +
            " TXT," + Keys.KEY_AADHAAR_NO + " TXT," + Keys.KEY_ID_TYPE + " INTEGER," + Keys.KEY_ID_NUM + " TXT," +
            Keys.KEY_ID_COPY + " TXT," + Keys.KEY_EMP_TYPE + " TXT," + Keys.KEY_PREF_JOB + " TXT," + Keys.KEY_YRS_EXP +
            " TXT," + Keys.KEY_REL_EXP + " TXT," + Keys.KEY_LAST_CTC + " TXT," + Keys.KEY_PREF_LOC + " TXT," +
            Keys.KEY_WILL_TRAVEL + " TXT," + Keys.KEY_WORK_SHIFT + " TXT," + Keys.KEY_BOCW_ID + " TXT," +
            Keys.KEY_EXPECT_CTC + " TXT," + Keys.KEY_LATITUDE + " TXT," + Keys.KEY_LONGITUDE + " TXT," +
            Keys.KEY_TIMESTAMP + " TXT," + Keys.KEY_CREATED_BY + " INTEGER," + Keys.KEY_ACTIVITY_STATUS_ID +
            " INTEGER," + Keys.KEY_AADHAAR_COPY + " TXT," + Keys.KEY_PRES_STATE + " INTEGER," + Keys.KEY_PERM_STATE +
            " INTEGER," + Keys.KEY_EMAIL_VALIDATE + " INTEGER," +
            Keys.KEY_READ_LOCAL + " TXT," + Keys.KEY_HAVE_PHONE + " TXT," +
            Keys.KEY_BUY_PHONE + " TXT," + Keys.KEY_HAVE_BIKE + " TXT," + Keys.KEY_OPERATE_PHONE + " TXT," +
            Keys.KEY_IS_ENTRE + " TXT," + Keys.KEY_FAMILY_PERMIT + " TXT," + Keys.KEY_MEMBER_SHG + " TXT," +
            Keys.KEY_WILL_SHE + " TXT," + Keys.KEY_ONLINE_TRAINING + " TXT," + Keys.KEY_SHARE_TO_LN + " TXT," +
            Keys.KEY_CONTRACT_LN + " TXT," + Keys.KEY_BUY_TOOLS + " TXT," + Keys.KEY_ADOPT_DIGITAL + " TXT," +
            Keys.KEY_REGISTER_SOCIAL + " TXT," + Keys.KEY_LOAN_PAST + " TXT," + Keys.KEY_ACTIVE_LOAN + " TXT,"
            + Keys.KEY_TAKE_LOAN + " TXT," + Keys.KEY_HEALTH_INS + " TXT," + Keys.KEY_ALLERGIC_DUST + " TXT," +
            Keys.KEY_WEAR_PPE + " TXT," + Keys.KEY_FOLLOW_SAFETY + " TXT," + Keys.KEY_LEGAL_ENQUIRY + " TXT," +
            Keys.KEY_PASS_8 + " TXT," + Keys.KEY_MCL_AGE + " TXT," + Keys.KEY_PAST_EXP + " TXT," +
            Keys.KEY_WORK_6HRS + " TXT," + Keys.KEY_TRAVEL_PLACE + " TXT," + Keys.KEY_HAVE_BANK + " TXT," +
            Keys.KEY_MOBILIZATION_TYPE + " INTEGER," + Keys.KEY_RESULT_PASS + " INTEGER," +
            Keys.KEY_MCL_SCORE+" INTEGER,"+ Keys.KEY_WA_NUM + " TXT,"+Keys.KEY_DELL_EDN_PIC+" TXT,"+Keys.KEY_ASP_DISTRICT+
            " TXT,"+Keys.KEY_INCOME_PROOF+" TXT,"+ Keys.KEY_JOB_ROLE_ID+" INTEGER,"+
            Keys.KEY_10_PASS + " TXT," + Keys.KEY_READ_WRITE_LOCAL + " TXT," + Keys.KEY_READ_ENGLISH + " TXT," +
            Keys.KEY_MIN_6_EXP + " TXT," + Keys.KEY_GOOD_COMMUNICATION + " TXT," + Keys.KEY_UNDERSTAND_COVID + " TXT," +
            Keys.KEY_SERVE_MOBILIZER + " TXT," + Keys.KEY_TRAVEL_PHC + " TXT," + Keys.KEY_WORK_FULL_TIME + " TXT," +
            Keys.KEY_REPORT_MBL_DATA + " TXT," + Keys.KEY_12_PASS + " TXT," + Keys.KEY_WORK_MS_OFFICE + " TXT,"
            + Keys.KEY_SERVE_DEO + " TXT," + Keys.KEY_REPORT_DEO_DATA + " TXT," +
            Keys.KEY_COMPLETE_BSC + " TXT," + Keys.KEY_EXP_NGO + " TXT," + Keys.KEY_EXP_VACCINE + " TXT," +
            Keys.KEY_EXP_HOSPITAL + " TXT," + Keys.KEY_SERVE_VACCINATOR + " TXT," + Keys.KEY_REPORT_VACCINE_DATA + " TXT" +
            ")";

    private static final String QUERY_TBL_ENR_CAND = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_ENR_CAND + " (" +
            Keys.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Keys.KEY_USER_ID + " INTEGER," + Keys.KEY_CAND_STAGE +
            " INTEGER," + Keys.KEY_ENR_COMPLETE + " INTEGER," + Keys.KEY_IS_FRESHER + " INTEGER," + Keys.KEY_CAND_PIC +
            " TXT," + Keys.KEY_CAND_SALTN + " TXT," + Keys.KEY_FIRST_NAME + " TXT," + Keys.KEY_MID_NAME + " TXT," +
            Keys.KEY_LAST_NAME + " TXT," + Keys.KEY_CAND_DOB + " TXT," + Keys.KEY_DOB_ENTERED + " INTEGER," +
            Keys.KEY_CAND_AGE + " TXT," + Keys.KEY_PRIMARY_MOB + " TXT," + Keys.KEY_MOB_VERIFIED + " INTEGER," +
            Keys.KEY_SEC_MOB + " TXT," + Keys.KEY_CAND_EMAIL + " TXT," + Keys.KEY_CAND_GENDER + " TXT," +
            Keys.KEY_MARITAL_ST + " TXT," + Keys.KEY_CAND_CASTE + " TXT," + Keys.KEY_DISABLE_ST + " TXT," +
            Keys.KEY_CAND_RELIGION + " TXT," + Keys.KEY_MOTHER_TONGUE + " TXT," + Keys.KEY_CAND_OCCUPTN + " TXT," +
            Keys.KEY_ANNUAL_INCOME + " TXT," + Keys.KEY_CAND_SOURCE + " TXT," + Keys.KEY_INTEREST_COURSE + " TXT," +
            Keys.KEY_CAND_PRODUCT + " TXT," + Keys.KEY_PRES_ADDR1 + " TXT," + Keys.KEY_PRES_ADDR2 + " TXT," +
            Keys.KEY_PRES_VILLAGE + " TXT," + Keys.KEY_PRES_PANCHAYAT + " TXT," + Keys.KEY_PRES_TALUK + " TXT," +
            Keys.KEY_PRES_PINCODE + " TXT," + Keys.KEY_PRES_DISTRICT + " TXT," + Keys.KEY_PRES_STATE + " INTEGER," +
            Keys.KEY_PRES_COUNTRY + " INTEGER," + Keys.KEY_ADDR_SAME + " INTEGER," + Keys.KEY_PERM_ADDR1 + " TXT," +
            Keys.KEY_PERM_ADDR2 + " TXT," + Keys.KEY_PERM_VILLAGE + " TXT," + Keys.KEY_PERM_PANCHAYAT + " TXT," +
            Keys.KEY_PERM_TALUK + " TXT," + Keys.KEY_PERM_PINCODE + " TXT," + Keys.KEY_PERM_DISTRICT + " TXT," +
            Keys.KEY_PERM_STATE + " INTEGER," + Keys.KEY_PERM_COUNTRY + " INTEGER," + Keys.KEY_HIGH_QUALI + " TXT," +
            Keys.KEY_EDN_STREAM + " TXT," + Keys.KEY_INSTI_NAME + " TXT," + Keys.KEY_UNIVERSITY + " TXT," +
            Keys.KEY_YR_PASS + " TXT," + Keys.KEY_PERCENTAGE + " TXT," + Keys.KEY_COMP_KNOW + " TXT," +
            Keys.KEY_TECH_KNOW + " TXT," + Keys.KEY_MEM_SALTN + " TXT," + Keys.KEY_MEM_NAME + " TXT," +
            Keys.KEY_MEM_DOB + " TXT," + Keys.KEY_MEM_AGE + " TXT," + Keys.KEY_MEM_CONT + " TXT," + Keys.KEY_MEM_EMAIL +
            " TXT," + Keys.KEY_MEM_GENDER + " TXT," + Keys.KEY_MEM_EDN + " TXT," + Keys.KEY_MEM_RELATION + " TXT," +
            Keys.KEY_MEM_OCCUPTN + " TXT," +
            Keys.KEY_HOUSE_INCOME + " TXT," + Keys.KEY_BANK_NAME + " TXT," +
            Keys.KEY_BRANCH_NAME + " TXT," + Keys.KEY_IFSC_CODE + " TXT," + Keys.KEY_ACC_TYPE + " TXT," +
            Keys.KEY_ACC_NUM + " TXT," + Keys.KEY_BANK_COPY + " TXT," + Keys.KEY_LATITUDE + " TXT," + Keys.KEY_LONGITUDE +
            " TXT," + Keys.KEY_TIMESTAMP + " TXT," + Keys.KEY_ASSIGN_BATCH + " INTEGER," + Keys.KEY_CREATED_BY +
            " INTEGER," + Keys.KEY_ACTIVITY_STATUS_ID + " INTEGER," + Keys.KEY_EMAIL_VALIDATE + " INTEGER," +
            Keys.KEY_MOBILIZATION_TYPE + " INTEGER," + Keys.KEY_RESULT_PASS + " INTEGER," + Keys.KEY_ADDR_ADHAR +
            " TXT," + Keys.KEY_MEMBER_COUNT + " TXT," +Keys.KEY_AST_TV + " TXT," + Keys.KEY_AST_FRIDGE + " TXT," +
            Keys.KEY_AST_WASH + " TXT," + Keys.KEY_AST_AC + " TXT," + Keys.KEY_AST_CAR + " TXT," + Keys.KEY_AST_MED_INS +
            " TXT," + Keys.KEY_AST_LIFE_INS + " TXT," + Keys.KEY_AST_KIDS_EDN + " TXT," + Keys.KEY_HOUSE_SIZE +
            " TXT," + Keys.KEY_AST_OTHERS + " TXT," +Keys.KEY_OWN_HOUSE + " TXT," + Keys.KEY_RATION_CARD + " TXT," +
            Keys.KEY_SHE_EDN_PIC + " TXT," + Keys.KEY_AGE_PIC+ " TXT," + Keys.KEY_MOU_PIC + " TXT," + Keys.KEY_DATE_MOU +
            " TXT," + Keys.KEY_DATE_KIT + " TXT," +Keys.KEY_HEAD_HOUSE + " TXT," + Keys.KEY_AST_FARM_LAND +
            " TXT," + Keys.KEY_FARM_ACRE + " TXT,"+Keys.KEY_HAVE_BANK+" TXT,"+ Keys.KEY_WA_NUM + " TXT,"+Keys.KEY_MEM_INCOME + " TXT" +")";

    private static final String QUERY_TBL_NEO_BATCHES = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_NEO_BATCHES +
            " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY," + Keys.KEY_NEO_BATCH_CODE + " TXT," + Keys.KEY_NEO_BATCH_NAME +
            " TXT,"+Keys.KEY_NEO_COURSE_CODE+" TXT,"+Keys.KEY_NEO_COURSE_NAME+" TXT,"+Keys.KEY_NEO_EXT_BATCH_CODE+
            " TXT)";
    private static final String QUERY_TBL_CAND_STATUS_HISTORY = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_CAND_STATUS_HISTORY + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY," + Keys.KEY_USER_ID + " INTEGER," + Keys.KEY_LATITUDE + " TXT," + Keys.KEY_LONGITUDE + " TXT," + Keys.KEY_TIMESTAMP + " TXT," + Keys.KEY_CAND_ID + " INTEGER," + Keys.KEY_CS_ID + " INTEGER," + Keys.KEY_CS_REASON + " TXT," + Keys.KEY_CS_DATE_TIME + " TXT," + Keys.KEY_CS_REMARKS + " TXT," + Keys.KEY_CS_UPLOAD_FLAG + " INTEGER," + Keys.KEY_CS_NAME + " TXT)";
    private static final String QUERY_TBL_FAMILY_MEMBERS = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_FAMILY_MEMBERS + " (" + Keys.KEY_ID + " INTEGER PRIMARY KEY," + Keys.KEY_USER_ID + " INTEGER," + Keys.KEY_CAND_ID + " INTEGER," + Keys.KEY_MEM_SALTN + " TXT," + Keys.KEY_MEM_NAME + " TXT," + Keys.KEY_MEM_DOB + " TXT," + Keys.KEY_MEM_AGE + " TXT," + Keys.KEY_MEM_CONT + " TXT," + Keys.KEY_MEM_EMAIL + " TXT," + Keys.KEY_MEM_GENDER + " TXT," + Keys.KEY_MEM_EDN + " TXT," + Keys.KEY_MEM_RELATION + " TXT," + Keys.KEY_MEM_OCCUPTN + " TXT,"+Keys.KEY_MEM_INCOME + " TXT)";

    private static final String QUERY_TBL_RE_ENR_CAND = "CREATE TABLE IF NOT EXISTS " + Keys.TBL_RE_ENR_CAND + " (" +
            Keys.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Keys.KEY_USER_ID + " INTEGER," + Keys.KEY_CAND_STAGE +
            " INTEGER," + Keys.KEY_RE_ENR_COMPLETE + " INTEGER," + Keys.KEY_IS_FRESHER + " INTEGER," + Keys.KEY_CAND_PIC +
            " TXT," + Keys.KEY_CAND_SALTN + " TXT," + Keys.KEY_FIRST_NAME + " TXT," + Keys.KEY_MID_NAME + " TXT," +
            Keys.KEY_LAST_NAME + " TXT," + Keys.KEY_CAND_DOB + " TXT," + Keys.KEY_DOB_ENTERED + " INTEGER," + Keys.KEY_CAND_AGE
            + " TXT," + Keys.KEY_PRIMARY_MOB + " TXT," + Keys.KEY_MOB_VERIFIED + " INTEGER," + Keys.KEY_SEC_MOB + " TXT," +
            Keys.KEY_CAND_EMAIL + " TXT," + Keys.KEY_CAND_GENDER + " TXT," + Keys.KEY_MARITAL_ST + " TXT," + Keys.KEY_CAND_CASTE
            + " TXT," + Keys.KEY_DISABLE_ST + " TXT," + Keys.KEY_CAND_RELIGION + " TXT," + Keys.KEY_MOTHER_TONGUE + " TXT," +
            Keys.KEY_CAND_OCCUPTN + " TXT," + Keys.KEY_ANNUAL_INCOME + " TXT," + Keys.KEY_CAND_SOURCE + " TXT," +
            Keys.KEY_INTEREST_COURSE + " TXT," + Keys.KEY_CAND_PRODUCT + " TXT," + Keys.KEY_PRES_ADDR1 + " TXT," +
            Keys.KEY_PRES_ADDR2 + " TXT," + Keys.KEY_PRES_VILLAGE + " TXT," + Keys.KEY_PRES_PANCHAYAT + " TXT," +
            Keys.KEY_PRES_TALUK + " TXT," + Keys.KEY_PRES_PINCODE + " TXT," + Keys.KEY_PRES_DISTRICT + " TXT," +
            Keys.KEY_PRES_STATE + " INTEGER," + Keys.KEY_PRES_COUNTRY + " INTEGER," + Keys.KEY_ADDR_SAME + " INTEGER," +
            Keys.KEY_PERM_ADDR1 + " TXT," + Keys.KEY_PERM_ADDR2 + " TXT," + Keys.KEY_PERM_VILLAGE + " TXT," +
            Keys.KEY_PERM_PANCHAYAT + " TXT," + Keys.KEY_PERM_TALUK + " TXT," + Keys.KEY_PERM_PINCODE + " TXT," +
            Keys.KEY_PERM_DISTRICT + " TXT," + Keys.KEY_PERM_STATE + " INTEGER," + Keys.KEY_PERM_COUNTRY + " INTEGER," +
            Keys.KEY_HIGH_QUALI + " TXT," + Keys.KEY_EDN_STREAM + " TXT," + Keys.KEY_INSTI_NAME + " TXT," + Keys.KEY_UNIVERSITY
            + " TXT," + Keys.KEY_YR_PASS + " TXT," + Keys.KEY_PERCENTAGE + " TXT," + Keys.KEY_COMP_KNOW + " TXT," +
            Keys.KEY_TECH_KNOW + " TXT," + Keys.KEY_MEM_SALTN + " TXT," + Keys.KEY_MEM_NAME + " TXT," + Keys.KEY_MEM_DOB +
            " TXT," + Keys.KEY_MEM_AGE + " TXT," + Keys.KEY_MEM_CONT + " TXT," + Keys.KEY_MEM_EMAIL + " TXT," +
            Keys.KEY_MEM_GENDER + " TXT," + Keys.KEY_MEM_EDN + " TXT," + Keys.KEY_MEM_RELATION + " TXT," + Keys.KEY_MEM_OCCUPTN
            + " TXT,"  + Keys.KEY_HOUSE_INCOME + " TXT," + Keys.KEY_BANK_NAME + " TXT," + Keys.KEY_BRANCH_NAME + " TXT," +
            Keys.KEY_IFSC_CODE + " TXT," + Keys.KEY_ACC_TYPE + " TXT," + Keys.KEY_ACC_NUM + " TXT," + Keys.KEY_BANK_COPY +
            " TXT," + Keys.KEY_LATITUDE + " TXT," + Keys.KEY_LONGITUDE + " TXT," + Keys.KEY_TIMESTAMP + " TXT," +
            Keys.KEY_ASSIGN_BATCH + " INTEGER," + Keys.KEY_CREATED_BY + " INTEGER," + Keys.KEY_ACTIVITY_STATUS_ID + " INTEGER,"
            + Keys.KEY_EMAIL_VALIDATE + " INTEGER," + Keys.KEY_WA_NUM + " TXT," +
            Keys.KEY_READ_LOCAL +
            " TXT," + Keys.KEY_HAVE_PHONE + " TXT," +
            Keys.KEY_BUY_PHONE + " TXT," + Keys.KEY_HAVE_BIKE + " TXT," + Keys.KEY_OPERATE_PHONE + " TXT," +
            Keys.KEY_IS_ENTRE + " TXT," + Keys.KEY_FAMILY_PERMIT + " TXT," + Keys.KEY_MEMBER_SHG + " TXT," +
            Keys.KEY_WILL_SHE + " TXT," + Keys.KEY_ONLINE_TRAINING + " TXT," + Keys.KEY_SHARE_TO_LN + " TXT," +
            Keys.KEY_CONTRACT_LN + " TXT," + Keys.KEY_BUY_TOOLS + " TXT," + Keys.KEY_ADOPT_DIGITAL + " TXT," +
            Keys.KEY_REGISTER_SOCIAL + " TXT," + Keys.KEY_LOAN_PAST + " TXT," + Keys.KEY_ACTIVE_LOAN + " TXT,"
            + Keys.KEY_TAKE_LOAN + " TXT," + Keys.KEY_HEALTH_INS + " TXT," + Keys.KEY_ALLERGIC_DUST + " TXT," +
            Keys.KEY_WEAR_PPE + " TXT," + Keys.KEY_FOLLOW_SAFETY + " TXT," + Keys.KEY_LEGAL_ENQUIRY + " TXT," +
            Keys.KEY_PASS_8 + " TXT," + Keys.KEY_MCL_AGE + " TXT," + Keys.KEY_PAST_EXP + " TXT," +
            Keys.KEY_WORK_6HRS + " TXT," + Keys.KEY_TRAVEL_PLACE + " TXT," +
            Keys.KEY_MOBILIZATION_TYPE + " INTEGER," + Keys.KEY_RESULT_PASS + " INTEGER," +
            Keys.KEY_MCL_SCORE+" INTEGER,"+Keys.KEY_DELL_EDN_PIC+" TXT,"+Keys.KEY_ASP_DISTRICT+
            " TXT,"+Keys.KEY_INCOME_PROOF+" TXT,"+
            Keys.KEY_ADDR_ADHAR +
            " TXT," + Keys.KEY_MEMBER_COUNT + " TXT," +Keys.KEY_AST_TV + " TXT," + Keys.KEY_AST_FRIDGE + " TXT," +
            Keys.KEY_AST_WASH + " TXT," + Keys.KEY_AST_AC + " TXT," + Keys.KEY_AST_CAR + " TXT," + Keys.KEY_AST_MED_INS +
            " TXT," + Keys.KEY_AST_LIFE_INS + " TXT," + Keys.KEY_AST_KIDS_EDN + " TXT," + Keys.KEY_HOUSE_SIZE +
            " TXT," + Keys.KEY_AST_OTHERS + " TXT," +Keys.KEY_OWN_HOUSE + " TXT," + Keys.KEY_RATION_CARD + " TXT," +
            Keys.KEY_SHE_EDN_PIC + " TXT," + Keys.KEY_AGE_PIC+ " TXT," + Keys.KEY_MOU_PIC + " TXT," + Keys.KEY_DATE_MOU +
            " TXT," + Keys.KEY_DATE_KIT + " TXT," +Keys.KEY_HEAD_HOUSE + " TXT," + Keys.KEY_AST_FARM_LAND +
            " TXT," + Keys.KEY_FARM_ACRE + " TXT,"+Keys.KEY_HAVE_BANK+" TXT," + Keys.KEY_MEM_INCOME + " TXT"+
            ")";
    /*TMA*/
    private static String table_assessor = "assessor_details";
    private static String table_predeficit = "predeficit";
    private static String table_arc = "arccode";
    private static String table_stage = "stage";
    private static String table_image = "image";
    private static String table_location = "location";
    private static String GPS_TIME = "time";
    private static String UserID = "userid";
    private static String KEY_ID = "id";
    private static String table_deviceid = "deviceid";
    private static String table_overallData = "overalldata";
    private static String table_requestid = "requestid";
    private static String temp_requestid = "id";
    private static String table_mode = "mode";
    private static String table_storedDates = "todaydatess";
    private static String table_backupdata = "jsonbackup";
    private static String table_add_activity = "addactivity";
    private static String table_doc_image = "docimage";
    private Context mContext = null;
    private SQLiteDatabase db = null;

    public Database(Context ctx) {
        mContext = ctx;
    }

    public void open() {
        try {
            OpenHelper oh = new OpenHelper(mContext);
            this.db = oh.getWritableDatabase();
        } catch (Exception ex) {
        }
    }

    public void close() {
        try {
            if (db != null) db.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public boolean check(String values) {
        this.open();
        Cursor handle = db.rawQuery(values, null);
        if (handle != null) {
            int row = handle.getCount();
            if (row > 0) {
                handle.close();
                this.close();
                return true;
            } else {
                handle.close();
                this.close();
                return false;
            }
        }
        return false;
    }

    public long insert(String values) {
//        Log.e(getClass().getName() + "insert", values);
        this.open();
        long k = 0;
        SQLiteStatement insertstmt = null;
        try {
            insertstmt = this.db.compileStatement(values);
            k = insertstmt.executeInsert();
            insertstmt.close();
            this.close();
        } catch (Exception e) {
            if (insertstmt != null) insertstmt.close();
            if (db != null) this.close();
            Log.e(getClass().getName() + " insert", e.getMessage());
        }
        return k;
    }

    public void update(String values) {
        Log.i(getClass().getName() + " update", values);
        this.open();
        try {
            db.execSQL(values);
            this.close();
        } catch (Exception e) {
            if (db != null) this.close();
            Log.e(getClass().getName(), e.getMessage());
        }
    }

    public void updateMobileEmail(boolean isMobile,int action, int candId,int userId, String mobile, String email) {
        Log.e(TAG, "updateMobileEmail");
        String tbl_name;

        if (action == Keys.ACTION_MOBILIZATION) {
            tbl_name = Keys.TBL_MBL_CAND;
        } else if (action == Keys.ACTION_REGISTRATION) {
            tbl_name = Keys.TBL_REG_CAND;
        } else if (action == Keys.ACTION_ENROLMENT) {
            tbl_name = Keys.TBL_ENR_CAND;
        } else {
            tbl_name = Keys.TBL_RE_ENR_CAND;
        }
        this.open();
        db.execSQL("UPDATE " + tbl_name + " SET " +
                (isMobile?(Keys.KEY_PRIMARY_MOB + "='" + mobile):(Keys.KEY_CAND_EMAIL+
                "='"+email))
                + "'"+" WHERE " + Keys.KEY_ID + "=" + candId+" AND "+Keys.KEY_USER_ID+"="+userId);
        this.close();
    }

    public void clearCandData(int userId) {
        Log.e(TAG, "clearCandData");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_MBL_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId);
        db.execSQL("DELETE FROM " + Keys.TBL_REG_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId);
        db.execSQL("DELETE FROM " + Keys.TBL_ENR_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId);
        db.execSQL("DELETE FROM " + Keys.TBL_RE_ENR_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId);
        this.close();
    }

    public boolean isDataPending(int userId) {
        Log.e("isDataPending", "start");
        boolean flag = false;
        this.open();
        String query = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_MBL_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId;
        Cursor handle = db.rawQuery(query, null);
        if (handle != null) {
            Log.e("isDataPending", "mbl");
            int row = handle.getCount();
            if (row > 0) {
                flag = true;
            }
            handle.close();
        }
        if (!flag) {
            String query2 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_REG_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId;
            Cursor handle2 = db.rawQuery(query2, null);
            if (handle2 != null) {
                Log.e("isDataPending", "reg");
                int row = handle2.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle2.close();
            }
        }
        if (!flag) {
            String query3 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_ENR_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId;
            Cursor handle3 = db.rawQuery(query3, null);
            if (handle3 != null) {
                Log.e("isDataPending", "enr");
                int row = handle3.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle3.close();
            }
        }
        if (!flag) {
            String query4 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_RE_ENR_CAND + " WHERE " + Keys.KEY_USER_ID + "=" + userId;
            Cursor handle4 = db.rawQuery(query4, null);
            if (handle4 != null) {
                Log.e("isDataPending", "reenr");
                int row = handle4.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle4.close();
            }
        }

        this.close();
        return flag;
    }

    public boolean mobileExists(int candId, String mobile) {
        Log.e("mobileExists", "start");
        boolean flag = false;
        this.open();
        String query = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_MBL_CAND + " WHERE " + Keys.KEY_PRIMARY_MOB + "='" + mobile + "' AND " + Keys.KEY_ID + "!=" + candId;
        Cursor handle = db.rawQuery(query, null);
        if (handle != null) {
            Log.e("mobileExists", "mbl");
            int row = handle.getCount();
            if (row > 0) {
                flag = true;
            }
            handle.close();
        }
        if (!flag) {
            String query2 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_REG_CAND + " WHERE " + Keys.KEY_PRIMARY_MOB + "='" + mobile + "' AND " + Keys.KEY_ID + "!=" + candId;
            Cursor handle2 = db.rawQuery(query2, null);
            if (handle2 != null) {
                Log.e("mobileExists", "reg");
                int row = handle2.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle2.close();
            }
        }
        if (!flag) {
            String query3 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_ENR_CAND + " WHERE " + Keys.KEY_PRIMARY_MOB + "='" + mobile + "' AND " + Keys.KEY_ID + "!=" + candId;
            Cursor handle3 = db.rawQuery(query3, null);
            if (handle3 != null) {
                Log.e("mobileExists", "enr");
                int row = handle3.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle3.close();
            }
        }
        if (!flag) {
            String query4 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_RE_ENR_CAND + " WHERE " + Keys.KEY_PRIMARY_MOB + "='" + mobile + "' AND " + Keys.KEY_ID + "!=" + candId;
            Cursor handle4 = db.rawQuery(query4, null);
            if (handle4 != null) {
                Log.e("mobileExists", "re enr");
                int row = handle4.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle4.close();
            }
        }
        this.close();
        return flag;
    }

    public boolean emailExists(int candId, String email) {
        Log.e("emailExists", "start");
        boolean flag = false;
        this.open();
        String query = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_MBL_CAND + " WHERE " + Keys.KEY_CAND_EMAIL + "='" + email + "' AND " + Keys.KEY_ID + "!=" + candId;
        Cursor handle = db.rawQuery(query, null);
        if (handle != null) {
            Log.e("emailExists", "mbl");
            int row = handle.getCount();
            if (row > 0) {
                flag = true;
            }
            handle.close();
        }
        if (!flag) {
            String query2 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_REG_CAND + " WHERE " + Keys.KEY_CAND_EMAIL + "='" + email + "' AND " + Keys.KEY_ID + "!=" + candId;
            Cursor handle2 = db.rawQuery(query2, null);
            if (handle2 != null) {
                Log.e("emailExists", "reg");
                int row = handle2.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle2.close();
            }
        }
        if (!flag) {
            String query3 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_ENR_CAND + " WHERE " + Keys.KEY_CAND_EMAIL + "='" + email + "' AND " + Keys.KEY_ID + "!=" + candId;
            Cursor handle3 = db.rawQuery(query3, null);
            if (handle3 != null) {
                Log.e("emailExists", "enr");
                int row = handle3.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle3.close();
            }
        }
        if (!flag) {
            String query4 = "SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_RE_ENR_CAND + " WHERE " + Keys.KEY_CAND_EMAIL + "='" + email + "' AND " + Keys.KEY_ID + "!=" + candId;
            Cursor handle4 = db.rawQuery(query4, null);
            if (handle4 != null) {
                Log.e("emailExists", "reenr");
                int row = handle4.getCount();
                if (row > 0) {
                    flag = true;
                }
                handle4.close();
            }
        }

        this.close();
        return flag;
    }

    public void clearFamilyMembers(int userId, int candId) {
        Log.e(TAG, "clearFamilyMembers");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_FAMILY_MEMBERS + " WHERE " + Keys.KEY_USER_ID + "=" + userId + " AND " + Keys.KEY_CAND_ID + "=" + candId);
        this.close();
    }

    public void addFamilyMembers(String query) throws Exception {


        final String qInsert = "INSERT INTO " + Keys.TBL_FAMILY_MEMBERS + " (" + Keys.KEY_USER_ID + "," + Keys.KEY_CAND_ID + "," + Keys.KEY_MEM_SALTN + "," + Keys.KEY_MEM_NAME + "," + Keys.KEY_MEM_DOB + "," + Keys.KEY_MEM_AGE + "," + Keys.KEY_MEM_CONT + "," + Keys.KEY_MEM_EMAIL + "," + Keys.KEY_MEM_GENDER + "," + Keys.KEY_MEM_EDN + "," + Keys.KEY_MEM_RELATION + "," + Keys.KEY_MEM_OCCUPTN + ","+Keys.KEY_MEM_INCOME+") VALUES " + query + ";";
        this.insert(qInsert);

    }
    public ArrayList<CandStatusHistoryModel> getCandStatusHistoryToUpload(int userId) {
        ArrayList<CandStatusHistoryModel> temp = new ArrayList<>();
        String query = "SELECT * FROM " + Keys.TBL_CAND_STATUS_HISTORY + " WHERE " + Keys.KEY_USER_ID + "=" + userId + " AND " + Keys.KEY_CS_UPLOAD_FLAG + "=0";
        this.open();
        Cursor handle = db.rawQuery(query, null);
        if (handle != null) {
            int row = handle.getCount();
            if (row > 0) {
                handle.moveToFirst();
                do {
                    CandStatusHistoryModel candStatusHistoryModel = new CandStatusHistoryModel();
                    candStatusHistoryModel.setId(handle.getInt(0));
                    candStatusHistoryModel.setLatitude(handle.getString(2));
                    candStatusHistoryModel.setLongitude(handle.getString(3));
                    candStatusHistoryModel.setTimestamp(handle.getString(4));
                    candStatusHistoryModel.setCandidateId(handle.getInt(5));
                    candStatusHistoryModel.setStatusId(handle.getInt(6));
                    candStatusHistoryModel.setReason(handle.getString(7));
                    candStatusHistoryModel.setDateTime(handle.getString(8));
                    candStatusHistoryModel.setRemarks(handle.getString(9));
                    candStatusHistoryModel.setUploadFlag(handle.getInt(10));
                    candStatusHistoryModel.setStatusName(handle.getString(11));

                    temp.add(candStatusHistoryModel);
                } while (handle.moveToNext());
            }
            handle.close();
        }
        this.close();
        return temp;
    }

    public ArrayList<FamilyMemberModel> getFamilyMembers(int userId, int candId) {
        ArrayList<FamilyMemberModel> temp = new ArrayList<>();
        String query = "SELECT * FROM " + Keys.TBL_FAMILY_MEMBERS + " WHERE " + Keys.KEY_USER_ID + "=" + userId + " AND " + Keys.KEY_CAND_ID + "=" + candId;
        this.open();
        Cursor handle = db.rawQuery(query, null);
        if (handle != null) {
            int row = handle.getCount();
            if (row > 0) {
                handle.moveToFirst();
                do {
                    FamilyMemberModel familyMemberModel = new FamilyMemberModel();

                    familyMemberModel.setMemberName(handle.getString(4));

                    familyMemberModel.setAge(handle.getString(6));

                    familyMemberModel.setEmail(handle.getString(8));
                    familyMemberModel.setGender(handle.getString(9));
                    familyMemberModel.setEdnQuali(handle.getString(10));
                    familyMemberModel.setRelation(handle.getString(11));
                    familyMemberModel.setOccupation(handle.getString(12));
                    familyMemberModel.setCard(handle.getString(13));

                    temp.add(familyMemberModel);
                } while (handle.moveToNext());
            }
            handle.close();
        }
        this.close();
        return temp;
    }

    public void updateLatestActivityStatus(int action, int statusId, int candId) {
        Log.e(TAG, "updateLatestActivityStatus");
        String tbl_name;
        if (action == Keys.ACTION_REGISTRATION) {
            tbl_name = Keys.TBL_REG_CAND;
        } else {
            tbl_name = Keys.TBL_ENR_CAND;
        }
        this.open();
        db.execSQL("UPDATE " + tbl_name + " SET " + Keys.KEY_ACTIVITY_STATUS_ID + "=" + statusId + " WHERE " + Keys.KEY_ID + "=" + candId);
        this.close();
    }

    public void clearCandStatusHistory(int userId) {
        Log.e(TAG, "clearCandStatusHistory");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_CAND_STATUS_HISTORY + " WHERE " + Keys.KEY_USER_ID + "=" + userId);
        this.close();
    }

    public void addCandStatusHistory(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_CAND_STATUS_HISTORY + " (" + Keys.KEY_USER_ID + "," + Keys.KEY_LATITUDE + "," + Keys.KEY_LONGITUDE + "," + Keys.KEY_CAND_ID + "," + Keys.KEY_CS_ID + "," + Keys.KEY_CS_NAME + "," + Keys.KEY_CS_REASON + "," + Keys.KEY_CS_REMARKS + "," + Keys.KEY_CS_DATE_TIME + "," + Keys.KEY_TIMESTAMP + "," + Keys.KEY_CS_UPLOAD_FLAG + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }

    public void updateUploadedStatusHistory(int userId) {
        Log.e(TAG, "updateUploadedStatusHistory");

        this.open();
        db.execSQL("UPDATE " + Keys.TBL_CAND_STATUS_HISTORY + " SET " + Keys.KEY_CS_UPLOAD_FLAG + "=1" + " WHERE " + Keys.KEY_ID + " IN (SELECT " + Keys.KEY_ID + " FROM " + Keys.TBL_CAND_STATUS_HISTORY + " WHERE " + Keys.KEY_USER_ID + "=" + userId + " AND " + Keys.KEY_CS_UPLOAD_FLAG + "=0)");
        this.close();
    }



    public void clearNeoBatches() {
        Log.e(TAG, "clearNeoBatches");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_NEO_BATCHES);
        this.close();
    }

    public void addNeoBatches(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_NEO_BATCHES + " (" +
                Keys.KEY_ID + "," + Keys.KEY_NEO_BATCH_CODE + "," + Keys.KEY_NEO_BATCH_NAME +","+Keys.KEY_NEO_COURSE_CODE+
                ","+Keys.KEY_NEO_COURSE_NAME+","+Keys.KEY_NEO_EXT_BATCH_CODE
                + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }


    public void updateReEnrCandBasic(int rowId, int cand_stage, int re_enr_complete, String latitude, String longitude,
                                     String timestamp, int is_fresher, String cand_pic, String cand_saltn, String first_name,
                                     String mid_name, String last_name, String cand_dob, int dob_entered, String cand_age,
                                     String primary_mob, int mob_verified, String sec_mob, String cand_email, String cand_gender,
                                     String marital_status, String cand_caste, String disable_status, String cand_religion,
                                     String mother_tongue, String cand_occuptn, String annual_income, String cand_source,
                                     String interest_course, String cand_product, int email_valid,String wa_num) {

        this.open();
        Log.e("updateReEnrCandBasic id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," +
                Keys.KEY_IS_FRESHER + "=" + is_fresher + "," + Keys.KEY_CAND_PIC + "='" + cand_pic + "'," +
                Keys.KEY_CAND_SALTN + "='" + cand_saltn + "'," + Keys.KEY_FIRST_NAME + "='" + first_name + "'," +
                Keys.KEY_MID_NAME + "='" + mid_name + "'," + Keys.KEY_LAST_NAME + "='" + last_name + "'," + Keys.KEY_CAND_DOB +
                "='" + cand_dob + "'," + Keys.KEY_DOB_ENTERED + "=" + dob_entered + "," + Keys.KEY_CAND_AGE + "='" + cand_age +
                "'," + Keys.KEY_PRIMARY_MOB + "='" + primary_mob + "'," + Keys.KEY_MOB_VERIFIED + "=" + mob_verified + "," +
                Keys.KEY_SEC_MOB + "='" + sec_mob + "'," + Keys.KEY_CAND_EMAIL + "='" + cand_email + "'," +
                Keys.KEY_CAND_GENDER + "='" + cand_gender + "'," + Keys.KEY_MARITAL_ST + "='" + marital_status + "'," +
                Keys.KEY_CAND_CASTE + "='" + cand_caste + "'," + Keys.KEY_DISABLE_ST + "='" + disable_status + "'," +
                Keys.KEY_CAND_RELIGION + "='" + cand_religion + "'," + Keys.KEY_MOTHER_TONGUE + "='" + mother_tongue + "'," +
                Keys.KEY_CAND_OCCUPTN + "='" + cand_occuptn + "'," + Keys.KEY_ANNUAL_INCOME + "='" + annual_income + "'," +
                Keys.KEY_CAND_SOURCE + "='" + cand_source + "'," + Keys.KEY_INTEREST_COURSE + "='" + interest_course + "'," +
                Keys.KEY_CAND_PRODUCT + "='" + cand_product + "'," + Keys.KEY_EMAIL_VALIDATE + "=" + email_valid +","+
                Keys.KEY_WA_NUM+"='"+wa_num+"'"+

                " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateEnrCandBasic(int rowId, int cand_stage, int enr_complete, String latitude, String longitude,
                                   String timestamp, int is_fresher, String cand_pic, String cand_saltn, String first_name,
                                   String mid_name, String last_name, String cand_dob, int dob_entered, String cand_age,
                                   String primary_mob, int mob_verified, String sec_mob, String cand_email, String cand_gender,
                                   String marital_status, String cand_caste, String disable_status, String cand_religion,
                                   String mother_tongue, String cand_occuptn, String annual_income, String cand_source,
                                   String interest_course, String cand_product, int email_valid,String wa_num) {

        this.open();
        Log.e("updateEnrCandBasic id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," +
                Keys.KEY_IS_FRESHER + "=" + is_fresher + "," + Keys.KEY_CAND_PIC + "='" + cand_pic + "'," +
                Keys.KEY_CAND_SALTN + "='" + cand_saltn + "'," + Keys.KEY_FIRST_NAME + "='" + first_name + "'," +
                Keys.KEY_MID_NAME + "='" + mid_name + "'," + Keys.KEY_LAST_NAME + "='" + last_name + "'," + Keys.KEY_CAND_DOB
                + "='" + cand_dob + "'," + Keys.KEY_DOB_ENTERED + "=" + dob_entered + "," + Keys.KEY_CAND_AGE + "='" + cand_age
                + "'," + Keys.KEY_PRIMARY_MOB + "='" + primary_mob + "'," + Keys.KEY_MOB_VERIFIED + "=" + mob_verified + "," +
                Keys.KEY_SEC_MOB + "='" + sec_mob + "'," + Keys.KEY_CAND_EMAIL + "='" + cand_email + "'," + Keys.KEY_CAND_GENDER
                + "='" + cand_gender + "'," + Keys.KEY_MARITAL_ST + "='" + marital_status + "'," + Keys.KEY_CAND_CASTE + "='" +
                cand_caste + "'," + Keys.KEY_DISABLE_ST + "='" + disable_status + "'," + Keys.KEY_CAND_RELIGION + "='" +
                cand_religion + "'," + Keys.KEY_MOTHER_TONGUE + "='" + mother_tongue + "'," + Keys.KEY_CAND_OCCUPTN + "='" +
                cand_occuptn + "'," + Keys.KEY_ANNUAL_INCOME + "='" + annual_income + "'," + Keys.KEY_CAND_SOURCE + "='" +
                cand_source + "'," + Keys.KEY_INTEREST_COURSE + "='" + interest_course + "'," + Keys.KEY_CAND_PRODUCT + "='" +
                cand_product + "'," + Keys.KEY_EMAIL_VALIDATE + "=" + email_valid +","+
                Keys.KEY_WA_NUM+"='"+wa_num+"'"+

                " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }


    public void updateRegCandBasic(int rowId, int cand_stage, int reg_complete, String latitude, String longitude,
                                   String timestamp, int is_fresher, String cand_pic, String cand_saltn,
                                   String first_name, String mid_name, String last_name, String cand_dob, int dob_entered,
                                   String cand_age, String primary_mob, int mob_verified, String sec_mob, String cand_email,
                                   String cand_gender, String marital_status, String cand_caste, String disable_status,
                                   String cand_religion, String mother_tongue, String cand_occuptn, String annual_income,
                                   String cand_source, String interest_course, String cand_product, int email_valid,String wa_num) {

        this.open();
        Log.e("updateRegCandBasic id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," +
                Keys.KEY_IS_FRESHER + "=" + is_fresher + "," + Keys.KEY_CAND_PIC + "='" + cand_pic + "'," + Keys.KEY_CAND_SALTN
                + "='" + cand_saltn + "'," + Keys.KEY_FIRST_NAME + "='" + first_name + "'," + Keys.KEY_MID_NAME + "='" +
                mid_name + "'," + Keys.KEY_LAST_NAME + "='" + last_name + "'," + Keys.KEY_CAND_DOB + "='" + cand_dob + "'," +
                Keys.KEY_DOB_ENTERED + "=" + dob_entered + "," + Keys.KEY_CAND_AGE + "='" + cand_age + "'," +
                Keys.KEY_PRIMARY_MOB + "='" + primary_mob + "'," + Keys.KEY_MOB_VERIFIED + "=" + mob_verified + "," +
                Keys.KEY_SEC_MOB + "='" + sec_mob + "'," + Keys.KEY_CAND_EMAIL + "='" + cand_email + "'," +
                Keys.KEY_CAND_GENDER + "='" + cand_gender + "'," + Keys.KEY_MARITAL_ST + "='" + marital_status + "'," +
                Keys.KEY_CAND_CASTE + "='" + cand_caste + "'," + Keys.KEY_DISABLE_ST + "='" + disable_status + "'," +
                Keys.KEY_CAND_RELIGION + "='" + cand_religion + "'," + Keys.KEY_MOTHER_TONGUE + "='" + mother_tongue + "'," +
                Keys.KEY_CAND_OCCUPTN + "='" + cand_occuptn + "'," + Keys.KEY_ANNUAL_INCOME + "='" + annual_income + "'," +
                Keys.KEY_CAND_SOURCE + "='" + cand_source + "'," + Keys.KEY_INTEREST_COURSE + "='" + interest_course + "'," +
                Keys.KEY_CAND_PRODUCT + "='" + cand_product + "'," + Keys.KEY_EMAIL_VALIDATE + "=" + email_valid +","+
                Keys.KEY_WA_NUM+"='"+wa_num+"'"+

                " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateMblCandBasic(int rowId, int cand_stage, int mbl_complete, String latitude, String longitude,
                                   String timestamp, int is_fresher, String cand_pic, String cand_saltn, String first_name,
                                   String mid_name, String last_name, String cand_dob, int dob_entered, String cand_age,
                                   String primary_mob, int mob_verified, String sec_mob, String cand_email, String cand_gender,
                                   String marital_status, String cand_caste, String disable_status, String cand_religion,
                                   String mother_tongue, String cand_occuptn, String annual_income, String cand_source,
                                   String interest_course, String cand_product, int email_valid,String wa_num) {

        this.open();
        Log.e("updateMblCandBasic id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_MBL_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_MBL_COMPLETE + "=" + mbl_complete + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," + Keys.KEY_IS_FRESHER
                + "=" + is_fresher + "," + Keys.KEY_CAND_PIC + "='" + cand_pic + "'," + Keys.KEY_CAND_SALTN + "='" + cand_saltn
                + "'," + Keys.KEY_FIRST_NAME + "='" + first_name + "'," + Keys.KEY_MID_NAME + "='" + mid_name + "'," +
                Keys.KEY_LAST_NAME + "='" + last_name + "'," + Keys.KEY_CAND_DOB + "='" + cand_dob + "'," + Keys.KEY_DOB_ENTERED
                + "=" + dob_entered + "," + Keys.KEY_CAND_AGE + "='" + cand_age + "'," + Keys.KEY_PRIMARY_MOB + "='" +
                primary_mob + "'," + Keys.KEY_MOB_VERIFIED + "=" + mob_verified + "," + Keys.KEY_SEC_MOB + "='" + sec_mob +
                "'," + Keys.KEY_CAND_EMAIL + "='" + cand_email + "'," + Keys.KEY_CAND_GENDER + "='" + cand_gender + "'," +
                Keys.KEY_MARITAL_ST + "='" + marital_status + "'," + Keys.KEY_CAND_CASTE + "='" + cand_caste + "'," +
                Keys.KEY_DISABLE_ST + "='" + disable_status + "'," + Keys.KEY_CAND_RELIGION + "='" + cand_religion + "'," +
                Keys.KEY_MOTHER_TONGUE + "='" + mother_tongue + "'," + Keys.KEY_CAND_OCCUPTN + "='" + cand_occuptn + "'," +
                Keys.KEY_ANNUAL_INCOME + "='" + annual_income + "'," + Keys.KEY_CAND_SOURCE + "='" + cand_source + "'," +
                Keys.KEY_INTEREST_COURSE + "='" + interest_course + "'," + Keys.KEY_CAND_PRODUCT + "='" + cand_product + "'," +
                Keys.KEY_EMAIL_VALIDATE + "=" + email_valid +","+
                Keys.KEY_WA_NUM+"='"+wa_num+"'"+

                " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrCandAddress(int rowId, int cand_stage, int re_enr_complete, String pres_addr_one, String pres_addr_two, String pres_village, String pres_panchayat, String pres_taluk, String pres_pincode, String pres_district, int pres_state, int pres_country, int addr_same, String perm_addr_one, String perm_addr_two, String perm_village, String perm_panchayat, String perm_taluk, String perm_pincode, String perm_district, int perm_state, int perm_country, String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrCandAddr id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_PRES_ADDR1 + "='" + pres_addr_one + "'," + Keys.KEY_PRES_ADDR2 + "='" + pres_addr_two + "'," + Keys.KEY_PRES_VILLAGE + "='" + pres_village + "'," + Keys.KEY_PRES_PANCHAYAT + "='" + pres_panchayat + "'," + Keys.KEY_PRES_TALUK + "='" + pres_taluk + "'," + Keys.KEY_PRES_PINCODE + "='" + pres_pincode + "'," + Keys.KEY_PRES_DISTRICT + "='" + pres_district + "'," + Keys.KEY_PRES_STATE + "=" + pres_state + "," + Keys.KEY_PRES_COUNTRY + "=" + pres_country + "," + Keys.KEY_ADDR_SAME + "=" + addr_same + "," + Keys.KEY_PERM_ADDR1 + "='" + perm_addr_one + "'," + Keys.KEY_PERM_ADDR2 + "='" + perm_addr_two + "'," + Keys.KEY_PERM_VILLAGE + "='" + perm_village + "'," + Keys.KEY_PERM_PANCHAYAT + "='" + perm_panchayat + "'," + Keys.KEY_PERM_TALUK + "='" + perm_taluk + "'," + Keys.KEY_PERM_PINCODE + "='" + perm_pincode + "'," + Keys.KEY_PERM_DISTRICT + "='" + perm_district + "'," + Keys.KEY_PERM_STATE + "=" + perm_state + "," + Keys.KEY_PERM_COUNTRY + "=" + perm_country + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateEnrCandAddress(int rowId, int cand_stage, int enr_complete, String pres_addr_one, String pres_addr_two, String pres_village, String pres_panchayat, String pres_taluk, String pres_pincode, String pres_district, int pres_state, int pres_country, int addr_same, String perm_addr_one, String perm_addr_two, String perm_village, String perm_panchayat, String perm_taluk, String perm_pincode, String perm_district, int perm_state, int perm_country, String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateEnrCandAddress id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_PRES_ADDR1 + "='" + pres_addr_one + "'," + Keys.KEY_PRES_ADDR2 + "='" + pres_addr_two + "'," + Keys.KEY_PRES_VILLAGE + "='" + pres_village + "'," + Keys.KEY_PRES_PANCHAYAT + "='" + pres_panchayat + "'," + Keys.KEY_PRES_TALUK + "='" + pres_taluk + "'," + Keys.KEY_PRES_PINCODE + "='" + pres_pincode + "'," + Keys.KEY_PRES_DISTRICT + "='" + pres_district + "'," + Keys.KEY_PRES_STATE + "=" + pres_state + "," + Keys.KEY_PRES_COUNTRY + "=" + pres_country + "," + Keys.KEY_ADDR_SAME + "=" + addr_same + "," + Keys.KEY_PERM_ADDR1 + "='" + perm_addr_one + "'," + Keys.KEY_PERM_ADDR2 + "='" + perm_addr_two + "'," + Keys.KEY_PERM_VILLAGE + "='" + perm_village + "'," + Keys.KEY_PERM_PANCHAYAT + "='" + perm_panchayat + "'," + Keys.KEY_PERM_TALUK + "='" + perm_taluk + "'," + Keys.KEY_PERM_PINCODE + "='" + perm_pincode + "'," + Keys.KEY_PERM_DISTRICT + "='" + perm_district + "'," + Keys.KEY_PERM_STATE + "=" + perm_state + "," + Keys.KEY_PERM_COUNTRY + "=" + perm_country + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateMblCandAddress(int rowId, int cand_stage, int mbl_complete, String pres_addr_one, String pres_addr_two, String pres_village, String pres_panchayat, String pres_taluk, String pres_pincode, String pres_district, int pres_state, int pres_country, int addr_same, String perm_addr_one, String perm_addr_two, String perm_village, String perm_panchayat, String perm_taluk, String perm_pincode, String perm_district, int perm_state, int perm_country, String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateMblCandAddress id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_MBL_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_MBL_COMPLETE + "=" + mbl_complete + "," + Keys.KEY_PRES_ADDR1 + "='" + pres_addr_one + "'," + Keys.KEY_PRES_ADDR2 + "='" + pres_addr_two + "'," + Keys.KEY_PRES_VILLAGE + "='" + pres_village + "'," + Keys.KEY_PRES_PANCHAYAT + "='" + pres_panchayat + "'," + Keys.KEY_PRES_TALUK + "='" + pres_taluk + "'," + Keys.KEY_PRES_PINCODE + "='" + pres_pincode + "'," + Keys.KEY_PRES_DISTRICT + "='" + pres_district + "'," + Keys.KEY_PRES_STATE + "=" + pres_state + "," + Keys.KEY_PRES_COUNTRY + "=" + pres_country + "," + Keys.KEY_ADDR_SAME + "=" + addr_same + "," + Keys.KEY_PERM_ADDR1 + "='" + perm_addr_one + "'," + Keys.KEY_PERM_ADDR2 + "='" + perm_addr_two + "'," + Keys.KEY_PERM_VILLAGE + "='" + perm_village + "'," + Keys.KEY_PERM_PANCHAYAT + "='" + perm_panchayat + "'," + Keys.KEY_PERM_TALUK + "='" + perm_taluk + "'," + Keys.KEY_PERM_PINCODE + "='" + perm_pincode + "'," + Keys.KEY_PERM_DISTRICT + "='" + perm_district + "'," + Keys.KEY_PERM_STATE + "=" + perm_state + "," + Keys.KEY_PERM_COUNTRY + "=" + perm_country + "," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateRegCandSocial(int rowId, int cand_stage, int reg_complete, String aadhaar_no, int id_type, String id_num,
                                    String id_copy, String latitude, String longitude, String timestamp, String aadhaarCopy,
                                    String ednCopy) {
        this.open();
        Log.e("updateRegCandSocial id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," + Keys.KEY_AADHAAR_NO + "='" + aadhaar_no + "'," +
                Keys.KEY_ID_TYPE + "=" + id_type + "," + Keys.KEY_ID_NUM + "='" + id_num + "'," + Keys.KEY_ID_COPY +
                "='" + id_copy + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" +
                longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," + Keys.KEY_AADHAAR_COPY + "='" +
                aadhaarCopy + "',"+Keys.KEY_DELL_EDN_PIC+"='"+ednCopy+"'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateRegCandWork(int rowId, int cand_stage, int reg_complete, String emp_type, String pref_job, String yrs_exp, String rel_exp, String last_ctc, String pref_loc, String will_travel, String work_shift, String bocw_id, String expect_ctc, String latitude, String longitude, String timestamp) {
        this.open();
        Log.e("updateRegCandWork id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," + Keys.KEY_EMP_TYPE + "='" + emp_type + "'," + Keys.KEY_PREF_JOB + "='" + pref_job + "'," + Keys.KEY_YRS_EXP + "='" + yrs_exp + "'," + Keys.KEY_REL_EXP + "='" + rel_exp + "'," + Keys.KEY_LAST_CTC + "='" + last_ctc + "'," + Keys.KEY_PREF_LOC + "='" + pref_loc + "'," + Keys.KEY_WILL_TRAVEL + "='" + will_travel + "'," + Keys.KEY_WORK_SHIFT + "='" + work_shift + "'," + Keys.KEY_BOCW_ID + "='" + bocw_id + "'," + Keys.KEY_EXPECT_CTC + "='" + expect_ctc + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrCandEdn(int rowId, int cand_stage, int re_enr_complete, String high_quali, String edn_stream, String insti_name, String university, String yr_pass, String percentage, String comp_know, String tech_know, String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrCandEdn id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_HIGH_QUALI + "='" + high_quali + "'," + Keys.KEY_EDN_STREAM + "='" + edn_stream + "'," + Keys.KEY_INSTI_NAME + "='" + insti_name + "'," + Keys.KEY_UNIVERSITY + "='" + university + "'," + Keys.KEY_YR_PASS + "='" + yr_pass + "'," + Keys.KEY_PERCENTAGE + "='" + percentage + "'," + Keys.KEY_COMP_KNOW + "='" + comp_know + "'," + Keys.KEY_TECH_KNOW + "='" + tech_know + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateEnrCandEdn(int rowId, int cand_stage, int enr_complete, String high_quali, String edn_stream, String insti_name, String university, String yr_pass, String percentage, String comp_know, String tech_know, String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateEnrCandEdn id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_HIGH_QUALI + "='" + high_quali + "'," + Keys.KEY_EDN_STREAM + "='" + edn_stream + "'," + Keys.KEY_INSTI_NAME + "='" + insti_name + "'," + Keys.KEY_UNIVERSITY + "='" + university + "'," + Keys.KEY_YR_PASS + "='" + yr_pass + "'," + Keys.KEY_PERCENTAGE + "='" + percentage + "'," + Keys.KEY_COMP_KNOW + "='" + comp_know + "'," + Keys.KEY_TECH_KNOW + "='" + tech_know + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrCandFamily(int rowId, int cand_stage, int re_enr_complete, String mem_saltn, String mem_name, String mem_dob, String mem_age, String mem_cont, String mem_email, String mem_gender, String mem_edn, String mem_relation, String mem_occuptn, String house_income, String latitude, String longitude, String timestamp,String anualincome) {

        this.open();
        Log.e("updateReEnrCandFam id", "" + anualincome);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_MEM_SALTN + "='" + mem_saltn + "'," + Keys.KEY_MEM_NAME + "='" + mem_name + "'," + Keys.KEY_MEM_DOB + "='" + mem_dob + "'," + Keys.KEY_MEM_AGE + "='" + mem_age + "'," + Keys.KEY_MEM_CONT + "='" + mem_cont + "'," + Keys.KEY_MEM_EMAIL + "='" + mem_email + "'," + Keys.KEY_MEM_GENDER + "='" + mem_gender + "'," + Keys.KEY_MEM_EDN + "='" + mem_edn + "'," + Keys.KEY_MEM_RELATION + "='" + mem_relation + "'," + Keys.KEY_MEM_OCCUPTN + "='" + mem_occuptn + "'," + Keys.KEY_HOUSE_INCOME + "='" + house_income + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," +Keys.KEY_MEM_INCOME+"='"+anualincome+"'"+ " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateEnrCandFamily(int rowId, int cand_stage, int enr_complete, String mem_saltn, String mem_name, String mem_dob, String mem_age, String mem_cont, String mem_email, String mem_gender, String mem_edn, String mem_relation, String mem_occuptn, String house_income, String latitude, String longitude, String timestamp,String memberanual) {

        this.open();
        Log.e("updateEnrCandFamily id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_MEM_SALTN + "='" + mem_saltn + "'," + Keys.KEY_MEM_NAME + "='" + mem_name + "'," + Keys.KEY_MEM_DOB + "='" + mem_dob + "'," + Keys.KEY_MEM_AGE + "='" + mem_age + "'," + Keys.KEY_MEM_CONT + "='" + mem_cont + "'," + Keys.KEY_MEM_EMAIL + "='" + mem_email + "'," + Keys.KEY_MEM_GENDER + "='" + mem_gender + "'," + Keys.KEY_MEM_EDN + "='" + mem_edn + "'," + Keys.KEY_MEM_RELATION + "='" + mem_relation + "'," + Keys.KEY_MEM_OCCUPTN + "='" + mem_occuptn + "'," + Keys.KEY_HOUSE_INCOME + "='" + house_income + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," +Keys.KEY_MEM_INCOME+"='"+memberanual+"'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrCandBank(int rowId, int cand_stage, int re_enr_complete, String bank_name, String branch_name, String ifsc_code, String acc_type, String acc_num, String bank_copy, String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrCandBank id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_BANK_NAME + "='" + bank_name + "'," + Keys.KEY_BRANCH_NAME + "='" + branch_name + "'," + Keys.KEY_IFSC_CODE + "='" + ifsc_code + "'," + Keys.KEY_ACC_TYPE + "='" + acc_type + "'," + Keys.KEY_ACC_NUM + "='" + acc_num + "'," + Keys.KEY_BANK_COPY + "='" + bank_copy + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateEnrCandBank(int rowId, int cand_stage, int enr_complete, String bank_name, String branch_name, String ifsc_code, String acc_type, String acc_num, String bank_copy, String latitude, String longitude, String timestamp, int resultPass) {

        this.open();
        Log.e("updateEnrCandBank id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," + Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_BANK_NAME + "='" + bank_name + "'," + Keys.KEY_BRANCH_NAME + "='" + branch_name + "'," + Keys.KEY_IFSC_CODE + "='" + ifsc_code + "'," + Keys.KEY_ACC_TYPE + "='" + acc_type + "'," + Keys.KEY_ACC_NUM + "='" + acc_num + "'," + Keys.KEY_BANK_COPY + "='" + bank_copy + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'," + Keys.KEY_RESULT_PASS + "=" + resultPass + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateEnrMclShe3(int rowId, int cand_stage, int enr_complete,
                                 String edn_pic, String age_pic, String mou_pic, String date_mou, String date_kit,
                                 String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateEnrMclShe3 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage +
                "," + Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_SHE_EDN_PIC + "='" + edn_pic + "'," +
                Keys.KEY_AGE_PIC + "='" +
                age_pic + "'," + Keys.KEY_MOU_PIC + "='" + mou_pic + "'," + Keys.KEY_DATE_MOU + "='" +
                date_mou + "'," + Keys.KEY_DATE_KIT + "='" + date_kit + "'," + Keys.KEY_LATITUDE + "='" +
                latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" +
                timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }
    public void updateEnrMclShe2(int rowId, int cand_stage, int enr_complete, String ast_tv, String ast_fridge,
                                 String ast_wash, String ast_ac, String ast_car, String ast_med_ins, String ast_life_ins,
                                 String ast_kids_edn, String house_size, String ast_others,
                                 String own_house,
                                 String ration_card, String ast_farm_land, String farm_acre,
                                 String addr_adhar,  String member_count, String headHouse,
                                 String latitude,     String longitude, String timestamp) {

        this.open();
        Log.e("updateEnrMclShe2 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_AST_TV + "='" + ast_tv + "'," +
                Keys.KEY_AST_FRIDGE + "='" + ast_fridge + "'," + Keys.KEY_AST_WASH + "='" + ast_wash + "'," +
                Keys.KEY_AST_AC + "='" + ast_ac + "'," + Keys.KEY_AST_CAR + "='" + ast_car + "'," +
                Keys.KEY_AST_MED_INS + "='" + ast_med_ins + "'," + Keys.KEY_AST_LIFE_INS + "='" + ast_life_ins + "'," +
                Keys.KEY_AST_KIDS_EDN + "='" + ast_kids_edn + "'," + Keys.KEY_HOUSE_SIZE + "='" + house_size + "'," +
                Keys.KEY_AST_OTHERS + "='" + ast_others + "'," +
                Keys.KEY_OWN_HOUSE + "='" + own_house + "'," +
                Keys.KEY_RATION_CARD + "='" + ration_card + "'," + Keys.KEY_AST_FARM_LAND + "='" + ast_farm_land + "'," +
                Keys.KEY_FARM_ACRE + "='" + farm_acre + "',"
             + Keys.KEY_ADDR_ADHAR + "='" + addr_adhar + "'," +
                Keys.KEY_MEMBER_COUNT + "='" + member_count + "'," +
               Keys.KEY_HEAD_HOUSE + "='" + headHouse + "'," +
                 Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrMclShe3(int rowId, int cand_stage, int re_enr_complete,
                                 String edn_pic, String age_pic, String mou_pic, String date_mou, String date_kit,
                                 String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrMclShe3 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage +
                "," + Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_SHE_EDN_PIC + "='" + edn_pic + "'," +
                Keys.KEY_AGE_PIC + "='" +
                age_pic + "'," + Keys.KEY_MOU_PIC + "='" + mou_pic + "'," + Keys.KEY_DATE_MOU + "='" +
                date_mou + "'," + Keys.KEY_DATE_KIT + "='" + date_kit + "'," + Keys.KEY_LATITUDE + "='" +
                latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" +
                timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }
    public void updateReEnrMclShe2(int rowId, int cand_stage, int re_enr_complete, String ast_tv, String ast_fridge,
                                 String ast_wash, String ast_ac, String ast_car, String ast_med_ins, String ast_life_ins,
                                 String ast_kids_edn, String house_size, String ast_others,
                                 String own_house,
                                 String ration_card, String ast_farm_land, String farm_acre,
                                 String addr_adhar,  String member_count, String headHouse,
                                 String latitude,     String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrMclShe2 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," + Keys.KEY_AST_TV + "='" + ast_tv + "'," +
                Keys.KEY_AST_FRIDGE + "='" + ast_fridge + "'," + Keys.KEY_AST_WASH + "='" + ast_wash + "'," +
                Keys.KEY_AST_AC + "='" + ast_ac + "'," + Keys.KEY_AST_CAR + "='" + ast_car + "'," +
                Keys.KEY_AST_MED_INS + "='" + ast_med_ins + "'," + Keys.KEY_AST_LIFE_INS + "='" + ast_life_ins + "'," +
                Keys.KEY_AST_KIDS_EDN + "='" + ast_kids_edn + "'," + Keys.KEY_HOUSE_SIZE + "='" + house_size + "'," +
                Keys.KEY_AST_OTHERS + "='" + ast_others + "'," +
                Keys.KEY_OWN_HOUSE + "='" + own_house + "'," +
                Keys.KEY_RATION_CARD + "='" + ration_card + "'," + Keys.KEY_AST_FARM_LAND + "='" + ast_farm_land + "'," +
                Keys.KEY_FARM_ACRE + "='" + farm_acre + "',"
                + Keys.KEY_ADDR_ADHAR + "='" + addr_adhar + "'," +
                Keys.KEY_MEMBER_COUNT + "='" + member_count + "'," +
                Keys.KEY_HEAD_HOUSE + "='" + headHouse + "'," +
                Keys.KEY_LATITUDE + "='" + latitude + "'," + Keys.KEY_LONGITUDE + "='" + longitude + "'," +
                Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " + Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrMclShe1(int rowId, int cand_stage, int re_enr_complete,
                                 int result_pass,int mob_type,int score,String mcl_age,
                                 String read_local, String have_phone, String buy_phone,
                                 String have_bike, String operate_phone, String is_entre, String family_permit,
                                 String member_shg, String will_she, String online_training, String share_to_ln,
                                 String contract_ln, String buy_tools, String adopt_digital, String register_social,
                                 String loan_past,  String active_loan, String take_loan, String health_ins,
                                 String allergic_dust,   String wear_ppe, String follow_safety, String legal_enquiry,
                                 String pass8, String past_exp,String work_6hrs,String travel_place,String have_bank,
                                 String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrMclShe1 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," +
                Keys.KEY_RESULT_PASS + "=" + result_pass + "," + Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+
                Keys.KEY_MCL_SCORE+"="+score+","+Keys.KEY_MCL_AGE + "='" + mcl_age + "'," +
                Keys.KEY_READ_LOCAL + "='" + read_local + "'," +
                Keys.KEY_HAVE_PHONE + "='" + have_phone + "'," + Keys.KEY_BUY_PHONE + "='" + buy_phone + "'," +
                Keys.KEY_HAVE_BIKE + "='" + have_bike + "'," + Keys.KEY_OPERATE_PHONE + "='" + operate_phone +
                "'," + Keys.KEY_IS_ENTRE + "='" + is_entre + "'," + Keys.KEY_FAMILY_PERMIT + "='" + family_permit +
                "'," + Keys.KEY_MEMBER_SHG + "='" + member_shg + "'," + Keys.KEY_WILL_SHE + "='" + will_she + "',"
                + Keys.KEY_ONLINE_TRAINING + "='" + online_training + "'," + Keys.KEY_SHARE_TO_LN + "='" + share_to_ln +
                "'," + Keys.KEY_CONTRACT_LN + "='" + contract_ln + "'," + Keys.KEY_BUY_TOOLS + "='" + buy_tools + "'," +
                Keys.KEY_ADOPT_DIGITAL + "='" + adopt_digital + "'," + Keys.KEY_REGISTER_SOCIAL + "='" + register_social +
                "'," + Keys.KEY_LOAN_PAST + "='" + loan_past + "'," +
                Keys.KEY_ACTIVE_LOAN + "='" + active_loan + "'," + Keys.KEY_TAKE_LOAN + "='" + take_loan + "'," +
                Keys.KEY_HEALTH_INS + "='" + health_ins + "'," + Keys.KEY_ALLERGIC_DUST + "='" + allergic_dust + "'," +
                Keys.KEY_WEAR_PPE + "='" + wear_ppe + "'," + Keys.KEY_FOLLOW_SAFETY + "='" + follow_safety + "'," +
                Keys.KEY_LEGAL_ENQUIRY + "='" + legal_enquiry + "'," +
                Keys.KEY_PASS_8 + "='" + pass8 + "'," + Keys.KEY_PAST_EXP + "='" + past_exp + "'," +
                Keys.KEY_WORK_6HRS + "='" + work_6hrs + "'," + Keys.KEY_TRAVEL_PLACE + "='" + travel_place + "'," +
                Keys.KEY_HAVE_BANK + "='" + have_bank + "'," +  Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
                Keys.KEY_ID + "=" + rowId);
        this.close();
    }

    public void updateReEnrMclDell1(int rowId, int cand_stage, int re_enr_complete,
                                  int result_pass,int mob_type,int score,
                                  /*String ednPic,*/ String aspDistrict, String incomePic,
                                  String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateReEnrMclDell1 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_RE_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_RE_ENR_COMPLETE + "=" + re_enr_complete + "," +
                Keys.KEY_RESULT_PASS + "=" + result_pass + "," + Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+
                Keys.KEY_MCL_SCORE+"="+score+","+
               /* Keys.KEY_DELL_EDN_PIC + "='" + ednPic + "'," +*/
                Keys.KEY_ASP_DISTRICT + "='" + aspDistrict + "'," +
                Keys.KEY_INCOME_PROOF+ "='" + incomePic + "'," +  Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
                Keys.KEY_ID + "=" + rowId);
        this.close();
    }
    public void updateRegMclShe1(int rowId, int cand_stage, int reg_complete,
                                 int result_pass,int mob_type,int score,String mcl_age,
                                 String read_local, String have_phone, String buy_phone,
                                 String have_bike, String operate_phone, String is_entre, String family_permit,
                                 String member_shg, String will_she, String online_training, String share_to_ln,
                                 String contract_ln, String buy_tools, String adopt_digital, String register_social,
                                 String loan_past,  String active_loan, String take_loan, String health_ins,
                                 String allergic_dust,   String wear_ppe, String follow_safety, String legal_enquiry,
                                 String pass8, String past_exp,String work_6hrs,String travel_place,String have_bank,
                                 String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateRegMclShe1 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," +
                Keys.KEY_RESULT_PASS + "=" + result_pass + "," + Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+
                Keys.KEY_MCL_SCORE+"="+score+","+Keys.KEY_MCL_AGE + "='" + mcl_age + "'," +
                Keys.KEY_READ_LOCAL + "='" + read_local + "'," +
                Keys.KEY_HAVE_PHONE + "='" + have_phone + "'," + Keys.KEY_BUY_PHONE + "='" + buy_phone + "'," +
                Keys.KEY_HAVE_BIKE + "='" + have_bike + "'," + Keys.KEY_OPERATE_PHONE + "='" + operate_phone +
                "'," + Keys.KEY_IS_ENTRE + "='" + is_entre + "'," + Keys.KEY_FAMILY_PERMIT + "='" + family_permit +
                "'," + Keys.KEY_MEMBER_SHG + "='" + member_shg + "'," + Keys.KEY_WILL_SHE + "='" + will_she + "',"
                + Keys.KEY_ONLINE_TRAINING + "='" + online_training + "'," + Keys.KEY_SHARE_TO_LN + "='" + share_to_ln +
                "'," + Keys.KEY_CONTRACT_LN + "='" + contract_ln + "'," + Keys.KEY_BUY_TOOLS + "='" + buy_tools + "'," +
                Keys.KEY_ADOPT_DIGITAL + "='" + adopt_digital + "'," + Keys.KEY_REGISTER_SOCIAL + "='" + register_social +
                "'," + Keys.KEY_LOAN_PAST + "='" + loan_past + "'," +
                Keys.KEY_ACTIVE_LOAN + "='" + active_loan + "'," + Keys.KEY_TAKE_LOAN + "='" + take_loan + "'," +
                Keys.KEY_HEALTH_INS + "='" + health_ins + "'," + Keys.KEY_ALLERGIC_DUST + "='" + allergic_dust + "'," +
                Keys.KEY_WEAR_PPE + "='" + wear_ppe + "'," + Keys.KEY_FOLLOW_SAFETY + "='" + follow_safety + "'," +
                Keys.KEY_LEGAL_ENQUIRY + "='" + legal_enquiry + "'," +
                Keys.KEY_PASS_8 + "='" + pass8 + "'," +            Keys.KEY_PAST_EXP + "='" + past_exp + "'," +
                Keys.KEY_WORK_6HRS + "='" + work_6hrs + "'," +            Keys.KEY_TRAVEL_PLACE + "='" + travel_place + "'," +
                Keys.KEY_HAVE_BANK + "='" + have_bank + "'," +  Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
                Keys.KEY_ID + "=" + rowId);
        this.close();
    }
    public void updateRegMclBatVcn1(int rowId, int cand_stage, int reg_complete,
                                    int result_pass,int mob_type,int score,int job_role,String complete_bsc,
                                    String read_write_local, String read_write_english,
                                    String exp_ngo, String exp_vcn, String exp_hosp, String family_permit,
                                    String serve_vcn, String travel_phc, String online_training, String work_full,
                                    String report_data, String have_bank, String follow_safety,
                                    String latitude, String longitude, String timestamp) {
        this.open();
        Log.e("updateRegMclBatVcn1 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," + Keys.KEY_RESULT_PASS + "=" + result_pass + "," +
                Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+ Keys.KEY_MCL_SCORE+"="+score+","+
                Keys.KEY_JOB_ROLE_ID+"="+job_role+","+
                Keys.KEY_COMPLETE_BSC + "='" + complete_bsc + "'," + Keys.KEY_READ_WRITE_LOCAL + "='" + read_write_local + "'," +
                Keys.KEY_READ_ENGLISH + "='" + read_write_english + "'," + Keys.KEY_EXP_NGO + "='" + exp_ngo + "'," +
                Keys.KEY_EXP_VACCINE + "='" + exp_vcn +  "'," + Keys.KEY_EXP_HOSPITAL + "='" + exp_hosp + "'," +
                Keys.KEY_FAMILY_PERMIT + "='" + family_permit +"'," + Keys.KEY_SERVE_VACCINATOR + "='" + serve_vcn + "'," +
                Keys.KEY_TRAVEL_PHC + "='" + travel_phc + "'," + Keys.KEY_ONLINE_TRAINING + "='" + online_training + "'," +
                Keys.KEY_WORK_FULL_TIME + "='" + work_full + "'," + Keys.KEY_REPORT_VACCINE_DATA + "='" + report_data + "'," +
                Keys.KEY_HAVE_BANK + "='" + have_bank +  "'," + Keys.KEY_FOLLOW_SAFETY + "='" + follow_safety + "'," +
                Keys.KEY_LATITUDE + "='" + latitude + "'," +  Keys.KEY_LONGITUDE + "='" + longitude + "'," +
                Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
                Keys.KEY_ID + "=" + rowId);
        this.close();
    }
    public void updateRegMclBatDeo1(int rowId, int cand_stage, int reg_complete,
                                    int result_pass,int mob_type,int score,int job_role,String mcl_age,
                                    String pass_12th, String read_write_local, String read_write_english,
                                    String min_6exp, String workMsOfc, String understand_covid, String family_permit,
                                    String serve_deo, String travel_phc, String online_training, String work_full,
                                    String report_data, String have_bank, String follow_safety,
                                    String latitude, String longitude, String timestamp) {
        this.open();
        Log.e("updateRegMclBatDeo1 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," + Keys.KEY_RESULT_PASS + "=" + result_pass + "," +
                Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+ Keys.KEY_MCL_SCORE+"="+score+","+
                Keys.KEY_JOB_ROLE_ID+"="+job_role+","+ Keys.KEY_MCL_AGE + "='" + mcl_age + "'," +
                Keys.KEY_12_PASS + "='" + pass_12th + "'," + Keys.KEY_READ_WRITE_LOCAL + "='" + read_write_local + "'," +
                Keys.KEY_READ_ENGLISH + "='" + read_write_english + "'," + Keys.KEY_MIN_6_EXP + "='" + min_6exp + "'," +
                Keys.KEY_WORK_MS_OFFICE + "='" + workMsOfc +  "'," + Keys.KEY_UNDERSTAND_COVID + "='" + understand_covid + "'," +
                Keys.KEY_FAMILY_PERMIT + "='" + family_permit +"'," + Keys.KEY_SERVE_DEO + "='" + serve_deo + "'," +
                Keys.KEY_TRAVEL_PHC + "='" + travel_phc + "'," + Keys.KEY_ONLINE_TRAINING + "='" + online_training + "'," +
                Keys.KEY_WORK_FULL_TIME + "='" + work_full + "'," + Keys.KEY_REPORT_DEO_DATA + "='" + report_data + "'," +
                Keys.KEY_HAVE_BANK + "='" + have_bank +  "'," + Keys.KEY_FOLLOW_SAFETY + "='" + follow_safety + "'," +
                Keys.KEY_LATITUDE + "='" + latitude + "'," +  Keys.KEY_LONGITUDE + "='" + longitude + "'," +
                Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
                Keys.KEY_ID + "=" + rowId);
        this.close();
    }

public void updateRegMclBatMbl1(int rowId, int cand_stage, int reg_complete,
                             int result_pass,int mob_type,int score,int job_role,String mcl_age,
                             String pass_10th, String read_write_local, String read_write_english,
                             String min_6exp, String good_commtn, String understand_covid, String family_permit,
                             String serve_mbl, String travel_phc, String online_training, String work_full,
                             String report_data, String have_bank, String follow_safety,
                             String latitude, String longitude, String timestamp) {
    this.open();
    Log.e("updateRegMclBatMbl1 id", "" + rowId);
    db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
            Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," + Keys.KEY_RESULT_PASS + "=" + result_pass + "," +
            Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+ Keys.KEY_MCL_SCORE+"="+score+","+
            Keys.KEY_JOB_ROLE_ID+"="+job_role+","+ Keys.KEY_MCL_AGE + "='" + mcl_age + "'," +
           Keys.KEY_10_PASS + "='" + pass_10th + "'," + Keys.KEY_READ_WRITE_LOCAL + "='" + read_write_local + "'," +
            Keys.KEY_READ_ENGLISH + "='" + read_write_english + "'," + Keys.KEY_MIN_6_EXP + "='" + min_6exp + "'," +
            Keys.KEY_GOOD_COMMUNICATION + "='" + good_commtn +  "'," + Keys.KEY_UNDERSTAND_COVID + "='" + understand_covid + "'," +
            Keys.KEY_FAMILY_PERMIT + "='" + family_permit +"'," + Keys.KEY_SERVE_MOBILIZER + "='" + serve_mbl + "'," +
            Keys.KEY_TRAVEL_PHC + "='" + travel_phc + "'," + Keys.KEY_ONLINE_TRAINING + "='" + online_training + "'," +
            Keys.KEY_WORK_FULL_TIME + "='" + work_full + "'," + Keys.KEY_REPORT_MBL_DATA + "='" + report_data + "'," +
            Keys.KEY_HAVE_BANK + "='" + have_bank +  "'," + Keys.KEY_FOLLOW_SAFETY + "='" + follow_safety + "'," +
              Keys.KEY_LATITUDE + "='" + latitude + "'," +  Keys.KEY_LONGITUDE + "='" + longitude + "'," +
            Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
            Keys.KEY_ID + "=" + rowId);
    this.close();
}

    public void updateRegMclDell1(int rowId, int cand_stage, int reg_complete,
                                 int result_pass,int mob_type,int score,
                                  /*String ednPic,*/ String aspDistrict, String incomePic,
                                 String latitude, String longitude, String timestamp) {

        this.open();
        Log.e("updateRegMclDell1 id", "" + rowId);
        db.execSQL("UPDATE " + Keys.TBL_REG_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
                Keys.KEY_REG_COMPLETE + "=" + reg_complete + "," +
                Keys.KEY_RESULT_PASS + "=" + result_pass + "," + Keys.KEY_MOBILIZATION_TYPE+"="+mob_type+","+
                Keys.KEY_MCL_SCORE+"="+score+","+
            /*    Keys.KEY_DELL_EDN_PIC + "='" + ednPic + "'," +*/
                Keys.KEY_ASP_DISTRICT + "='" + aspDistrict + "'," +
                Keys.KEY_INCOME_PROOF+ "='" + incomePic + "'," +  Keys.KEY_LATITUDE + "='" + latitude + "'," +
                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
                Keys.KEY_ID + "=" + rowId);
        this.close();
    }
//    public void updateEnrMclShe1(int rowId, int cand_stage, int enr_complete, String addr_adhar,
//                                 String member_count, String read_local, String have_phone, String buy_phone,
//                                 String have_bike, String operate_phone, String is_entre, String family_permit,
//                                 String member_shg, String will_she, String online_training, String share_to_ln,
//                                 String contract_ln, String buy_tools, String adopt_digital, String register_social,
//                                 String headHouse, String latitude, String longitude, String timestamp) {
//
//        this.open();
//        Log.e("updateEnrMclShe1 id", "" + rowId);
//        db.execSQL("UPDATE " + Keys.TBL_ENR_CAND + " SET " + Keys.KEY_CAND_STAGE + "=" + cand_stage + "," +
//                Keys.KEY_ENR_COMPLETE + "=" + enr_complete + "," + Keys.KEY_ADDR_ADHAR + "='" + addr_adhar + "'," +
//                Keys.KEY_MEMBER_COUNT + "='" + member_count + "'," + Keys.KEY_READ_LOCAL + "='" + read_local + "'," +
//                Keys.KEY_HAVE_PHONE + "='" + have_phone + "'," + Keys.KEY_BUY_PHONE + "='" + buy_phone + "'," +
//                Keys.KEY_HAVE_BIKE + "='" + have_bike + "'," + Keys.KEY_OPERATE_PHONE + "='" + operate_phone +
//                "'," + Keys.KEY_IS_ENTRE + "='" + is_entre + "'," + Keys.KEY_FAMILY_PERMIT + "='" + family_permit +
//                "'," + Keys.KEY_MEMBER_SHG + "='" + member_shg + "'," + Keys.KEY_WILL_SHE + "='" + will_she + "',"
//                + Keys.KEY_ONLINE_TRAINING + "='" + online_training + "'," + Keys.KEY_SHARE_TO_LN + "='" + share_to_ln +
//                "'," + Keys.KEY_CONTRACT_LN + "='" + contract_ln + "'," + Keys.KEY_BUY_TOOLS + "='" + buy_tools + "'," +
//                Keys.KEY_ADOPT_DIGITAL + "='" + adopt_digital + "'," + Keys.KEY_REGISTER_SOCIAL + "='" + register_social +
//                "'," + Keys.KEY_HEAD_HOUSE + "='" + headHouse + "'," + Keys.KEY_LATITUDE + "='" + latitude + "'," +
//                Keys.KEY_LONGITUDE + "='" + longitude + "'," + Keys.KEY_TIMESTAMP + "='" + timestamp + "'" + " WHERE " +
//                Keys.KEY_ID + "=" + rowId);
//        this.close();
//    }



    public long addReEnrCandidates(int dbId, int userId, int cand_stage, int re_enr_complete, int is_fresher,
                                   String cand_pic, String cand_saltn, String first_name, String mid_name, String last_name,
                                   String cand_dob, int dob_entered, String cand_age, String primary_mob, int mob_verified,
                                   String sec_mob, String cand_email, String cand_gender, String marital_status,
                                   String cand_caste, String disable_status, String cand_religion, String mother_tongue,
                                   String cand_occuptn, String annual_income, String cand_source, String interest_course,
                                   String cand_product, String pres_addr_one, String pres_addr_two, String pres_village,
                                   String pres_panchayat, String pres_taluk, String pres_pincode, String pres_district,
                                   int pres_state, int pres_country, int addr_same, String perm_addr_one, String perm_addr_two,
                                   String perm_village, String perm_panchayat, String perm_taluk, String perm_pincode,
                                   String perm_district, int perm_state, int perm_country, String latitude, String longitude,
                                   String timestamp, int createdBy, int currentStatusId, int email_valid, String highQuali,
                                   String candStream, String instiName, String university, String yrPass, String percentage,
                                   String compKnow, String techKnow, String bankName, String branchName, String ifscCode,
                                   String accType, String accNum, String bankCopy, String houseIncome,String wa_num,
                                    int mob_type, int result_pass,int score,
                                   String mcl_age,
                                   String read_local, String have_phone, String buy_phone,
                                   String have_bike, String will_she,
                                   String contract_ln, String adopt_digital,
                                   String loan_past,  String active_loan, String take_loan, String health_ins,
                                   String allergic_dust,  String follow_safety, String legal_enquiry,
                                   String pass8, String past_exp,String work_6hrs,String travel_place,String have_bank,
                                   String ast_tv, String ast_fridge,
                                   String ast_wash, String ast_ac, String ast_car, String ast_med_ins, String ast_life_ins,
                                    String house_size, String ast_others,
                                   String own_house,
                                   String ration_card, String ast_farm_land, String farm_acre,
                                   String addr_adhar,  String member_count, String headHouse,
                                   String she_edn_pic, String age_pic, String mou_pic, String date_mou, String date_kit,
                                   /*String dellEdnPic,*/ String aspDistrict, String incomePic)
            throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_RE_ENR_CAND + " (" + Keys.KEY_ID + "," + Keys.KEY_USER_ID + "," +
                Keys.KEY_CAND_STAGE + "," + Keys.KEY_RE_ENR_COMPLETE + "," + Keys.KEY_IS_FRESHER + "," + Keys.KEY_CAND_PIC +
                "," + Keys.KEY_CAND_SALTN + "," + Keys.KEY_FIRST_NAME + "," + Keys.KEY_MID_NAME + "," + Keys.KEY_LAST_NAME +
                "," + Keys.KEY_CAND_DOB + "," + Keys.KEY_DOB_ENTERED + "," + Keys.KEY_CAND_AGE + "," + Keys.KEY_PRIMARY_MOB +
                "," + Keys.KEY_MOB_VERIFIED + "," + Keys.KEY_SEC_MOB + "," + Keys.KEY_CAND_EMAIL + "," + Keys.KEY_CAND_GENDER +
                "," + Keys.KEY_MARITAL_ST + "," + Keys.KEY_CAND_CASTE + "," + Keys.KEY_DISABLE_ST + "," + Keys.KEY_CAND_RELIGION
                + "," + Keys.KEY_MOTHER_TONGUE + "," + Keys.KEY_CAND_OCCUPTN + "," + Keys.KEY_ANNUAL_INCOME + "," +
                Keys.KEY_CAND_SOURCE + "," + Keys.KEY_INTEREST_COURSE + "," + Keys.KEY_CAND_PRODUCT + "," + Keys.KEY_PRES_ADDR1
                + "," + Keys.KEY_PRES_ADDR2 + "," + Keys.KEY_PRES_VILLAGE + "," + Keys.KEY_PRES_PANCHAYAT + "," +
                Keys.KEY_PRES_TALUK + "," + Keys.KEY_PRES_PINCODE + "," + Keys.KEY_PRES_DISTRICT + "," + Keys.KEY_PRES_STATE
                + "," + Keys.KEY_PRES_COUNTRY + "," + Keys.KEY_ADDR_SAME + "," + Keys.KEY_PERM_ADDR1 + "," + Keys.KEY_PERM_ADDR2
                + "," + Keys.KEY_PERM_VILLAGE + "," + Keys.KEY_PERM_PANCHAYAT + "," + Keys.KEY_PERM_TALUK + "," +
                Keys.KEY_PERM_PINCODE + "," + Keys.KEY_PERM_DISTRICT + "," + Keys.KEY_PERM_STATE + "," + Keys.KEY_PERM_COUNTRY
                + "," + Keys.KEY_LATITUDE + "," + Keys.KEY_LONGITUDE + "," + Keys.KEY_TIMESTAMP + "," + Keys.KEY_CREATED_BY +
                "," + Keys.KEY_ACTIVITY_STATUS_ID + "," + Keys.KEY_EMAIL_VALIDATE + "," + Keys.KEY_HIGH_QUALI + "," +
                Keys.KEY_EDN_STREAM + "," + Keys.KEY_INSTI_NAME + "," + Keys.KEY_UNIVERSITY + "," + Keys.KEY_YR_PASS + "," +
                Keys.KEY_PERCENTAGE + "," + Keys.KEY_COMP_KNOW + "," + Keys.KEY_TECH_KNOW + "," + Keys.KEY_BANK_NAME + "," +
                Keys.KEY_BRANCH_NAME + "," + Keys.KEY_IFSC_CODE + "," + Keys.KEY_ACC_TYPE + "," + Keys.KEY_ACC_NUM + "," +
                Keys.KEY_BANK_COPY + "," + Keys.KEY_HOUSE_INCOME+","+Keys.KEY_WA_NUM
                + ","+Keys.KEY_MOBILIZATION_TYPE+","+Keys.KEY_RESULT_PASS+","+Keys.KEY_MCL_SCORE+","
                +Keys.KEY_MCL_AGE +  "," +
                Keys.KEY_READ_LOCAL + "," +
                Keys.KEY_HAVE_PHONE + "," + Keys.KEY_BUY_PHONE +  "," +
                Keys.KEY_HAVE_BIKE + "," +  Keys.KEY_WILL_SHE + ","
               + Keys.KEY_CONTRACT_LN +"," +
                Keys.KEY_ADOPT_DIGITAL + "," + Keys.KEY_LOAN_PAST +  "," +
                Keys.KEY_ACTIVE_LOAN +  "," + Keys.KEY_TAKE_LOAN +  "," +
                Keys.KEY_HEALTH_INS +  "," + Keys.KEY_ALLERGIC_DUST + "," +
                Keys.KEY_FOLLOW_SAFETY +"," +
                Keys.KEY_LEGAL_ENQUIRY + "," +
                Keys.KEY_PASS_8 + "," +            Keys.KEY_PAST_EXP + "," +
                Keys.KEY_WORK_6HRS +  "," +            Keys.KEY_TRAVEL_PLACE +  "," +
                Keys.KEY_HAVE_BANK +","
                + Keys.KEY_AST_TV + "," +
                Keys.KEY_AST_FRIDGE +  "," + Keys.KEY_AST_WASH +  "," +
                Keys.KEY_AST_AC +  "," + Keys.KEY_AST_CAR +"," +
                Keys.KEY_AST_MED_INS +  "," + Keys.KEY_AST_LIFE_INS + "," +
                Keys.KEY_HOUSE_SIZE +  "," +
                Keys.KEY_AST_OTHERS +  "," +
                Keys.KEY_OWN_HOUSE + "," +
                Keys.KEY_RATION_CARD +  "," + Keys.KEY_AST_FARM_LAND +  "," +
                Keys.KEY_FARM_ACRE +  ","
                + Keys.KEY_ADDR_ADHAR +"," +
                Keys.KEY_MEMBER_COUNT + "," +
                Keys.KEY_HEAD_HOUSE +","
                + Keys.KEY_SHE_EDN_PIC + "," +
                Keys.KEY_AGE_PIC +  "," + Keys.KEY_MOU_PIC + "," + Keys.KEY_DATE_MOU + "," + Keys.KEY_DATE_KIT +","+
               /* Keys.KEY_DELL_EDN_PIC +  "," +*/
                Keys.KEY_ASP_DISTRICT +  "," +
                Keys.KEY_INCOME_PROOF
                + ") VALUES(" + dbId + "," + userId + "," + cand_stage + "," +
                re_enr_complete + "," + is_fresher + ",'" + cand_pic + "','" + cand_saltn + "','" + first_name + "','" +
                mid_name + "','" + last_name + "','" + cand_dob + "'," + dob_entered + ",'" + cand_age + "','" + primary_mob +
                "'," + mob_verified + ",'" + sec_mob + "','" + cand_email + "','" + cand_gender + "','" + marital_status + "','"
                + cand_caste + "','" + disable_status + "','" + cand_religion + "','" + mother_tongue + "','" + cand_occuptn +
                "','" + annual_income + "','" + cand_source + "','" + interest_course + "','" + cand_product + "','" +
                pres_addr_one + "','" + pres_addr_two + "','" + pres_village + "','" + pres_panchayat + "','" + pres_taluk +
                "','" + pres_pincode + "','" + pres_district + "'," + pres_state + "," + pres_country + "," + addr_same +
                ",'" + perm_addr_one + "','" + perm_addr_two + "','" + perm_village + "','" + perm_panchayat + "','" +
                perm_taluk + "','" + perm_pincode + "','" + perm_district + "'," + perm_state + "," + perm_country + ",'" +
                latitude + "','" + longitude + "','" + timestamp + "'," + createdBy + "," + currentStatusId + "," +
                email_valid + ",'" + highQuali + "','" + candStream + "','" + instiName + "','" + university + "','" + yrPass +
                "','" + percentage + "','" + compKnow + "','" + techKnow + "','" + bankName + "','" + branchName + "','" +
                ifscCode + "','" + accType + "','" + accNum + "','" + bankCopy + "','" + houseIncome+"','"+wa_num
                +"',"+mob_type+","+result_pass+","+score
        +",'"+ mcl_age+"','"+ read_local +"','"+ have_phone +"','"+ buy_phone
                +"','"+ have_bike +"','"+ will_she +"','"+ contract_ln +"','"+  adopt_digital +"','"+
                loan_past +"','"+ active_loan +"','"+ take_loan +"','"+ health_ins
                +"','"+ allergic_dust +"','"+  follow_safety +"','"+ legal_enquiry
                +"','"+ pass8 +"','"+ past_exp +"','"+ work_6hrs +"','"+ travel_place +"','"+ have_bank
                +"','"+ ast_tv +"','"+ ast_fridge
                +"','"+ ast_wash +"','"+ ast_ac +"','"+ ast_car +"','"+ ast_med_ins +"','"+ ast_life_ins
                +"','"+ house_size +"','"+ ast_others
                +"','"+ own_house
                +"','"+ ration_card +"','"+ ast_farm_land +"','"+ farm_acre
                +"','"+ addr_adhar  +"','"+ member_count +"','"+ headHouse
                +"','"+ she_edn_pic +"','"+ age_pic +"','"+ mou_pic +"','"+ date_mou +"','"+ date_kit
                +"','"+ /*dellEdnPic +"','"+*/ aspDistrict +"','"+ incomePic+"'"
                + ")";
        Log.e(TAG, qInsert);
        return this.insert(qInsert);
    }

    public long addEnrCandidates(int dbId, int userId, int cand_stage, int enr_complete, int is_fresher, String cand_pic,
                                 String cand_saltn, String first_name, String mid_name, String last_name, String cand_dob,
                                 int dob_entered, String cand_age, String primary_mob, int mob_verified, String sec_mob,
                                 String cand_email, String cand_gender, String marital_status, String cand_caste,
                                 String disable_status, String cand_religion, String mother_tongue, String cand_occuptn,
                                 String annual_income, String cand_source, String interest_course, String cand_product,
                                 String pres_addr_one, String pres_addr_two, String pres_village, String pres_panchayat,
                                 String pres_taluk, String pres_pincode, String pres_district, int pres_state, int pres_country,
                                 int addr_same, String perm_addr_one, String perm_addr_two, String perm_village,
                                 String perm_panchayat, String perm_taluk, String perm_pincode, String perm_district,
                                 int perm_state, int perm_country, String latitude, String longitude, String timestamp,
                                 int createdBy, int currentStatusId, int email_valid, int mob_type, String haveBank,String wa_num)
            throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_ENR_CAND + " (" + Keys.KEY_ID + "," + Keys.KEY_USER_ID + "," +
                Keys.KEY_CAND_STAGE + "," + Keys.KEY_ENR_COMPLETE + "," + Keys.KEY_IS_FRESHER + "," + Keys.KEY_CAND_PIC + "," +
                Keys.KEY_CAND_SALTN + "," + Keys.KEY_FIRST_NAME + "," + Keys.KEY_MID_NAME + "," + Keys.KEY_LAST_NAME + "," +
                Keys.KEY_CAND_DOB + "," + Keys.KEY_DOB_ENTERED + "," + Keys.KEY_CAND_AGE + "," + Keys.KEY_PRIMARY_MOB + "," +
                Keys.KEY_MOB_VERIFIED + "," + Keys.KEY_SEC_MOB + "," + Keys.KEY_CAND_EMAIL + "," + Keys.KEY_CAND_GENDER + "," +
                Keys.KEY_MARITAL_ST + "," + Keys.KEY_CAND_CASTE + "," + Keys.KEY_DISABLE_ST + "," + Keys.KEY_CAND_RELIGION +
                "," + Keys.KEY_MOTHER_TONGUE + "," + Keys.KEY_CAND_OCCUPTN + "," + Keys.KEY_ANNUAL_INCOME + "," +
                Keys.KEY_CAND_SOURCE + "," + Keys.KEY_INTEREST_COURSE + "," + Keys.KEY_CAND_PRODUCT + "," + Keys.KEY_PRES_ADDR1
                + "," + Keys.KEY_PRES_ADDR2 + "," + Keys.KEY_PRES_VILLAGE + "," + Keys.KEY_PRES_PANCHAYAT + "," +
                Keys.KEY_PRES_TALUK + "," + Keys.KEY_PRES_PINCODE + "," + Keys.KEY_PRES_DISTRICT + "," + Keys.KEY_PRES_STATE +
                "," + Keys.KEY_PRES_COUNTRY + "," + Keys.KEY_ADDR_SAME + "," + Keys.KEY_PERM_ADDR1 + "," + Keys.KEY_PERM_ADDR2 +
                "," + Keys.KEY_PERM_VILLAGE + "," + Keys.KEY_PERM_PANCHAYAT + "," + Keys.KEY_PERM_TALUK + "," +
                Keys.KEY_PERM_PINCODE + "," + Keys.KEY_PERM_DISTRICT + "," + Keys.KEY_PERM_STATE + "," + Keys.KEY_PERM_COUNTRY
                + "," + Keys.KEY_LATITUDE + "," + Keys.KEY_LONGITUDE + "," + Keys.KEY_TIMESTAMP + "," + Keys.KEY_CREATED_BY +
                "," + Keys.KEY_ACTIVITY_STATUS_ID + "," + Keys.KEY_EMAIL_VALIDATE + "," + Keys.KEY_MOBILIZATION_TYPE + ","+
                Keys.KEY_HAVE_BANK+","+Keys.KEY_WA_NUM+
                ") VALUES(" + dbId + "," + userId + "," + cand_stage + "," + enr_complete + "," + is_fresher + ",'" +
                cand_pic + "','" + cand_saltn + "','" + first_name + "','" + mid_name + "','" + last_name + "','" + cand_dob
                + "'," + dob_entered + ",'" + cand_age + "','" + primary_mob + "'," + mob_verified + ",'" + sec_mob + "','" +
                cand_email + "','" + cand_gender + "','" + marital_status + "','" + cand_caste + "','" + disable_status + "','"
                + cand_religion + "','" + mother_tongue + "','" + cand_occuptn + "','" + annual_income + "','" + cand_source +
                "','" + interest_course + "','" + cand_product + "','" + pres_addr_one + "','" + pres_addr_two + "','" +
                pres_village + "','" + pres_panchayat + "','" + pres_taluk + "','" + pres_pincode + "','" + pres_district +
                "'," + pres_state + "," + pres_country + "," + addr_same + ",'" + perm_addr_one + "','" + perm_addr_two +
                "','" + perm_village + "','" + perm_panchayat + "','" + perm_taluk + "','" + perm_pincode + "','" +
                perm_district + "'," + perm_state + "," + perm_country + ",'" + latitude + "','" + longitude + "','" +
                timestamp + "'," + createdBy + "," + currentStatusId + "," + email_valid + "," + mob_type +",'"+haveBank+
                "','"+wa_num+"')";
        Log.e(TAG, qInsert);
        return this.insert(qInsert);
    }

    public long addRegCandidates(int dbId, int userId, int cand_stage, int reg_complete, int is_fresher,
                                 String cand_pic, String cand_saltn, String first_name, String mid_name,
                                 String last_name, String cand_dob, int dob_entered, String cand_age,
                                 String primary_mob, int mob_verified, String sec_mob, String cand_email,
                                 String cand_gender, String marital_status, String cand_caste, String disable_status,
                                 String cand_religion, String mother_tongue, String cand_occuptn, String annual_income,
                                 String cand_source, String interest_course, String cand_product, String latitude,
                                 String longitude, String timestamp, int createdBy, int currentStatusId, int pres_state,
                                 int perm_state, int email_valid,int mob_type, int result_pass,int score,String wa_num)
            throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_REG_CAND + " (" + Keys.KEY_ID + "," + Keys.KEY_USER_ID + "," +
                Keys.KEY_CAND_STAGE + "," + Keys.KEY_REG_COMPLETE + "," + Keys.KEY_IS_FRESHER + "," + Keys.KEY_CAND_PIC +
                "," + Keys.KEY_CAND_SALTN + "," + Keys.KEY_FIRST_NAME + "," + Keys.KEY_MID_NAME + "," + Keys.KEY_LAST_NAME +
                "," + Keys.KEY_CAND_DOB + "," + Keys.KEY_DOB_ENTERED + "," + Keys.KEY_CAND_AGE + "," + Keys.KEY_PRIMARY_MOB +
                "," + Keys.KEY_MOB_VERIFIED + "," + Keys.KEY_SEC_MOB + "," + Keys.KEY_CAND_EMAIL + "," + Keys.KEY_CAND_GENDER +
                "," + Keys.KEY_MARITAL_ST + "," + Keys.KEY_CAND_CASTE + "," + Keys.KEY_DISABLE_ST + "," +
                Keys.KEY_CAND_RELIGION + "," + Keys.KEY_MOTHER_TONGUE + "," + Keys.KEY_CAND_OCCUPTN + "," +
                Keys.KEY_ANNUAL_INCOME + "," + Keys.KEY_CAND_SOURCE + "," + Keys.KEY_INTEREST_COURSE + "," +
                Keys.KEY_CAND_PRODUCT + "," + Keys.KEY_LATITUDE + "," + Keys.KEY_LONGITUDE + "," + Keys.KEY_TIMESTAMP +
                "," + Keys.KEY_CREATED_BY + "," + Keys.KEY_ACTIVITY_STATUS_ID + "," + Keys.KEY_PRES_STATE + "," +
                Keys.KEY_PERM_STATE + "," + Keys.KEY_EMAIL_VALIDATE + ","+Keys.KEY_MOBILIZATION_TYPE+","+
                Keys.KEY_RESULT_PASS+","+Keys.KEY_MCL_SCORE+","+Keys.KEY_WA_NUM+
                ") VALUES(" + dbId + "," + userId + "," +
                cand_stage + "," + reg_complete + "," + is_fresher + ",'" + cand_pic + "','" + cand_saltn + "','" +
                first_name + "','" + mid_name + "','" + last_name + "','" + cand_dob + "'," + dob_entered + ",'" +
                cand_age + "','" + primary_mob + "'," + mob_verified + ",'" + sec_mob + "','" + cand_email + "','" +
                cand_gender + "','" + marital_status + "','" + cand_caste + "','" + disable_status + "','" +
                cand_religion + "','" + mother_tongue + "','" + cand_occuptn + "','" + annual_income + "','" +
                cand_source + "','" + interest_course + "','" + cand_product + "','" + latitude + "','" + longitude +
                "','" + timestamp + "'," + createdBy + "," + currentStatusId + "," + pres_state + "," + perm_state +
                "," + email_valid +","+mob_type+","+result_pass+","+score+",'"+wa_num+ "')";
        Log.e(TAG, qInsert);
        return this.insert(qInsert);
    }

    public long addMblCandidates(int userId, int cand_stage, int mbl_complete, int is_fresher, String cand_pic,
                                 String cand_saltn, String first_name, String mid_name, String last_name, String cand_dob,
                                 int dob_entered, String cand_age, String primary_mob, int mob_verified, String sec_mob,
                                 String cand_email, String cand_gender, String marital_status, String cand_caste,
                                 String disable_status, String cand_religion, String mother_tongue, String cand_occuptn,
                                 String annual_income, String cand_source, String interest_course, String cand_product,
                                 String latitude, String longitude, String timestamp, int email_valid,String wa_num)
            throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_MBL_CAND + " (" + Keys.KEY_USER_ID + "," + Keys.KEY_CAND_STAGE +
                "," + Keys.KEY_MBL_COMPLETE + "," + Keys.KEY_IS_FRESHER + "," + Keys.KEY_CAND_PIC + "," + Keys.KEY_CAND_SALTN
                + "," + Keys.KEY_FIRST_NAME + "," + Keys.KEY_MID_NAME + "," + Keys.KEY_LAST_NAME + "," + Keys.KEY_CAND_DOB +
                "," + Keys.KEY_DOB_ENTERED + "," + Keys.KEY_CAND_AGE + "," + Keys.KEY_PRIMARY_MOB + "," + Keys.KEY_MOB_VERIFIED
                + "," + Keys.KEY_SEC_MOB + "," + Keys.KEY_CAND_EMAIL + "," + Keys.KEY_CAND_GENDER + "," + Keys.KEY_MARITAL_ST +
                "," + Keys.KEY_CAND_CASTE + "," + Keys.KEY_DISABLE_ST + "," + Keys.KEY_CAND_RELIGION + "," +
                Keys.KEY_MOTHER_TONGUE + "," + Keys.KEY_CAND_OCCUPTN + "," + Keys.KEY_ANNUAL_INCOME + "," + Keys.KEY_CAND_SOURCE
                + "," + Keys.KEY_INTEREST_COURSE + "," + Keys.KEY_CAND_PRODUCT + "," + Keys.KEY_LATITUDE + "," +
                Keys.KEY_LONGITUDE + "," + Keys.KEY_TIMESTAMP + "," + Keys.KEY_EMAIL_VALIDATE+","+Keys.KEY_WA_NUM + ") VALUES(" + userId + "," +
                cand_stage + "," + mbl_complete + "," + is_fresher + ",'" + cand_pic + "','" + cand_saltn + "','" + first_name
                + "','" + mid_name + "','" + last_name + "','" + cand_dob + "'," + dob_entered + ",'" + cand_age + "','" +
                primary_mob + "'," + mob_verified + ",'" + sec_mob + "','" + cand_email + "','" + cand_gender + "','" +
                marital_status + "','" + cand_caste + "','" + disable_status + "','" + cand_religion + "','" + mother_tongue +
                "','" + cand_occuptn + "','" + annual_income + "','" + cand_source + "','" + interest_course + "','" +
                cand_product + "','" + latitude + "','" + longitude + "','" + timestamp + "'," + email_valid +",'"+wa_num+ "')";
        Log.e(TAG, qInsert);
        return this.insert(qInsert);
    }









    public boolean isCandSaved(int action, int dbId) {
//        Log.e(TAG, "isCandSaved " + dbId);
        String tbl_name;
        boolean result = false;
        if (action == Keys.ACTION_REGISTRATION) {
            tbl_name = Keys.TBL_REG_CAND;
        } else if (action == Keys.ACTION_ENROLMENT) {
            tbl_name = Keys.TBL_ENR_CAND;
        } else {
            tbl_name = Keys.TBL_RE_ENR_CAND;
        }
        String query = "SELECT * FROM " + tbl_name + " WHERE " + Keys.KEY_ID + "=" + dbId;
        this.open();
        Cursor handle = db.rawQuery(query, null);
        if (handle != null) {
            int row = handle.getCount();
            result = row > 0;
            handle.close();
        }
        this.close();
        return result;
    }

    public void deleteUploadedCand(int action, int userId) {
        Log.e(TAG, "deleteUploadedCand");
        String tbl_name, col_name;
        if (action == Keys.ACTION_MOBILIZATION) {
            tbl_name = Keys.TBL_MBL_CAND;
            col_name = Keys.KEY_MBL_COMPLETE;
        } else if (action == Keys.ACTION_REGISTRATION) {
            tbl_name = Keys.TBL_REG_CAND;
            col_name = Keys.KEY_REG_COMPLETE;
        } else if (action == Keys.ACTION_ENROLMENT) {
            tbl_name = Keys.TBL_ENR_CAND;
            col_name = Keys.KEY_ENR_COMPLETE;
        } else {
            tbl_name = Keys.TBL_RE_ENR_CAND;
            col_name = Keys.KEY_RE_ENR_COMPLETE;
        }
        this.open();
        db.execSQL("DELETE FROM " + tbl_name + " WHERE " + Keys.KEY_ID + " IN (SELECT " + Keys.KEY_ID + " FROM " + tbl_name + " WHERE " + Keys.KEY_USER_ID + "=" + userId + " AND " + col_name + "=1)");
        this.close();
    }
    public void deleteMultipleCand(int action, String dbId) {
        Log.e(TAG, "deleteMultipleCand");
        String tbl_name;
        if (action == Keys.ACTION_MOBILIZATION) {
            tbl_name = Keys.TBL_MBL_CAND;
        } else if (action == Keys.ACTION_REGISTRATION) {
            tbl_name = Keys.TBL_REG_CAND;
        } else if (action == Keys.ACTION_ENROLMENT) {
            tbl_name = Keys.TBL_ENR_CAND;
        } else {
            tbl_name = Keys.TBL_RE_ENR_CAND;
        }
        this.open();
        db.execSQL("DELETE FROM " + tbl_name + " WHERE " + Keys.KEY_ID +" IN ("+ dbId+")");
        this.close();
    }
    public void deleteOneCand(int action, int dbId) {
        Log.e(TAG, "deleteOneCand");
        String tbl_name;
        if (action == Keys.ACTION_MOBILIZATION) {
            tbl_name = Keys.TBL_MBL_CAND;
        } else if (action == Keys.ACTION_REGISTRATION) {
            tbl_name = Keys.TBL_REG_CAND;
        } else if (action == Keys.ACTION_ENROLMENT) {
            tbl_name = Keys.TBL_ENR_CAND;
        } else {
            tbl_name = Keys.TBL_RE_ENR_CAND;
        }
        this.open();
        db.execSQL("DELETE FROM " + tbl_name + " WHERE " + Keys.KEY_ID + "=" + dbId);
        this.close();
    }

    public void clearBatches() {
        Log.e(TAG, "clearBatches");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_BATCHES);
        this.close();
    }

    public void addBatches(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_BATCHES + " (" + Keys.KEY_ID + ", " + Keys.KEY_BATCH_CODE + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }




    public void clearSessionPlansModules() {
        Log.e(TAG, "clearSessionPlansModules");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_SESSION_PLANS);
        db.execSQL("DELETE FROM " + Keys.TBL_MODULES);
        this.close();
    }

    public void addSessionPlans(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_SESSION_PLANS + " (" + Keys.KEY_ID + ", " + Keys.KEY_SESSION_PLAN_NAME + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }

    public void addModules(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_MODULES + " (" + Keys.KEY_ID + ", " + Keys.KEY_SESSION_PLAN_ID + ", " + Keys.KEY_MODULE_NAME + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }

    public void clearContractors() {
        Log.e(TAG, "clearContractors");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_CONTRACTORS);
        this.close();
    }

    public void addContractors(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_CONTRACTORS + " (" + Keys.KEY_ID + ", " + Keys.KEY_CONTRACTOR_NAME + ", " + Keys.KEY_DEP_QUES_IDS + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }




    public void clearStateDistrict() {
        Log.e(TAG, "clearStateDistrict");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_STATES);
        db.execSQL("DELETE FROM " + Keys.TBL_DISTRICTS);
        this.close();
    }

    public void addStates(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_STATES + " (" + Keys.KEY_ID + ", " + Keys.KEY_STATE_NAME + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }

    public void addDistricts(String query) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_DISTRICTS + " (" + Keys.KEY_ID + ", " + Keys.KEY_STATE_ID + ", " + Keys.KEY_DISTRICT_NAME + ") VALUES " + query + ";";
        this.insert(qInsert);
        Log.e(TAG, qInsert);
    }











    public void addWFOps(int id, int ques_id, String text, int flag_dependency, int dep_ques_id) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_WF_OPS + " (" + Keys.KEY_ID + ", " + Keys.KEY_QUES_ID + ", " + Keys.KEY_OPS_TXT + ", " + Keys.KEY_FLAG_DEP + ", " + Keys.KEY_DEP_QUES_ID + ") VALUES(" + id + ", " + ques_id + ", '" + text + "', " + flag_dependency + ", " + dep_ques_id + ")";
        this.insert(qInsert);

    }

    public void clearSection() {
        Log.e(TAG, "clearSection");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_WF_QUES);
        db.execSQL("DELETE FROM " + Keys.TBL_WF_OPS);
        this.close();
    }

    public void addWFQues(int id, int flag_dependency, String text, int ques_type, int input_type, int length, int flag_mandatory, int counter, int child_ques_id, String img_url) throws Exception {
//Log.e(TAG,"addWFQues "+counter+text);
        final String qInsert = "INSERT INTO " + Keys.TBL_WF_QUES + " (" + Keys.KEY_ID + ", " + Keys.KEY_FLAG_DEP + ", " + Keys.KEY_QUES_TXT + ", " + Keys.KEY_QUES_TYPE + ", " + Keys.KEY_IP_TYPE + ", " + Keys.KEY_IP_LENGTH + ", " + Keys.KEY_FLAG_MAND + ", " + Keys.KEY_ROW_NUM + ", " + Keys.KEY_CHILD_QUES_ID + ", " + Keys.KEY_IMG_URL + ") VALUES(" + id + ", " + flag_dependency + ", '" + text + "', " + ques_type + ", " + input_type + ", " + length + ", " + flag_mandatory + ", " + counter + ", " + child_ques_id + ", '" + img_url + "')";
        this.insert(qInsert);

    }

    public void addProfOps(int id, int ques_id, String text, int flag_dependency, int dep_ques_id) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_PROF_OPS + " (" + Keys.KEY_ID + ", " + Keys.KEY_QUES_ID + ", " + Keys.KEY_OPS_TXT + ", " + Keys.KEY_FLAG_DEP + ", " + Keys.KEY_DEP_QUES_ID + ") VALUES(" + id + ", " + ques_id + ", '" + text + "', " + flag_dependency + ", " + dep_ques_id + ")";
        this.insert(qInsert);

    }

    public void clearBasicProf() {
        Log.e(TAG, "clearBasicProf");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_PROF_QUES);
        db.execSQL("DELETE FROM " + Keys.TBL_PROF_OPS);
        this.close();
    }

    public void addProfQues(int id, int flag_dependency, String text, int ques_type, int input_type, int length, int flag_mandatory, int child_ques_id, String img_url, int counter, int correct_op_id) throws Exception {

        final String qInsert = "INSERT INTO " + Keys.TBL_PROF_QUES + " (" + Keys.KEY_ID + ", " + Keys.KEY_FLAG_DEP + ", " + Keys.KEY_QUES_TXT + ", " + Keys.KEY_QUES_TYPE + ", " + Keys.KEY_IP_TYPE + ", " + Keys.KEY_IP_LENGTH + ", " + Keys.KEY_FLAG_MAND + ", " + Keys.KEY_CHILD_QUES_ID + ", " + Keys.KEY_IMG_URL + ", " + Keys.KEY_ROW_NUM + ", " + Keys.KEY_CORRECT_OP_ID + ") VALUES(" + id + ", " + flag_dependency + ", '" + text + "', " + ques_type + ", " + input_type + ", " + length + ", " + flag_mandatory + ", " + child_ques_id + ", '" + img_url + "', " + counter + ", " + correct_op_id + ")";
        this.insert(qInsert);

    }

    public void clearPracCenterCourse() {
        Log.e(TAG, "clearPracCenterCourse");
        this.open();
        db.execSQL("DELETE FROM " + Keys.TBL_PRACTICES);
        db.execSQL("DELETE FROM " + Keys.TBL_CENTERS);
        db.execSQL("DELETE FROM " + Keys.TBL_COURSES);
        this.close();
    }

    public void addPractices(int id, String name) throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_PRACTICES + " (" + Keys.KEY_ID + ", " + Keys.KEY_PRACTICE_NAME + ") VALUES(" + id + ", '" + name + "')";
        this.insert(qInsert);

    }

    public void addCourses(int id, int pracId, String name, int centerId) throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_COURSES + " (" + Keys.KEY_ID + ", " + Keys.KEY_PRAC_ID + ", " + Keys.KEY_COURSE_NAME + ", " + Keys.KEY_CENTER_ID + ") VALUES(" + id + ", " + pracId + ", '" + name + "', " + centerId + ")";
        this.insert(qInsert);

    }

    public void addCenters(int id, int userId, String name) throws Exception {
        final String qInsert = "INSERT INTO " + Keys.TBL_CENTERS + " (" + Keys.KEY_ID + ", " + Keys.KEY_USER_ID + ", " + Keys.KEY_CENTER_NAME + ") VALUES(" + id + ", " + userId + ", '" + name + "')";
        this.insert(qInsert);
    }

    /*TMA*/
    public Cursor getAssessor() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_assessor, null);
        return cursor;
    }

    public void addAssesor(String email, String password) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_assessor + "(email, password) VALUES('"+ email + "','" + password + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_assessor + "(email, password) VALUES('" + email + "','" + password + "' )";
        this.insert(qInsert);
    }

    public void deleteAssessor() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_assessor + "   ");
//        database.close();
        this.close();
    }

    public void addUserId(String id) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + UserID + "(id) VALUES('"+ id + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + UserID + "(id) VALUES('" + id + "' )";
        this.insert(qInsert);
    }

    public Cursor getUserId() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserID, null);
        return cursor;
    }

    public void deleteUserId() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + UserID + "   ");
//        database.close();
        this.close();
    }

    public void addRequestId(String id) {
//        SQLiteDatabase database = this.getWritableDatabase();
        try {
//            database.execSQL("INSERT INTO " + table_requestid + " (requestid) VALUES('" + id + "' )");
//            database.close();
            final String qInsert = "INSERT INTO " + table_requestid + " (requestid) VALUES('" + id + "' )";
            this.insert(qInsert);
        } catch (Exception e) {
            Log.e("DB-->", Log.getStackTraceString(e));
        }
    }


    public void addTempRequest(String id) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + temp_requestid + "(id) VALUES('"+ id + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + temp_requestid + "(id) VALUES('" + id + "' )";
        this.insert(qInsert);
    }

    public Cursor getTempRequestId() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + temp_requestid, null);
        return cursor;
    }

    public void deleteTempRequestId() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + temp_requestid + "   ");
//        database.close();
        this.close();
    }

    public void AddMode(int mode) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_mode + "(mode) VALUES(" + mode + " )");
        final String qInsert = "INSERT INTO " + table_mode + "(mode) VALUES(" + mode + " )";
        this.insert(qInsert);
    }

    public void deleteMode() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_mode + "   ");
        db.close();
    }

    public Cursor getMode() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_mode, null);
        return cursor;
    }

    public boolean getModeStatus() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_mode, null);

        return cursor.moveToNext();

    }

    public void addRequestId(String id, String status) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_requestid + "(requestid, status) VALUES('"+ id + "','" + status + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_requestid + "(requestid, status) VALUES('" + id + "','" + status + "' )";
        this.insert(qInsert);
    }

    public void addRequestId(String requestid, String batchid, String stage, String batchStatus, String dateOfAssessment, String paymentStatus, String remainDays, String initiatePayment, String location, String status, String email, String ratePerBatch, String assessorId, String isPaymentInitiated, String isBetaUser) {
//        SQLiteDatabase database = this.getWritableDatabase();
        try {
            Log.e("DB----->", "addRequestId: " + requestid);
//            database.execSQL("INSERT INTO " + table_requestid + "(requestid, batchid, stage, batchStatus, dateOfAssessment, " +
//                    "paymentStatus, remainDays, initiatePayment, location, status, email, ratePerBatch, assessor_id , " +
//                    "isPaymentInitiated , isBetaUser) VALUES('" + requestid + "', '" + batchid + "', '" + stage + "', '" +
//                    batchStatus + "', '" + dateOfAssessment + "', '" + paymentStatus + "', '" + remainDays + "', '" +
//                    initiatePayment + "','" + location+ "','" + status + "','"+ email+"','"+ ratePerBatch+"','"+ assessorId+
//                    "','"+ isPaymentInitiated+"','"+isBetaUser+"' )");
//            database.close();
            final String qInsert = "INSERT INTO " + table_requestid + "(requestid, batchid, stage, batchStatus, dateOfAssessment, " + "paymentStatus, remainDays, initiatePayment, location, status, email, ratePerBatch, assessor_id , " + "isPaymentInitiated , isBetaUser) VALUES('" + requestid + "', '" + batchid + "', '" + stage + "', '" + batchStatus + "', '" + dateOfAssessment + "', '" + paymentStatus + "', '" + remainDays + "', '" + initiatePayment + "','" + location + "','" + status + "','" + email + "','" + ratePerBatch + "','" + assessorId + "','" + isPaymentInitiated + "','" + isBetaUser + "' )";
            this.insert(qInsert);
        } catch (Exception e) {
            //database.execSQL("UPDATE " + table_requestid + " SET  requestid = '" + requestid + "' ");
            Log.e("DB-->", Log.getStackTraceString(e));
        }

    }

    public void updateRequestId(String requestid, String batchid, String stage, String batchStatus, String dateOfAssessment, String paymentStatus, String remainDays, String initiatePayment, String location, String status, String email, String ratePerBatch, String assessorId, String isPaymentInitiated, String isBetaUser) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        try {
            Log.e("DB----->", "addRequestId: " + requestid);
            db.execSQL("UPDATE " + table_requestid + " SET stage = '" + stage + "', batchStatus ='" + batchStatus + "', dateOfAssessment = '" + dateOfAssessment + "', paymentStatus = '" + paymentStatus + "', remainDays = '" + remainDays + "', initiatePayment = '" + initiatePayment + "', location = '" + location + "', email='" + email + "', ratePerBatch='" + ratePerBatch + "', isPaymentInitiated='" + isPaymentInitiated + "' , isBetaUser='" + isBetaUser + "'  WHERE requestid='" + requestid + "' AND email='" + email + "' AND assessor_id='" + assessorId + "'");
            this.close();
        } catch (Exception e) {
            //database.execSQL("UPDATE " + table_requestid + " SET  requestid = '" + requestid + "' ");
            Log.e("DB-->", Log.getStackTraceString(e));
        }

    }

    public void updateRequestIdStage(String requestid, String email, String stage) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        try {
            Log.e("DB----->", "addRequestId: " + requestid);
            db.execSQL("UPDATE " + table_requestid + " SET  stage = '" + stage + "' " + " WHERE requestid='" + requestid + "' AND email='" + email + "'");
            this.close();
        } catch (Exception e) {
            //database.execSQL("UPDATE " + table_requestid + " SET  requestid = '" + requestid + "' ");
            Log.e("DB-->", Log.getStackTraceString(e));
        }

    }

    public void deleteRequestId() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_requestid);
        this.close();
    }


    public Cursor getRequestId() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_requestid, null);
        return cursor;
    }


    public Cursor getRequestIdsOnEmail(String email) {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_requestid + " WHERE email = '" + email + "'", null);

        if (cursor != null) return cursor;

        else return null;
    }


    public Cursor getStoredDates(String datess) {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_storedDates + " WHERE datess = '" + datess + "'", null);
        return cursor;
    }

    public Cursor getStatusBasedOnEmailid(String emailid) {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_requestid + " WHERE email = '" + emailid + "'", null);
        return cursor;
    }

    public Boolean getDateStatus() {
//        SQLiteDatabase db=this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_storedDates, null);

        return cursor.moveToNext();

    }


    public void deleteAllDates() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_storedDates + "   ");
        this.close();
    }

    public void deleteRequestIdBasedOnEmail(String email) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_requestid + " WHERE email = '" + email + "'");
        this.close();
    }


    public void deleteDatesBasedOnEmail(String email) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_storedDates + " WHERE emaild = '" + email + "'");
        this.close();
    }

    public void insertTodaysDate(String todaysdate, String emailid) {
//        SQLiteDatabase database = this.getWritableDatabase();

        Log.e("DATABASE", "insertTodaysDate: " + todaysdate + "" + emailid);
//        database.execSQL("INSERT INTO " + table_storedDates + "(datess, emaild) VALUES('"+ todaysdate + "','" + emailid + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_storedDates + "(datess, emaild) VALUES('" + todaysdate + "','" + emailid + "' )";
        this.insert(qInsert);
    }

    public Cursor getAllDates() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_storedDates, null);
        return cursor;
    }


    public void updateCompletedStatus(String id, String assessor_id, String status) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("UPDATE " + table_requestid + " SET  status = '" + status + "' " + " WHERE requestid='" + id + "' AND assessor_id='" + assessor_id + "'");
        this.close();
    }

    public void addDeviceId(String deviceid) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_deviceid+ "(deviceid) VALUES('"+ deviceid + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_deviceid + "(deviceid) VALUES('" + deviceid + "' )";
        this.insert(qInsert);
    }

    public void addStage(int stage, String email, String requestid) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_stage + "(stage, emailid , requestid) VALUES('"+ stage + "','" +
//                email + "','" + requestid + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_stage + "(stage, emailid , requestid) VALUES('" + stage + "','" + email + "','" + requestid + "' )";
        this.insert(qInsert);
    }

    public void updateStage(int stage, String id, String requestid) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("UPDATE " + table_stage + " SET  stage = '" + stage + "' " + " WHERE emailid='" + id + "' AND requestid='" + requestid + "'");
        this.close();
    }

    public Cursor getStage(String email, String requestid) {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_stage + " WHERE requestid'" + requestid + "' AND emailid='" + email + "'", null);
        return cursor;
    }

    public Cursor getStageData() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_stage, null);
        return cursor;
    }

    public Cursor getStageBasedOnEmail(String emailid, String requestid) {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_stage + " WHERE emailid = '" + emailid + "' AND requestid='" + requestid + "'", null);
        if (cursor != null) return cursor;

        else return null;
    }

    public void deleteStage() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_stage + "   ");
        this.close();
    }

    public void deleteStageBasedOnEmailid(String emailid) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_stage + " WHERE emailid='" + emailid + "'");
        this.close();
    }

    public void addArc(String arc) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_arc + "(arc_code) VALUES('"+ arc + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_arc + "(arc_code) VALUES('" + arc + "' )";
        this.insert(qInsert);
    }

    public Cursor getACR() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_arc, null);
        return cursor;
    }

    public void deleteArc() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_arc + "   ");
        this.close();
    }


    public void addImage(String image, int mode) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_image + "(image,mode) VALUES('"+ image + "','" + mode + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_image + "(image,mode) VALUES('" + image + "','" + mode + "' )";
        this.insert(qInsert);
    }

    public Cursor getImage() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_image, null);
        return cursor;
    }

    public void deleteImage() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_image + "   ");
        this.close();
    }

    public void deleteImage(String filename) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_image + " WHERE image='" + filename + "'");
        this.close();
    }

    public void addLocation(double lattitude, double longitude, String area, String citystate, String country, String time) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_location + "(lattitude, longitude,area,citystate,country,time) VALUES('" +
//                lattitude + "','" + longitude+ "','"+area+"','"+citystate+"','"+country+"','"+time+"' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_location + "(lattitude, longitude,area,citystate,country,time) VALUES('" + lattitude + "','" + longitude + "','" + area + "','" + citystate + "','" + country + "','" + time + "' )";
        this.insert(qInsert);
    }

    public Cursor getLocation() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_location + " ORDER BY " + GPS_TIME + " DESC", null);
        return cursor;
    }

    public void deletelocation() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_location + "   ");
        this.close();
    }

    public Cursor getDeviceId() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_deviceid, null);
        return cursor;
    }

    public void deleteDeviceId() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_deviceid + "   ");
        this.close();
    }

    // jsonData BackUp
    public void addOverAllResponse(String response) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_overallData+ "(gpsdata,status) VALUES('"+ response +"','"+"N" +"' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_overallData + "(gpsdata,status) VALUES('" + response + "','" + "N" + "' )";
        this.insert(qInsert);
    }

    public void updateStatus() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        Cursor cursor = db.query(table_overallData, null, null, null, null, null, null);
        cursor.moveToNext();
        String id = cursor.getString(0);
        db.execSQL("UPDATE " + table_overallData + " SET  status = '" + "Y" + "' " + " WHERE id='" + id + "'");
        this.close();
    }

    public Cursor getOverAllResponse() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_overallData, null);
        return cursor;
    }

    public void deleteOverAllResponse() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_overallData + "   ");
        this.close();
    }


    public void addBackupData(String json, int mode) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_backupdata + "(json, mode) VALUES('"+ json + "','" + mode + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_backupdata + "(json, mode) VALUES('" + json + "','" + mode + "' )";
        this.insert(qInsert);
    }

    public void deleteBackupData() {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_backupdata + "   ");
        this.close();
    }

    public Cursor getBackupData() {
//        SQLiteDatabase db = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_backupdata, null);
        return cursor;
    }

    public void deleteBackupData(int id) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_backupdata + " WHERE id='" + id + "'");
        this.close();
    }

    public void addActivity(String requestid, String userid, String ratePerBatch, String expenseAmount, String expenseType, String remarks, String receiptRemarks, String date, String mode) {
        //if(requestid.equalsIgnoreCase("")) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        database.execSQL("INSERT INTO " + table_add_activity + "(rate_per_batch, type_of_expense, expense_amount," +
//                " remarks, image, requestid, userid, date, mode) VALUES('" + ratePerBatch + "','" + expenseType + "','" +
//                expenseAmount + "','" + remarks + "','" + receiptRemarks + "','" + requestid + "','" + userid + "','" +
//                date + "','" + mode + "' )");
//        database.close();
        final String qInsert = "INSERT INTO " + table_add_activity + "(rate_per_batch, type_of_expense, expense_amount," + " remarks, image, requestid, userid, date, mode) VALUES('" + ratePerBatch + "','" + expenseType + "','" + expenseAmount + "','" + remarks + "','" + receiptRemarks + "','" + requestid + "','" + userid + "','" + date + "','" + mode + "' )";
        this.insert(qInsert);

    }

    public void updateActivity(String requestid, String userid, String ratePerBatch, String expenseAmount, String expenseType, String remarks, String receiptRemarks, String addActNum, String date, String mode) {
        //if(requestid.equalsIgnoreCase("")) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("UPDATE " + table_add_activity + " SET  image = '" + receiptRemarks + "',type_of_expense ='" + expenseType + "',expense_amount ='" + expenseAmount + "', remarks='" + remarks + "', date='" + date + "'" + " WHERE addedActivityNumber='" + addActNum + "' AND requestid='" + requestid + "' AND mode='" + mode + "' AND userid = '" + userid + "'");
        this.close();

    }

    public void updateImageNameActivity(String requestid, String userid, String addActNum, String receiptRemarks, String mode) {
        //if(requestid.equalsIgnoreCase("")) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("UPDATE " + table_add_activity + " SET  image = '" + receiptRemarks + "'" + " WHERE addedActivityNumber='" + addActNum + "' AND requestid='" + requestid + "' AND mode='" + mode + "' AND userid ='" + userid + "'");
        this.close();

    }

    //once done the server call
    public void updateStatusActivity(String requestid, String userid, String status, String mode) {

//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("UPDATE " + table_add_activity + " SET  status = '" + "Done" + "'" + " WHERE requestid='" + requestid + "' AND mode='" + mode + "' AND userid='" + userid + "'");
        //database.execSQL("UPDATE " + table_add_activity + " SET  status = '" + "Y" + "'"  + " WHERE addedActivityNumber='" + addActNum + "' AND requestid='"+requestid+"'");
        this.close();

    }

    public void deleteActivity(String requestid, String userid, String addActNum, String mode) {
        //if(requestid.equalsIgnoreCase("")) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_add_activity + " WHERE requestid='" + requestid + "' AND userid='" + userid + "' AND addedActivityNumber='" + addActNum + "' AND mode='" + mode + "'");
        this.close();

    }

    public Cursor getAddedActivity(String requestid, String userid, String mode) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM addActivity " + " WHERE userid='" + userid + "' AND requestid='" + requestid + "' AND mode = '" + mode + "'", null);
        //Cursor cursor = database.rawQuery("SELECT * FROM " + table_add_activity + "WHERE addedActivityNumber='"+id+"'", null);
        Log.e("Item Count", cursor.getCount() + "");
        return cursor;
    }



    public Cursor getOnlyClickedAddedActivity(String requestid, String addedActNum) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_add_activity + " WHERE requestid='" + requestid + "' AND addedActivityNumber='" + addedActNum + "'", null);

        return cursor;
    }

    public void getDeleteActivity(String userID) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_add_activity + " WHERE userid='" + userID + "'");
        this.close();
    }

    public void addDocImage(String imagefilename, String date, String latitude, String longitude, String foldername, String userID, String addActNum, String requestid, String upload_status, String mode) {
//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_doc_image + " WHERE addActivityNumber='" + addActNum + "' AND requestid='" + requestid + "' AND mode='" + mode + "'", null);
        if (cursor.moveToNext()) {
            db.execSQL("UPDATE " + table_doc_image + " SET  image = '" + imagefilename + "' " + " WHERE userid='" + userID + "' AND addActivityNumber='" + addActNum + "' AND requestid='" + requestid + "' AND mode='" + mode + "'");
            this.close();
        } else {
//            database.execSQL("INSERT INTO " + table_doc_image + "(date,userid,image,addActivityNumber,requestid,folder_name,upload_status,mode ) VALUES('"+ date + "','"+ userID + "','" + imagefilename + "','" + addActNum + "','" + requestid + "','" + foldername + "','" + upload_status + "','" + mode + "' )");
//            database.close();
            final String qInsert = "INSERT INTO " + table_doc_image + "(date,userid,image,addActivityNumber,requestid," + "folder_name,upload_status,mode ) VALUES('" + date + "','" + userID + "','" + imagefilename + "','" + addActNum + "','" + requestid + "','" + foldername + "','" + upload_status + "','" + mode + "' )";
            this.insert(qInsert);
        }
    }

    //once done the server call
    public void updateStatusImage(String requestid, String userid, String status, String mode) {

//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("UPDATE " + table_doc_image + " SET  upload_status = '" + status + "'" + " WHERE userid='" + userid + "' AND requestid='" + requestid + "'");
        this.close();

    }

    public Cursor getDocImage(String requestid, String userid, String mode) {
//        SQLiteDatabase database = this.getReadableDatabase();
        this.open();
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_doc_image + " WHERE requestid='" + requestid + "' AND mode = '" + mode + "' AND userid='" + userid + "'", null);
        return cursor;
    }

    public void deleteDocImage(String requestid, String userID, String mode) {

//        SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        db.execSQL("DELETE  FROM " + table_doc_image + " WHERE userid='" + userID + "' AND requestid ='" + requestid + "' AND mode = '" + mode + "'");
        this.close();

    }

    public void copyAppDbToDownloadFolder(Context context) throws IOException {
        try {
            File backupDB = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyDatabaseNEO"); // for example "my_data_backup.db"
            File currentDB = context.getDatabasePath("neo"); //databaseName=your current application database name, for example "my_data.db"
            if (currentDB.exists()) {
                FileInputStream fis = new FileInputStream(currentDB);
                FileOutputStream fos = new FileOutputStream(backupDB);
                fos.getChannel().transferFrom(fis.getChannel(), 0, fis.getChannel().size());
                // or fis.getChannel().transferTo(0, fis.getChannel().size(), fos.getChannel());
                fis.close();
                fos.close();
                Log.i("Database successfully", " copied to download folder");
                return;
            } else Log.i("Copying Database", " fail, database not found");
        } catch (IOException e) {
            Log.d("Copying Database", "fail, reason:", e);
        }
    }


    private static class OpenHelper extends SQLiteOpenHelper {
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(QUERY_TBL_CENTERS);
                db.execSQL(QUERY_TBL_PRACTICES);
                db.execSQL(QUERY_TBL_COURSES);
                db.execSQL(QUERY_TBL_PROF_QUES);
                db.execSQL(QUERY_TBL_PROF_OPS);
                db.execSQL(QUERY_TBL_WF_QUES);
                db.execSQL(QUERY_TBL_WF_OPS);
                db.execSQL(QUERY_TBL_STATES);
                db.execSQL(QUERY_TBL_DISTRICTS);
                db.execSQL(QUERY_TBL_CONTRACTORS);
                db.execSQL(QUERY_TBL_BATCHES);

                /*TMA*/
                String assessor_table = "CREATE TABLE IF NOT EXISTS   " + table_assessor + " (email TEXT,password TEXT)";
                db.execSQL(assessor_table);

                String userid_table = "CREATE TABLE IF NOT EXISTS   " + UserID + " (id TEXT)";
                db.execSQL(userid_table);

                String requestid_table = "CREATE TABLE IF NOT EXISTS   " + table_requestid + " (requestid TEXT ,batchid TEXT, stage TEXT, batchStatus TEXT, dateOfAssessment TEXT, paymentStatus TEXT, remainDays TEXT, initiatePayment TEXT, ratePerBatch TEXT, location TEXT,status TEXT,email TEXT, ama_stages TEXT, assessor_id TEXT, isPaymentInitiated TEXT, isBetaUser TEXT)";
                db.execSQL(requestid_table);

                String device_table = "CREATE TABLE IF NOT EXISTS   " + table_deviceid + " (deviceid TEXT)";
                db.execSQL(device_table);

                String stage_table = "CREATE TABLE IF NOT EXISTS " + table_stage + " (stage INT, emailid TEXT, requestid TEXT)";
                db.execSQL(stage_table);

                String arc_table = "CREATE TABLE IF NOT EXISTS " + table_arc + " (arc_code TEXT)";
                db.execSQL(arc_table);

                String status_table = "CREATE TABLE IF NOT EXISTS " + table_stage + " (stage INT)";
                db.execSQL(status_table);

                String image_table = "CREATE TABLE IF NOT EXISTS " + table_image + " (image TEXT, mode INTEGER)";
                db.execSQL(image_table);

                String deviceid_table = "CREATE TABLE IF NOT EXISTS " + table_deviceid + " (deviceid TEXT)";
                db.execSQL(deviceid_table);

                String locationupdate = "CREATE TABLE IF NOT EXISTS   " + table_location + " (lattitude INTEGER,longitude INTEGER,area TEXT,citystate TEXT,country TEXT,time TEXT)";
                db.execSQL(locationupdate);

                String overalldata = "CREATE TABLE IF NOT EXISTS   " + table_overallData + " (id INTEGER PRIMARY KEY autoincrement, gpsdata Text,status TEXT)";
                db.execSQL(overalldata);

                String temp_request_table = "CREATE TABLE IF NOT EXISTS   " + temp_requestid + " (id TEXT)";
                db.execSQL(temp_request_table);

                String mode_table = "CREATE TABLE IF NOT EXISTS   " + table_mode + " (mode INTEGER)";
                db.execSQL(mode_table);

                String datess_table = "CREATE TABLE IF NOT EXISTS   " + table_storedDates + " (datess TEXT, emaild TEXT)";
                db.execSQL(datess_table);

                String json_table = "CREATE TABLE IF NOT EXISTS   " + table_backupdata + " (id INTEGER PRIMARY KEY autoincrement, json TEXT, mode INTEGER)";
                db.execSQL(json_table);

                String addactivity_table = "CREATE TABLE IF NOT EXISTS   " + table_add_activity + " (addedActivityNumber INTEGER PRIMARY KEY autoincrement, userid TEXT, requestid TEXT, batchid TEXT, rate_per_batch TEXT, type_of_expense TEXT, expense_amount TEXT, remarks TEXT, image TEXT, status TEXT, date TEXT, mode TEXT)";
                db.execSQL(addactivity_table);

                String docimage_table = "CREATE TABLE IF NOT EXISTS   " + table_doc_image + " (date TEXT, userid TEXT, requestid TEXT, image TEXT, folder_name TEXT, upload_status TEXT,addActivityNumber TEXT,mode TEXT)";
                db.execSQL(docimage_table);

                db.execSQL(QUERY_TBL_SESSION_PLANS);
                db.execSQL(QUERY_TBL_MODULES);

                /*new neo*/
                db.execSQL(QUERY_TBL_MBL_CAND);
                db.execSQL(QUERY_TBL_REG_CAND);
                db.execSQL(QUERY_TBL_ENR_CAND);
                db.execSQL(QUERY_TBL_NEO_BATCHES);
                db.execSQL(QUERY_TBL_CAND_STATUS_HISTORY);
                db.execSQL(QUERY_TBL_FAMILY_MEMBERS);
                db.execSQL(QUERY_TBL_RE_ENR_CAND);

            } catch (Exception e) {
                Log.e("Database onCreate", e.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                if (oldVersion < newVersion) {
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_CENTERS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_PRACTICES);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_COURSES);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_PROF_QUES);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_PROF_OPS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_WF_QUES);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_WF_OPS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_STATES);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_DISTRICTS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_CONTRACTORS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_BATCHES);

                    /*TMA*/
                    db.execSQL("DROP TABLE IF EXISTS " + table_assessor);
                    db.execSQL("DROP TABLE IF EXISTS " + UserID);
                    db.execSQL("DROP TABLE IF EXISTS " + table_deviceid);
                    db.execSQL("DROP TABLE IF EXISTS " + table_stage);
                    db.execSQL("DROP TABLE IF EXISTS " + table_arc);
                    db.execSQL("DROP TABLE IF EXISTS " + table_stage);
                    db.execSQL("DROP TABLE IF EXISTS " + table_image);
                    db.execSQL("DROP TABLE IF EXISTS " + table_location);
                    db.execSQL("DROP TABLE IF EXISTS " + table_deviceid);
                    db.execSQL("DROP TABLE IF EXISTS " + table_overallData);
                    db.execSQL("DROP TABLE IF EXISTS " + table_requestid);
                    db.execSQL("DROP TABLE IF EXISTS " + temp_requestid);
                    db.execSQL("DROP TABLE IF EXISTS " + table_mode);
                    db.execSQL("DROP TABLE IF EXISTS " + table_storedDates);
                    db.execSQL("DROP TABLE IF EXISTS " + table_backupdata);
                    db.execSQL("DROP TABLE IF EXISTS " + table_add_activity);
                    db.execSQL("DROP TABLE IF EXISTS " + table_doc_image);

                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_SESSION_PLANS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_MODULES);

                    /*new neo*/
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_MBL_CAND);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_REG_CAND);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_ENR_CAND);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_NEO_BATCHES);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_CAND_STATUS_HISTORY);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_FAMILY_MEMBERS);
                    db.execSQL("DROP TABLE IF EXISTS " + Keys.TBL_RE_ENR_CAND);

                    onCreate(db);
                }
            } catch (Exception e) {
                Log.e("Database onUpgrade", e.getMessage());
            }
        }
    }
}
package com.bb.lib.apis;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bb.lib.BuildConfig;
import com.bb.lib.ConfigConstants;
import com.bb.lib.utils.ILog;
import com.bb.lib.volley.request.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by mihir on 11-03-2016.
 */
public class ApiUtils extends ConfigConstants {

    public static final String DUMMY_MOBILE = "9999999999";

    protected static final String TAG = ApiUtils.class.getSimpleName();

    protected static final String CHECKSUM = "checkSum";

    protected static final String APP = "APP", PREPAID = "PREPAID", POSTPAID = "POSTPAID",
            PIPE = "|",   REQUEST_DATE = "requestDate", MOBILE = "mo", REQUEST_FROM = "reqFrom",
            REQUEST_TYPE = "reqType", CIRCLE_ID = "cId", USSD_DETAILS = "ussdDetails", EMAIL = "email", REQ_ARRAY = "reqArray", IMSI = "imsi";

    public static final String MOB = "1", WIFI = "2";

    public static final String UTF_8 = "UTF-8", NA = "NA";

    protected static final String BASE_URL = BuildConfig.DEBUG ? BASE_DEBUG_URL : BASE_RELEASE_URL;
    // Result tags
    public static final String STATUS = "status", SUCCESS = "success";

    public static final String BB_KEY = "dC_8F01133Db4E_CD982BE428_38_56e";

    public static final String UID = "uid";

    /**
     * Call this api without passing checksum, this will calculate checksum and add it to params
     *
     * @param context
     * @param map
     * @return
     */
    protected static Map<String, String> getParamsWithCheckSum(Context context, @NonNull
            Map<String, String> map) {
        String checkSum = ApiUtils.getCheckSum(context, map);
        map.put(CHECKSUM, checkSum);

        //TODO only for testing DEBUG MODE
        if (BuildConfig.DEBUG) {
            for (String keySet : map.keySet()) {
                ILog.d(BaseRequest.class.getSimpleName(), keySet + "-->" + map.get(keySet));
            }
        }
        return map;
    }

    public static String getCheckSum(Context context, @NonNull Map<String, String> params) {
        //ILog.d(TAG, "Key : " + PreferenceUtils.getKey(context) + " found");
        String md5String = generateMD5String(params, BB_KEY);
        ILog.d(TAG, "MD5String : " + md5String);
        return generateCheckSum(md5String);
    }

    protected static String generateMD5String(Map<String, String> params, String key) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value == null || value.equals("")) {
                value = NA;
            }
            builder.append(value + PIPE);
        }
        builder.append(key);

        return builder.toString();
    }

    protected static String generateCheckSum(String string) {
        StringBuilder sb = new StringBuilder();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(string.getBytes());
            byte[] digest = md.digest();

            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        String checkSum = sb.toString().toUpperCase().trim();
        ILog.d(TAG, "Checksum " + checkSum);
        return checkSum;
    }

    protected static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH24mm");
        return formatter.format(new Date());
    }

    /*
    * NDP specific methods for req with checksum
    */
    protected static Map<String, String> getParamsWithCheckSumNDP(Context context, @NonNull
            Map<String, String> map) {
        String checkSum = ApiUtils.getCheckSumNDP(context, map);
        map.put(CHECKSUM, checkSum);
        return map;
    }

    public static String getCheckSumNDP(Context context, @NonNull Map<String, String> params) {
        String md5String = generateMD5StringNDP(params, BB_KEY);
        ILog.d(TAG, "MD5StringNDP : " + md5String);
        return generateCheckSum(md5String);
    }

    protected static String generateMD5StringNDP(Map<String, String> params, String key) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        String md5Result = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value == null || value.equals("")) {
                value = NA;
            }
            try {
                out.write((value + PIPE).getBytes(UTF_8));
            } catch (IOException ex) {
                ex.printStackTrace();
                return md5Result;
            }
        }
        try {
            out.write(key.getBytes(UTF_8));
            md5Result = out.toString(UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5Result;
    }

    public static boolean isValidResponse(String response) {
        ILog.d(BaseRequest.class.getSimpleName(), "Response " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(STATUS).equalsIgnoreCase(SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}

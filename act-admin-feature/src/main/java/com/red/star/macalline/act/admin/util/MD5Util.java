package com.red.star.macalline.act.admin.util;

import java.security.MessageDigest;

/**
 * @author
 */
public class MD5Util {

    private static final String HEXDIGITS[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	public static String encode(String origin) {
		return encode(origin, "UTF-8");
	}

    public static String encode(String origin, String charsetname) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(origin.getBytes()));
            }
            else
            {
                resultString = byteArrayToHexString(md.digest(origin.getBytes(charsetname)));
            }

        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }

}
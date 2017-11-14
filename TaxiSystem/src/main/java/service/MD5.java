package service;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by DNAPC on 14.11.2017.
 */
public class MD5 {
    public static String md5Hash(String line){
        return DigestUtils.md5Hex(line);
    }
}

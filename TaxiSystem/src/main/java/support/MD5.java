package support;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
    public static String md5Hash(String line){
        return DigestUtils.md5Hex(line);
    }
}

package utils;

import java.security.MessageDigest;
import java.util.HexFormat;

public class ImportServiceUtil {

    public static String getAndVerifyFileHash(MessageDigest messageDigest) {
        String fileHash = "";

        byte[] digestBytes = messageDigest.digest();
        fileHash = HexFormat.of().formatHex(digestBytes);

        // verify file hash

        return fileHash;
    }

}

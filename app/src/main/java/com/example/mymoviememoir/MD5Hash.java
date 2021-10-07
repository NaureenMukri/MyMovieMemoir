package com.example.mymoviememoir;

import java.security.MessageDigest;

public class MD5Hash {

    public static byte[] encryptMD5(byte[] data)throws Exception{

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }
}

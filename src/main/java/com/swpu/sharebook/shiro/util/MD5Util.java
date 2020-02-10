package com.swpu.sharebook.shiro.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Util {

    //String PUBLIC_KEY = new Md5Hash("KeennessNewBie").toString();
    public static final String PUBLIC_KEY = "207A886B657DB0F895BABF4BE755BFA6";
    private static final int HASHITERATIONS = 4;

    //公钥加密
    private static String md5PubliceSalt(String source) {
        return new Md5Hash(source, PUBLIC_KEY, HASHITERATIONS).toString().toUpperCase();
    }

    //使用私钥 加密
    public static String md5PrivateSalt(String source, String salt) {
        return new Md5Hash(md5PubliceSalt(source), salt, HASHITERATIONS).toString().toUpperCase();
    }


}

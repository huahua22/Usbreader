package com.xwr.usbreader.utils;

/**
 * Create by xwr on 2020/2/14
 * Describe:
 */
public class HexUtil {
  public static String bytesToHexString(byte[] src, int length) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (src == null || length <= 0) {
      return null;
    }
    for (int i = 0; i < length; i++) {
      int v = src[i] & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv + " ");
    }
    return stringBuilder.toString();
  }
}

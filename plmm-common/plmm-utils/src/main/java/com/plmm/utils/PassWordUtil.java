package com.plmm.utils;

public class PassWordUtil{
	private static final String M_STRKEY1 = "zxcvbnm,./asdfg";
	private static final String M_STRKEYA = "cjk;";
	private static final String M_STRKEY2 = "hjkl;'qwertyuiop";
	private static final String M_STRKEYB = "cai2";
	private static final String M_STRKEY3 = "[]\\1234567890-";
	private static final String M_STRKEYC = "%^@#";
	private static final String M_STRKEY4 = "=` ZXCVBNM<>?:LKJ";
	private static final String M_STRKEYD = "*(N";
	private static final String M_STRKEY5 = "HGFDSAQWERTYUI";
	private static final String M_STRKEYE = "%^HJ";
	private static final String M_STRKEY6 = "OP{}|+_)(*&^%$#@!~";

	/**
	 * 加密密码
	 * @param strPasswd
	 */
	public String encode(String strPasswd) {
		if (strPasswd == null) {
			return null;
		}
		String orig_passwd;
		String strEncodePasswd = "";
		String strKey;
		char code, mid = 0, temp = 0;
		int n, length, flag;

		orig_passwd = strPasswd;

		if (orig_passwd.length() == 0) {
			return "";
		}
		if (includeChineseChar(strPasswd)) {
//			orig_passwd = "123456";
		}
		strKey = M_STRKEY1 + M_STRKEY2 + M_STRKEY3 + M_STRKEY4 + M_STRKEY5 + M_STRKEY6;


		for (n = 0; n < orig_passwd.length(); n++) {
			while (true) {
				code = (char) (Math.rint(Math.random() * 100));
				while ((code > 0) && (((code ^ orig_passwd.charAt(n)) < 0) || ((code ^ orig_passwd.charAt(n)) > 90))) {
					code = (char) ((int) code - 1);
				}

				flag = code ^ orig_passwd.charAt(n);

				if (flag > 93) {
					mid = 0;
				} else {
					mid = strKey.charAt(flag);
				}

				if ((code > 35) && (code < 122) && (code != 124) && (code != 39) && (code != 44) && (mid != 124)
						&& (mid != 39) && (mid != 44)) {
					break;
				}

			}

			temp = strKey.charAt(code ^ orig_passwd.charAt(n));

			strEncodePasswd = strEncodePasswd + new Character(code).toString();
			strEncodePasswd = strEncodePasswd + new Character(temp).toString();

		}
		return strEncodePasswd;
	}

	/**
	 * 解密密码
	 * @param strPasswd
	 */
	public String decode(String strPasswd) {
		if (strPasswd == null) {
			return null;
		}
		String orig_passwd;
		String strDecode="";
		String strKey;
		char b;
		int n, length;

		orig_passwd = strPasswd;
		if (orig_passwd.length() == 0) {
			return "";
		}

		strKey = M_STRKEY1 + M_STRKEY2 + M_STRKEY3 + M_STRKEY4 + M_STRKEY5 + M_STRKEY6;

		if ((length = orig_passwd.length()) % 2 == 1) {
			orig_passwd = orig_passwd + "?";
		}

		for (n = 0; n <= orig_passwd.length() / 2 - 1; n++) {
			b = orig_passwd.charAt(n * 2);
			char c = (char) strKey.indexOf(orig_passwd.charAt(n * 2 + 1));
			int flag = b ^ c;
			char a = (char) flag;
			strDecode = strDecode + new Character(a).toString();
		}

		n = strDecode.indexOf(1);

		if (n > 0 && n <= strDecode.length()) {
			strDecode = strDecode.substring(0, n);
		}

		return strDecode;
	}

	public boolean includeChineseChar(String strPwd) {
		int l = strPwd.length();
		int num = 0;
		int str = 0;
		for (int i = 0; i < l; i++) {
			if (strPwd.charAt(i) < 0 || strPwd.charAt(i) > 255 || strPwd.charAt(i) == 38 || strPwd.charAt(i) == 35
					|| strPwd.charAt(i) == 36 || strPwd.charAt(i) == 37 || strPwd.charAt(i) == 63
					|| strPwd.charAt(i) == 64 || strPwd.charAt(i) == 42 || strPwd.charAt(i) == 94) {
				return true;
			} else {
				if (strPwd.charAt(i) >= 48 && strPwd.charAt(i) <= 57) {
					num++;
				} else {
					str++;
				}
			}
		}
		if (num == 0 || str == 0) {
			// return true;
		}
		return false;
	}

	/*public static void main(String args[]) {
		PassWord pw = new PassWord();
		System.out.println(pw.decode(pw.encode("unitele")));
		System.out.println(pw.encode("lemontea"));

		System.out.println("&".getBytes()[0]);
		System.out.println("?".getBytes()[0]);
		System.out.println("@".getBytes()[0]);
		System.out.println("#".getBytes()[0]);
		System.out.println("$".getBytes()[0]);
		System.out.println("%".getBytes()[0]);
		System.out.println("^".getBytes()[0]);
		System.out.println("*".getBytes()[0]);
	}*/

}

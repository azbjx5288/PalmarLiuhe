package com.liuheonline.la.utils;

import android.util.Patterns;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BenYanYi
 * @date 2018/12/12 10:38
 * @email ben@yanyi.red
 * @overview
 */
public class WebAddressUtil {
    private static String mScheme;
    private static String mHost;
    private static int mPort;
    private static String mPath;
    private static String mAuthInfo;

    static final int MATCH_GROUP_SCHEME = 1;
    static final int MATCH_GROUP_AUTHORITY = 2;
    static final int MATCH_GROUP_HOST = 3;
    static final int MATCH_GROUP_PORT = 4;
    static final int MATCH_GROUP_PATH = 5;

    /* ENRICO: imported the ParseExeption here */
    public static class ParseException extends RuntimeException {
        public String response;

        ParseException(String response) {
            this.response = response;
        }
    }

    //根据正则表达式进行判断
    static Pattern sAddressPattern = Pattern.compile(
            /* scheme    */ "(?:(http|https|file)\\:\\/\\/)?" +
                    /* authority */ "(?:([-A-Za-z0-9$_.+!*'(),;?&=]+(?:\\:[-A-Za-z0-9$_.+!*'(),;?&=]+)?)@)?" +
                    /* host      */ "([" + Patterns.GOOD_IRI_CHAR + "%_-][" + Patterns.GOOD_IRI_CHAR + "%_\\.-]*|\\[[0-9a-fA-F:\\.]+\\])?" +
                    /* port      */ "(?:\\:([0-9]*))?" +
                    /* path      */ "(\\/?[^#]*)?" +
                    /* anchor    */ ".*", Pattern.CASE_INSENSITIVE);

    /**
     * parses given uriString.
     */
//用法:将需要判断的url传入,new WebAddress(address) 如果抛出异常,则会出现问题
    public static boolean isUrl(String address) {
        if (address == null) {
            return false;
        }

        // android.util.Log.d(LOGTAG, "WebAddress: " + address);

        mScheme = "";
        mHost = "";
        mPort = -1;
        mPath = "/";
        mAuthInfo = "";

        Matcher m = sAddressPattern.matcher(address);
        String t;
        if (m.matches()) {
            t = m.group(MATCH_GROUP_SCHEME);
            if (t != null) mScheme = t.toLowerCase(Locale.getDefault());
            t = m.group(MATCH_GROUP_AUTHORITY);
            if (t != null) mAuthInfo = t;
            t = m.group(MATCH_GROUP_HOST);
            if (t != null) mHost = t;
            t = m.group(MATCH_GROUP_PORT);
            if (t != null && t.length() > 0) {
                // The ':' character is not returned by the regex.
                try {
                    mPort = Integer.parseInt(t);
                } catch (NumberFormatException ex) {
                    return false;
                }
            }
            t = m.group(MATCH_GROUP_PATH);
            if (t != null && t.length() > 0) {
                /* handle busted myspace frontpage redirect with
                   missing initial "/" */
                if (t.charAt(0) == '/') {
                    mPath = t;
                } else {
                    mPath = "/" + t;
                }
            }

        } else {
            // nothing found... outa here
            return false;
        }

        /* Get port from scheme or scheme from port, if necessary and
           possible */
        if (mPort == 443 && mScheme.equals("")) {
            mScheme = "https";
        } else if (mPort == -1) {
            if (mScheme.equals("https"))
                mPort = 443;
            else
                mPort = 80; // default
        }
        if (mScheme.equals("")) mScheme = "http";
        return true;
    }

    @Override
    public String toString() {
        String port = "";
        if ((mPort != 443 && mScheme.equals("https")) ||
                (mPort != 80 && mScheme.equals("http"))) {
            port = ":" + Integer.toString(mPort);
        }
        String authInfo = "";
        if (mAuthInfo.length() > 0) {
            authInfo = mAuthInfo + "@";
        }

        return mScheme + "://" + authInfo + mHost + port + mPath;
    }

    public void setScheme(String scheme) {
        mScheme = scheme;
    }

    public String getScheme() {
        return mScheme;
    }

    public void setHost(String host) {
        mHost = host;
    }

    public String getHost() {
        return mHost;
    }

    public void setPort(int port) {
        mPort = port;
    }

    public int getPort() {
        return mPort;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getPath() {
        return mPath;
    }

    public void setAuthInfo(String authInfo) {
        mAuthInfo = authInfo;
    }

    public String getAuthInfo() {
        return mAuthInfo;
    }
}

package com.github.wolfgang.operation.core.http;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.github.wolfgang.operation.utils.Utils.asHex;

/**
 * @author wolfgang
 * @date 2019-12-27 16:40:52
 * @version $ Id: AsyncEipOperExecutorImpl.java, v 0.1  wolfgang Exp $
 */
public class ApiAuthenticator {
    private static final long DEFAULT_EXPIRE_TIME_MS = 10000L * 5 * 60;

    private Date    time;
    private String  url;
    private Integer nonce;
    private long    expTimeMs;

    private String encryptKey;

    public ApiAuthenticator(String url) {
        this(new Date(), url, DEFAULT_EXPIRE_TIME_MS, TimeUnit.MILLISECONDS);
    }

    public ApiAuthenticator(Date time, String url) {
        this(time, url, DEFAULT_EXPIRE_TIME_MS, TimeUnit.MILLISECONDS);
    }

    public ApiAuthenticator(String url, long duration, TimeUnit expireTimeUnit) {
        this(new Date(), url, duration, expireTimeUnit);
    }

    public ApiAuthenticator(Date time, String url, long duration, TimeUnit expireTimeUnit) {
        this(time, url, new Random(10).nextInt(1000), duration, expireTimeUnit);
    }

    public ApiAuthenticator(Date time, String url, Integer nonce) {
        this(time, url, nonce, DEFAULT_EXPIRE_TIME_MS, TimeUnit.MILLISECONDS);
    }

    public ApiAuthenticator(Date time, String url, Integer nonce, long duration, TimeUnit expireTimeUnit) {
        this.time = time;
        this.url = url;
        this.nonce = nonce;
        expTimeMs = expireTimeUnit.toMillis(duration);
        this.encryptKey = asHex(asHex(url) + time.getTime() + nonce);
    }

    public String toParameters() {
        return "time=" + time.getTime() + "&nonce=" + nonce + "&key=" + encrypt();
    }

    public boolean matches(String key) {
        return key != null && key.equals(encryptKey);
    }

    private String encrypt() {
        return encryptKey;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - time.getTime() > expTimeMs;
    }
}
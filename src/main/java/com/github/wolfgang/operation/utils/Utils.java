/*
 * Zenlayer.com Inc.
 * Copyright (c) 2014-2021 All Rights Reserved.
 */
package com.github.wolfgang.operation.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author wolfgang
 * @date 2021-04-03 21:59:36
 * @version $ Id: Utils.java, v 0.1  wolfgang Exp $
 */
public class Utils {

    public static String asHex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return asHex(md.digest());
        } catch (NoSuchAlgorithmException var2) {
            return null;
        }
    }

    public static String asHex(byte[] bytes) {
        StringBuffer hex = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; ++i) {
            hex.append(Character.forDigit((bytes[i] & 240) >> 4, 16));
            hex.append(Character.forDigit(bytes[i] & 15, 16));
        }

        return hex.toString();
    }

    public static <E, R> LinkedHashMap<R, E> toMap(Collection<E> collection, Function<E, R> keyGet) {
        if (isEmpty(collection)) {
            return new LinkedHashMap(0);
        } else {
            LinkedHashMap<R, E> maps = new LinkedHashMap(collection.size());
            Iterator<E> var3 = collection.iterator();

            while (var3.hasNext()) {
                E ele = var3.next();
                maps.put(keyGet.apply(ele), ele);
            }

            return maps;
        }
    }

    public static <R, T> Map<R, List<T>> groupBy(List<T> list, Function<T, R> k) {
        Map<R, List<T>> result = new LinkedHashMap();
        Iterator<T> var3 = list.iterator();

        while (var3.hasNext()) {
            T t1 = var3.next();
            R key = k.apply(t1);
            if (key != null) {
                result.computeIfAbsent(key, (k1) -> {
                    return new ArrayList();
                });
                ((List) result.get(key)).add(t1);
            }
        }

        return result;
    }
}

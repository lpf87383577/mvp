package com.shinhoandroid.mvp.model.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author gsd on 2017/3/22.
 */

public class CollectionUtils {



    public static List<String> removeDuplicates(List<String> strs) {
        Set<String> s = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        s.addAll(strs);
        return new ArrayList<>(s);
    }

    /**
     * List是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        if (list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Set set) {
        if (set == null) {
            return true;
        }
        if (set.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(LinkedHashMap list) {
        if (list == null) {
            return true;
        }
        if (list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Collection list) {
        if (list == null) {
            return true;
        }
        if (list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object[] objects) {
        if (objects == null) {
            return true;
        }
        if (objects.length == 0) {
            return true;
        }
        return false;
    }
}

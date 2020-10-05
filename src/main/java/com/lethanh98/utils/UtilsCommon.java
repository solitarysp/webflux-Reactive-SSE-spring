package com.lethanh98.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class UtilsCommon {
    public static String fortmatDate(Date date) {
        SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        String dateStr = ISO8601DATEFORMAT.format(date);
        return dateStr.replace("0700", "07:00");
    }

    public static String fortmatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String dateStr = format.format(date);
        return dateStr;
    }
    public String getFullURL(HttpServletRequest request) {
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
    public static <T> List<List<T>> subList(List<T> lists, int itemOneList) {
        List<List<T>> listsResponse = new LinkedList<>();

        List<T> listInSubList = new LinkedList<T>();
        for (T s : lists) {
            if (listInSubList.size() < itemOneList) {
                listInSubList.add(s);
            } else {
                listsResponse.add(listInSubList);
                listInSubList = new LinkedList<>();
                listInSubList.add(s);
            }
        }
        if (listInSubList.size() > 0) {
            listsResponse.add(listInSubList);
        }
        return listsResponse;
    }

    public static String readFileGetBodyFile(String url) {
        try {
            File file = new File(url);
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();

            String st;
            while ((st = br.readLine()) != null) {
                stringBuilder.append(st);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}

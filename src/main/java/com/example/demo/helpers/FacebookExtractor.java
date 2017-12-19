package com.example.demo.helpers;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by tito on 12/19/2017.
 */
public class FacebookExtractor {
    public static String[] extractField(Document doc, FacebookSelectors selector) {
        if (null != doc) {
            return doc.select(selector.toString()).stream().map(i -> i.text()).toArray(String[]::new);
        }
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }
}

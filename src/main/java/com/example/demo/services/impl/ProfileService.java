package com.example.demo.services.impl;

import com.example.demo.annotations.Cache;
import com.example.demo.controllers.BaseController;
import com.example.demo.helpers.FacebookExtractor;
import com.example.demo.helpers.FacebookSelectors;
import com.example.demo.models.dto.ProfileInfo;
import com.example.demo.services.IProfileService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.BadRequestException;

/**
 * This class allows to get profile info from facebook.
 * It uses jsoup to parse facebook page and extract requred data.
 * Jsoup could be replaced by any other similar lib, for exmpl. Selenium.
 *
 * @author Bogdan
 */
@Component
public class ProfileService implements IProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class);
    /**
     * url to get profile info page.
     */
    @Value("${demo.facebook.baseProfileUrl}")
    private String url;

    /**
     * string format expression to combine url and id.
     */
    @Value("${demo.facebook.profileUrlFormat}")
    private String format;

    /**
     * Returns an ProfileInfo object that can then be displayed to clients.
     *
     * @param id an id of user which will be used in URL to get html document.
     * @return the profile info at the specified URL
     * @see ProfileInfo
     */
    @Override
    //There are a lot of implementations for Cache(using in memory cache or some source to store data), I will use simple custom one instead.
    @Cache(key = "info", time = 10)
    public ProfileInfo getInfo(String id) {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("id should be present.");
        }
        ProfileInfo profile = new ProfileInfo();
        //could be used Selenium for more complex interaction with UI elements.
        try {
            Document doc = Jsoup.connect(String.format(format, url, id)).get();
            profile.setName(String.join(", ", FacebookExtractor.extractField(doc, FacebookSelectors.NAME_SELECTOR)));
            profile.setExperiences(FacebookExtractor.extractField(doc, FacebookSelectors.WORK_SELECTOR));
            profile.setEducations(FacebookExtractor.extractField(doc, FacebookSelectors.EDUCATION_SELECTOR));
            profile.setAbout(String.join(", ", FacebookExtractor.extractField(doc, FacebookSelectors.ABOUT_SELECTOR)));
        } catch (Exception ex) {
            LOGGER.error("Exception occurred,", ex);
            throw new BadRequestException("Something go bad.");
        }
        return profile;
    }
}


package com.example.demo.models.dto;

import java.io.Serializable;

public class ProfileInfo extends BaseModel implements Serializable {
    private String name;
    private String about;
    private String[] educations;
    private String[] experiences;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String[] getEducations() {
        return educations;
    }

    public void setEducations(String[] educations) {
        this.educations = educations;
    }

    public String[] getExperiences() {
        return experiences;
    }

    public void setExperiences(String[] experiences) {
        this.experiences = experiences;
    }
}

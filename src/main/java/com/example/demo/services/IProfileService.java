package com.example.demo.services;

import com.example.demo.models.dto.ProfileInfo;

/**
 * Created by tito on 12/18/2017.
 */
public interface IProfileService {
    ProfileInfo getInfo(String id);
}

package com.example.demo.helpers;

/**
 * Created by tito on 12/19/2017.
 */
public enum FacebookSelectors {
    NAME_SELECTOR {
        @Override
        public String toString() {
            return "#fb-timeline-cover-name";
        }
    },
    WORK_SELECTOR {
        @Override
        public String toString() {
            return "div[data-pnref='work'] li";
        }
    },
    EDUCATION_SELECTOR {
        @Override
        public String toString() {
            return "div[data-pnref='edu'] li";
        }
    },
    ABOUT_SELECTOR {
        @Override
        public String toString() {
            return "#pagelet_bio ul";
        }
    }


}

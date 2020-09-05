package com.example.eduapp.study_material.text_books;

public class getPDF {

    public String name;
    public String url;

    public getPDF () {

    }

    public getPDF(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}

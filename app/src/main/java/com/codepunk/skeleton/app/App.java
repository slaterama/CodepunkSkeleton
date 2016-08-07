package com.codepunk.skeleton.app;

import android.app.Application;

import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.codepunk.skeleton.app.CustomToStringStyle.CUSTOM_STYLE;

public class App extends Application {

    public App() {
        super();
        ToStringBuilder.setDefaultStyle(CUSTOM_STYLE);
    }
}

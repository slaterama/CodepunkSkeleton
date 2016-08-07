package com.codepunk.skeleton.app;

import org.apache.commons.lang3.builder.StandardToStringStyle;

@SuppressWarnings({ "WeakerAccess" })
public class CustomToStringStyle {

    private static class ToStringStyle extends StandardToStringStyle {

        ToStringStyle() {
            super();
            setFieldSeparator(", ");
        }
    }

    public static final ToStringStyle CUSTOM_STYLE = new ToStringStyle();

    private CustomToStringStyle() {
    }
}

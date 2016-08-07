package com.codepunk.skeleton.app;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings({ "unused", "WeakerAccess" })
public class OAuthToken {

    public enum TokenType {
        @SerializedName("bearer")
        BEARER,

        @SerializedName("mac")
        MAC
    }

    // TODO Make Parcelable?
    // TODO Make a Builder?

    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("expires_in")
    private int mExpiresIn;

    @SerializedName("token_type")
    private TokenType mTokenType;

    @SerializedName("scope")
    private String mScope;

    @SerializedName("refresh_token")
    private String mRefreshToken;

    public String getAccessToken() {
        return mAccessToken;
    }

    public int getExpiresIn() {
        return mExpiresIn;
    }

    public TokenType getTokenType() {
        return mTokenType;
    }

    public String getScope() {
        return mScope;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(31, 31, this);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof OAuthToken) && EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

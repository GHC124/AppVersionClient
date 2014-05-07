package com.ghc.appversionclient.common.net.data;

/**
 *
 */
public class AppData {
    private Long mId;
    private String mName;
    private String mIconUrl;
    private String mDescription;
    private String mLatestVersion;
    private Long mPlatformId;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getLatestVersion() {
        return mLatestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        mLatestVersion = latestVersion;
    }

    public Long getPlatformId() {
        return mPlatformId;
    }

    public void setPlatformId(Long platformId) {
        mPlatformId = platformId;
    }
}

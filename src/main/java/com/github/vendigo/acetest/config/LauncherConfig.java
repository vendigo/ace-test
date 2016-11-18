package com.github.vendigo.acetest.config;

import com.google.common.base.MoreObjects;

public class LauncherConfig {
    private String appName;
    private String className;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("appName", appName)
                .add("className", className)
                .toString();
    }
}

package com.github.vendigo.acetest.config;

import com.google.common.base.MoreObjects;

import java.util.List;

public class Config {
    private List<DatasourceConfig> datasources;
    private List<String> folders;
    private List<LauncherConfig> launchers;

    public List<DatasourceConfig> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<DatasourceConfig> datasources) {
        this.datasources = datasources;
    }

    public List<String> getFolders() {
        return folders;
    }

    public void setFolders(List<String> folders) {
        this.folders = folders;
    }

    public List<LauncherConfig> getLaunchers() {
        return launchers;
    }

    public void setLaunchers(List<LauncherConfig> launchers) {
        this.launchers = launchers;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("datasources", datasources)
                .add("folders", folders)
                .add("launchers", launchers)
                .toString();
    }
}

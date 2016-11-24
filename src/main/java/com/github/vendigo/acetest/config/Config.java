package com.github.vendigo.acetest.config;

import lombok.Data;

import java.util.List;

@Data
public class Config {
    private List<DatasourceConfig> datasources;
    private List<String> folders;
    private List<LauncherConfig> launchers;
}

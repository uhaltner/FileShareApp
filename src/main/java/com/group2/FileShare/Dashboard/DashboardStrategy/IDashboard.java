package com.group2.FileShare.Dashboard.DashboardStrategy;

public interface IDashboard {

    public boolean isPublicOnly();
    public boolean isTrashedOnly();
    public String getTemplate();
}

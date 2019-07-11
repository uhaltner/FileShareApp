package com.group2.FileShare.Dashboard.DashboardStrategy;

public class PrivateDashboard implements IDashboard {

    public PrivateDashboard(){
    }

    @Override
    public boolean isPublicOnly() {
        return false;
    }

    @Override
    public boolean isTrashedOnly() {
        return false;
    }

    @Override
    public String getTemplate() {
        return "dashboard";
    }
}

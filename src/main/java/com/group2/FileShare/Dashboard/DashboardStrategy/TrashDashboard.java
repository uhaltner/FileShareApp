package com.group2.FileShare.Dashboard.DashboardStrategy;

public class TrashDashboard implements IDashboard {

    public TrashDashboard(){
    }

    @Override
    public boolean isPublicOnly() {
        return false;
    }

    @Override
    public boolean isTrashedOnly() {
        return true;
    }

    @Override
    public String getTemplate() {
        return "trash_dashboard";
    }
}

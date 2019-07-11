package com.group2.FileShare.Dashboard.DashboardStrategy;

public class PublicDashboard implements IDashboard{

    public PublicDashboard(){
    }

    @Override
    public boolean isPublicOnly() {
        return true;
    }

    @Override
    public boolean isTrashedOnly() {
        return false;
    }

    @Override
    public String getTemplate() {
        return "public_dashboard";
    }
}

package com.group2.FileShare.Dashboard.DashboardStrategy;

public class Dashboard implements IDashboard{

    private IDashboard dashboard;

    public Dashboard(IDashboard dashboard)
    {
        this.dashboard = dashboard;
    }

    public void changeDashboard(IDashboard dashboard)
    {
        this.dashboard = dashboard;
    }

    @Override
    public boolean isPublicOnly() {
        return this.dashboard.isPublicOnly();
    }

    @Override
    public boolean isTrashedOnly() {
        return this.dashboard.isTrashedOnly();
    }

    @Override
    public String getTemplate() {
        return this.dashboard.getTemplate();
    }
}

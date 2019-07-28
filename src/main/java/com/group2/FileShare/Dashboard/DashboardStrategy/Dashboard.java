package com.group2.FileShare.Dashboard.DashboardStrategy;

public class Dashboard implements IDashboard{

    private IDashboard dashboard;

    public Dashboard(IDashboard dashboard)
    {
        changeDashboard(dashboard);
    }

    public void changeDashboard(IDashboard dashboard)
    {
        if(dashboard == null) {
            throw new NullPointerException("Dashboard cannot be null");
        }else{
            this.dashboard = dashboard;
        }
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

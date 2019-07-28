package com.group2.FileShare.Dashboard;

import com.group2.FileShare.Dashboard.DashboardStrategy.Dashboard;
import com.group2.FileShare.Dashboard.DashboardStrategy.PrivateDashboard;
import com.group2.FileShare.Dashboard.DashboardStrategy.PublicDashboard;
import com.group2.FileShare.Dashboard.DashboardStrategy.TrashDashboard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DashboardStrategyTest {

	Dashboard dashboard = new Dashboard(new PrivateDashboard());

	@Test
	public void PrivateDashboardTest()
	{
		dashboard.changeDashboard(new PrivateDashboard());

		boolean result = false;

		if(dashboard.isPublicOnly() == false &&
				dashboard.isTrashedOnly() == false &&
				dashboard.getTemplate().equals("dashboard"))
		{
			result = true;
		}

		assertEquals("Private Dashboard Output Test", true, result);
	}

	@Test
	public void PublicDashboardTest()
	{
		dashboard.changeDashboard(new PublicDashboard());

		boolean result = false;

		if(dashboard.isPublicOnly() == true &&
				dashboard.isTrashedOnly() == false &&
				dashboard.getTemplate().equals("public_dashboard"))
		{
			result = true;
		}

		assertEquals("Public Dashboard Output Test", true, result);
	}

	@Test
	public void TrashDashboardTest()
	{
		dashboard.changeDashboard(new TrashDashboard());

		boolean result = false;

		if(dashboard.isPublicOnly() == false &&
				dashboard.isTrashedOnly() == true &&
				dashboard.getTemplate().equals("trash_dashboard"))
		{
			result = true;
		}

		assertEquals("Trash Dashboard Output Test", true, result);
	}

	@Test
	public void ChangeToNullDashboardTest()
	{
		boolean errorFound = false;

		try{
			dashboard.changeDashboard(null);
		}catch (Exception e){
			errorFound = true;
		}

		assertEquals("Null Dashboard Change Test", true, errorFound);
	}

	@Test
	public void InputNullDashboardTest(){
		boolean errorFound = false;

		try{
			Dashboard dashboard = new Dashboard(null);
		}catch (Exception e){
			errorFound = true;
		}

		assertEquals("Null Dashboard Input Test", true, errorFound);
	}

}

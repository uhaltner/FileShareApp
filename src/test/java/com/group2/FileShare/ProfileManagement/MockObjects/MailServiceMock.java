package com.group2.FileShare.ProfileManagement.MockObjects;

import com.group2.FileShare.IMailService;
import com.group2.FileShare.ProfileManagement.PasswordRecovery.IMail;

public class MailServiceMock implements IMailService {

	public void sendEmail(IMail mail){
		return;
	}
}

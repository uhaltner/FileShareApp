package com.group2.FileShare;

import com.group2.FileShare.ProfileManagement.PasswordRecovery.IMail;

public interface IMailService {

	public void sendEmail(IMail mail);
}

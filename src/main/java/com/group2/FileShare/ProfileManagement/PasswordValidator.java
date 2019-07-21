package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRule;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PasswordValidator implements IPasswordValidator {

    private static final Logger logger = LogManager.getLogger(PasswordValidator.class);

    public boolean validatePassword(String password, String passwordConfirm, ArrayList<IPasswordRule> passwordRuleList){

        boolean validationResult = false;

        try{

            if( !isEmpty(password, passwordConfirm) &&
                    password.equals(passwordConfirm) &&
                    verifyRules(password, passwordRuleList) )
            {
                validationResult = true;
            }else{
                validationResult = false;
            }

        }catch (Exception e){
            logger.log(Level.ERROR, "Failed to validate password at validatePassword(): ", e);
        }

        return validationResult;

    }

    private boolean isEmpty(String password, String passwordConfirm){

        if(password == null || passwordConfirm == null || passwordConfirm.isEmpty() || password.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public boolean verifyRules(String password, ArrayList<IPasswordRule> passwordRuleList){

        boolean validity = false;

        try{
            //if no rules are provided to check against, the result is a pass as there are no limitations to the password.
            if(passwordRuleList.isEmpty()) {
                logger.log(Level.WARN, "An empty password rule list has been checked at verifyRules()");
                validity = true;
            }else{

                for(int i = 0; i < passwordRuleList.size(); i++){

                    IPasswordRule rule = passwordRuleList.get(i);

                    if(rule.isValid(password)){
                        validity = true;
                    }else{
                        validity = false;
                        break;
                    }
                }
            }

        }catch (Exception e){
            logger.log(Level.ERROR, "Error in checking verifying ruleset in verifyRules()", e);
        }

        return validity;
    }


}

package com.group2.FileShare.ProfileManagement;

import com.group2.FileShare.ProfileManagement.PasswordRules.IPasswordRule;

import java.util.ArrayList;

public class PasswordValidator {

    public boolean validatePassword(String password, String passwordConfirm, ArrayList<IPasswordRule> RuleList){

        //is either password empty or null
        if(password == null || passwordConfirm == null || passwordConfirm.isEmpty() || password.isEmpty()){

            return false;
        }

        //if the passwords are the same
        if(password.equals(passwordConfirm) == false){
            return false;
        }

        //check all password rules if the list is not empty
        if(!RuleList.isEmpty()){

            for(int i = 0; i < RuleList.size(); i++){

                IPasswordRule rule = RuleList.get(i);

                if(!rule.isValid(password)){
                    return false;
                }
            }

        }


        //if all succeed, return true
        return true;
    }

}

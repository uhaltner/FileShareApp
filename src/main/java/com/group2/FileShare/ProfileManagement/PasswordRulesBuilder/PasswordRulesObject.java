package com.group2.FileShare.ProfileManagement.PasswordRulesBuilder;

import java.util.ArrayList;

public class PasswordRulesObject {

	private ArrayList<String> ruleParameters;
	private ArrayList<Integer> ruleValues;

	public PasswordRulesObject(){
		this.ruleParameters = new ArrayList<>();
		this.ruleValues = new ArrayList<>();
	}

	public void addParameter(String parameter){
		ruleParameters.add(parameter);
	}

	public void addValue(int value){
		ruleValues.add(value);
	}

	public boolean isEmpty(){
		if(ruleValues.isEmpty() && ruleParameters.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	public int getValue(int i) {
		return ruleValues.get(i);
	}

	public String getParameter(int i) {
		return ruleParameters.get(i);
	}

	public int size(){
		return ruleParameters.size();
	}
}

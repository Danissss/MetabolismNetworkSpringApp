package com.example.MetabolismNetwork.AjaxBody;

import java.util.ArrayList;

public class TransporterAjaxResponseBody {
	
	String role;
	String predictedRole;
	String errorMsg;
	ArrayList<String> role_list = new ArrayList<String>();
	
	// getter setter
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public void setRoleList(ArrayList<String> role_list) {
		this.role_list = role_list;
	}
	
	public void addRoleList(String role) {
		this.role_list.add(role);
	}
	
	public ArrayList<String> getRoleList(){
		return this.role_list;
	}
	
	public void setErrorMsg(String error) {
		this.errorMsg = error;
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	public void setPredictedRole(String role) {
		this.predictedRole = role;
	}
	
	public String getPredictedRole() {
		return this.predictedRole;
	}
	
	
	

}

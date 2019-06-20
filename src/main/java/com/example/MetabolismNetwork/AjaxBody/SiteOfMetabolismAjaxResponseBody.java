package com.example.MetabolismNetwork.AjaxBody;

public class SiteOfMetabolismAjaxResponseBody {
	
	String errorMsg;
	String Status;
	String image_path;
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	boolean Fail;
	boolean Success;
	
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public boolean isFail() {
		return Fail;
	}
	public void setFail(boolean fail) {
		Fail = fail;
	}
	public boolean isSuccess() {
		return Success;
	}
	public void setSuccess(boolean success) {
		Success = success;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}

package com.example.MetabolismNetwork.SubmitCriteria;

public class TransporterSubmitCriteria {
	
	
	public String smiles;
	public String sdf;
	public String file;
	public String chemdraw;
	public String role;
	public String protein;
	
	
	//setter & getter
	public void setSmiles(String smiles) {
		this.smiles = smiles;
	}
	
	public String getSmiles() {
		return this.smiles;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return this.file;
	}
	
	public void setsdf(String sdf) {
		this.sdf = sdf;
	}
	
	public String getsdf() {
		return this.sdf;
	}
	
	public void setchemdraw(String chemdraw) {
		this.chemdraw = chemdraw;
	}
	
	public String getchemdraw() {
		return this.chemdraw;
	}
	public void setrole(String role) {
		this.role = role;
	}
	
	public String getrole() {
		return this.role;
	}
	public void setprotein(String protein) {
		this.protein = protein;
	}
	
	public String getprotein() {
		return this.protein;
	}
	
}

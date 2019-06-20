package com.example.MetabolismNetwork.Model;

public class NmrPredModel {
	
	private String Solvent;
	private String Nucleus;
	private String MolBlock;
	private String SMILES;
	
	
	public String getSolvent() {
		return Solvent;
	}
	public void setSolvent(String solvent) {
		Solvent = solvent;
	}
	public String getMolBlock() {
		return MolBlock;
	}
	public void setMolBlock(String molBlock) {
		MolBlock = molBlock;
	}
	public String getSMILES() {
		return SMILES;
	}
	public void setSMILES(String sMILES) {
		SMILES = sMILES;
	}
	public String getNucleus() {
		return Nucleus;
	}
	public void setNucleus(String nucleus) {
		Nucleus = nucleus;
	}
	
	
}

package Xuan.NMRShiftPrediction;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;



public class HoseCodeEvaluator {
	
	HoseDB hosedb = new HoseDB();
	Connection conn = hosedb.ConnectToDB();
	GenerateHoseCode GHC = new GenerateHoseCode();
	
	/**
	 * given solvent and sphere, try to get the matching hose code
	 * return the shift for specific atom
	 * @param mole
	 * @param atom
	 * @param Solvent
	 * @return
	 * @throws CDKException 
	 * @throws SQLException 
	 */
	public Double RunHose13CPrediction(IAtomContainer mole, IAtom atom, String solvent) throws CDKException, SQLException{
		boolean aromatic = CDKHueckelAromaticityDetector.detectAromaticity(mole);
		
		
		// max sphere 6; min sphere 3
		for(int sphere = 6; sphere >= 3; sphere--) {
			String hoseCode = GHC.GetHoseCodesForMolecule(mole, atom, aromatic,sphere);
			Double shift = hosedb.FindMatchingHoseCode(hoseCode,solvent,sphere,conn,"13C");
			
			// if find any, should return the shift value at this point;
			if(shift != null) {
				return shift;
			}
			else {
				continue;
			}
		}
		
		return 0.0;
	}
	
	
	/**
	 * repulicated function for 1H prediction
	 * @param mole
	 * @param atom
	 * @param solvent
	 * @return
	 * @throws CDKException
	 * @throws SQLException
	 */
	public Double RunHose1HPrediction(IAtomContainer mole, IAtom atom, String solvent) throws CDKException, SQLException{
		boolean aromatic = CDKHueckelAromaticityDetector.detectAromaticity(mole);
		
		
		// max sphere 6; min sphere 3
		for(int sphere = 6; sphere >= 3; sphere--) {
			String hoseCode = GHC.GetHoseCodesForMolecule(mole, atom, aromatic,sphere);
			Double shift = hosedb.FindMatchingHoseCode(hoseCode,solvent,sphere,conn,"1H");
			
			// if find any, should return the shift value at this point;
			if(shift != null) {
				return shift;
			}
			else {
				continue;
			}
		}
		
		return 0.0;
	}
}

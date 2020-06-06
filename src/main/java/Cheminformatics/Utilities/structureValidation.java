package Cheminformatics.Utilities;

import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

public class structureValidation {
	
	
	/**
	 * check function
	 * @param structure
	 * @return
	 */
	public static boolean isStructureValid(String structure) {
		
		boolean isValid = true;
		if(parseSmilesToContainer(structure) ==  null) {
			isValid = false;
		}
		
		return isValid;
		
	}
	
	
	/**
	 * Helper function to read the structure from smiles;
	 * @param smiles
	 * @return
	 */
	public static IAtomContainer parseSmilesToContainer(String smiles) {
		
		
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		SmilesParser smiParser = new SmilesParser(builder);
		IChemObjectBuilder containerBuilder = SilentChemObjectBuilder.getInstance();
		IAtomContainer mole = containerBuilder.newAtomContainer();
		
		try {
			
			mole = smiParser.parseSmiles(smiles);
			
		} catch (InvalidSmilesException e) {
			
			mole = null;
		}
		
		return mole;
		
	}
	
}

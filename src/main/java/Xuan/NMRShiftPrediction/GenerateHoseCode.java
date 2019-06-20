package Xuan.NMRShiftPrediction;


import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.HOSECodeGenerator;

/**
 * Hello world!
 * Construct HOSE Code Library From Sphere 1 - 6 
 * Construct machine learning based predictor
 * If searching through the library and there is no matching Hose code
 * Move to machine learning based predictor 
 * Hence, the machine learning predictor still require good performance
 *
 */
public class GenerateHoseCode 
{
	
	
	
	
	/**
	 * getHOSECode(IAtomContainer ac, IAtom root, int noOfSpheres, boolean ringsize)
	 * CDKHueckelAromaticityDetector prior to using getHOSECode().
	 * @param mol
	 * @param atom
	 * @param sphere
	 * @param boolean aromatic = CDKHueckelAromaticityDetector.detectAromaticity(mol);
	 * @return String 
	 * @throws CDKException 
	 */
	public static String GetHoseCodesForMolecule(IAtomContainer mol, IAtom atom, boolean aromatic, int sphere) throws CDKException {
		HOSECodeGenerator hoseG = new HOSECodeGenerator();
		String hose = new String();
		try {
			hose = hoseG.getHOSECode(mol,atom,sphere,aromatic);
		} catch (CDKException e) {
			e.printStackTrace();
			hose = null;
		}
		
		return hose;
	}
	
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws InvalidSmilesException
	 */
	public static IAtomContainer GetAtomContainerSmiles(String input) throws InvalidSmilesException {
		
		
		IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
		SmilesParser sp = new SmilesParser(builder);
		IAtomContainer mol = sp.parseSmiles(input);
		
		return mol;
	}
	
	
	
	/**
	 * 
	 * @param args
	 * @throws CDKException 
	 */
    public static void main( String[] args ) throws CDKException
    {
        IAtomContainer mole = GetAtomContainerSmiles("[H]OC(=O)[C@@]([H])(N([H])[H])C([H])([H])C1=C([H])N(C([H])=N1)C([H])([H])[H]");
        
    }
}

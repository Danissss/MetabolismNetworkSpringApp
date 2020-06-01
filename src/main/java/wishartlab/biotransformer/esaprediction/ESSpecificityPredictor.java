/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.esaprediction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.smarts.SmartsPattern;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import ambit2.smarts.query.SMARTSException;
import wishartlab.biotransformer.biomolecule.Enzyme;
import wishartlab.biotransformer.biomolecule.Enzyme.EnzymeName;
import wishartlab.biotransformer.biosystems.BioSystem;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import wishartlab.biotransformer.utils.ChemStructureExplorer;
import wishartlab.biotransformer.utils.ChemicalClassFinder;
import wishartlab.biotransformer.utils.ChemicalClassFinder.ChemicalClassName;
import Siyang.CypReact.ReactantPred;

/**
 * Predicts whether a compound is a substrate of a given enzyme within a specific biosystem.
 * @author Djoumbou Feunang, Yannick
 *
 */

public class ESSpecificityPredictor {
	
	BioSystem bSys;

	/**
	 * Creates a specific 
	 * @param bsys
	 */
	public ESSpecificityPredictor(BioSystem bsys) {
		// TODO Auto-generated constructor stub
		this.bSys = bsys;
	
	}
	
	public boolean isValidCyp450Substrate(IAtomContainer substrate, EnzymeName enz) throws Exception{

		ArrayList<ChemicalClassName> chemClasses = ChemicalClassFinder.AssignChemicalClasses(substrate);
		return isValidCyp450Substrate(substrate, enz, chemClasses);
	}
	
	
	public boolean isValidCyp450Substrate(IAtomContainer substrate, EnzymeName enz, ArrayList<ChemicalClassName> chemClasses) throws Exception{
		
		// ADD testing condition for scenario where an enzyme is not in the biosystem.	
		if(!(enz.toString().contains("CYP1A2") || enz.toString().contains("CYP2A6") || enz.toString().contains("CYP2B6")
				|| enz.toString().contains("CYP2C8") || enz.toString().contains("CYP2C9") || enz.toString().contains("CYP2C19")
				|| enz.toString().contains("CYP2D6") || enz.toString().contains("CYP2E1") || enz.toString().contains("CYP3A4"))){
			
			throw new IllegalArgumentException(enz.toString() + " is not a valid CYP isozyme for this system. The selected isozyme must"
					+ "be either of the following: CYP1A2, CYP2A6, CYP2B6, CYP2C8, CYP2C9, CYP2C19, CYP2D6, CYP2E1, or CYP3A4.");
		} else if(!ChemStructureExplorer.isMixture(substrate)){
			boolean validCyp450 = false;
//			System.err.println(chemClasses);
			
			if(!( ChemStructureExplorer.getMajorIsotopeMass(substrate) > 1500.0 || 
					chemClasses.contains(ChemicalClassName.GLYCOSYLATED_COMPOUND) ||
					chemClasses.contains(ChemicalClassName.GLUTATHIONE_CONJUGATE) ||
					chemClasses.contains(ChemicalClassName.SULFATED_COMPOUND) ||
					chemClasses.contains(ChemicalClassName.ACYL_CoA_CONJUGATE) ||
					chemClasses.contains(ChemicalClassName.TETRAPYRROLE) ||
					chemClasses.contains(ChemicalClassName.SACCHARIDE) ||
					chemClasses.contains(ChemicalClassName.ETHER_LIPID) ||
					chemClasses.contains(ChemicalClassName.GLYCEROLIPID) ||
					chemClasses.contains(ChemicalClassName.GLYCEROPHOSPHOLIPID) ||
					chemClasses.contains(ChemicalClassName.GLYCEROL_3_PHOSPHATE_INOSITOL) ||
					chemClasses.contains(ChemicalClassName.SPHINGOLIPID) )) {
				
				if(this.bSys.name.toString() == "HUMAN"){
							
					ReactantPred rp = new ReactantPred();
					IAtomContainerSet inputMolecules = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
					inputMolecules.addAtomContainer(substrate);
					ArrayList<HashMap<String,String>> predictedResult = new ArrayList<HashMap<String,String>>();
					predictedResult = rp.initPreResults(predictedResult,inputMolecules.getAtomContainerCount());
					/**
					 * TODO: add the makeMultiPrediction function
					 */
//					ArrayList<HashMap<String,String>>  res = rp.makeMultiPrediction(enz.toString(), inputMolecules, predictedResult);
					ArrayList<HashMap<String,String>> res = new ArrayList<HashMap<String,String>>();
					
					
					validCyp450 = (res.get(0).get(enz.toString().replace("CYP", "")) == "R");
				}
			}
			
			return validCyp450;
		}
		else{
			return false;
		}
	}
	
	
	public boolean isValid_EC_2_8_2_2_substrate(IAtomContainer substrate, boolean preprocess) throws Exception{
		boolean validEC_2_8_2_2 = false;
		
		IAtomContainer atc = substrate.clone();
		// phenolic hydroxysteroid
		Pattern smp = SmartsPattern.create(
				"[#6]=,:1[#6]=,:[#6][#6]~2=,:[#6]([#6]=,:1)~[#6,#7]~[#6,#7]~[#6,#7]~1-,=[#6,#7]-,=3-,=[#6,#8,#7,#16]-,=[#6,#8,#7,#16]-,=[#6,#8,#7,#16;A]-,=[#6,#7]-,=3~[#6,#7,#16]~[#6,#7,#16]~[#6,#7]~2~1"
				, SilentChemObjectBuilder.getInstance());
		
		validEC_2_8_2_2 = (!smp.matches(atc)) && (isPotentialSubstrateByReactionPatternMatching(atc, EnzymeName.EC_2_8_2_2));
		
		return validEC_2_8_2_2;
	}
	
	
	public boolean isValidSubstrate(IAtomContainer substrate, EnzymeName enz) throws Exception {
		ArrayList<ChemicalClassName> chemClasses = ChemicalClassFinder.AssignChemicalClasses(substrate);
		return isValidSubstrate(substrate, enz, chemClasses);
	}

	public boolean isValidSubstrate(IAtomContainer substrate, EnzymeName enz, ArrayList<ChemicalClassName> chemClasses) throws Exception {
		
		boolean validSubstrate = false;
		
		
		if(  (enz.toString().contains("CYP1A2") || enz.toString().contains("CYP2A6") || enz.toString().contains("CYP2B6")
				|| enz.toString().contains("CYP2C8") || enz.toString().contains("CYP2C9") || enz.toString().contains("CYP2C19")
				|| enz.toString().contains("CYP2D6") || enz.toString().contains("CYP2E1") || enz.toString().contains("CYP3A4"))){
			
			
			return isValidCyp450Substrate(substrate, enz, chemClasses);
		} 
		
		else if(enz.toString().contentEquals("EC_2_8_2_2")){
			return isValid_EC_2_8_2_2_substrate(substrate,false);
		}
		
		else{
			
			/* This will apply to the enzyme for which we do not have machine-learning prediction models. Specific substrate specificity
			 * prediction methods can be implemented for specific enzymes, if the rules take into consideration more than the pattern matching.
			 * For instance, for some molecules, one could incorporate the LogP.
			 */
			validSubstrate =  true;
		}
		return validSubstrate;		
	}
	
	
	
	
	public boolean isPotentialSubstrateByReactionPatternMatching(IAtomContainer substrate, EnzymeName enz) throws SMARTSException, CDKException, IOException{
		boolean isPotentialSubstrate = false;
		
		if(this.bSys.getEnzymeHash().containsKey(enz)){
			Enzyme e = this.bSys.getEnzymeHash().get(enz);
			for( MetabolicReaction mreact : e.getReactionSet()){
				if(ChemStructureExplorer.compoundMatchesReactionConstraints(mreact, substrate)){
					isPotentialSubstrate = true;
					break;
				}
			}
			return isPotentialSubstrate;
		} else {
			throw new IllegalArgumentException(enz.toString() + " is not associated with the biosystem " + this.bSys.name);
		}
	}

	


}

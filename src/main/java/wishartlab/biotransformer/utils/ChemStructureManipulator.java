/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.utils;

import java.util.ArrayList;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.aromaticity.ElectronDonation;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.Cycles;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import ambit2.smarts.SMIRKSManager;

import wishartlab.biotransformer.transformation.MReactionSets;
import wishartlab.biotransformer.transformation.MetabolicReaction;

public class ChemStructureManipulator {
	protected static SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
	
	public ChemStructureManipulator() {
		// TODO Auto-generated constructor stub
		smrkMan.setFlagApplyStereoTransformation(false);
		smrkMan.setFlagCheckResultStereo(true);
		smrkMan.setFlagFilterEquivalentMappings(true);
		smrkMan.setFlagProcessResultStructures(true);
		smrkMan.setFlagAddImplicitHAtomsOnResultProcess(true);
		
	}
	
	/**
	 * This function applies some preprocessing operations, such as setting the
	 * flag of atoms from aromatic rings to "ISAROMATIC", and kelulizing
	 * molecules.
	 * 
	 * @param molecule
	 *            : A molecule of interest
	 * @return : A processed molecule (AtomContainer)
	 * @throws Exception 
	 */

	public static IAtomContainer preprocessContainer(IAtomContainer molecule)
			throws CDKException {
		
		AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(molecule);
		Aromaticity aromaticity = new Aromaticity(ElectronDonation.daylight(), Cycles.or(Cycles.all(), Cycles.all(6)));
		
		for (IBond bond : molecule.bonds()) {
			if (bond.isAromatic() && bond.getOrder() == IBond.Order.UNSET) {
				bond.setFlag(CDKConstants.ISAROMATIC, true);
				bond.getAtom(0).setFlag(CDKConstants.ISAROMATIC, true);
				bond.getAtom(1).setFlag(CDKConstants.ISAROMATIC, true);

			} 
		}
		aromaticity.apply(molecule);
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(molecule);
		sdg.generateCoordinates();		
		IAtomContainer layedOutMol = sdg.getMolecule();
		return layedOutMol;
	}
	public static IAtomContainer standardizeMoleculeWithCopy(IAtomContainer molecule) throws Exception{
		return  standardizeMoleculeWithCopy(molecule, true);
	}

	public static IAtomContainer standardizeMoleculeWithCopy(IAtomContainer molecule, boolean preprocess) throws Exception{
		CDKHydrogenAdder adder = CDKHydrogenAdder.getInstance(SilentChemObjectBuilder.getInstance());
		IAtomContainer molClone =  molecule.clone();
		
		if(preprocess){
			molClone = ChemStructureManipulator.preprocessContainer(molClone);
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(molClone);
		} else {
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(molClone);
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(molClone);
		}
		
		ArrayList<MetabolicReaction> matchedReactions = new ArrayList<MetabolicReaction>();
		for(MetabolicReaction mr : MReactionSets.standardizationReactions){
			if(ChemStructureExplorer.compoundMatchesReactionConstraints(mr, molClone)){
				matchedReactions.add(mr);
			}
		}
		
		for(MetabolicReaction m : matchedReactions){
			while(ChemStructureExplorer.compoundMatchesReactionConstraints(m, molClone)){
				smrkMan.applyTransformation(molClone, m.getSmirksReaction());
				AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(molClone);
				
				// I added the following line because NullPointerExceptions were returned complaining about some atoms with unset implicit hydrogens
				// This occurred when the compound was transformed.			
				adder.addImplicitHydrogens(molClone);
			
			}

		}

		AtomContainerManipulator.suppressHydrogens(molClone);
		return molClone;
	}


}

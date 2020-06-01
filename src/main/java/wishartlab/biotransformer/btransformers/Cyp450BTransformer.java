/**
 * This class implements the class of CYP450Biotransformers, which simulate the transformation of molecules by CYP450 enzymes.
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.btransformers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.silent.SilentChemObjectBuilder;

import wishartlab.biotransformer.biomolecule.Enzyme;
import wishartlab.biotransformer.biomolecule.Enzyme.EnzymeName;
import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.btransformers.Biotransformer;
import wishartlab.biotransformer.transformation.Biotransformation;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;
import wishartlab.biotransformer.utils.ChemStructureExplorer;
import wishartlab.biotransformer.utils.ChemStructureManipulator;
import wishartlab.biotransformer.utils.ChemicalClassFinder;
import wishartlab.biotransformer.utils.FileUtilities;
import wishartlab.biotransformer.utils.Utilities;
import wishartlab.biotransformer.utils.ChemicalClassFinder.ChemicalClassName;


public class Cyp450BTransformer extends Biotransformer {
	
	public Cyp450BTransformer(BioSystemName bioSName) throws JsonParseException, JsonMappingException, IOException, CDKException {
		super(bioSName);
		setCyp450EnzymesAndReactionList();
		
	}
	
	private void setCyp450EnzymesAndReactionList(){
		ArrayList<MetabolicReaction> react = new ArrayList<MetabolicReaction>();
		
		/*
		 * Adding enzymes
		 */
		for(Enzyme enz : this.bSystem.getEnzymesList()){
			if(enz.getName().contains("CYP")){
				this.enzymesList.add(enz);
				react.addAll(enz.getReactionSet());
			}	
		}
		
		/*
		 * adding a list of unique Cyp450 mediated reactions
		 */
		Set<MetabolicReaction> hs = new HashSet<MetabolicReaction>();
		hs.addAll(react);
		react.clear();
		react.addAll(hs);
//		System.out.println("react: " + react.size());
		this.reactionsByGroups.put("cypReactions",react);	
		
		for(MetabolicReaction reaction : react){
			this.reactionsHash.put(reaction.getReactionName(), reaction);
		}
	}



	public ArrayList<Biotransformation> predictCyp450Biotransformations(IAtomContainer substrate, boolean preprocess, boolean filter, double threshold) throws Exception{
		
		try{
			ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
			
			ChemStructureExplorer.addInChIandKey(substrate);
			if(ChemStructureExplorer.isCompoundInorganic(substrate) || ChemStructureExplorer.isMixture(substrate)){
				throw new IllegalArgumentException(substrate.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} else if(ChemStructureExplorer.isBioTransformerValid(substrate)){
					EnzymeName[] cyp450s =  {EnzymeName.CYP1A2, EnzymeName.CYP2A6, EnzymeName.CYP2B6, EnzymeName.CYP2C8, 
							EnzymeName.CYP2C9, EnzymeName.CYP2C19, EnzymeName.CYP2D6, EnzymeName.CYP2E1, EnzymeName.CYP3A4};
					
					ArrayList<Enzyme> cyp450Enzymes = new ArrayList<Enzyme>();
					for(int i = 0; i < cyp450s.length; i++){
						cyp450Enzymes.add(this.bSystem.getEnzymeHash().get(cyp450s[i]));
					}
					
					biotransformations = this.metabolizeWithEnzymes(substrate, cyp450Enzymes, preprocess, filter, threshold);
				
			}
			return biotransformations;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ArrayList<Biotransformation> predictCyp450BiotransformationsForSpecificEnzymes(IAtomContainer substrate, ArrayList<EnzymeName> enzymeNames, boolean preprocess, boolean filter, double threshold) throws Exception{
		
		try{
			ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
			
			ChemStructureExplorer.addInChIandKey(substrate);
			if(ChemStructureExplorer.isCompoundInorganic(substrate) || ChemStructureExplorer.isMixture(substrate)){
				throw new IllegalArgumentException(substrate.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} else if(ChemStructureExplorer.isBioTransformerValid(substrate)) {
					ArrayList<Enzyme> cyp450Enzymes = new ArrayList<Enzyme>();
					for(int i = 0; i < enzymeNames.size(); i++){
						cyp450Enzymes.add(this.bSystem.getEnzymeHash().get(enzymeNames.get(i)));
					}
					
					biotransformations = this.metabolizeWithEnzymes(substrate, cyp450Enzymes, preprocess, filter, threshold);	
				
			}
			return biotransformations;
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	

	public ArrayList<Biotransformation> predictCyp450Biotransformations(IAtomContainerSet substrates, boolean preprocess, boolean filter, double threshold) throws Exception{
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		
		for(IAtomContainer sub : substrates.atomContainers()){
			biotransformations.addAll( predictCyp450Biotransformations(sub, preprocess, filter, threshold) );		
		}
		
		return biotransformations;
	}
	
	public ArrayList<Biotransformation> predictCyp450Biotransformations(String sdfFileName, boolean preprocess, boolean filter, double threshold) throws Exception{
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		
		IAtomContainerSet containers = FileUtilities.parseSdf(sdfFileName);
		biotransformations = predictCyp450Biotransformations(containers, preprocess, filter, threshold);

		return biotransformations;
	}
	
	
	public ArrayList<Biotransformation> predictCyp450BiotransformationChain(IAtomContainer substrate, boolean preprocess, boolean filter, int nrOfSteps, double threshold) throws Exception{
		IAtomContainerSet molecules = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		molecules.addAtomContainer(substrate);
		return this.predictCyp450BiotransformationChain(molecules, preprocess, filter, nrOfSteps, threshold);
	}
	
	public ArrayList<Biotransformation> predictCyp450BiotransformationChain(IAtomContainerSet substrates, boolean preprocess, boolean filter, int nrOfSteps, double threshold) throws Exception{
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		IAtomContainerSet containers = substrates;
		int counter = 0;
		while(nrOfSteps>0){
			counter++;
			ArrayList<Biotransformation> currentBiotransformations = this.predictCyp450Biotransformations(containers, preprocess, filter, threshold);
			nrOfSteps--;
			if(!currentBiotransformations.isEmpty()){
				biotransformations.addAll(currentBiotransformations);
				containers.removeAllAtomContainers();
				containers = extractProductsFromBiotransformations(currentBiotransformations);				
			}
			else{
				break;
			}
		}

		return Utilities.selectUniqueBiotransformations(biotransformations);
	}	
	
	public void  simulateCyp450MetabolismAndSaveToSDF(IAtomContainerSet containers, int nrOfSteps, Double scoreThreshold, String outputFolder, boolean annotate) throws Exception {
		
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){

				String identifier = molecule.getProperty(CDKConstants.TITLE);
				if(identifier == null){
					identifier = molecule.getProperty("Name");
					if(identifier == null){
						identifier = molecule.getProperty("InChiKey");
						if(identifier == null){
							identifier = this.inchiGenFactory.getInChIGenerator(molecule).getInchiKey();
						}
					}
				}
				System.out.println(identifier);
				if(identifier.contains("/") || identifier.contains(":")){
					System.out.println("The identifier contains characters that cannot be used to create a file. / and : would be replaced wit - and _, respectively");
					identifier = identifier.replace(":", "-").replace("/", "_");
				}
				ArrayList<Biotransformation> biotransformations = this.predictCyp450BiotransformationChain(molecule, true, true, nrOfSteps, scoreThreshold);
//				System.out.println(biotransformations.size() + " biotransformations.");
				this.saveBioTransformationProductsToSdf(biotransformations, outputFolder + "/" + identifier + "_CYP450_based_metabolites.sdf", annotate);			
			}
		}		
	}
	
	
	public void  simulateCyp450MetabolismAndSaveToSingleSDF(IAtomContainerSet containers, int nrOfSteps, Double scoreThreshold, String outputFileName, boolean annotate) throws Exception {
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){
				biotransformations.addAll(this.predictCyp450BiotransformationChain(molecule, true, true, nrOfSteps, scoreThreshold));
//				System.out.println(biotransformations.size() + " biotransformations.");
			}
			
			this.saveBioTransformationProductsToSdf(biotransformations, outputFileName, annotate);			

		}		
	}
	
	public void printStatistics(){
//		System.out.println(this.reactionsByGroups.get("cypReactions").size());
		int count = 0;
		for(Enzyme e: this.enzymesList){
//			System.out.println(e.getName() + " : " + e.getReactionsNames().size());
			count = count + e.getReactionsNames().size();
		}
		System.out.println("Humber of enzymes: " + this.enzymesList.size());
		System.out.println("Humber of biotransformation rules: " + this.reactionsByGroups.get("cypReactions").size());
		System.out.println("Humber of enzyme-biotransformation rules associations: " + count);
	}
	
}

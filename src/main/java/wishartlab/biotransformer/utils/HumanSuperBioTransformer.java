/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;

import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.btransformers.Cyp450BTransformer;
import wishartlab.biotransformer.btransformers.ECBasedBTransformer;
import wishartlab.biotransformer.btransformers.HGutBTransformer;
import wishartlab.biotransformer.btransformers.Phase2BTransformer;
import wishartlab.biotransformer.transformation.Biotransformation;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import wishartlab.biotransformer.transformation.MetabolicPathway.MPathwayName;
import wishartlab.biotransformer.utils.ChemicalClassFinder.ChemicalClassName;
import predicition.P2Filter;

public class HumanSuperBioTransformer {
	
	protected ECBasedBTransformer ecb		 		= new ECBasedBTransformer(BioSystemName.HUMAN);
	protected Cyp450BTransformer cyb 				= new Cyp450BTransformer(BioSystemName.HUMAN);
	protected HGutBTransformer hgb 					= new HGutBTransformer();
	protected Phase2BTransformer p2b 				= new Phase2BTransformer(BioSystemName.HUMAN);
	protected LinkedHashMap<String, LinkedHashMap<String, String>> compoundDictionary		
													= new LinkedHashMap<String, LinkedHashMap<String, String>>();
	
	protected P2Filter p2filter 					= new P2Filter();
	protected LinkedHashMap<String, MetabolicReaction> combinedReactionsHash							
													= new LinkedHashMap<String, MetabolicReaction>();
	
	public SmilesParser smiParser					= ecb.getSmiParser();
	public SmilesGenerator smiGen 		=           new SmilesGenerator().isomeric();
	
	
	public HumanSuperBioTransformer() throws IOException, ParseException, CDKException {

		for(Map.Entry<String, MetabolicReaction> m : this.ecb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(m.getKey())){
				this.combinedReactionsHash.put(m.getKey(), m.getValue());
			}
		}
		
		for(Map.Entry<String, MetabolicReaction> n : this.cyb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(n.getKey())){
				this.combinedReactionsHash.put(n.getKey(), n.getValue());
			}
		}

		for(Map.Entry<String, MetabolicReaction> p : this.hgb.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(p.getKey())){
				this.combinedReactionsHash.put(p.getKey(), p.getValue());
			}
		}
		
		for(Map.Entry<String, MetabolicReaction> o : this.p2b.reactionsHash.entrySet()){
			if(! this.combinedReactionsHash.containsKey(o.getKey())){
				this.combinedReactionsHash.put(o.getKey(), o.getValue());
			}
		}
	}

	public InChIGeneratorFactory getInChIGenFactory(){
		return this.ecb.inchiGenFactory;
	}
	public ArrayList<Biotransformation> simulateHumanSuperbioMetabolism(IAtomContainer target) throws Exception{
		return 	simulateHumanSuperbioMetabolism(target, 0.5);
	}
	
	
	/**
	 * extract the ecbased transformation.
	 * @param target
	 * @param scoreThreshold
	 * @return IAtomContainerSet
	 * @throws Exception
	 */
	public IAtomContainerSet simulateHumanECBased(IAtomContainer target, Double scoreThreshold) throws Exception {
		
		IAtomContainerSet products = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		products.addAtomContainer(target);
		biotransformations.addAll(this.ecb.simulateECBasedPhaseIMetabolismChain(target, true, true, 1, scoreThreshold));
		products.add(this.ecb.extractProductsFromBiotransformations(biotransformations));
		products = ChemStructureExplorer.uniquefy(products);
		
		return products;
	}
	
	
	/**
	 * 
	 * @param target
	 * @param scoreThreshold
	 * @return
	 * @throws Exception
	 */
	public IAtomContainerSet simulateHumanCYP450(IAtomContainer target, Double scoreThreshold) throws Exception {
		
		IAtomContainerSet products = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		ArrayList<Biotransformation> cyp450Biots = new  ArrayList<Biotransformation>();
		cyp450Biots.addAll(this.cyb.predictCyp450Biotransformations(target, true, true, scoreThreshold));
		IAtomContainerSet cyp450Prods = this.ecb.extractProductsFromBiotransformations(cyp450Biots);
		products.add(cyp450Prods);
		products = ChemStructureExplorer.uniquefy(products);
		return products;
	}
	
	
	/**
	 * 
	 * @param target
	 * @param scoreThreshold
	 * @return
	 * @throws Exception
	 */
	public IAtomContainerSet simulateHumanGutMicrobial(IAtomContainer target, Double scoreThreshold) throws Exception {
		
		// TODO
		return null;
	}
	
	
	/**
	 * 
	 * @param target
	 * @param scoreThreshold
	 * @return
	 * @throws Exception
	 */
	public IAtomContainerSet simulateHumanPhaseII(IAtomContainer target, Double scoreThreshold) throws Exception {
		
		// TODO
		return null;
	}
	
	
	
	public ArrayList<Biotransformation> simulateHumanSuperbioMetabolism(IAtomContainer target, Double scoreThreshold) throws Exception{
		 ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		 
			if(ChemStructureExplorer.isBioTransformerValid(target)){
				 IAtomContainer molecule = ChemStructureManipulator.standardizeMoleculeWithCopy(target);
				 IAtomContainerSet products = DefaultChemObjectBuilder
							.getInstance().newInstance(IAtomContainerSet.class);
				 
				 IAtomContainerSet phaseIISubstrates = DefaultChemObjectBuilder
							.getInstance().newInstance(IAtomContainerSet.class);
				 
				 IAtomContainerSet phaseIINonSubstrates = DefaultChemObjectBuilder
							.getInstance().newInstance(IAtomContainerSet.class);
				 
				 products.addAtomContainer(molecule);

				 ArrayList<ChemicalClassName> chemClasses = ChemicalClassFinder.AssignChemicalClasses(molecule);
				 

				if(!(chemClasses.contains(ChemicalClassName.ETHER_LIPID) ||
						chemClasses.contains(ChemicalClassName.GLYCEROLIPID) ||
						chemClasses.contains(ChemicalClassName.GLYCEROPHOSPHOLIPID) ||
						chemClasses.contains(ChemicalClassName.GLYCEROL_3_PHOSPHATE_INOSITOL) ||
						chemClasses.contains(ChemicalClassName.SPHINGOLIPID) )) {	
//					 System.out.println("Predicting EC  metabolism round 1");
					/*
					 *  Apply ECBased
					 */		 
					
					System.out.println("\n\n===========================================");
					System.out.println("Predicting EC metabolism");
					System.out.println("===========================================\n\n");
					System.out.println("Smiles before EC-based simulation: " + this.smiGen.create(molecule));
					 biotransformations.addAll(this.ecb.simulateECBasedPhaseIMetabolismChain(molecule, true, true, 1, scoreThreshold));
					 products.add(this.ecb.extractProductsFromBiotransformations(biotransformations));
					 products = ChemStructureExplorer.uniquefy(products);
					 System.out.println("Number of EC-based biotransformations after first pass: " + biotransformations.size());
					 System.out.println("Number of EC-based metabolites after first pass: " + products.getAtomContainerCount());
//					 System.out.println("Predicting CYP450 metabolism");
					/*
					 *  Apply CYP450 metabolism
					 */	 
					 
					System.out.println("\n\n===========================================");
					System.out.println("Predicting CYP450 metabolism");
					System.out.println("===========================================\n\n");
					 ArrayList<Biotransformation> cyp450Biots = new  ArrayList<Biotransformation>();
					 for(IAtomContainer met : products.atomContainers()){
//						 System.out.println("predicting CYP450 metabolites for: " + this.ecb.smiGen.create(met));
						 cyp450Biots.addAll(this.cyb.predictCyp450Biotransformations(met, true, true, scoreThreshold));
					 }
					 IAtomContainerSet cyp450Prods = this.ecb.extractProductsFromBiotransformations(cyp450Biots);
					 products.add(cyp450Prods);
					 products = ChemStructureExplorer.uniquefy(products);
					 biotransformations.addAll(cyp450Biots);
					 System.out.println("Number of CYP450 products: " + cyp450Prods.getAtomContainerCount());

					 
					/*
					 *  Apply ECBased
					 *  Some products of CYP450 might be unstable, such as epoxides, and will be further transformed.
					 *  
					 *  !!!!!!!! MAKE SURE THESE DO NOT INCLUDE HYDROLYSIS REATIONS AND SOME OTHERS. THAT OFTEN DO NOT OCCUR AFTER CYP450
					 */		 
					 
					 LinkedHashMap<String, IAtomContainerSet> partitionedMolecules = this.p2filter.partitionSetForPhaseIIMetabolism(products);
					 
					 phaseIISubstrates.add(partitionedMolecules.get("phaseIISubstrates"));
					 phaseIINonSubstrates.add(partitionedMolecules.get("phaseIINonSubstrates"));
					 
					 System.out.println("phaseIISubstrates: " + phaseIISubstrates.getAtomContainerCount());
					 
					 if	(phaseIINonSubstrates.getAtomContainerCount()>0){
						System.out.println("\n\n===========================================");
						System.out.println("Predicting EC metabolism - 2nd pass");
						System.out.println("===========================================\n\n");
						 ArrayList<Biotransformation> ecBiotsSecondPass = new  ArrayList<Biotransformation>();
						 ecBiotsSecondPass = this.ecb.simulateECBasedPhaseIMetabolismChain(partitionedMolecules.get("phaseIINonSubstrates"), true, true, 1, scoreThreshold);
						 biotransformations.addAll(ecBiotsSecondPass);
						 IAtomContainerSet ecBiotsSecondPassProducts = this.ecb.extractProductsFromBiotransformations(ecBiotsSecondPass);					 
						 products.add(ecBiotsSecondPassProducts);
						 products = ChemStructureExplorer.uniquefy(products);
						 
						 /*
						  *   Only the ones suitable for phaseII will land into the gut
						  */
						 
						 LinkedHashMap<String, IAtomContainerSet> partitionedMoleculesAfterEC2 = this.p2filter.partitionSetForPhaseIIMetabolism(ecBiotsSecondPassProducts);

						 phaseIISubstrates.add(partitionedMoleculesAfterEC2.get("phaseIISubstrates"));
						 
						 phaseIINonSubstrates.add(partitionedMoleculesAfterEC2.get("phaseIINonSubstrates"));	
					}
					 

					/*
					 *  Apply Human gut metabolism
					 */

					 
					 IAtomContainerSet hGutSubstrates = phaseIISubstrates;
					 
					 for(IAtomContainer a : phaseIINonSubstrates.atomContainers()){
						 /*
						  * add some large molecules, such as tannins, which can be degraded by bacteria
						  */
						 if(ChemStructureExplorer.getMajorIsotopeMass(a) >= 900.0){
							 hGutSubstrates.addAtomContainer(a);
						 }
					 }
					 System.out.println("Predicting human gut metabolism of " + products.getAtomContainerCount() + " metabolites");
					 
					 ArrayList<Biotransformation> hGutBiots = new  ArrayList<Biotransformation>();
					 
					System.out.println("\n\n===========================================");
					System.out.println("Predicting Human Gut Microbial Degradation");
					System.out.println("===========================================\n\n");
					 for(IAtomContainer hGutSub : hGutSubstrates.atomContainers()) {
						 if(ChemStructureExplorer.isMetabolizablePolyphenolOrDerivative(hGutSub)) {
							 if(chemClasses.contains(ChemicalClassName.CURCUMINOID)|| chemClasses.contains(ChemicalClassName.STILBENOID)) {
								 hGutBiots.addAll(this.hgb.applyGutMicrobialMetabolismHydrolysisAndRedoxChain(hGutSubstrates, true, true, 1, scoreThreshold));
							 }
							 else {
								 /**
								  * TO-DO:
								  * int nSteps = 8;
								  * currenthGutProducts = 
								  * 
								  * While nStep >8 and not (currenthGutProducts contains popyphenol_dead_end_agycone_metabolites){
								  * 	nSteps--
								  * 	this.hgb.applyGutMicrobialMetabolismHydrolysisAndRedoxStep(hGutSubstrates, true, true, 0.5)
								  * }
								  */
								 hGutBiots.addAll(this.hgb.applyGutMicrobialMetabolismHydrolysisAndRedoxChain(hGutSubstrates, true, true, 8, scoreThreshold));
							 }
						 }
						 else {
							 hGutBiots.addAll(this.hgb.applyGutMicrobialMetabolismHydrolysisAndRedoxChain(hGutSubstrates, true, true, 1, scoreThreshold));
						 }
					 }

					 System.out.println("Number of human gut biotransformations: " + hGutBiots.size());
					 biotransformations.addAll(hGutBiots);
					 IAtomContainerSet hGutProducts = this.ecb.extractProductsFromBiotransformations(hGutBiots);
					 System.out.println("Number of human gut metabolites: " + hGutProducts.getAtomContainerCount());
					 products.add(hGutProducts);
					 
					 LinkedHashMap<String, IAtomContainerSet> partitionedMoleculesAfterHGut = this.p2filter.partitionSetForPhaseIIMetabolism(hGutProducts);
					 
					 phaseIISubstrates.add(partitionedMoleculesAfterHGut.get("phaseIISubstrates"));
					 phaseIINonSubstrates.add(partitionedMoleculesAfterHGut.get("phaseIINonSubstrates"));
					 
					 phaseIISubstrates = ChemStructureExplorer.uniquefy(phaseIISubstrates);
					 
					 if(phaseIISubstrates.getAtomContainerCount()>0){
						 /*
						 *  Apply Phase II metabolism
						 */	 
						System.out.println("Predicting Phase 2 metabolism for " + phaseIISubstrates.getAtomContainerCount());
						System.out.println("\n\n===========================================");
						System.out.println("Predicting human phase 2 metabolism");
						System.out.println("===========================================\n\n");
						ArrayList<Biotransformation> phaseIIBiots = new  ArrayList<Biotransformation>();
						phaseIIBiots.addAll(this.p2b.applyPhase2TransformationsChainAndReturnBiotransformations(phaseIISubstrates, true, true, true, 1, scoreThreshold));
						products.add(this.ecb.extractProductsFromBiotransformations(phaseIIBiots));
						products = ChemStructureExplorer.uniquefy(products);
						biotransformations.addAll(phaseIIBiots);
						System.out.println("Number of PhaseII biotransformations: " + phaseIIBiots.size() + "\n\n\n");				
					 }

				}
					
				else {
					System.out.println("\n\n===========================================");
					System.out.println("Predicting EC metabolism");
					System.out.println("===========================================\n\n");
					System.out.println("Smiles before EC-based simulation: " + this.smiGen.create(molecule));
					 biotransformations.addAll(this.ecb.simulateECBasedPhaseIMetabolismChain(molecule, true, true, 1, scoreThreshold));
					 products.add(this.ecb.extractProductsFromBiotransformations(biotransformations));
					 products = ChemStructureExplorer.uniquefy(products);
					 System.out.println("Number of EC-based biotransformations after first pass: " + biotransformations.size());
					 System.out.println("Number of metabolites after first EC pass: " + products.getAtomContainerCount());
					 /*
					 *  Apply ECBased
					 *  Some products of CYP450 might be unstable, such as epoxides, and will be further transformed.
					 *  
					 *  !!!!!!!! MAKE SURE THESE DO NOT INCLUDE HYDROLYSIS REATIONS AND SOME OTHERS. THAT OFTEN DO NOT OCCUR AFTER CYP450
					 */		 
					 
					 LinkedHashMap<String, IAtomContainerSet> partitionedMolecules = this.p2filter.partitionSetForPhaseIIMetabolism(products);
					 
					 phaseIISubstrates.add(partitionedMolecules.get("phaseIISubstrates"));
					 phaseIINonSubstrates.add(partitionedMolecules.get("phaseIINonSubstrates"));
					 

					 System.out.println("Predicting human gut metabolism of " + products.getAtomContainerCount() + " metabolites");
					/*
					 *  Apply Human gut metabolism
					 */
					 
						 for(IAtomContainer atc : products.atomContainers()){
							 System.out.println(this.ecb.smiGen.create(atc));
						 }
					 
					 IAtomContainerSet hGutSubstrates = phaseIISubstrates;
					 
					 for(IAtomContainer a : phaseIINonSubstrates.atomContainers()){
						 /*
						  * add some large molecules, such as tannins, which can be degraded by bacteria
						  */
						 if(ChemStructureExplorer.getMajorIsotopeMass(a) >= 900.0){
							 hGutSubstrates.addAtomContainer(a);
						 }
					 }
					System.out.println("\n\n===========================================");
					System.out.println("Predicting Human Gut Microbial Degradation");
					System.out.println("===========================================\n\n");
				 
//					 System.out.println(hGutSubstrates == null);
//					 System.out.println(hGutSubstrates.getAtomContainerCount());
					 ArrayList<Biotransformation> hGutBiots = new  ArrayList<Biotransformation>();
					 hGutBiots.addAll(this.hgb.applyGutMicrobialMetabolismHydrolysisAndRedoxChain(hGutSubstrates, true, true, 1, scoreThreshold));
					 System.out.println("Number of human gut biotransformations: " + hGutBiots.size());
					
//					 for(int i = 0; i < hGutBiots.size(); i++){
//						 System.out.println(hGutBiots.get(i).getReactionType());
//					 }
					 
					 biotransformations.addAll(hGutBiots);
					 IAtomContainerSet hGutProducts = this.ecb.extractProductsFromBiotransformations(hGutBiots);
					 System.out.println("Number of human gut metabolites: " + hGutProducts.getAtomContainerCount());
					 products.add(hGutProducts);
//					 products = ChemStructureExplorer.uniquefy(products);
					 
					 LinkedHashMap<String, IAtomContainerSet> partitionedMoleculesAfterHGut = this.p2filter.partitionSetForPhaseIIMetabolism(hGutProducts);
					 
					 phaseIISubstrates.add(partitionedMoleculesAfterHGut.get("phaseIISubstrates"));
					 phaseIINonSubstrates.add(partitionedMoleculesAfterHGut.get("phaseIINonSubstrates"));
					 
					 phaseIISubstrates = ChemStructureExplorer.uniquefy(phaseIISubstrates);
					 
//					 System.out.println("Predicting phase II metabolism of " + phaseIISubstrates.getAtomContainerCount() + " out of " + products.getAtomContainerCount());
					 
					 
					 if(phaseIISubstrates.getAtomContainerCount()>0){
						 
						 /*
						 *  Apply Phase II metabolism
						 */	 
						System.out.println("Predicting Phase 2 metabolism for " + phaseIISubstrates.getAtomContainerCount() + " metabolites.");
						System.out.println("\n\n===========================================");
						System.out.println("Predicting human phase 2 metabolism");
						System.out.println("===========================================\n\n");
						 ArrayList<Biotransformation> phaseIIBiots = new  ArrayList<Biotransformation>();
						 phaseIIBiots.addAll(this.p2b.applyPhase2TransformationsChainAndReturnBiotransformations(phaseIISubstrates, true, true, true, 1, scoreThreshold));
						 products.add(this.ecb.extractProductsFromBiotransformations(phaseIIBiots));
						 products = ChemStructureExplorer.uniquefy(products);
						 biotransformations.addAll(phaseIIBiots);
						 System.out.println("Number of PhaseII biotransformations: " + Utilities.selectUniqueBiotransformations(phaseIIBiots).size() + "\n\n\n");				
					 }
				}

			}
		 
		IAtomContainerSet a = this.extractProductsFromBiotransformations(biotransformations);
		System.out.println("Number of predicted compounds: " +  ChemStructureExplorer.uniquefy(a).getAtomContainerCount());
		 
		 System.out.println("Number of predicted biotransformations: " + biotransformations.size());
		 ArrayList<Biotransformation> uniqueBiotransformations = Utilities.selectUniqueBiotransformations(biotransformations);
		 System.out.println("Number of unique predicted biotransformations: " + uniqueBiotransformations.size());
		 return uniqueBiotransformations;
	}

	
	
	
	public void simulateHumanSuperbioMetabolismAndSaveToSDF(IAtomContainer molecule, String outputFileName, boolean annotate) throws Exception{
		ArrayList<Biotransformation> biotransformations = this.simulateHumanSuperbioMetabolism(molecule);

		this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
	}
		
	public void simulateHumanSuperbioMetabolismFromSDF(String inputFileName, boolean annotate) throws Exception {
		simulateHumanSuperbioMetabolismFromSDF(inputFileName, "data", annotate);
	}
	
	public void simulateHumanSuperbioMetabolismFromSDF(String inputFileName, String outputFolder, boolean annotate) throws Exception {
		
		IAtomContainerSet containers = FileUtilities.parseSdfAndAddTitles(inputFileName, this.getInChIGenFactory());
		int nr = 0;
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){
				try{
					String identifier = molecule.getProperty(CDKConstants.TITLE);
					identifier = identifier.replace(":", "-").replace("/", "_");
					this.simulateHumanSuperbioMetabolismAndSaveToSDF(molecule, outputFolder + "/" + identifier + "_BioT_sim_metabolites.sdf", annotate);

				}
				catch(Exception e){
					System.err.println("Could not predicted metabolism for molecule nr. " + nr);
					System.err.println(e.getLocalizedMessage());
				}
			}			
		}
		
	}
	
	public void  simulateHumanSuperbioMetabolismAndSaveToSDF(IAtomContainerSet containers, String outputFolder, boolean annotate) throws Exception {
		int nr = 0;
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){

				try{
					String identifier = molecule.getProperty(CDKConstants.TITLE);
					if(identifier == null){
						identifier = molecule.getProperty("Name");
						if(identifier == null){
							identifier = molecule.getProperty("$MolName"); 
							if(identifier == null){
								identifier = molecule.getProperty("InChIKey");
								if(identifier == null){
									identifier = this.getInChIGenFactory().getInChIGenerator(molecule).getInchiKey();
								}
							}

						}
						molecule.setProperty(CDKConstants.TITLE, identifier);
					}
					identifier = identifier.replace(":", "-").replace("/", "_");
					this.simulateHumanSuperbioMetabolismAndSaveToSDF(molecule, outputFolder + "/" + identifier + "_BioT_sim_metabolites.sdf", annotate);

				}
				catch(Exception e){
					System.err.println("Could not predicted metabolism for molecule nr. " + nr);
					System.err.println(e.getLocalizedMessage());
				}

			}
		}		
	}
	
	
	
	
	public void simulateHumanSuperbioMetabolismAndSaveToCSV(IAtomContainer molecule, String outputFileName, boolean annotate) throws Exception{
		ArrayList<Biotransformation> biotransformations = this.simulateHumanSuperbioMetabolism(molecule);

		this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
	}
		
	
	
	public ArrayList<Biotransformation>  simulateAllHumanMetabolism(IAtomContainerSet containers, double scoreThreshold) throws Exception {
		int nr = 0;
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){

				try{
					biotransformations.addAll(simulateOneStepAllHuman(molecule, scoreThreshold));
				}
				catch(Exception e){
					System.err.println("Could not predicted metabolism for molecule nr. " + nr);
					System.err.println(e.getLocalizedMessage());
				}

			}
		}
		return biotransformations;
	}
	

	
	
	public void simulateAllHumanMetabolismAndSavetoCSV(IAtomContainer molecule, String outputFileName, double scoreThreshold, boolean annotate) throws Exception {
		try{
			ArrayList<Biotransformation> biotransformations = simulateOneStepAllHuman(molecule, scoreThreshold);
			this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}

	
	public void simulateAllHumanMetabolismAndSavetoSDF(IAtomContainer molecule, String outputFileName, double scoreThreshold, boolean annotate) throws Exception {
		try{
			ArrayList<Biotransformation> biotransformations = simulateOneStepAllHuman(molecule, scoreThreshold);
			this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
	
	
	public void predictAllHumanBiotransformationChainAndSaveToCSV(IAtomContainer substrate, int nrOfSteps, double threshold, String outputFileName, boolean annotate){
		try{
			
			ArrayList<Biotransformation> biotransformations = predictAllHumanBiotransformationChain(substrate, nrOfSteps, threshold);
			this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
	
	
	public void predictAllHumanBiotransformationChainAndSaveToCSV(IAtomContainerSet containers, int nrOfSteps, double threshold, String outputFileName, boolean annotate){
		try{
			
			ArrayList<Biotransformation> biotransformations = predictAllHumanBiotransformationChain(containers, nrOfSteps, threshold);
			this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
	
	public void predictAllHumanBiotransformationChainAndSaveToSDF(IAtomContainerSet containers, int nrOfSteps, double threshold, String outputFileName, boolean annotate){
		try{
			
			ArrayList<Biotransformation> biotransformations = predictAllHumanBiotransformationChain(containers, nrOfSteps, threshold);
			this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}	
	
	public void simulateAllHumanMetabolismAndSavetoSDF(IAtomContainerSet containers, String outputFileName, double scoreThreshold, boolean annotate) throws Exception {
		try{
			ArrayList<Biotransformation> biotransformations = simulateAllHumanMetabolism(containers, scoreThreshold);
			this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
	

	public LinkedHashMap<String, IAtomContainerSet> partitionSetForPhaseIIMetabolism(IAtomContainerSet products) throws CDKException, Exception{
		return this.p2filter.partitionSetForPhaseIIMetabolism(products);
	}
	
	public void simulateHumanSuperbioMetabolismFromSDFtoSingleSDF(String inputFileName, String outputFileName, boolean annotate) throws Exception {
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
				
		int nr = 0;
		IAtomContainerSet containers = FileUtilities.parseSdfAndAddTitles(inputFileName, this.getInChIGenFactory());
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){
				nr++;
				System.out.println("molecule nr. " +nr);
				
				try{
					
					ArrayList<Biotransformation> bts = this.simulateHumanSuperbioMetabolism(molecule);
					System.out.println(bts.size() + " biotransformations");
					biotransformations.addAll(bts); 	
				}
				catch(Exception e){
					System.err.println("Could not predicted metabolism for molecule nr. " + nr);
					System.err.println(e.getLocalizedMessage());
				}
			}	
		}
//		this.ecb.saveBioTransformationsToSDF(biotransformations, outputFileName);
		this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
	}	
	
	
	public void simulateHumanSuperbioMetabolismFromSDFtoSingleCSV(String inputFileName, String outputFileName, boolean annotate) throws Exception {
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
				
		int nr = 0;
		IAtomContainerSet containers = FileUtilities.parseSdfAndAddTitles(inputFileName, this.getInChIGenFactory());
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){
				nr++;
				System.out.println("molecule nr. " +nr);
				
				try{

					ArrayList<Biotransformation> bts = this.simulateHumanSuperbioMetabolism(molecule);
					System.out.println(bts.size() + " biotransformations");
					biotransformations.addAll(bts); 	
				}
				catch(Exception e){
					System.err.println("Could not predicted metabolism for molecule nr. " + nr);
					System.err.println(e.getLocalizedMessage());
				}
			}	
		}
//		this.ecb.saveBioTransformationsToSDF(biotransformations, outputFileName);
		this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
	}
	
	
	public ArrayList<Biotransformation> simulateOneStepAllHuman(IAtomContainer target) throws Exception{
		return simulateOneStepAllHuman(target, 0.5);
	}
	
	public ArrayList<Biotransformation> simulateOneStepAllHuman(IAtomContainer target, double scoreThreshold) throws Exception {
		 ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		 IAtomContainer molecule = ChemStructureManipulator.standardizeMoleculeWithCopy(target);
		 IAtomContainerSet products = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		 
		if(ChemStructureExplorer.isBioTransformerValid(molecule)){
			
			ChemicalClassName clname = ChemicalClassFinder.findChemicalClass(molecule);
			
			
			
			if( !(clname == ChemicalClassName.ETHER_LIPID || clname == ChemicalClassName.GLYCEROLIPID || 
					clname == ChemicalClassName.GLYCEROPHOSPHOLIPID ||
					clname == ChemicalClassName.SPHINGOLIPID || clname == ChemicalClassName.GLYCEROL_3_PHOSPHATE_INOSITOL ) ) {
				
				/*
				 *  Apply ECBased
				 *  
				 */	
				biotransformations.addAll(this.ecb.simulateECBasedPhaseIMetabolismChain(molecule, true, true, 1, scoreThreshold));

				/*
				 *  Apply CYp450
				 *  
				 */
				biotransformations.addAll(this.cyb.predictCyp450Biotransformations(molecule, true, true, scoreThreshold));
							
				/*
				 * Human gut metabolism
				 */
//				System.out.println("Predict Human gut metabolism");
				biotransformations.addAll(this.hgb.simulateGutMicrobialMetabolism(molecule, true, true, 1, scoreThreshold));
				
				/*
				 *  Apply Phase II metabolism
				 */	
//				System.out.println("Predict Human Phase II metabolism");
				products.addAtomContainer(molecule);
				biotransformations.addAll(this.p2b.applyPhase2TransformationsChainAndReturnBiotransformations(products, true, false, true, 1, scoreThreshold));
				
				
			
			} else {
				if(ChemicalClassFinder.isEtherLipid(molecule)){
					biotransformations.addAll(this.ecb.applyPathwaySpecificBiotransformations(molecule, MPathwayName.ETHER_LIPID_METABOLISM, true, true, scoreThreshold));
				}
				if(ChemicalClassFinder.isGlyceroLipid(molecule)){
					biotransformations.addAll(this.ecb.applyPathwaySpecificBiotransformations(molecule, MPathwayName.GLYCEROLIPID_METABOLISM, true, true, scoreThreshold));
				}
				if(ChemicalClassFinder.isGlycerol_3_PhosphateInositol(molecule)){
					biotransformations.addAll(this.ecb.applyPathwaySpecificBiotransformations(molecule, MPathwayName.INOSITOL_PHOSPHATE_METABOLISM, true, true, scoreThreshold));
				}								
				if(ChemicalClassFinder.isGlycerophosphoLipid(molecule)){
					biotransformations.addAll(this.ecb.applyPathwaySpecificBiotransformations(molecule, MPathwayName.GLYCEROPHOSPHOLIPID_METABOLISM, true, true, scoreThreshold));
				}		
				if(ChemicalClassFinder.isSphingoLipid(molecule)){
//					System.out.println("Is Sphingolipid");
					biotransformations.addAll(this.ecb.applyPathwaySpecificBiotransformations(molecule, MPathwayName.SPHINGOLIPID_METABOLISM, true, true, scoreThreshold));
				}
				if(ChemicalClassFinder.isC24BileAcid(molecule) || ChemicalClassFinder.isC23BileAcid(molecule)){
//					System.out.println("Is Sphingolipid");
					biotransformations.addAll(this.ecb.applyPathwaySpecificBiotransformations(molecule, MPathwayName.BILE_ACID_METABOLISM, true, true, scoreThreshold));
				}
				
			}			
		}


		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	
	public ArrayList<Biotransformation> applyPathwaySpecificBiotransformations(IAtomContainer molecule, MPathwayName pathway, boolean preprocess, boolean filter, double scoreThreshold) throws Exception{
		return this.ecb.applyPathwaySpecificBiotransformations(molecule, pathway, preprocess, filter, scoreThreshold);
	}
	
	public ArrayList<Biotransformation> simulateGutMicrobialMetabolism(IAtomContainer molecule, int nrOfSteps, double scoreThreshold) throws Exception{
		return this.hgb.simulateGutMicrobialMetabolism(molecule, true, true, nrOfSteps, scoreThreshold);
	}
	
	public ArrayList<Biotransformation> applyPhaseIITransformationsChainAndReturnBiotransformations(IAtomContainerSet products, int nrOfSteps, double scoreThreshold) throws Exception{
		return this.p2b.applyPhase2TransformationsChainAndReturnBiotransformations(products, true, true, true, nrOfSteps, scoreThreshold);
	}
	
	public ArrayList<Biotransformation> simulateECBasedPhaseIMetabolismChain(IAtomContainerSet molecules, boolean preprocess, boolean filter,int nrOfSteps, Double scoreThreshold) throws Exception{
		return this.ecb.simulateECBasedPhaseIMetabolismChain(molecules, true, true, nrOfSteps, scoreThreshold);
	}	
	
	public ArrayList<Biotransformation> simulateOneStepAllHuman(IAtomContainerSet targets, double scoreThreshold) throws Exception {
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		for(IAtomContainer target : targets.atomContainers()){
//			System.out.println("AtomContainer");
			biotransformations.addAll(simulateOneStepAllHuman(target, scoreThreshold));
		}
		
		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	
	public ArrayList<Biotransformation> predictAllHumanBiotransformationChain(IAtomContainer substrate, int nrOfSteps, double threshold) throws Exception{
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		IAtomContainerSet containers = DefaultChemObjectBuilder
				.getInstance().newInstance(IAtomContainerSet.class);
		
		containers.addAtomContainer(substrate);
		while(nrOfSteps>0){
			ArrayList<Biotransformation> currentBiotransformations = simulateOneStepAllHuman(containers, threshold);
			nrOfSteps--;
			if(!currentBiotransformations.isEmpty()){
				biotransformations.addAll(currentBiotransformations);
				containers.removeAllAtomContainers();
				containers = this.ecb.extractProductsFromBiotransformations(currentBiotransformations);				
			}
			else{
				break;
			}
		}

		return Utilities.selectUniqueBiotransformations(biotransformations);
	}	

	
	
	public ArrayList<Biotransformation> predictAllHumanBiotransformationChain(IAtomContainerSet containers, int nrOfSteps, double threshold) throws Exception{
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();

		while(nrOfSteps>0){
			// System.out.println("\nStep: " + counter + "\n");
			ArrayList<Biotransformation> currentBiotransformations = simulateOneStepAllHuman(containers, threshold);
			nrOfSteps--;
			if(!currentBiotransformations.isEmpty()){
				biotransformations.addAll(currentBiotransformations);
				containers.removeAllAtomContainers();
				containers = this.ecb.extractProductsFromBiotransformations(currentBiotransformations);				
			}
			else{
				break;
			}
		}

		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	
	
	
	public void predictMetabolismAllHumanFromSDFtoSDF(String inputFileName, String outputFileName, boolean annotate) throws Exception{
		predictMetabolismAllHumanFromSDFAndSavetoSDF(inputFileName, outputFileName, 1, 0.5, annotate);
	}

		
	public void predictAllHumanBiotransformationChainAndSaveToSDF(IAtomContainer substrate, int nrOfSteps, double threshold, String outputFileName, boolean annotate) throws Exception{
		ArrayList<Biotransformation> biotransformations= predictAllHumanBiotransformationChain(substrate, nrOfSteps, threshold);
		this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
	}
		
	public void predictMetabolismAllHumanFromSDFAndSavetoSDF(String inputFileName, String outputFileName, int nrOfSteps, boolean annotate) throws Exception{
			predictMetabolismAllHumanFromSDFAndSavetoSDF(inputFileName, outputFileName, nrOfSteps, 0.5, annotate);

	}	
		
	public void predictMetabolismAllHumanFromSDFAndSavetoSDF(String inputFileName, String outputF, double screThreshold, boolean annotate) throws Exception{
		predictMetabolismAllHumanFromSDFAndSavetoSDF(inputFileName, outputF, 1, screThreshold, annotate);

	}	


	public void predictMetabolismAllHumanFromSDFAndSavetoSDF(String inputFileName, String outputFolder, int nrOfSteps, double scoreThreshold, boolean annotate) throws Exception{
			
		int nr = 0;
		IAtomContainerSet containers = FileUtilities.parseSdfAndAddTitles(inputFileName, this.getInChIGenFactory());
		for(IAtomContainer atc : containers.atomContainers()){
			try{
				String identifier = atc.getProperty(CDKConstants.TITLE);
				identifier = identifier.replace(":", "-").replace("/", "_");
				ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
				biotransformations.addAll(this.predictAllHumanBiotransformationChain(atc, nrOfSteps, scoreThreshold));
				this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFolder + "/" + identifier + "_BioT_allHuman_metabolites.sdf", this.combinedReactionsHash, annotate);
			}
			catch(Exception e){
				System.err.println("Could not predicted metabolism for molecule nr. " + nr);
				System.err.println(e.getLocalizedMessage());
			}
		}		
	}	

	
	public void predictMetabolismAllHumanFromSDFAndSavetoSingleSDF(String inputFileName, String outputFileName, int nrOfSteps, double scoreThreshold, boolean annotate) throws Exception{
		
		int nr = 0;
		IAtomContainerSet containers = FileUtilities.parseSdfAndAddTitles(inputFileName, this.getInChIGenFactory());
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		for(IAtomContainer atc : containers.atomContainers()){
			try{
				String identifier = atc.getProperty(CDKConstants.TITLE);
				identifier = identifier.replace(":", "-").replace("/", "_");
				
				biotransformations.addAll(this.predictAllHumanBiotransformationChain(atc, nrOfSteps, scoreThreshold));
			}
				
			catch(Exception e){
				System.err.println("Could not predicted metabolism for molecule nr. " + nr);
				System.err.println(e.getLocalizedMessage());
			}
		}
		this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		
	}	

	public IAtomContainer standardizeMoleculeWithCopy(IAtomContainer target) throws Exception{
		return  ChemStructureManipulator.standardizeMoleculeWithCopy(target);
	}
	

	public ArrayList<Biotransformation> applyGutMicrobialMetabolismHydrolysisAndRedoxChain(IAtomContainerSet molecules, boolean preprocess, boolean filter , int nrOfSteps, Double scoreThreshold) throws Exception{
		return this.hgb.applyGutMicrobialMetabolismHydrolysisAndRedoxChain(molecules, preprocess, filter , nrOfSteps, scoreThreshold);
	
	}
	
	public IAtomContainerSet extractProductsFromBiotransformations(ArrayList<Biotransformation> biotransformations) throws Exception{
		return this.ecb.extractProductsFromBiotransformations(biotransformations);
	}
	

	public IAtomContainerSet extractProductsFromBiotransformationsWithTransformationData(ArrayList<Biotransformation> biotransformations, boolean annotate) throws Exception{
		return this.ecb.extractProductsFromBiotransformationsWithTransformationData(biotransformations, this.combinedReactionsHash, annotate);
	}
	
	public void saveBioTransformationProductsToSdf(ArrayList<Biotransformation> biotransformations, String outputFileName, boolean annotate) throws Exception {
		try{
			this.ecb.saveBioTransformationProductsToSdf(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}
	public void saveBioTransformationProductsToCSV(ArrayList<Biotransformation> biotransformations, String outputFileName, boolean annotate) throws Exception {
		try{
			this.ecb.saveBioTransformationProductsToCSV(Utilities.selectUniqueBiotransformations(biotransformations), outputFileName, this.combinedReactionsHash, annotate);
		}
		catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}		
	}	
	
	

	

	}

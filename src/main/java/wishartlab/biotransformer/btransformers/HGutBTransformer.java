/**
 * This class implements the class of human gut biotransformers, which simulate the transformation of molecules
 * by enzymes from microbial species found in the human gut. It implements rules and constraints extracted from or designed upon
 * (1) mining the scientific literature, (2) expert collaboration, and/or (3) experimental validation.
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */


package wishartlab.biotransformer.btransformers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.io.iterator.IteratingSDFReader;

import ambit2.smarts.query.SMARTSException;

import wishartlab.biotransformer.biomolecule.Enzyme;
import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.btransformers.Biotransformer;
import wishartlab.biotransformer.transformation.Biotransformation;
import wishartlab.biotransformer.transformation.MetabolicReaction;
import wishartlab.biotransformer.utils.ChemStructureExplorer;
import wishartlab.biotransformer.utils.ChemStructureManipulator;
import wishartlab.biotransformer.utils.ChemicalClassFinder;
import wishartlab.biotransformer.utils.Utilities;

public class HGutBTransformer extends Biotransformer {

	public HGutBTransformer() throws IOException, CDKException{
		super(BioSystemName.GUTMICRO);
		setReactionsList();
	}
	
	/**
	 * Collect the list of metabolic reactions associated with the current biosystem, inferred from the list of enzymes.
	 * The deconjugation enzymes include esterases, alpha-rhamnosidases, beta-glucuronidases, beta-glycosidases.
	 */
	private void setReactionsList(){
		ArrayList<MetabolicReaction> redoxReact 	= new ArrayList<MetabolicReaction>() ;
		ArrayList<MetabolicReaction> phaseIIReact 		= new ArrayList<MetabolicReaction>();
		ArrayList<MetabolicReaction> deconjugationReact = new ArrayList<MetabolicReaction>();
		ArrayList<Enzyme> redoxEnz 					= new ArrayList<Enzyme>() ;
		ArrayList<Enzyme> phaseIIEnz 					= new ArrayList<Enzyme>();
		ArrayList<Enzyme> deconjugationEnz 				= new ArrayList<Enzyme>();
		
				
		for( Enzyme enz : this.bSystem.getEnzymesList()){
			if(enz.getName().contentEquals("EC_2_1_1_6") || enz.getName().contentEquals("SULFOTRANSFERASE") ||
					enz.getName().contentEquals("UDP_GLUCURONOSYLTRANSFERASE")){
				phaseIIReact.addAll(enz.getReactionSet());
				phaseIIEnz.add(enz);
			}
			
			else if (
					
					// carboxylesterase
					enz.getName().contentEquals("EC_3_1_1_1") || enz.getName().contentEquals("EC_3_1_1_2") || enz.getName().contentEquals("EC_3_1_1_20") || 
					
					// sulfatase
					
					enz.getName().contentEquals("EC_3_1_6_1") || enz.getName().contentEquals("EC_3_1_6_19") ||
					
					// glycosidase
					enz.getName().contentEquals("EC_3_2_1_X") || enz.getName().contentEquals("EC_3_2_1_20") || 
					enz.getName().contentEquals("EC_3_2_1_21") || enz.getName().contentEquals("EC_3_2_1_23") || 
					enz.getName().contentEquals("EC_3_2_1_31") || enz.getName().contentEquals("EC_3_2_1_40") ||
					enz.getName().contentEquals("EC_3_2_1_147") || enz.getName().contentEquals("FLAVONOID_C_GLYCOSIDASE") || 
					enz.getName().contentEquals("EC_3_1_1_73") ||
					
					// hydrolase
					enz.getName().contentEquals("EC_3_5_1_24")
					
					){
				deconjugationReact.addAll(enz.getReactionSet());
				deconjugationEnz.add(enz);
			}
			
			else{
				redoxReact.addAll(enz.getReactionSet());
				redoxEnz.add(enz);				
			}
		}
				
		this.reactionsByGroups.put("gutMicroReductiveReactions", redoxReact);
		this.reactionsByGroups.put("gutMicroPhaseIIReactions", phaseIIReact);
		this.reactionsByGroups.put("deconjugationReactions", deconjugationReact );
		this.enzymesByreactionGroups.put("deconjugationReactions", deconjugationEnz);
		this.enzymesByreactionGroups.put("gutMicroReductiveReactions", redoxEnz);
		this.enzymesByreactionGroups.put("gutMicroPhaseIIReactions", phaseIIEnz);

		for(MetabolicReaction m : this.reactionsByGroups.get("gutMicroReductiveReactions")){
			this.reactionsHash.put(m.name, m);
		}
		for(MetabolicReaction m : this.reactionsByGroups.get("gutMicroPhaseIIReactions")){
			this.reactionsHash.put(m.name, m);
		}
		for(MetabolicReaction m : this.reactionsByGroups.get("deconjugationReactions")){
			this.reactionsHash.put(m.name, m);
		}

	}
	/**
	 * returns a linked hash map with the reactions associated with the human gut, in addition to standardization reactions.
	 */
	public LinkedHashMap<String, ArrayList<MetabolicReaction>> getReactionsList(){
		return this.reactionsByGroups;
	}
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @return an arraylist of biotransformations, which are instances of the human gut microbial reactions applied to the target, with a threshold of 0.0
	 * @throws Exception - throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialDeconjugations(IAtomContainer target, boolean preprocess, boolean filter)
			throws Exception {
		return this.applyGutMicrobialDeconjugations(target, preprocess, filter, 0.0);
	}
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @return an arraylist of biotransformations, which are instances of the human gut microbial reactions applied to the target, with the set minimum threshold
	 * @throws Exception -  throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialDeconjugations(IAtomContainer target, boolean preprocess, boolean filter, double scoreThreshold)
			throws Exception {
		
		if(ChemStructureExplorer.isBioTransformerValid(target)) {
						return this.metabolizeWithEnzymes(target, this.enzymesByreactionGroups.get("deconjugationReactions"), preprocess, filter, scoreThreshold);
		
		}
		else if(ChemStructureExplorer.isMixture(target) || ChemStructureExplorer.isCompoundInorganic(target)){
			throw new IllegalArgumentException("\n\n INVALID COMPOUND:\nThe compound is not valid for BioTransformer. Make sure that the compound is: 1) organic; and 2) not a mixture.");
		}
//		
		else{
			return null;
		}
	}

	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @param nr_of_steps -  number of steps
	 * @return an arraylist of biotransformations obtained after the specified number of steps (nr_of_steps), which are 
	 * instances of the human gut microbial metabolic reactions applied to the target, with the minimum threshold of 0.0
	 * @throws Exception - throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialDeconjugationsChain(IAtomContainer target,
			boolean preprocess, boolean filter, int nr_of_steps) throws Exception{
		return  this.applyGutMicrobialDeconjugationsChain(target, preprocess, filter, nr_of_steps, 0.0);
	}
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @param nr_of_steps -  number of steps
	 * @param scoreThreshold -  The minimum score for a reaction
	 * @return an arraylist of biotransformations obtained after the specified number of steps (nr_of_steps), which are 
	 * instances of the human gut microbial metabolic reactions applied to the target, with the set minimum threshold
	 * @throws Exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialDeconjugationsChain(IAtomContainer target,
			boolean preprocess, boolean filter, int nr_of_steps, Double scoreThreshold) throws Exception{
			return this.metabolizeWithEnzymesBreadthFirst(target, this.enzymesByreactionGroups.get("deconjugationReactions"), preprocess, filter, nr_of_steps, scoreThreshold);

	}
	
	
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @return an arraylist of biotransformations, which are instances of the human gut microbial reactions applied to the target, with a threshold of 0.0
	 * @throws Exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialRedoxReactions(IAtomContainer target, boolean preprocess, boolean filter)
			throws Exception {
		return this.applyGutMicrobialRedoxReactions(target, preprocess, filter, 0.0);
	}
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @param scoreThreshold - minimum threshold for reaction scores
	 * @return an arraylist of biotransformations, which are instances of the human gut microbial reactions applied to the target, with the set minimum threshold
	 * @throws Exception - throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialRedoxReactions(IAtomContainer target, boolean preprocess, boolean filter, double scoreThreshold)
			throws Exception {
		
		try {
			if(ChemStructureExplorer.isCompoundInorganic(target) || ChemStructureExplorer.isMixture(target)){
				throw new IllegalArgumentException(target.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} else if(ChemStructureExplorer.isBioTransformerValid(target)){
				return this.metabolizeWithEnzymes(target, this.enzymesByreactionGroups.get("gutMicroReductiveReactions"), preprocess, filter, scoreThreshold);
	//			return this.applyReactionsAndReturnBiotransformations(target, this.reactionsByGroups.get("gutMicroReductiveReactions"), preprocess, filter, scoreThreshold);
			}else{
				return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @param nr_of_steps -  number of steps
	 * @return an arraylist of biotransformations obtained after the specified number of steps (nr_of_steps), which are 
	 * instances of the human gut microbial metabolic reactions applied to the target, with the minimum threshold of 0.0
	 * @throws Exception - throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialRedoxReactionsChain(IAtomContainer target,
			boolean preprocess, boolean filter, int nr_of_steps) throws Exception{
		return  this.applyGutMicrobialRedoxChain(target, preprocess, filter, nr_of_steps, 0.0);
	}
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @param nr_of_steps -  number of steps
	 * @return an arraylist of biotransformations obtained after the specified number of steps (nr_of_steps), which are 
	 * instances of the human gut microbial metabolic reactions applied to the target, with the set minimum threshold
	 * @throws Exception -throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialRedoxChain(IAtomContainer target,
			boolean preprocess, boolean filter, int nr_of_steps, Double scoreThreshold) throws Exception{
				
		try{
			if(ChemStructureExplorer.isCompoundInorganic(target) || ChemStructureExplorer.isMixture(target)){
				throw new IllegalArgumentException(target.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} else if(ChemStructureExplorer.isBioTransformerValid(target)) {	
//				if( !this.isDeconjugationCandidate(target)){
					return this.metabolizeWithEnzymesBreadthFirst(target, this.enzymesByreactionGroups.get("gutMicroReductiveReactions"), preprocess, filter, nr_of_steps, scoreThreshold);
	//				return this.applyReactionsChainAndReturnBiotransformations(target, this.reactionsByGroups.get("gutMicroReductiveReactions"), preprocess, filter, nr_of_steps, scoreThreshold);			
//				}else{
//					return null;
//				}
			}else{
				return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	

	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @return an arraylist of biotransformations, which are instances of the human gut microbial reactions applied to the target, with a threshold of 0.0
	 * @throws Exception - throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialConjugations(IAtomContainer target, boolean preprocess, boolean filter)
			throws Exception {
		return this.applyGutMicrobialConjugations(target, preprocess, filter, 0.0);
	}
	
	/**
	 * 
	 * @param target - The molecule to transform
	 * @param preprocess - specify whether to perform molecule preprocessing
	 * @param filter - apply reaction filtering
	 * @return an arraylist of biotransformations, which are instances of the human gut microbial reactions applied to the target, with the set minimum threshold
	 * @throws Exception - throw any exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialConjugations(IAtomContainer target, boolean preprocess, boolean filter, double scoreThreshold)
			throws Exception {
		
		try{
			if(ChemStructureExplorer.isCompoundInorganic(target) || ChemStructureExplorer.isMixture(target)){
				throw new IllegalArgumentException(target.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} 
			else if(ChemStructureExplorer.isBioTransformerValid(target) && 
					!(ChemStructureExplorer.isInvalidPhaseIISubstrateByExlcusion(target))) {		
				
				ArrayList<Biotransformation> biotransformations  = this.metabolizeWithEnzymes(target, this.enzymesByreactionGroups.get("gutMicroPhaseIIReactions"), preprocess, filter, scoreThreshold);				
				ArrayList<Biotransformation> selectedBiotransformations = new ArrayList<Biotransformation>();
				
				for(Biotransformation bt : biotransformations) {
					boolean goodBiontransfo =  true;
					for( IAtomContainer at : bt.getProducts().atomContainers() ){

						if(ChemStructureExplorer.isInvalidPhase2Metabolite(at)){
							goodBiontransfo = false;					
							break;
						}
					}
					
					if(goodBiontransfo){
						selectedBiotransformations.add(bt);
					}
				}
				
				return selectedBiotransformations;
			} else {
				return null;
			}
		}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
	}

	/**
	 * 
	 * @param target
	 * @param preprocess
	 * @param filter
	 * @param nr_of_steps
	 * @return an arraylist of biotransformations obtained after the specified number of steps (nr_of_steps), which are 
	 * instances of the human gut microbial metabolic reactions applied to the target, with the minimum threshold of 0.0
	 * @throws Exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialConjugationsChain(IAtomContainer target,
			boolean preprocess, boolean filter, int nr_of_steps) throws Exception{
		try{
			if(ChemStructureExplorer.isCompoundInorganic(target) || ChemStructureExplorer.isMixture(target)){
				throw new IllegalArgumentException(target.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} 
			else if(ChemStructureExplorer.isBioTransformerValid(target))  {				
				return  this.applyGutMicrobialConjugationsChain(target, preprocess, filter, nr_of_steps, 0.0);
			}
			else {
				return null;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param target
	 * @param preprocess
	 * @param filter
	 * @param nr_of_steps
	 * @return an arraylist of biotransformations obtained after the specified number of steps (nr_of_steps), which are 
	 * instances of the human gut microbial metabolic reactions applied to the target, with the set minimum threshold
	 * @throws Exception
	 */
	public ArrayList<Biotransformation> applyGutMicrobialConjugationsChain(IAtomContainer target,
			boolean preprocess, boolean filter, int nr_of_steps, Double scoreThreshold) throws Exception{
	
		ArrayList<Biotransformation> biotransformations  = this.metabolizeWithEnzymesBreadthFirst(target, this.enzymesByreactionGroups.get("gutMicroPhaseIIReactions"), preprocess, filter, nr_of_steps, scoreThreshold);
		ArrayList<Biotransformation> selectedBiotransformations = new ArrayList<Biotransformation>();
		
		for(Biotransformation bt : biotransformations) {
			boolean goodBiontransfo =  true;
			for( IAtomContainer at : bt.getProducts().atomContainers() ){
				if(ChemStructureExplorer.isInvalidPhase2Metabolite(at)){
					goodBiontransfo = false;					
					break;
				}
			}
			
			if(goodBiontransfo){
				selectedBiotransformations.add(bt);
			}
		}
		
		
		return selectedBiotransformations;
	
	
	}
		
	public ArrayList<Biotransformation> applyGutMicrobialMetabolismHydrolysisAndRedoxStep(IAtomContainer target,
			boolean preprocess, boolean filter, Double scoreThreshold) throws Exception{
		
		try {
			if(ChemStructureExplorer.isCompoundInorganic(target) || ChemStructureExplorer.isMixture(target)){
				throw new IllegalArgumentException(target.getProperty("InChIKey")+ "\nThe substrate must be: 1) organic, and; 2) not a mixture.");
			} 
			else if(ChemStructureExplorer.isBioTransformerValid(target)){
				ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
				IAtomContainer st = ChemStructureManipulator.standardizeMoleculeWithCopy(target, true);
				
				if(this.isDeconjugationCandidate(st)){
//					System.err.println("IS A DECONJUGATION CANDIDATE");
					biotransformations = this.applyGutMicrobialDeconjugationsChain(st,
							preprocess, filter, 1, scoreThreshold);
				}
				else {
//					System.err.println("IS NOT A DECONJUGATION CANDIDATE");
					biotransformations = this.applyGutMicrobialRedoxChain(st,
							preprocess, filter, 1, scoreThreshold);							
				}
				return Utilities.selectUniqueBiotransformations(biotransformations);
			}else{
				return null;
			}
		}
		catch (Exception iae) {
			System.err.println(iae.getLocalizedMessage());
			return null;
		}
	}
		
	public ArrayList<Biotransformation> applyGutMicrobialMetabolismHydrolysisAndRedoxStep(IAtomContainerSet targets, boolean preprocess, 
			boolean filter, Double scoreThreshold) throws Exception{
		
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		for(IAtomContainer atc : targets.atomContainers()){
			ArrayList<Biotransformation> bts = this.applyGutMicrobialMetabolismHydrolysisAndRedoxStep(atc, preprocess, 
					filter, scoreThreshold);
			if(bts != null && bts.size()>0){
				biotransformations.addAll(bts);
			}
		}
		
		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	
	
	public ArrayList<Biotransformation> applyGutMicrobialMetabolismHydrolysisAndRedoxChain(IAtomContainer target, boolean preprocess, 
			boolean filter, int nr_of_steps, Double scoreThreshold) throws Exception{
		IAtomContainerSet targets = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		targets.addAtomContainer(target);
		return applyGutMicrobialMetabolismHydrolysisAndRedoxChain(targets, preprocess, filter, nr_of_steps, scoreThreshold);
	}
	
	
	public ArrayList<Biotransformation> applyGutMicrobialMetabolismHydrolysisAndRedoxChain(IAtomContainerSet targets, boolean preprocess, 
			boolean filter, int nr_of_steps, Double scoreThreshold) throws Exception{
		
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		int i = 0;
		IAtomContainerSet products = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		products.add(targets);
		
		while (i < nr_of_steps){
//			System.err.println("Step nr " + (i+1));
			biotransformations.addAll(this.applyGutMicrobialMetabolismHydrolysisAndRedoxStep(products, preprocess, filter, scoreThreshold));
			products = this.extractProductsFromBiotransformations(biotransformations);
			i++;
		}
		
		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	

	public ArrayList<Biotransformation> simulateGutMicrobialMetabolism(IAtomContainer target, boolean preprocess, 
			boolean filter, int numberOfSteps) throws Exception{
		 return simulateGutMicrobialMetabolism(target, preprocess, filter, numberOfSteps, 0.0);
	}

	public ArrayList<Biotransformation> simulateGutMicrobialMetabolism(IAtomContainer target, boolean preprocess, 
			boolean filter, int numberOfSteps, Double scoreThreshold) throws Exception{

		IAtomContainerSet targets = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		targets.addAtomContainer(target);					
		return simulateGutMicrobialMetabolism(targets, preprocess, filter, numberOfSteps, scoreThreshold);
	}
	
	public ArrayList<Biotransformation> simulateGutMicrobialMetabolism(IAtomContainerSet targets, boolean preprocess, 
			boolean filter, int numberOfSteps) throws Exception{
		 return simulateGutMicrobialMetabolism(targets, preprocess, filter, numberOfSteps, 0.0);
	}
	
	public ArrayList<Biotransformation> simulateGutMicrobialMetabolism(IAtomContainerSet targets, boolean preprocess, 
			boolean filter, int numberOfSteps, Double scoreThreshold) throws Exception{

		/**
		 In cases of multi-step transformations, instead of conjugating molecules and deconjugating them over 
		 and over again, it is better to just apply one conjugation step just at the end.
		 */

		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
		int i = 0;
		
		IAtomContainerSet phaseIIsubs = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
		IAtomContainerSet currentProducts = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);

		phaseIIsubs.add(targets);
		currentProducts.add(targets);
		
		while (i < numberOfSteps){
			/**
			 The function that extracts atom containers from botransformations automatically pre-processes them. 
			 So, we set preprocess=false in the function below.
			 !!! TO DO: Setting this to false does not return anything. Check why
			 */
			biotransformations.addAll(this.applyGutMicrobialMetabolismHydrolysisAndRedoxStep(currentProducts, preprocess, filter, scoreThreshold));
			currentProducts = this.extractProductsFromBiotransformations(biotransformations);
			if(i < numberOfSteps -1) {
				phaseIIsubs.add(currentProducts);
			}
			
			i++;
		}
		

		for(IAtomContainer atCon : phaseIIsubs.atomContainers()) {
			ArrayList<Biotransformation> phaseIIBiotransformations = applyGutMicrobialConjugations(atCon,
					preprocess, filter, scoreThreshold);
			if(phaseIIBiotransformations != null && phaseIIBiotransformations.size()>0) {
				biotransformations.addAll(phaseIIBiotransformations);
			}
			
		}	
		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	

	
	public ArrayList<Biotransformation> simulateGutMicrobialMetabolism(String targetsFileNameInSDF, boolean preprocess, 
			boolean filter, int nr_of_steps, Double scoreThreshold) throws Exception{
		
		ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();			
		IteratingSDFReader sdfr = new IteratingSDFReader(new FileReader(targetsFileNameInSDF), this.builder);
		
		while (sdfr.hasNext()){
			IAtomContainer mol = sdfr.next();
			try {
				ArrayList<Biotransformation> bts = this.simulateGutMicrobialMetabolism(mol, preprocess, 
						filter, nr_of_steps, scoreThreshold);
				if(bts!= null && bts.size()>0){
					biotransformations.addAll(bts);	
				}
			}
				catch(Exception e) {
					e.printStackTrace();
					return null;
			}
		}
		
		return Utilities.selectUniqueBiotransformations(biotransformations);
	}
	

	public void simulateGutMicrobialMetabolismAndSave(String targetsFileNameInSDF, boolean preprocess, 
			boolean filter, int nr_of_steps, Double scoreThreshold, String metabolitesFileNameInSDF) throws Exception{

		simulateGutMicrobialMetabolismAndSave(targetsFileNameInSDF, preprocess, 
				filter, nr_of_steps, scoreThreshold, metabolitesFileNameInSDF, false);
	}
	
	
	public void simulateGutMicrobialMetabolismAndSave(String targetsFileNameInSDF, boolean preprocess, 
			boolean filter, int nr_of_steps, Double scoreThreshold, String metabolitesFileNameInSDF, boolean annotate) throws Exception{
		
		ArrayList<Biotransformation> biotransformations = simulateGutMicrobialMetabolism(targetsFileNameInSDF, preprocess, 
				filter, nr_of_steps, scoreThreshold);		
		this.saveBioTransformationProductsToSdf(biotransformations, metabolitesFileNameInSDF, annotate);
		
	}

	public void  simulateGutMicrobialMetabolismAndSaveToSDF(IAtomContainerSet containers, int nrOfSteps, Double scoreThreshold, String outputFolder) throws Exception {
		simulateGutMicrobialMetabolismAndSaveToSDF(containers, nrOfSteps, scoreThreshold, outputFolder, false);
	}
	
	
	public void  simulateGutMicrobialMetabolismAndSaveToSDF(IAtomContainerSet containers, int nrOfSteps, Double scoreThreshold, String outputFolder, boolean annotate) throws Exception {
		
		if(!containers.isEmpty()){
			for(IAtomContainer molecule : containers.atomContainers()){

				String identifier = molecule.getProperty(CDKConstants.TITLE);
				if(identifier == null){
					identifier = molecule.getProperty("Name");
					if(identifier == null){
						identifier = molecule.getProperty("InChIKey");
						if(identifier == null){
							identifier = this.inchiGenFactory.getInChIGenerator(molecule).getInchiKey();
						}
					}
				}
				identifier = identifier.replace(":", "-").replace("/", "_");
				System.out.println(identifier);
				ArrayList<Biotransformation> biotransformations = this.simulateGutMicrobialMetabolism(molecule, true, true, nrOfSteps, scoreThreshold);
				System.out.println(biotransformations.size() + " biotransformations.");
				this.saveBioTransformationProductsToSdf(biotransformations, outputFolder + "/" + identifier + "_EC_based_metabolites.sdf", annotate);
			
			}
		}		
	}
	
	
	
	public boolean isDeconjugationCandidate(IAtomContainer molecule) throws SMARTSException, CDKException, IOException{
		boolean dec = false;
		
		for(MetabolicReaction m : this.reactionsByGroups.get("deconjugationReactions")){
			if(ChemStructureExplorer.compoundMatchesReactionConstraints(m, molecule)){
				dec = true;
				break;
			}
			if(dec == false && ChemicalClassFinder.isSulfateEster(molecule)){
				dec = true;
			}
		}		
		return dec;		
	}

	public boolean isConjugationCandidate(IAtomContainer molecule) throws SMARTSException, CDKException, IOException{
		boolean dec = false;
		
		for(MetabolicReaction m : this.reactionsByGroups.get("gutMicroPhaseIIReactions")){
			if(ChemStructureExplorer.compoundMatchesReactionConstraints(m, molecule)){
				dec = true;
				break;
			}
		}
		
		return dec;		
	}
	
	
	public void printStatistics(){
		int count = 0;
		for(Enzyme e: this.enzymesList){
			count = count + e.getReactionsNames().size();
		}
		System.out.println("Humber of enzymes: " + this.enzymesList.size());
		System.out.println("Humber of biotransformation rules: " + (this.reactionsByGroups.get("gutMicroReductiveReactions").size() 
				+ this.reactionsByGroups.get("gutMicroPhaseIIReactions").size() 
				+ this.reactionsByGroups.get("deconjugationReactions").size()));		
		System.out.println("Humber of enzyme-biotransformation rules associations: " + count);	
	}
}

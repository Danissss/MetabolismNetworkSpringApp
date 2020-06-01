package wishartlab.biotransformer.transformation;

/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
import java.util.ArrayList;

import org.openscience.cdk.silent.SilentChemObjectBuilder;

import ambit2.smarts.SMIRKSReaction;
import wishartlab.biotransformer.biomolecule.Enzyme;
import wishartlab.biotransformer.transformation.MRPatterns;
import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;
import ambit2.smarts.SMIRKSManager;


/**
 * The first reactantsSMARTS is the main one, for which indices might be
 * retrieved to apply position-specific transformations. The subsequent SMARTS
 * (negativeReactantsSMARTS) will be checked just to match further constraints.
 * For instance, long-chain fatty acids must NOT match the pattern of
 * very-long-chain fatty acids. This combination of SMARTS patterns could be
 * replaced in favor of MARKUSH formats. Because we are using CDK, we rather
 * stick up the the combination of SMARTS
 *
 */

public class MetabolicReaction {

	public String				name;
	public String				commonName;
	public String				reactionsBTMRID;
	private ArrayList<String>	reactantsSMARTS			= new ArrayList<String>();
	private ArrayList<String>	negativeReactantsSMARTS	= new ArrayList<String>();
	private String				productsSMARTS;
	private String				reactionSMIRKS;
	private String				reactionSMIRKS_text;
	private String				reactionEquation;
	private SMIRKSReaction		smirksReaction;
	private ArrayList<Enzyme>	catalyzingEnzymes 		= new ArrayList<Enzyme>();

	public static void main (String[] args){
		MetabolicReaction m = new MetabolicReaction(ReactionName.AZIDE_STANDARDIZATION);
		m.display();
	}
	
	public MetabolicReaction(ReactionName r_name) {
		SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
		this.name = r_name.toString();
		this.reactionSMIRKS = MRPatterns.setOfSMIRKS.get(name);

		for (String smarts : MRPatterns.setOfReactantSMARTS.get(name)) {
			if (!(smarts.trim().isEmpty())) {
				this.reactantsSMARTS.add(smarts);
			}
		}

		if (!(MRPatterns.setOfNetgativeReactantSMARTS.get(r_name.toString()) == null)) {
			for (String n_smarts : MRPatterns.setOfNetgativeReactantSMARTS.get(name)) {
				if (!(n_smarts.trim().isEmpty())) {
					this.negativeReactantsSMARTS.add(n_smarts);
				}
			}
		}
		this.smirksReaction = smrkMan.parse(reactionSMIRKS);

	}

	
	
	public MetabolicReaction(ReactionName r_name, String commmonName, String reactionsBTMRID, String reactionSMIRKS, ArrayList<String> reactantsSMARTS, ArrayList<String> negativeReactantsSMARTS, SMIRKSManager smrkMan) {
		this.name = r_name.toString();
		this.commonName = commmonName;
		this.reactionsBTMRID = reactionsBTMRID;
		this.reactionSMIRKS = reactionSMIRKS;
		this.reactantsSMARTS = reactantsSMARTS;
		this.negativeReactantsSMARTS = negativeReactantsSMARTS;
		this.smirksReaction = smrkMan.parse(reactionSMIRKS);
	}

	
	public MetabolicReaction(ReactionName r_name, String commmonName, String reactionSMIRKS, ArrayList<String> reactantsSMARTS, ArrayList<String> negativeReactantsSMARTS, SMIRKSManager smrkMan) {
		this.name = r_name.toString();
		this.commonName = commmonName;
		this.reactionSMIRKS = reactionSMIRKS;
		this.reactantsSMARTS = reactantsSMARTS;
		this.negativeReactantsSMARTS = negativeReactantsSMARTS;
		this.smirksReaction = smrkMan.parse(reactionSMIRKS);
	}
	
	public MetabolicReaction(ReactionName r_name, String reactionSMIRKS, ArrayList<String> reactantsSMARTS, ArrayList<String> negativeReactantsSMARTS, SMIRKSManager smrkMan) {
		this.name = r_name.toString();
		this.reactionSMIRKS = reactionSMIRKS;
		this.reactantsSMARTS = reactantsSMARTS;
		this.negativeReactantsSMARTS = negativeReactantsSMARTS;
		this.smirksReaction = smrkMan.parse(reactionSMIRKS);
	}
	
	public MetabolicReaction(String smirks) {
		SMIRKSManager smrkMan = new SMIRKSManager(SilentChemObjectBuilder.getInstance());
		reactionSMIRKS = smirks;
		smirksReaction = smrkMan.parse(reactionSMIRKS);
	}

	public MetabolicReaction(ReactionName r_name, SMIRKSManager smrkMan) {
		name = r_name.toString();
		reactionSMIRKS = MRPatterns.setOfSMIRKS.get(r_name.toString());

		for (String smarts : MRPatterns.setOfReactantSMARTS.get(r_name.toString())) {
			if (!(smarts.trim().isEmpty())) {
				reactantsSMARTS.add(smarts);
			}
		}

		if (!(MRPatterns.setOfNetgativeReactantSMARTS.get(r_name.toString()) == null)) {
			for (String n_smarts : MRPatterns.setOfNetgativeReactantSMARTS.get(r_name.toString())) {
				if (!(n_smarts.trim().isEmpty())) {
					negativeReactantsSMARTS.add(n_smarts);
				}
			}
		}

		smirksReaction = smrkMan.parse(reactionSMIRKS);

	}

	public MetabolicReaction(String smirks, SMIRKSManager smrkMan) {
		reactionSMIRKS = smirks;
		smirksReaction = smrkMan.parse(reactionSMIRKS);
	}

	public ArrayList<String> getReactantSMARTS() {
		return this.reactantsSMARTS;
	}

	public ArrayList<String> getNegativeReactantsSMARTS() {
		return this.negativeReactantsSMARTS;
	}	
	
	public String getBTRMID(){
		return this.reactionsBTMRID;
	}
	
	public String getComonName(){
		return this.commonName;
	}
	
	public String getReactionSMIRKS() {
		return this.reactionSMIRKS;
	}

	public SMIRKSReaction getSmirksReaction() {
		return this.smirksReaction;
	}

	public String getReactionName() {
		return this.name;
	}

	public String getReactionEquation() {
		return reactionEquation;
	}

	public void setReactionEquation(String rEquation) {
		reactionEquation = rEquation;
	}

	public String transformationDataToString() {
		String description = "";
		description.concat(String.format("%-20s\t%-25s","Name:",this.name));
		description.concat(String.format("%-20s\t%-150s","SMIRKS:",this.reactionSMIRKS));
		return description;
	}
	
	public void display(){
		System.out.println(String.format("%-20s\t%-25s","Name:",this.name));
		System.out.println(String.format("%-30s\t%-35s","Common name:",this.commonName));
		System.out.println(String.format("%-20s\t%-150s","SMIRKS:",this.reactionSMIRKS));
		if(this.reactionsBTMRID !=null){
			System.out.println(String.format("%-20s\t%-25s","btmrID:",this.reactionsBTMRID));
		}
		System.out.print("\n");
	}
	
	
}

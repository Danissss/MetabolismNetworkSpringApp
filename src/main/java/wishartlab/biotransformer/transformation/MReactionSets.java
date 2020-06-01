/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.transformation;

import java.util.ArrayList;

import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;

public class MReactionSets {

	public MReactionSets() {
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<MetabolicReaction> standardizationReactions;
	static{
		standardizationReactions = new ArrayList<MetabolicReaction>();
		standardizationReactions.add(new MetabolicReaction(ReactionName.AZIDE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.CARBOXAMIDE_STANDARDIZATION_PATTERN1));
		standardizationReactions.add(new MetabolicReaction(ReactionName.CARBOXAMIDE_STANDARDIZATION_PATTERN2));
		standardizationReactions.add(new MetabolicReaction(ReactionName.DIAZO_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.DIAZONIUM_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.IMINIUM_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.ISOCYANATE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.NITRILIUM_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.NITRO_STANDARDIZATION));		
		standardizationReactions.add(new MetabolicReaction(ReactionName.NITRO_STANDARDIZATION_1));
		standardizationReactions.add(new MetabolicReaction(ReactionName.NITRONATE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.NITROSO_STANDARDIZATION));		
		standardizationReactions.add(new MetabolicReaction(ReactionName.PHOSPHONIC_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.PHOSPHORIC_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.PHOSPHORIC_STANDARDIZATION_2));
		standardizationReactions.add(new MetabolicReaction(ReactionName.PHOSPHONIUM_YLIDE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SELENITE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SILICATE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SULFINE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SULFONE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SULFONIUM_YLIDE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SULFOXIDE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.SULFOXONIUM_YLIDE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.TERTIARY_N_OXIDE_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.THIOUREA_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.CARBAMIDOTHIOIC_ACID_TO_THIOUREA_STANDARDIZATION));
		standardizationReactions.add(new MetabolicReaction(ReactionName.CARBOXYLIC_ACID_ANION_STANDARDIZATION));
	}
	
	

}

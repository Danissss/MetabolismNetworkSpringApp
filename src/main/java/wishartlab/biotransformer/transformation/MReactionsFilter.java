/**
 * 
 * @author Djoumbou Feunang, Yannick, PhD
 *
 */

package wishartlab.biotransformer.transformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org._3pq.jgrapht.alg.ConnectivityInspector;
import org._3pq.jgrapht.graph.SimpleDirectedGraph;
import org.apache.commons.lang3.ArrayUtils;

import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.transformation.MRPatterns.ReactionName;


public class MReactionsFilter {

	public LinkedHashMap<ReactionName, ReactionName[]> btPriorityHash = new LinkedHashMap<ReactionName, ReactionName[]>();
	public BioSystemName bioSysName;
	
	public MReactionsFilter(BioSystemName bioSysName){
		this.bioSysName = bioSysName;
		this.setPriorityHash();
	}
	
	
	public void setPriorityHash(){
		if(this.bioSysName == BioSystemName.HUMAN){
			
			
//			btPriorityHash.put(ReactionName., new ReactionName[] 
//					{ReactionName.
//							});	
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_TERMINAL_14_LINKED_ALPHA_D_GLUCOSE_PATTERN1, new ReactionName[] 
			{ReactionName.D_GLYCOSIDE_HYDROLYSIS,
					ReactionName.GLYCOSIDE_HYDROLYSIS,
					});				
			
			// Zhou, S.F. et al. (2009); Substrates, Inducers, Inhibitors and Structure-Activity Relationships of 
			// Human Cytochrome P450 2C9 and Implications in Drug Development; Current Medicinal Chemistry, 2009, 16, 3480-3675
			btPriorityHash.put(ReactionName.EPOXIDE_HYDROLYSIS, new ReactionName[] 
			{ReactionName.EPOXIDATION,
					ReactionName.EPOXIDATION_OF_VINYL_ETHER,
					ReactionName.ALKENE_EPOXIDATION_PATTERN1,
					ReactionName.ALKENE_EPOXIDATION_PATTERN2,
					ReactionName.ARENE_EPOXIDATION_PATTERN1,
					ReactionName.ARENE_EPOXIDATION_PATTERN2
					});				
			
			// Giraud, B. et al. (2010); Oxazaphosphorines: new therapeutic strategies for an old class of drugs; Expert Opinion 
			// on Drug Metabolism & Toxicology, 6:8, 919-938, DOI: 10.1517/17425255.2010.487861
			btPriorityHash.put(ReactionName._4P_HYDROXYLATION_OF_OXAZAPHOSPHORINE, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_SECONDARY_HETEROALICYCLIC_CARBON_PATTERN1,
					ReactionName.HYDROXYLATION_OF_SECONDARY_HETEROALICYCLIC_CARBON_PATTERN2
					});			
			
			btPriorityHash.put(ReactionName.HYDROXYLATION_OF_TERMINAL_METHYL, new ReactionName[] 
					{ReactionName.HYDROXYLATION_OF_ACYCLIC_ALIPHATIC_SECONDARY_CARBON
							});					
			
			btPriorityHash.put(ReactionName.HYDROXYLATION_OF_ALIPHATIC_SECONDARY_PENULTIMATE_CARBON, new ReactionName[] 
					{ReactionName.HYDROXYLATION_OF_ACYCLIC_ALIPHATIC_SECONDARY_CARBON
							});
						
			btPriorityHash.put(ReactionName.HYDROXYLATION_OF_ALIPHATIC_TERTIARY_PENULTIMATE_CARBON, new ReactionName[] 
					{ReactionName.HYDROXYLATION_OF_ACYCLIC_ALIPHATIC_SECONDARY_CARBON
							});	
			
			
			
			btPriorityHash.put(ReactionName.AROMATIC_OH_GLUCURONIDATION, new ReactionName[] 
					{ReactionName._13_DICARBONYL_C_GLUCURONIDATION
							});			

			btPriorityHash.put(ReactionName.BETA_OXIDATION_OF_CARBXOYLIC_ACID, new ReactionName[] 
					{ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
							});			
			
			btPriorityHash.put(ReactionName.AROMATIC_OH_GLUCURONIDATION, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION
							});	
			
			/*
			 * 1. Kuuranne, T. et al. (2003); Glucuronidation of anabolic androgenic steroids by recombinant human UDP-glucuronosyltransferases.
			 * Drug Metab Dispos. 2003 Sep;31(9):1117-24; PMID: 12920167; DOI: 10.1124/dmd.31.9.1117
			 */
			
			btPriorityHash.put(ReactionName._3_OH_GLUCURONIDATION_OF_STEROL, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
							});	
			
			btPriorityHash.put(ReactionName._3_OH_GLUCURONIDATION_OF_STEROL_PATTERN2, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
							});	
			
			btPriorityHash.put(ReactionName._6_OH_GLUCURONIDATION_OF_STEROL, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
					});				
			
			btPriorityHash.put(ReactionName._6_OH_GLUCURONIDATION_OF_STEROL_PATTERN2, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
					});	
			
			btPriorityHash.put(ReactionName._7_OH_GLUCURONIDATION_OF_STEROL, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
					});		
			
			btPriorityHash.put(ReactionName._7_OH_GLUCURONIDATION_OF_STEROL_PATTERN2, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
					});
			
			btPriorityHash.put(ReactionName._17_OH_GLUCURONIDATION_OF_STEROL, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
					});
			
			btPriorityHash.put(ReactionName._17_OH_GLUCURONIDATION_OF_STEROL_PATTERN2, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.ALIPHATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION
					});
			
			/*
			 * 1. Zimniak, P. et al. (1988); Formation of three types of glucuronides of 6-hydroxy bile acids by rat liver microsomes
			 * J Lipid Res. 1988 Feb;29(2):183-90.; PMID3367087
			 * 2.  Dombroski, R. et al.(1997); Human Saturated Steroid 6a-Hydroxylase; J Clin Endocrinol Metab. 1997 May;82(5):1338-44.
			 * PMID: 9141513 DOI: 10.1210/jcem.82.5.3908
			 */
			
			btPriorityHash.put(ReactionName._6_OH_GLUCURONIDATION_OF_STEROL, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION, ReactionName.AROMATIC_OH_GLUCURONIDATION
							});
		
			/* 1. Pikuleva, IA et. al. (2013); Cytochromes p450: roles in diseases.; J Biol Chem. 2013 Jun 14;288(24):17091-8. doi: 10.1074/jbc.R112.431916
			 * PMID: 23632021 PMCID: PMC3682515 DOI: 10.1074/jbc.R112.431916
			 * 2. iii.	Muhammad, A. et al. (2011); A review of mechanistic studies on aromatase (CYP19) and 17-hydroxylase-17,20-lyase (CYP17); 
			 * J Steroid Biochem Mol Biol. 2011 May;125(1-2):2-12; PMID: 21094255; DOI: 10.1016/j.jsbmb.2010.11.003 
			 * 3. http://www.chem.qmul.ac.uk/iubmb/enzyme/EC1/14/14/16.html
			 * 4. http://www.chem.qmul.ac.uk/iubmb/enzyme/EC1/14/14/19.html
			 * 5. http://www.chem.qmul.ac.uk/iubmb/enzyme/EC1/14/14/29.html
			 * 6. http://www.chem.qmul.ac.uk/iubmb/enzyme/EC1/14/15/4.html
			 * 7. Niwa, T. et al. (1998); Contribution of human hepatic cytochrome P450 isoforms to regioselective hydroxylation of steroid 
			 * hormones.; Xenobiotica. 1998 Jun;28(6):539-47; PMID:9667077; DOI:10.1080/004982598239290
			 */
						
			
			btPriorityHash.put(ReactionName.STEROL_2_HYDROXYLATION_PATTERN1, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});	

			btPriorityHash.put(ReactionName.STEROL_2_HYDROXYLATION_PATTERN2, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.AROMATIC_HYDROXYLATION_OF_FUSED_BENZENE_RING_PATTERN2,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});	
			
			btPriorityHash.put(ReactionName.STEROL_4_HYDROXYLATION_PATTERN1, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});	
			
			btPriorityHash.put(ReactionName.STEROL_4_HYDROXYLATION_PATTERN2, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.AROMATIC_HYDROXYLATION_OF_FUSED_BENZENE_RING_PATTERN2,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});
			
			btPriorityHash.put(ReactionName.STEROL_6_HYDROXYLATION_PATTERN1, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});	
			
			btPriorityHash.put(ReactionName.STEROL_6_HYDROXYLATION_PATTERN2, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.AROMATIC_HYDROXYLATION_OF_FUSED_BENZENE_RING_PATTERN2,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});				
			
			btPriorityHash.put(ReactionName.STEROL_7_HYDROXYLATION_PATTERN1, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});		
			
			btPriorityHash.put(ReactionName.STEROL_7_HYDROXYLATION_PATTERN2, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});				
			
			btPriorityHash.put(ReactionName.STEROL_7_HYDROXYLATION_PATTERN3, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});	
			
			btPriorityHash.put(ReactionName.STEROL_11_BETA_HYDROXYLATION, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					
					});	
			btPriorityHash.put(ReactionName.STEROL_17_ALPHA_HYDROXYLATION_PATTERN1, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});
			btPriorityHash.put(ReactionName.STEROL_17_ALPHA_HYDROXYLATION_PATTERN2, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});
			btPriorityHash.put(ReactionName.STEROL_21_HYDROXYLATION, new ReactionName[] 
			{ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN3,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_SECONDARY_CARBON_PATTERN4,
				ReactionName.ALPHA_HYDROXYLATION_OF_CARBONYL_GROUP,
				ReactionName.HYDROXYLATION_OF_ALICYCLIC_TERTIARY_CARBON
					});	
			
			
			/*
			 * 1. Mueller, J.W. ET AL (2015); The Regulation of Steroid Action by Sulfation and Desulfation; 
			 * Endocrine Reviews, October 2015, 36(5):526 –563; doi: 10.1210/er.2015-1036
			 * 2. Alnouti,Y (2009); Bile Acid Sulfation: A Pathway of Bile Acid Elimination and Detoxification; 
			 * TOXICOLOGICAL SCIENCES 108(2), 225–246 (2009); doi:10.1093/toxsci/kfn268
			 * 3. Setchell, K.D.R. et al. (1988); The Bile Acids: Chemistry, Physiology, and Metabolism; ISBN 978-1-4613-0901-7; p. 27
			 */
			btPriorityHash.put(ReactionName.STEROL_3_OH_SULFATION, new ReactionName[] 
					{ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND,
							ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
							ReactionName.SULFATION_OF_PRIMARY_ALCOHOL
							});		
			
			btPriorityHash.put(ReactionName.STEROL_7_OH_SULFATION, new ReactionName[] 
					{ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND,
							ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
							ReactionName.SULFATION_OF_PRIMARY_ALCOHOL
							});	
			
			btPriorityHash.put(ReactionName.STEROL_7_OH_SULFATION_PATTERN2, new ReactionName[] 
					{ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND,
							ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
							ReactionName.SULFATION_OF_PRIMARY_ALCOHOL
							});	
			
			btPriorityHash.put(ReactionName.STEROL_12_OH_SULFATION, new ReactionName[] 
					{ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND,
							ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
							ReactionName.SULFATION_OF_PRIMARY_ALCOHOL
							});				
			
			
			btPriorityHash.put(ReactionName.STEROL_12_OH_SULFATION_PATTERN2, new ReactionName[] 
					{ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND,
							ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
							ReactionName.SULFATION_OF_PRIMARY_ALCOHOL
							});	
			

			btPriorityHash.put(ReactionName.PHENOLIC_STEROID_SULFONATION, new ReactionName[] 
					{ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND,
							ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
							ReactionName.SULFATION_OF_PRIMARY_ALCOHOL,
//							ReactionName.AROMATIC_OH_GLUCURONIDATION
							});			
			
			
			// Phytochemistry Reviews 1: 175–182, 2002. Phase II metabolism (p.180)
			btPriorityHash.put(ReactionName.ISOFLAVONE_4P_OH_GLUCURONIDATION, new ReactionName[] 
					{ReactionName.AROMATIC_OH_GLUCURONIDATION
							});				
			btPriorityHash.put(ReactionName.ISOFLAVONE_7_OH_GLUCURONIDATION, new ReactionName[] 
					{ReactionName.AROMATIC_OH_GLUCURONIDATION
							});		
			// Re. Jarlei
			// For compounds with two primary alcohol functions, oxidation is usually seen at only one of the groups.  
			// Compounds containing both primary and secondary alcohol groups are preferentially oxidized at the primary group.
			btPriorityHash.put(ReactionName.OXIDATION_OF_PRIM_ALCOHOL_TO_ALDEHYDE, new ReactionName[] 
					{ReactionName.OXIDATION_OF_SEC_ALCOHOL_TO_KETONE, ReactionName.OXIDATION_OF_SEC_ALICYCLIC_ALCOHOL
							});

			btPriorityHash.put(ReactionName.GSH_CONJUGATION_OF_EPOXIDE, new ReactionName[] 
					{ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN1, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN2, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN4,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN5,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN6,
							ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.GLUCURONIDATION_OF_PRIMARY_AROMATIC_AMINE,
							ReactionName.GLUCURONIDATION_OF_ALIPHATIC_TERTIARY_AMINE,
							ReactionName.GLUCURONIDATION_OF_AMINE_OXIDE,
							ReactionName.GLUCURONIDATION_OF_PRIM_SEC_ALIPH_AND_BENZYLIC_ALCOHOLS,
							ReactionName.AMIDE_N_GLUCURONIDATION,
							ReactionName.ALKYL_OH_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.CARBAMATE_N_GLUCURONIDATION,
							ReactionName.PHENOL_O_GLUCURONIDATION,
//							ReactionName.IMINO_GLUCURONIDATION,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN2
							});
			
			btPriorityHash.put(ReactionName.GSH_CONJUGATION_OF_ARENE_EPOXIDE, new ReactionName[] 
					{ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN1, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN2, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN4,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN5,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN6,
							ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.GLUCURONIDATION_OF_PRIMARY_AROMATIC_AMINE,
							ReactionName.GLUCURONIDATION_OF_ALIPHATIC_TERTIARY_AMINE,
							ReactionName.GLUCURONIDATION_OF_AMINE_OXIDE,
							ReactionName.GLUCURONIDATION_OF_PRIM_SEC_ALIPH_AND_BENZYLIC_ALCOHOLS,
							ReactionName.AMIDE_N_GLUCURONIDATION,
							ReactionName.ALKYL_OH_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.CARBAMATE_N_GLUCURONIDATION,
							ReactionName.PHENOL_O_GLUCURONIDATION,
//							ReactionName.IMINO_GLUCURONIDATION,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN2
							});			
			
			btPriorityHash.put(ReactionName.GSH_CONJUGATION_OF_ARYL_HALIDE_PATTERN1, new ReactionName[] 
					{ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN1, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN2, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN4,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN5,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN6,
							ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.GLUCURONIDATION_OF_PRIMARY_AROMATIC_AMINE,
							ReactionName.GLUCURONIDATION_OF_ALIPHATIC_TERTIARY_AMINE,
							ReactionName.GLUCURONIDATION_OF_AMINE_OXIDE,
							ReactionName.GLUCURONIDATION_OF_PRIM_SEC_ALIPH_AND_BENZYLIC_ALCOHOLS,
							ReactionName.AMIDE_N_GLUCURONIDATION,
							ReactionName.ALKYL_OH_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.CARBAMATE_N_GLUCURONIDATION,
							ReactionName.PHENOL_O_GLUCURONIDATION,
//							ReactionName.IMINO_GLUCURONIDATION,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN2
							});				
			
			btPriorityHash.put(ReactionName.GSH_CONJUGATION_OF_ARYL_HALIDE_PATTERN2, new ReactionName[] 
					{ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN1, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN2, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN4,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN5,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN6,
							ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.GLUCURONIDATION_OF_PRIMARY_AROMATIC_AMINE,
							ReactionName.GLUCURONIDATION_OF_ALIPHATIC_TERTIARY_AMINE,
							ReactionName.GLUCURONIDATION_OF_AMINE_OXIDE,
							ReactionName.GLUCURONIDATION_OF_PRIM_SEC_ALIPH_AND_BENZYLIC_ALCOHOLS,
							ReactionName.AMIDE_N_GLUCURONIDATION,
							ReactionName.ALKYL_OH_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.CARBAMATE_N_GLUCURONIDATION,
							ReactionName.PHENOL_O_GLUCURONIDATION,
//							ReactionName.IMINO_GLUCURONIDATION,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN2
							});				
			
			btPriorityHash.put(ReactionName.GSH_CONJUGATION_OF_EWG_SUBSTITUTED_BENZENE_PATTERN1, new ReactionName[] 
					{ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN1, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN2, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN4,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN5,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN6,
							ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.GLUCURONIDATION_OF_PRIMARY_AROMATIC_AMINE,
							ReactionName.GLUCURONIDATION_OF_ALIPHATIC_TERTIARY_AMINE,
							ReactionName.GLUCURONIDATION_OF_AMINE_OXIDE,
							ReactionName.GLUCURONIDATION_OF_PRIM_SEC_ALIPH_AND_BENZYLIC_ALCOHOLS,
							ReactionName.AMIDE_N_GLUCURONIDATION,
							ReactionName.ALKYL_OH_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.CARBAMATE_N_GLUCURONIDATION,
							ReactionName.PHENOL_O_GLUCURONIDATION,
//							ReactionName.IMINO_GLUCURONIDATION,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN2
							});	
			
			btPriorityHash.put(ReactionName.GSH_CONJUGATION_OF_EWG_SUBSTITUTED_BENZENE_PATTERN2, new ReactionName[] 
					{ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN1, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN2, 
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN4,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN5,
							ReactionName.AZOLE_N_GLUCURONIDATION_PATTERN6,
							ReactionName.AROMATIC_OH_GLUCURONIDATION,
							ReactionName.GLUCURONIDATION_OF_PRIMARY_AROMATIC_AMINE,
							ReactionName.GLUCURONIDATION_OF_ALIPHATIC_TERTIARY_AMINE,
							ReactionName.GLUCURONIDATION_OF_AMINE_OXIDE,
							ReactionName.GLUCURONIDATION_OF_PRIM_SEC_ALIPH_AND_BENZYLIC_ALCOHOLS,
							ReactionName.AMIDE_N_GLUCURONIDATION,
							ReactionName.ALKYL_OH_GLUCURONIDATION,
							ReactionName.AROMATIC_ACYL_O_GLUCURONIDATION,
							ReactionName.CARBAMATE_N_GLUCURONIDATION,
							ReactionName.PHENOL_O_GLUCURONIDATION,
//							ReactionName.IMINO_GLUCURONIDATION,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_ALIPHATIC_AMINE_N_GLUCURONIDATION_PATTERN3,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_AROMATIC_AMINE_N_GLUCURONIDATION_PATTERN2,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN1,
							ReactionName.TERTIARY_MIXED_AMINE_N_GLUCURONIDATION_PATTERN2
							});	
			
//			btPriorityHash.put(ReactionName.EPOXIDE_HYDROLYSIS_PATTERN2, new ReactionName[] 
//					{ReactionName.EPOXIDE_HYDROLYSIS
//				});
			
			// Itaaho K et al. biochemical pharmacology 74 (2007) 504–510; Regioselective sulfonation of dopamine by SULT1A3
			// in vitro provides a molecular explanation for the preponderance of dopamine-3-O-sulfate in human blood circulation
			
			btPriorityHash.put(ReactionName._3_O_SULFATION_OF_PHENOLIC_COMPOUND, new ReactionName[] 
					{ReactionName._4_O_SULFATION_OF_PHENOLIC_COMPOUND, ReactionName.O_SULFONATION_OF_AROMATIC_ALCOHOL, 
						ReactionName.SULFONATION_OF_ALIPHATIC_ALCOHOL,
						ReactionName.SULFATION_OF_SECONDARY_ALCOHOL,
						ReactionName.O_SULFONATION_OF_VINYL_ALCOHOL
									
					// ReactionName.PHENOL_O_SULFONATION
							});	
			
			// Itaaho K et al. biochemical pharmacology 74 (2007) 504–510; Regioselective sulfonation of dopamine by SULT1A3
			// in vitro provides a molecular explanation for the preponderance of dopamine-3-O-sulfate in human blood circulation
			btPriorityHash.put(ReactionName._4_O_SULFATION_OF_PHENOLIC_COMPOUND, new ReactionName[] 
					{							
						ReactionName.O_SULFONATION_OF_AROMATIC_ALCOHOL, 
						ReactionName.SULFONATION_OF_ALIPHATIC_ALCOHOL,
						ReactionName.O_SULFONATION_OF_VINYL_ALCOHOL							
						// ReactionName.PHENOL_O_SULFONATION
							});		
			
			// Curr Drug Metab. 2011 November ; 12(9): 900–916 (Regioselective Sulfation and Glucuronidation of Phenolics: Insights into the Structural 
			// Basis of Conjugation; Section 4.2)	
//			btPriorityHash.put(ReactionName.PHENOL_O_SULFONATION, new ReactionName[] 
//					{ReactionName.O_SULFONATION_OF_AROMATIC_ALCOHOL, ReactionName.SULFONATION_OF_ALIPHATIC_ALCOHOL,
//							ReactionName.O_SULFONATION_OF_VINYL_ALCOHOL
//							});	
//			btPriorityHash.put(ReactionName.PHENOL_O_SULFONATION, new ReactionName[] 
//					{ReactionName.O_SULFONATION_OF_AROMATIC_ALCOHOL, ReactionName.SULFONATION_OF_ALIPHATIC_ALCOHOL,
//							ReactionName.O_SULFONATION_OF_VINYL_ALCOHOL
//							});	
			// Curr Drug Metab. 2011 November ; 12(9): 900–916 (Regioselective Sulfation and Glucuronidation of Phenolics: Insights into the Structural
			// Basis of Conjugation; Section 4.2)
			btPriorityHash.put(ReactionName.O_SULFONATION_OF_AROMATIC_ALCOHOL, new ReactionName[] 
					{ReactionName.SULFONATION_OF_ALIPHATIC_ALCOHOL,
							ReactionName.O_SULFONATION_OF_VINYL_ALCOHOL
							});		
			
			// Douglas Tsao et al. Chem Phys Lett. 2011 Apr 20; 506(4-6): 135–138.
			// Regioselectivity of Catechol O-Methyltransferase Confers Enhancement of Catalytic Activity
			btPriorityHash.put(ReactionName.CATECHOL_O_METHYLATION_PATTERN2, new ReactionName[] 
					{
//						ReactionName.CATECHOL_O_METHYLATION_PATTERN3, 
							ReactionName.CATECHOL_O_METHYLATION_PATTERN1, 
							ReactionName.AROMATIC_O_METHYLATION,
							});			
			
			btPriorityHash.put(ReactionName.CATECHOL_O_METHYLATION_PATTERN3, new ReactionName[] 
					{ReactionName.CATECHOL_O_METHYLATION_PATTERN1, ReactionName.AROMATIC_O_METHYLATION,
							});			
			
			btPriorityHash.put(ReactionName.CATECHOL_O_METHYLATION_PATTERN1, new ReactionName[] 
					{ReactionName.AROMATIC_O_METHYLATION,
							});
			
			btPriorityHash.put(ReactionName.LONG_CHAIN_S_3_HYDROXYACYL_COA_DEHYDROGENATION, new ReactionName[] 
					{ReactionName.S_3_HYDROXYACYL_COA_DEHYDROGENATION,
					});

			// KEGG (http://www.genome.jp/dbget-bin/www_bget?reaction+R03617)
			btPriorityHash.put(ReactionName.GALACTOSYLCERAMIDE_GALACTO_HYDROLISIS, new ReactionName[] 
					{ReactionName.GLYCOSYLCERAMIDE_GLYCOSYL_HYDROLYSIS,
					});		
			
			btPriorityHash.put(ReactionName.DIGALACTOSYLCERAMIDE_GALACTO_HYDROLYSIS_PATTERN1, new ReactionName[] 
					{ReactionName.GLYCOSYLCERAMIDE_GLYCOSYL_HYDROLYSIS,
					});	
			
			btPriorityHash.put(ReactionName.DIGALACTOSYLCERAMIDE_GALACTO_HYDROLYSIS_PATTERN2, new ReactionName[] 
					{ReactionName.GLYCOSYLCERAMIDE_GLYCOSYL_HYDROLYSIS,
					});	
			
			btPriorityHash.put(ReactionName.CEREBROSIDE_3_SULFATE_SULFOHYDROLYSIS, new ReactionName[] 
					{ReactionName.ARYLSULFATE_SULFOHYDROLYSIS,
					});	
			
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN1, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});	
			
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN2, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});	
					
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN3, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});	
			
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN4, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});
			
			
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN5, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});	
		
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN6, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});
			
			
			
			/**
			 * (R1) Bolleddula, J. et al. (2014); Biotransformation and bioactivation reactions of alicyclic amines in drug molecules, Drug Metabolism Reviews, 46:3, 379-419, DOI: 10.3109/03602532.2014.924962. |
			 * pp. 397-398.
			 */
			
			// N-Glucuronidation (4N-Gluc, 4N+(O )-Gluc, 4N+(R)-Gluc, 4N-O-gluc, 4N (CO)Ogluc), N-sulfation (4N+(R)SO3H, 4N-O-SO3H), and N-acetylation are also reported for piperidine drugs
			
			
		}
		else if(this.bioSysName == BioSystemName.ENVMICRO){
			/**
			 * A reactions paired with sets of reactions they have priorities over. This was
			 * retrieved from the paper by Fenner K, Gao J, Kramer S, Ellis L, and Wackett L.
			 * Data-driven extraction of relative reasoning rules to limit combinatorial explosion in 
			 * biodegradation pathway prediction. Bioinformatics. 2008 Sep 15;24(18):2079-85. 
			 * doi: 10.1093/bioinformatics/btn378
			 */

			// Alcohol oxidation(acx) reactions dominates arm and alh
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0001, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013
						, ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0241,
						ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3,
						ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2
						});
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0002, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
						ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
						ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332,  ReactionName.EAWAG_RULE_BT0333,  ReactionName.EAWAG_RULE_BT0334,
						
						});		
			
			// Aldehyde oxidation (adx) dominates over ard, arm, cc, alh.	
			//		btPriorityHash.put(ReactionName.EAWAG_RULE_BT0003, new ReactionName[] 
			//				{}); // These strict rules were treated separately and do not form part of the extended rule set
			
			
			// Aromatic ring dioxygenation (ard)  
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0005_PATTERN1, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0005_PATTERN2, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0005_PATTERN3, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0042, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0012});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0055_PATTERN1, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013});	
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0055_PATTERN2, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013});			
			
			
			// Aromatic vic-diol ring cleavage (arc) dominates over ard, arm.
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0008, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
						ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
						ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0064,
						ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0128});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0045, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
						ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, 
						ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0064});
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0069, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0014});	
			
			//				// could not find this rule
			//				btPriorityHash.put(ReactionName.EAWAG_RULE_BT0131, new ReactionName[] 
			//						{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0014,
			//							ReactionName.EAWAG_RULE_BT0064});
			//
			//				// could not find this rule
			//				btPriorityHash.put(ReactionName.EAWAG_RULE_BT0165, new ReactionName[] 
			//						{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0014,
			//							ReactionName.EAWAG_RULE_BT0064});
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0174, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2});	
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0297, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0011,
						ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014});			
			
			
			
			// Phenolic ring monooxygenation (prm) dominates over dominates over arm
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0014, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, 
						ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
						 ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0353 });
			
			// C=C bond reactions (cc) dominates over alh
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0021_PATTERN1, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
						ReactionName.EAWAG_RULE_BT0242_PATTERN3});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0021_PATTERN2, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
						ReactionName.EAWAG_RULE_BT0242_PATTERN3});
			
			//				// CoA-thioester formation
			//				// could not find this rule
			//				btPriorityHash.put(ReactionName.EAWAG_RULE_BT0094, new ReactionName[] 
			//						{ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
			//								ReactionName.EAWAG_RULE_BT0242_PATTERN3});
			
			
			
			// Keto-ene hydrolysis (keh) dominates over ard, arm, cc, coa, dc, ket
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0040, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
						ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0049, 
						ReactionName.EAWAG_RULE_BT0231});
			
			//				btPriorityHash.put(ReactionName.EAWAG_RULE_BT0047, new ReactionName[] 
			//						{});
			
			// Oxidation of vic-di-H-di-OH to aromatic (vda) dominates over acx, ard, arm, cc
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0056, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					    ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, 
					    ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					    ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0291_PATTERN1, ReactionName.EAWAG_RULE_BT0291_PATTERN2
					 });
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0297, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					    ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, 
					    ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					    ReactionName.EAWAG_RULE_BT0049
					 });		
			
			// Others
//			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0041, new ReactionName[] 
//					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
//						ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, 
//						ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0064});
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0254_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0036,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
//					 ReactionName.EAWAG_RULE_BT0196, 
					
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, ReactionName.EAWAG_RULE_BT0353, 
					ReactionName.EAWAG_RULE_BT0359, ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN2			
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0254_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0036,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
//					 ReactionName.EAWAG_RULE_BT0196, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359, ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN2			
			});
			
			// aliphatic hydroxylation
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0036, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0037_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, 
					ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0102,
					ReactionName.EAWAG_RULE_BT0103_PATTERN1, ReactionName.EAWAG_RULE_BT0103_PATTERN2, ReactionName.EAWAG_RULE_BT0333,
					ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0037_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, 
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0102,
					ReactionName.EAWAG_RULE_BT0103_PATTERN1, ReactionName.EAWAG_RULE_BT0103_PATTERN2, ReactionName.EAWAG_RULE_BT0333,
					ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});	
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0023_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0051_PATTERN1,
					ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, ReactionName.EAWAG_RULE_BT0051_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0332,
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0023_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0051_PATTERN1,
					ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, ReactionName.EAWAG_RULE_BT0051_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0332,
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0028, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0030, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0033, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0034_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0034_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0035, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0102, ReactionName.EAWAG_RULE_BT0103_PATTERN1, ReactionName.EAWAG_RULE_BT0103_PATTERN2,
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0373, ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0050, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0147, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0058, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0001, ReactionName.EAWAG_RULE_BT0002});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0060, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,  ReactionName.EAWAG_RULE_BT0072,
					 ReactionName.EAWAG_RULE_BT0128,  ReactionName.EAWAG_RULE_BT0343,  ReactionName.EAWAG_RULE_BT0353,  ReactionName.EAWAG_RULE_BT0374_PATTERN1, 
					 ReactionName.EAWAG_RULE_BT0374_PATTERN2
					});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0060, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0241
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0062, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3,
					ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0063_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0078, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, 
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0383
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0063_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0078, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, 
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0383
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0064, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, 
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0065_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0055_PATTERN1,
					ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0066_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0002
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0066_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0002
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0068, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3,
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353,
					ReactionName.EAWAG_RULE_BT0402
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0071, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333,
					ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0072, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0359
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0073, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, 
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0077, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0332,  ReactionName.EAWAG_RULE_BT0333,  ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0078, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0373, ReactionName.EAWAG_RULE_BT0374_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0374_PATTERN2, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0080, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0373, ReactionName.EAWAG_RULE_BT0374_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0374_PATTERN2, ReactionName.EAWAG_RULE_BT0430
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0082, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3,
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0078, ReactionName.EAWAG_RULE_BT0080, ReactionName.EAWAG_RULE_BT0241,
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3,
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0383
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0102, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0024_PATTERN1, ReactionName.EAWAG_RULE_BT0024_PATTERN2, ReactionName.EAWAG_RULE_BT0042,
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0064,
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0072,
					ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0343, ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0361, ReactionName.EAWAG_RULE_BT0432_PATTERN1,
					ReactionName.EAWAG_RULE_BT0432_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0103_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0103_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0104_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0361
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0104_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0361
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0107, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0107, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0051_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, ReactionName.EAWAG_RULE_BT0051_PATTERN4
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0143, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3,
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0144, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0146_PATTERN1_A, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0146_PATTERN1_B, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0146_PATTERN1_C, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0146_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0153_PATTERN1, new ReactionName[] {
					 ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3,
					 ReactionName.EAWAG_RULE_BT0372
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0158_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0158_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0166_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN3, ReactionName.EAWAG_RULE_BT0051_PATTERN4
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0166_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN3, ReactionName.EAWAG_RULE_BT0051_PATTERN4
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0173, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0177, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0071
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0180, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334			
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0184_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0184_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0193, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0195, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0064
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0200_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0044, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, ReactionName.EAWAG_RULE_BT0352_PATTERN1,
					ReactionName.EAWAG_RULE_BT0352_PATTERN2, ReactionName.EAWAG_RULE_BT0352_PATTERN3, ReactionName.EAWAG_RULE_BT0352_PATTERN4,
					ReactionName.EAWAG_RULE_BT0352_PATTERN5
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0200_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0044, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, ReactionName.EAWAG_RULE_BT0352_PATTERN1,
					ReactionName.EAWAG_RULE_BT0352_PATTERN2, ReactionName.EAWAG_RULE_BT0352_PATTERN3, ReactionName.EAWAG_RULE_BT0352_PATTERN4,
					ReactionName.EAWAG_RULE_BT0352_PATTERN5
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0225, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0014
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0226, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0237, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, 
					ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0042, 
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0291_PATTERN1,
					ReactionName.EAWAG_RULE_BT0291_PATTERN2, ReactionName.EAWAG_RULE_BT0353
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0243, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0259, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0260, new ReactionName[] {			
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0267, new ReactionName[] {			
					ReactionName.EAWAG_RULE_BT0071, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333,
					ReactionName.EAWAG_RULE_BT0334
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0280, new ReactionName[] {			
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0281, new ReactionName[] {			
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0282, new ReactionName[] {			
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0284, new ReactionName[] {			
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0298, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0306, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0318_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0353
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0318_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0353
					
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0322, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0322, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0327, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0330_PATTERN1, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2, ReactionName.EAWAG_RULE_BT0162,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0259
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0330_PATTERN2, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2, ReactionName.EAWAG_RULE_BT0162,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0259
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0330_PATTERN3, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2, ReactionName.EAWAG_RULE_BT0162,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0259
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0330_PATTERN4, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2, ReactionName.EAWAG_RULE_BT0162,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0259
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0330_PATTERN5, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2, ReactionName.EAWAG_RULE_BT0162,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0259
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0330_PATTERN6, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0023_PATTERN1, ReactionName.EAWAG_RULE_BT0023_PATTERN2, ReactionName.EAWAG_RULE_BT0162,
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0259
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0333, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0332
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0334, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0337_PATTERN1, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0337_PATTERN2, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
					
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0337_PATTERN3, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0337_PATTERN4, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0337_PATTERN5, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0337_PATTERN6, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0339, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0340_PATTERN1, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0001, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0042,
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0437_PATTERN1, ReactionName.EAWAG_RULE_BT0437_PATTERN1
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0340_PATTERN2, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0001, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0042,
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0437_PATTERN1, ReactionName.EAWAG_RULE_BT0437_PATTERN1
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0340_PATTERN3, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0001, ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0005_PATTERN3, ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0042,
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0210, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0437_PATTERN1, ReactionName.EAWAG_RULE_BT0437_PATTERN1
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0342, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0344, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0345, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0082,
					ReactionName.EAWAG_RULE_BT0353
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN3, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN4, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN5, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN6, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0348_PATTERN7, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0349_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0349_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0349_PATTERN3, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0349_PATTERN4, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0254_PATTERN1, ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0351_PATTERN5, ReactionName.EAWAG_RULE_BT0351_PATTERN6, ReactionName.EAWAG_RULE_BT0353
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0350_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0063_PATTERN1, ReactionName.EAWAG_RULE_BT0063_PATTERN2,
					ReactionName.EAWAG_RULE_BT0067_PATTERN1, ReactionName.EAWAG_RULE_BT0067_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, ReactionName.EAWAG_RULE_BT0353,
					ReactionName.EAWAG_RULE_BT0414, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0350_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0063_PATTERN1, ReactionName.EAWAG_RULE_BT0063_PATTERN2,
					ReactionName.EAWAG_RULE_BT0067_PATTERN1, ReactionName.EAWAG_RULE_BT0067_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, ReactionName.EAWAG_RULE_BT0353,
					ReactionName.EAWAG_RULE_BT0414, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN2
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, ReactionName.EAWAG_RULE_BT0416_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN3, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN1
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN4, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN1
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN5, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, ReactionName.EAWAG_RULE_BT0416_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN6, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN1
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0351_PATTERN7, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0021_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0049,
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0060, ReactionName.EAWAG_RULE_BT0064, ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2,
					ReactionName.EAWAG_RULE_BT0072, ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0162, 
					ReactionName.EAWAG_RULE_BT0231, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0359,
					ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0416_PATTERN1, ReactionName.EAWAG_RULE_BT0416_PATTERN1
					
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0352_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,  ReactionName.EAWAG_RULE_BT0014, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0049, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0082, 
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0427	
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0352_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,  ReactionName.EAWAG_RULE_BT0014, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0049, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0082, 
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0427	
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0352_PATTERN3, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,  ReactionName.EAWAG_RULE_BT0014, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0049, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0082, 
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0427	
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0352_PATTERN4, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,  ReactionName.EAWAG_RULE_BT0014, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0049, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0082, 
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0427	
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0352_PATTERN5, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,  ReactionName.EAWAG_RULE_BT0014, 
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0049, 
					ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, ReactionName.EAWAG_RULE_BT0082, 
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334,
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0427	
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0353, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN1, ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0051_PATTERN4,  ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0333
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0356_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0071,
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0356_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0071,
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0356_PATTERN3, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2, ReactionName.EAWAG_RULE_BT0071,
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0357_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2,
					ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0358, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0337_PATTERN1, ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN4, ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6,
					ReactionName.EAWAG_RULE_BT0352_PATTERN1, ReactionName.EAWAG_RULE_BT0352_PATTERN2, ReactionName.EAWAG_RULE_BT0352_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0352_PATTERN4, ReactionName.EAWAG_RULE_BT0352_PATTERN5, ReactionName.EAWAG_RULE_BT0373
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0359, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013,  ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0361, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0024_PATTERN1, ReactionName.EAWAG_RULE_BT0024_PATTERN1, ReactionName.EAWAG_RULE_BT0332,
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0362_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0362_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0363, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0353
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0366_PATTERN1, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0349_PATTERN1, ReactionName.EAWAG_RULE_BT0349_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0349_PATTERN3, ReactionName.EAWAG_RULE_BT0349_PATTERN4
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0366_PATTERN2, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0349_PATTERN1, ReactionName.EAWAG_RULE_BT0349_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0349_PATTERN3, ReactionName.EAWAG_RULE_BT0349_PATTERN4
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0373, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0065_PATTERN1,
					ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN1,
					ReactionName.EAWAG_RULE_BT0337_PATTERN2, ReactionName.EAWAG_RULE_BT0337_PATTERN3, ReactionName.EAWAG_RULE_BT0337_PATTERN4, 
					ReactionName.EAWAG_RULE_BT0337_PATTERN5, ReactionName.EAWAG_RULE_BT0337_PATTERN6, 
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0374_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0037_PATTERN1, ReactionName.EAWAG_RULE_BT0037_PATTERN1, ReactionName.EAWAG_RULE_BT0042,
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0374_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0037_PATTERN1, ReactionName.EAWAG_RULE_BT0037_PATTERN1, ReactionName.EAWAG_RULE_BT0042,
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0375, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0036
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0376, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0036
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0377, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2,
					ReactionName.EAWAG_RULE_BT0254_PATTERN1,ReactionName.EAWAG_RULE_BT0254_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0378_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0078, ReactionName.EAWAG_RULE_BT0080
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0378_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0078, ReactionName.EAWAG_RULE_BT0080
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0379, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0029, ReactionName.EAWAG_RULE_BT0291_PATTERN1, ReactionName.EAWAG_RULE_BT0291_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0383, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0078, ReactionName.EAWAG_RULE_BT0234, ReactionName.EAWAG_RULE_BT0242_PATTERN1,
					ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0387, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0128,
					ReactionName.EAWAG_RULE_BT0210
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0387, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0014, ReactionName.EAWAG_RULE_BT0021_PATTERN1,  ReactionName.EAWAG_RULE_BT0021_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0389, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0318_PATTERN1, ReactionName.EAWAG_RULE_BT0318_PATTERN2, ReactionName.EAWAG_RULE_BT0332, 
					ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0390, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0391_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0391_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0392, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0393, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0353
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0394, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0353
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0401, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0036
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0403, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0036
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0404, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0036
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0405, new ReactionName[] {	
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0051_PATTERN1,
					ReactionName.EAWAG_RULE_BT0051_PATTERN2, ReactionName.EAWAG_RULE_BT0051_PATTERN3, ReactionName.EAWAG_RULE_BT0067_PATTERN1,
					ReactionName.EAWAG_RULE_BT0067_PATTERN2, ReactionName.EAWAG_RULE_BT0430
			});	
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0408, new ReactionName[] {		
					ReactionName.EAWAG_RULE_BT0073, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0416_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0416_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0418_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0071
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0418_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0071
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0420_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0063_PATTERN1, ReactionName.EAWAG_RULE_BT0063_PATTERN2,
					ReactionName.EAWAG_RULE_BT0162
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0420_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0063_PATTERN1, ReactionName.EAWAG_RULE_BT0063_PATTERN2,
					ReactionName.EAWAG_RULE_BT0162
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0421, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0067_PATTERN1, ReactionName.EAWAG_RULE_BT0067_PATTERN2
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0425_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0067_PATTERN1, ReactionName.EAWAG_RULE_BT0067_PATTERN2
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0425_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0067_PATTERN1, ReactionName.EAWAG_RULE_BT0067_PATTERN2
			});			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0429, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0029, ReactionName.EAWAG_RULE_BT0067_PATTERN1, ReactionName.EAWAG_RULE_BT0067_PATTERN2,
					ReactionName.EAWAG_RULE_BT0243, ReactionName.EAWAG_RULE_BT0430
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0430, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3,
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0402
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0431, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN3
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0432_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0432_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0002, ReactionName.EAWAG_RULE_BT0021_PATTERN1, ReactionName.EAWAG_RULE_BT0021_PATTERN2,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, ReactionName.EAWAG_RULE_BT0334				
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0438, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0440, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0432_PATTERN1, ReactionName.EAWAG_RULE_BT0432_PATTERN2
			});		
			
			
			// Amide hydrolysis dominates (amh) over alh
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0024_PATTERN1, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0063_PATTERN1, ReactionName.EAWAG_RULE_BT0063_PATTERN2, ReactionName.EAWAG_RULE_BT0064, 
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0072, 
					ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2,  ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0254_PATTERN1,
					ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, 
					ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0343, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2
			});
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0024_PATTERN2, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0042, ReactionName.EAWAG_RULE_BT0055_PATTERN1, ReactionName.EAWAG_RULE_BT0055_PATTERN2, 
					ReactionName.EAWAG_RULE_BT0063_PATTERN1, ReactionName.EAWAG_RULE_BT0063_PATTERN2, ReactionName.EAWAG_RULE_BT0064, 
					ReactionName.EAWAG_RULE_BT0065_PATTERN1, ReactionName.EAWAG_RULE_BT0065_PATTERN2, ReactionName.EAWAG_RULE_BT0072, 
					ReactionName.EAWAG_RULE_BT0128, ReactionName.EAWAG_RULE_BT0241, ReactionName.EAWAG_RULE_BT0242_PATTERN1, 
					ReactionName.EAWAG_RULE_BT0242_PATTERN2,  ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0254_PATTERN1,
					ReactionName.EAWAG_RULE_BT0254_PATTERN2, ReactionName.EAWAG_RULE_BT0332, ReactionName.EAWAG_RULE_BT0333, 
					ReactionName.EAWAG_RULE_BT0334, ReactionName.EAWAG_RULE_BT0343, ReactionName.EAWAG_RULE_BT0351_PATTERN1,
					ReactionName.EAWAG_RULE_BT0351_PATTERN2, ReactionName.EAWAG_RULE_BT0351_PATTERN3, ReactionName.EAWAG_RULE_BT0351_PATTERN4,
					ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0374_PATTERN1, ReactionName.EAWAG_RULE_BT0374_PATTERN2
			});		
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0027, new ReactionName[] {
					ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
					ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
					ReactionName.EAWAG_RULE_BT0049, ReactionName.EAWAG_RULE_BT0353
			});
				
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0067_PATTERN1, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
						ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
						ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
						ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0402
						});		
			
			btPriorityHash.put(ReactionName.EAWAG_RULE_BT0067_PATTERN2, new ReactionName[] 
					{ReactionName.EAWAG_RULE_BT0005_PATTERN1, ReactionName.EAWAG_RULE_BT0005_PATTERN2, ReactionName.EAWAG_RULE_BT0005_PATTERN3, 
							ReactionName.EAWAG_RULE_BT0011, ReactionName.EAWAG_RULE_BT0012, ReactionName.EAWAG_RULE_BT0013, ReactionName.EAWAG_RULE_BT0014,
							ReactionName.EAWAG_RULE_BT0036, ReactionName.EAWAG_RULE_BT0242_PATTERN1, ReactionName.EAWAG_RULE_BT0242_PATTERN2, 
							ReactionName.EAWAG_RULE_BT0242_PATTERN3, ReactionName.EAWAG_RULE_BT0353, ReactionName.EAWAG_RULE_BT0402
							});	
			}

		else if(this.bioSysName == BioSystemName.GUTMICRO){
			
			
			
//			btPriorityHash.put(ReactionName.GLYCOSIDE_HYDROLYSIS, new ReactionName[] 
//					{ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1, ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2
//					});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_TERMINAL_14_LINKED_ALPHA_D_GLUCOSE_PATTERN1, new ReactionName[] 
			{ReactionName.D_GLYCOSIDE_HYDROLYSIS,
					ReactionName.GLYCOSIDE_HYDROLYSIS,
					});	
			btPriorityHash.put(ReactionName.AROMATIC_OH_GLUCURONIDATION, new ReactionName[] 
					{ReactionName.ALKYL_OH_GLUCURONIDATION
							});	
			
			btPriorityHash.put(ReactionName.B_TYPE_PROCYANIDIN_DIMER_DEGRADATION_PATTERN1, new ReactionName[] 
			{		ReactionName._3P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE,
					ReactionName._4P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE,
//					ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//					ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
					ReactionName.CATECHOL_DEHYDROXYLATION_PATTERN2
			});
			
			btPriorityHash.put(ReactionName.ANTHOCYANIDIN_C_RING_FISSION_PATTERN5, new ReactionName[] 
					{ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN1, 
//							ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//							ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4
					});
			
			
			/*
			 * Mosele, J.I. et al. (2015); Metabolic and Microbial Modulation of the Large Intestine 
			 * Ecosystem by Non-Absorbed Diet Phenolic Compounds: A Review; Molecules 2015, 20, 
			 * 17429-17468; doi:10.3390/molecules200917429
			 */
			
			btPriorityHash.put(ReactionName.FLAVAN_3_OL_C_RING_FISSION_PATTERN1, new ReactionName[] 
					{ReactionName._3P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE,
							ReactionName._4P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE
							});				
			
			btPriorityHash.put(ReactionName.FLAVAN_3_OL_C_RING_FISSION_PATTERN2, new ReactionName[] 
					{ReactionName._3P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE,
							ReactionName._4P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE
							});				
			

			
			// the 4-dehydroxylation of 4-hydroxy-5-phenylvalric acids (which results from the gamma-valerolactne fission) is thought to occur
			// in a two-step process. First the 4-hydroxy-5-phenylvalric acids are oxidised to their corresponding 4-oxo-5-phenylvalric acids.
			// This is followed by reductiv elimination of oxygen at the 4-oxo group to produce 5-phenylvaleric acids (Takagaki, A. and Nanjo, F., 
			// 2013, dx.doi.org/10.1021/jf304431v | J. Agric. Food Chem. 2013, 61, 4927−4935).
			// Takagaki, A. and Nanjo, F did not detect the 4-oxo-5-phenylvalric acids intermediates. 
			// Jiménez-Girón, A. et al (2014; Metabolites. 2014 Dec; 4(4): 1101–1118; doi:10.3390/metabo4041101 ) also proposed 4-dehydroxylation		
			// of the 4-hydroxy-5-phenylvalric acids.
			// The dehydroxylation of the catechol moiety occurs after the 4-dehydroxylation and subsequent beta-oxidation is completed (based on reported metabolites)
			
			btPriorityHash.put(ReactionName._4_HYDROXY_5_PHENYLVALERIC_ACID_DEHYDROXIDATION, new ReactionName[] 
					{ReactionName.BETA_OXIDATION_OF_CARBXOYLIC_ACID, ReactionName.CATECHOL_DEHYDROXYLATION_PATTERN1,
							ReactionName.CATECHOL_DEHYDROXYLATION_PATTERN2, ReactionName._3P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE, ReactionName._4P_DEHYDROXYLATION_OF_SUBSTITUTED_BENZENE
					});
			
			btPriorityHash.put(ReactionName.BETA_OXIDATION_OF_CARBXOYLIC_ACID, new ReactionName[] 
					{ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
							});			
			btPriorityHash.put(ReactionName.KETO_ENOL_TAUTOMERIZATION_PATTERN1, new ReactionName[] 
					{ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN1,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN2,
							ReactionName.REDUCTION_OF_ALPHA_BETA_UNSATURATED_COMPOUNDS_PATTERN3,
					});	
			
			/**
			 * (R1) Monagas, M. et al. (2010); “Insights into the metabolism and microbial biotransformation of dietary flavan-3-ols and the bioactivity of their metabolites,” Food and Function, vol. 1, no. 3, pp. 233–253.
			 * Dehydroxylation of the catechol group after ring fission occurs preferably at the C4'-site (R1, Fig.3).
			 */
			
			btPriorityHash.put(ReactionName.CATECHOL_DEHYDROXYLATION_PATTERN2, new ReactionName[] 
					{ReactionName.CATECHOL_DEHYDROXYLATION_PATTERN1
							});
			/**
			 * (R1) Burapan, S. et al. (2017); Demethylation of Polymethoxyflavones by Human Gut Bacterium, Blautia sp. MRG-PMF1; J. Agric. Food Chem., 2017, 65 (8), pp 1620–1629; DOI: 10.1021/acs.jafc.7b00408
			 * (R2) Kim, M. et al. (2014); Metabolism of Kaempferia parviflora Polymethoxyflavones by an Intestinal Bacterium Bautia sp. MRG-PMF1; J. Agric. Food Chem. 2014, 62, 12377−12383; dx.doi.org/10.1021/jf504074n
			 * (R3) Yang, Y. and Ho, CT. (2009); from Yoshikawa T (ed): Food Factors for Health Promotion.; Forum Nutr. Basel, Karger, 2009, vol 61, pp 64–74     https://www.karger.com/Article/PDF/212739
			 * 
			 * Chemical cleavage of the methyl aryl ether bond requires very rigorous reaction conditions. The time-course monitoring of PMF biotransformations elucidated bioconversion pathways, including the 
			 * identification of metabolic intermediates. As a robust flavonoid demethylase, regioselectivity of PMF demethylation generally followed the order C-7 > C-4′ ≈ C-3′ > C-5 > C-3 (R1).
			 * 
			 * The isolated MRGPMF1 was able to metabolize various PMFs to the corresponding demethylated flavonesFrom a kinetics study, the methoxy group on the flavone C-7 position was found to be 
			 * preferentially hydrolyzed (R2).
			 * 
			 * In fact, P450-related metabolism of flavonoids especially demethylation is mainly focused on the subclass of PMFs. Cytochrome P450 (CYP) is the key enzyme system involved in the metabolism of PMFs 
			 * and is capable of catalyzing hydroxylation and demethylation reactions. The metabolic pathway of PMFs is con- sidered to be identical across the species. The 3  and 4  positions on the B ring of PMFs 
			 * are the primary site of biotransformation. The number and position of the hydroxyl and methoxy groups on the B ring of PMFs have a great influence on the metabolism of PMFs (R3).
			 * 
			 * 
			 * Because the substrates are polymethoxyflavones or metabolites thereof, the following constraints must be met:
			 * 	1) there must me at least one methoxy group, and one hydroxyl group (obtained from a previous O-demethylation) or,
			 *  2) two or more O-methyl groups.
			 */
			
			btPriorityHash.put(ReactionName._7_O_DEMETHYLATION_OF_FLAVONE, new ReactionName[] 
					{ReactionName._4P_O_DEMETHYLATION_OF_FLAVONE, ReactionName._3P_O_DEMETHYLATION_OF_FLAVONE,
							ReactionName._5_O_DEMETHYLATION_OF_FLAVONE, ReactionName._3_O_DEMETHYLATION_OF_FLAVONE
							});			

			btPriorityHash.put(ReactionName._4P_O_DEMETHYLATION_OF_FLAVONE, new ReactionName[] 
					{ReactionName._5_O_DEMETHYLATION_OF_FLAVONE, ReactionName._3_O_DEMETHYLATION_OF_FLAVONE
							});

			btPriorityHash.put(ReactionName._3P_O_DEMETHYLATION_OF_FLAVONE, new ReactionName[] 
					{ReactionName._5_O_DEMETHYLATION_OF_FLAVONE, ReactionName._3_O_DEMETHYLATION_OF_FLAVONE
							});			

			btPriorityHash.put(ReactionName._5_O_DEMETHYLATION_OF_FLAVONE, new ReactionName[] 
					{ReactionName._3_O_DEMETHYLATION_OF_FLAVONE
							});	
			
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN1, new ReactionName[] 
			{	
					ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
				
					});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN2, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
					});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN3, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,	
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
					});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN4, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
					});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN5, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
					});
						
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN6, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
					});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN7, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN8, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN9, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN10, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN11, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_GLYCOSYLATED_CONDENSED_GALLOYL_GROUPS_PATTERN12, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});			
			btPriorityHash.put(ReactionName.LACTONIZATION_OF_HEXAHYDROXYDIPHENOLIC_ACID, new ReactionName[] 
			{	ReactionName.ALDEHYDE_OXIDATION,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN1,
//				ReactionName.DEHYDROXYLATION_OF_FUSED_BENZENE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
				ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
//				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
//				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN1,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN2,
				ReactionName.LACTONE_HYDROLYSIS_PATTERN3,
				ReactionName.ALPHA_OXIDATION_OF_CARBXOYLIC_ACID
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1, new ReactionName[] 
			{			
				ReactionName.LACTONIZATION_OF_HEXAHYDROXYDIPHENOLIC_ACID,
				ReactionName.BENZOIC_ACID_DECARBOXYLATION,
			});
			
			btPriorityHash.put(ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2, new ReactionName[] 
			{			
				ReactionName.LACTONIZATION_OF_HEXAHYDROXYDIPHENOLIC_ACID,
				ReactionName.BENZOIC_ACID_DECARBOXYLATION,
			});			
			// Deglycosylation must happen before everything
			
			
			// Goel, G. et al. (2005); Interaction of gut microflora with tannins in feeds; Naturwissenschaften (2005) 92: 497–503; DOI 10.1007/s00114-005-0040-7
			btPriorityHash.put(ReactionName.GALLOYL_ESTER_HYDROLYSIS, new ReactionName[] 
			{			
				ReactionName.LACTONIZATION_OF_HEXAHYDROXYDIPHENOLIC_ACID,
				ReactionName.BENZOIC_ACID_DECARBOXYLATION,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
			});
			
			btPriorityHash.put(ReactionName.DEPSIDE_HYDROLYSIS, new ReactionName[] 
			{			
				ReactionName.LACTONIZATION_OF_HEXAHYDROXYDIPHENOLIC_ACID,
				ReactionName.BENZOIC_ACID_DECARBOXYLATION,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN1,
				ReactionName.HYDROLYSIS_OF_CARBOXYLIC_ACID_ESTER_PATTERN2,
			});	
			
			
			// This is especially for degradation products of ellagic acids, as the diphenic acids are usually
			// decarboxylated to urolithins first, which are then dehydroxylated.
			btPriorityHash.put(ReactionName.DECARBOXYLATION_OF_FUSED_BENZENE, new ReactionName[] 
					{
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN1,
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN2,
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN3,
							ReactionName.DEHYDROXYLATION_OF_DIBENZOPYRANONE_PATTERN4,
					});
			
			
				
			/**
			 * (R1) Mena, P. Bioactivation of High-Molecular-Weight Polyphenols by the Gut Microbiome; 
			 * DIET-MICROBE INTERACTIONS IN THE GUT; Chapter 6; DOI: http://dx.doi.org/10.1016/B978-0-12-407825-3.00006-X
			 * 
			 * "human microbiota have a preference to dehydroxylate the 4'-OH position (para). This phenomenon might be 
			 * postulated considering the trace amount of 4'-hydroxyphenylpropionic acid compared to its isomer 
			 * 3'-hydroxyphenylpropionic. Moreover, the favorite dehydroxylation in the para-position by human colonic 
			 * microbiota has been revealed previously for several flavonoids" (R1).
			 */		
		}

	}
	
	
	private static LinkedHashMap<ReactionName, ReactionName[]> unfoldReactionPriorityMap(LinkedHashMap<ReactionName, ReactionName[]> rpmap){
		LinkedHashMap<ReactionName, ReactionName[]> unfolded = new LinkedHashMap<ReactionName, ReactionName[]>();
		
		// Following the example on this page: https://github.com/jgrapht/jgrapht/wiki/DirectedGraphDemo
		SimpleDirectedGraph sdg = new SimpleDirectedGraph();
		
		for(ReactionName rn : rpmap.keySet()){
			ReactionName[] rn_children = rpmap.get(rn);
			if(!sdg.containsVertex(rn)){
				sdg.addVertex(rn);
			}
			
			for(int i = 0; i < rn_children.length; i++){
				if(!sdg.containsVertex(rn_children[i])){
					sdg.addVertex(rn_children[i]);
				}
				sdg.addEdge(rn, rn_children[i]);
			}			
		}		
		System.out.println(sdg.edgeSet().size() + " edges from " + sdg.vertexSet().size() + " vertices.");
	
		ConnectivityInspector ci = new ConnectivityInspector(sdg);
	
		for( ReactionName rn : rpmap.keySet() ){
			Set<ReactionName> des =  new  HashSet<ReactionName>();
			for(ReactionName rn2: rpmap.keySet()){
				if(rn != rn2 && ci.pathExists(rn, rn2)){
					des.add(rn2);
				}
				unfolded.put(rn, des.toArray(new ReactionName[des.size()]));				
			}
		}

		return unfolded;
	}
	

	
	public LinkedHashMap<ReactionName, MetabolicReaction> filterReactions(ArrayList<MetabolicReaction> reactions){
		return filterReactions(reactions, this.btPriorityHash);
	}

	public static LinkedHashMap<ReactionName, MetabolicReaction> filterReactions(ArrayList<MetabolicReaction> reactions, LinkedHashMap<ReactionName, ReactionName[]> btph ){

		LinkedHashMap<ReactionName, MetabolicReaction> reactionsHash = new LinkedHashMap<ReactionName, MetabolicReaction>();
		LinkedHashMap<ReactionName, MetabolicReaction> filteredReactions = new LinkedHashMap<ReactionName, MetabolicReaction>();
		ArrayList<ReactionName> rNames = new ArrayList<ReactionName>();
		
		for(MetabolicReaction i : reactions){
			reactionsHash.put(ReactionName.valueOf(i.name), i);
			rNames.add(ReactionName.valueOf(i.name));
		}
		
		
		ArrayList<ReactionName> rNamesWithUMStrictReasoning = new ArrayList<ReactionName>();
		if(rNames.contains(ReactionName.EAWAG_RULE_BT0003)){
//			System.err.println("YES STRICT");
			rNamesWithUMStrictReasoning.add(ReactionName.EAWAG_RULE_BT0003);
		}
		if(rNames.contains(ReactionName.EAWAG_RULE_BT0255_PATTERN1)){
			rNamesWithUMStrictReasoning.add(ReactionName.EAWAG_RULE_BT0255_PATTERN1);
		}
		if(rNames.contains(ReactionName.EAWAG_RULE_BT0255_PATTERN2)){
			rNamesWithUMStrictReasoning.add(ReactionName.EAWAG_RULE_BT0255_PATTERN2);
		}		
		if(rNamesWithUMStrictReasoning.size()>0){
			LinkedHashMap<ReactionName, MetabolicReaction> filteredReactionsStrict = new LinkedHashMap<ReactionName, MetabolicReaction>();
			for(ReactionName rn : rNamesWithUMStrictReasoning){
				filteredReactionsStrict.put(rn, reactionsHash.get(rn));
			}
			
			return filteredReactionsStrict;
		} else {
			
			ArrayList<ReactionName> parentNodes = new ArrayList<ReactionName>();
			ArrayList<ReactionName> childNodes = new ArrayList<ReactionName>();
			ArrayList<ReactionName> nodesWithNoParentOrChild = new ArrayList<ReactionName>();

			for(ReactionName rN : rNames){
				for(ReactionName rN2 : rNames){
					if(rN2 != rN){
						if(ArrayUtils.contains(btph.get(rN), rN2)){
							parentNodes.add(rN);
						} else if (ArrayUtils.contains(btph.get(rN2), rN))	{
							childNodes.add(rN);
						}
					}
				}
				if(!(parentNodes.contains(rN) || childNodes.contains(rN))){
					nodesWithNoParentOrChild.add(rN);
				}
			}
			
			
			ArrayList<ReactionName> rNames_reduced  = (ArrayList<ReactionName>) rNames.clone();
			
			// This will avoid remove nodes Xi that are children of Yi, but not children of any other reaction.
			for(ReactionName r: rNames){
				if(parentNodes.contains(r) && childNodes.contains(r)){
					rNames_reduced.remove(r);
				}
			}
					
			ArrayList<ReactionName> toRemove = new ArrayList<ReactionName>();
			for(ReactionName rN : rNames){
				for(ReactionName rN2 : rNames){
					if(rN2 != rN){
						if(ArrayUtils.contains(btph.get(rN), rN2)){
							toRemove.add(rN2);
						}
					}
				}
			}
	
			rNames_reduced.removeAll(toRemove);
			
			for( ReactionName rs : rNames_reduced){
				filteredReactions.put(rs, reactionsHash.get(rs));
			}
			
		}
		
		
		return filteredReactions;
	}

	public LinkedHashMap<ReactionName, MetabolicReaction> filterReactions(LinkedHashMap<ReactionName, MetabolicReaction> reactions){
		return filterReactions(reactions, unfoldReactionPriorityMap(this.btPriorityHash));
	}
	
	public static LinkedHashMap<ReactionName, MetabolicReaction> filterReactions(LinkedHashMap<ReactionName, MetabolicReaction> reactions, LinkedHashMap<ReactionName, ReactionName[]> btph){
		LinkedHashMap<ReactionName, MetabolicReaction> filteredReactions = new LinkedHashMap<ReactionName, MetabolicReaction>();

		ArrayList<ReactionName> rNames = (ArrayList<ReactionName>) reactions.keySet();
		List<ReactionName> rNamesFiltered = new ArrayList<ReactionName>(reactions.keySet());

		int count = reactions.size();
		int index = 0;
		
		while(index< reactions.size() && !rNames.isEmpty()){	
			if(btph.containsKey( rNames.get(index))){
			List<ReactionName> current = Arrays.asList(btph.get( rNames.get(index)));
			for( int k = 0; k < current.size(); k++){				
				if(btph.containsKey(current.get(k))){
					for(ReactionName r : btph.get(current.get(k))){
						filteredReactions.remove(r);
						count--;
					}
				}
				
				rNamesFiltered.remove(current.get(k));
					
					count--;
				}
			}
			index++;
		}

		for (int z = 0; z < rNamesFiltered.size(); z++){
			filteredReactions.put(rNames.get(z), reactions.get(rNames.get(z)));
		}
//		System.err.println("Retaining " + filteredReactions.size() + " reactions, with filter set to true");
		return filteredReactions;
		
	}

	public LinkedHashMap<ReactionName, MetabolicReaction> filterReactionsLSM(LinkedHashMap<String, MetabolicReaction> reactions){
		LinkedHashMap<ReactionName, MetabolicReaction> filteredReactions = new LinkedHashMap<ReactionName, MetabolicReaction>();
		
		List<String> rNames = new ArrayList<String>(reactions.keySet());
		List<String> rNamesFiltered = new ArrayList<String>(reactions.keySet());

		int count = reactions.size();
		int index = 0;
		
		while(index< reactions.size() && !rNames.isEmpty()){
			System.err.println("rNamesFiltered: " + rNamesFiltered.size());
			System.err.println("Index: " + rNames.get(index));
			if(this.btPriorityHash.containsKey(ReactionName.valueOf(rNames.get(index)))){
//				System.err.println(rNames.get(index) + " dominates over reactions in btPriorityHash");
				List<ReactionName> current = 
					Arrays.asList(this.btPriorityHash.get(ReactionName.valueOf(rNames.get(index))));
				for( int k = 0; k < current.size(); k++){
//					System.err.println("REMOVING REACTION: " + current.get(k));
					rNamesFiltered.remove(current.get(k).toString());
					count--;
				}		
			}		
			index++;
		}

		for (int z = 0; z < rNamesFiltered.size(); z++){
			filteredReactions.put(ReactionName.valueOf(rNames.get(z)), reactions.get(rNames.get(z)));
		}
		return filteredReactions;	
	}

	
	public MReactionsFilter() {
		// TODO Auto-generated constructor stub
	}

}

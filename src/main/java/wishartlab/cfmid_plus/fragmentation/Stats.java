package wishartlab.cfmid_plus.fragmentation;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import wishartlab.cfmid_plus.fragmentation.FPLists;
import wishartlab.cfmid_plus.fragmentation.MSPRelativeAbundanceList;
import wishartlab.cfmid_plus.fragmentation.MSPeakRelativeAbundance;
import wishartlab.cfmid_plus.fragmentation.StructuralClass.ClassName;

public class Stats {
	public static int numberOfPeaksForClass(ClassName cName){
		int peaks = 0;
		
		LinkedHashMap<String,MSPeakRelativeAbundance> abundances = MSPRelativeAbundanceList.classSpecificRelativeAbundances.get(cName);
		if(abundances != null){
			for(String s: abundances.keySet()){
				LinkedHashMap<String, Double> relativeAbundances = abundances.get(s).relativeAbundances;
				System.out.println(s);
				System.out.println(relativeAbundances.size());
				peaks = peaks + relativeAbundances.size();	
			}			
		}
		
		System.out.println(peaks);
		return peaks;
	}

	public static int totalNumberOfPeaks(){
		int totalPeaks = 0;
		
		for(ClassName c : MSPRelativeAbundanceList.classSpecificRelativeAbundances.keySet()){
			totalPeaks = totalPeaks + numberOfPeaksForClass(c);
		}
		
		
		return totalPeaks;
	}
	
	public static Set<String> listAllCoveredAdducts(){
		Set<String> adductSet = new HashSet<String>();
		
		for(ClassName cn : FPLists.classSpecificFragmentationPatterns.keySet()){
			for(String ad : FPLists.classSpecificFragmentationPatterns.get(cn).keySet()){
				adductSet.add(ad);
			}
		}
		System.out.println(adductSet);
		return adductSet;
	}

	public static void main(String[]args){
		numberOfPeaksForClass(ClassName.CERAMIDE_1_PHOSPHATES);
		System.out.println(totalNumberOfPeaks());
		listAllCoveredAdducts();
		
	}
	
	
}

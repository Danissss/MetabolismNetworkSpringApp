package wishartlab.cfmid_plus.test;

import static org.junit.Assert.assertArrayEquals;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import wishartlab.cfmid_plus.fragmentation.FragmentationCondition;
import wishartlab.cfmid_plus.fragmentation.Fragmenter;
import wishartlab.cfmid_plus.fragmentation.StructuralClass;
import wishartlab.cfmid_plus.fragmentation.StructureExplorer;

public class TestCFMID {
	
	@Test
	public void testSingleAdduct() throws Exception {
		Fragmenter fr   = new Fragmenter();
		StructureExplorer sExplorer = new StructureExplorer();
		SmilesParser sParser = new SmilesParser(SilentChemObjectBuilder.getInstance());
		String adductType 	= "[M+H]+;";
		ArrayList<String> adduct_list = new ArrayList<String>(Arrays.asList(adductType.split(";")));
		String smiles   = "CCCCCCCCCCCCCCCC(=O)OC[C@H](COP([O-])(=O)OCC[N+](C)(C)C)OC(=O)CCCCCCCC=CCCCCCCCC";
		String file_name = "sample";
		IAtomContainer molecule = sParser.parseSmiles(smiles.replace("[O-]", "O"));
		
		int status = fr.validateTheInputForCFMID(molecule.clone(), adductType);
		if(status == 1) {
			LinkedHashMap<Integer, LinkedHashMap<String, ArrayList<String>>> peakList = fr.saveSingleCfmidLikeMSPeakList(molecule, file_name, adduct_list);
			int level = 0;
			int fragIndex = 0;
			
			for(Map.Entry<Integer, LinkedHashMap<String, ArrayList<String>>> peaks : peakList.entrySet()){
				System.out.println("energy" + level);

				level++;
				for(String s : peaks.getValue().get("peaks_list")){
					System.out.println(s);
				}
			}
			for( String fg : peakList.get(10).get("fragments")){
				System.out.println( fragIndex + " " + fg);
				fragIndex++;
			}
		}
		
	}
}

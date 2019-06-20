package Cheminformatics.Utilities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.openscience.cdk.ChemFile;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.ReaderFactory;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

public class ReadMolecule {
	
	
	/**
	 * Try to read the raw molecule file
	 * @param input
	 * @return
	 * @throws IOException
	 * @throws CDKException
	 */
	public static IAtomContainer GetMoleculeFromMolBlock(String input) throws IOException, CDKException {
		List<IAtomContainer> list;
		InputStream is = new ByteArrayInputStream(input.getBytes("UTF-8"));
		// System.out.println(is);
		ISimpleChemObjectReader reader = new ReaderFactory().createReader(new InputStreamReader(is));
		if (reader == null)
			throw new IllegalArgumentException("Could not determine input file type");
		IChemFile content = (IChemFile) reader.read((IChemObject) new ChemFile());
		list = ChemFileManipulator.getAllAtomContainers(content);
		reader.close();
		// System.out.println(list.size());
		if(list.size() == 1) {
			return list.get(0);
		}else if(list.size() < 1) {
			return null;
		}else {
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param mol
	 * @return
	 */
	public static String ConvertIAtomContainerToSmiles(IAtomContainer mol) {
		
		SmilesGenerator sg  = new SmilesGenerator(SmiFlavor.Generic);
		try {
			String smiles = sg.create(mol);
			return smiles;
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
}

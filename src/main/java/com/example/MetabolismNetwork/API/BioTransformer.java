package com.example.MetabolismNetwork.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wishartlab.biotransformer.biosystems.BioSystem.BioSystemName;
import wishartlab.biotransformer.btransformers.ECBasedBTransformer;
import wishartlab.biotransformer.transformation.Biotransformation;
import wishartlab.biotransformer.utils.ChemStructureExplorer;
import wishartlab.biotransformer.utils.ChemStructureManipulator;


@RestController
@RequestMapping("/api/")
public class BioTransformer {

	private SmilesParser sParser = new SmilesParser(SilentChemObjectBuilder.getInstance());
	
	@PostMapping(path = "/biotransformer/ecbased")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<String, Object> runECBasedTransformation(@RequestParam("structure") String structure) {
		
		Map<String, Object> json = new LinkedHashMap<String, Object>();
		IAtomContainer target;
		try {
			target = sParser.parseSmiles(structure);
			ECBasedBTransformer ecb	= new ECBasedBTransformer(BioSystemName.HUMAN);
			IAtomContainer molecule = ChemStructureManipulator.standardizeMoleculeWithCopy(target);
			IAtomContainerSet products = DefaultChemObjectBuilder.getInstance().newInstance(IAtomContainerSet.class);
			products.addAtomContainer(molecule);
			ArrayList<Biotransformation> biotransformations = new ArrayList<Biotransformation>();
			 
			biotransformations.addAll(ecb.simulateECBasedPhaseIMetabolismChain(molecule, true, true, 1, 0.0));
			products.add(ecb.extractProductsFromBiotransformations(biotransformations));
			products = ChemStructureExplorer.uniquefy(products);
			
			for(int i = 0; i < products.getAtomContainerCount(); i++) {
				System.out.println(products.getAtomContainer(i));
			}
			
		} catch (InvalidSmilesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
		
		
		
	}
}

package com.example.MetabolismNetwork.Helper;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.depict.DepictionGenerator;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.modeling.builder3d.ModelBuilder3D;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.HighlightGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

import Xuan.SiteOfMetabolism.SomPrediction;
import weka.core.Instances;

public class SiteOfMetabolismHelper {
	
	
	
	
	private String current_dir = System.getProperty("user.dir");
	/**
	 * get the hashmap of which site is likely to have reaction; and present in the picture on the web
	 * @param mol
	 * @param model_type CYP1A2,CYP2A1,etc...
	 * @throws CDKException
	 * @throws IOException
	 * @throws CloneNotSupportedException
	 */
	public HashMap<Integer,String> RunSomPrediction(IAtomContainer mol, String model_type) throws CDKException, IOException, CloneNotSupportedException {
		
		SomPrediction sompred = new SomPrediction();
		Instances testInstance = sompred.create_test_instance(mol);
		try {
			
			
			HashMap<Integer,String> som_result = sompred.runSomClassifier(testInstance,model_type);
			return som_result;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	/**
	 * Draw the image with information of SoM, and save them into the destinated folder
	 * if successuful draw, return true
	 * current save image as svg
	 * @param mol
	 */
	public boolean SaveImageToFolder(IAtomContainer mol, List<IAtom> atomList, String file_format) {
		
		DepictionGenerator dptgen = new DepictionGenerator();
		dptgen.withSize(400, 400).withMolTitle().withTitleColor(Color.DARK_GRAY);
		dptgen.withHighlight(atomList, Color.RED);
		
		try {
			dptgen.depict(mol).writeTo(String.format("%s/CompoundImages/%s.%s", current_dir,"image",file_format));
			File test_file_existing = new File(String.format("%s/CompoundImages/%s.%s", current_dir,"image",file_format));
			if(test_file_existing.exists()) {
				
				return true;
			}else {
				return false;
			}
			
		} catch (IOException | CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return false;
		
		
		
	}
	
	/**
	 * 
	 * @param molecule
	 * @param atoms
	 * @return
	 */
	public String drawMolecule(IAtomContainer molecule, ArrayList<IAtom> atoms) {
		
        // layout the molecule
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        sdg.setMolecule(molecule, false);
        try {
			sdg.generateCoordinates();
		} catch (CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // make generators
        List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new HighlightGenerator());
        generators.add(new BasicAtomGenerator());
        
        
        Map<IChemObject, Integer> ids = new HashMap<>();
        // ids.put(molecule.getAtom(0), 0); // 0 is atom class 
		// ids.put(molecule.getAtom(1), 0); // 0 is atom class 
		// ids.put(molecule.getAtom(2), 0); // 0 is atom class 
		// ids.put(molecule.getAtom(5), 2); // 2 is atom class 
		// ids.put(molecule.getAtom(6), 1); // 1 is atom class 
		
		for(int i = 0 ; i < atoms.size(); i++) {
			ids.put(atoms.get(i), 0);
		}
		// ids.put(molecule.getAtom(1), 0);
		molecule.setProperty(HighlightGenerator.ID_MAP, ids);
		 
        
        // setup the renderer
        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());
        RendererModel model = renderer.getRenderer2DModel(); 
        model.set(BasicAtomGenerator.CompactAtom.class, true);
        model.set(BasicAtomGenerator.AtomRadius.class, 3.0);
        model.set(BasicAtomGenerator.CompactShape.class, BasicAtomGenerator.Shape.OVAL);
        model.set(BasicAtomGenerator.KekuleStructure.class, true);
        model.set(BasicBondGenerator.BondWidth.class, 1.0);
        model.set(HighlightGenerator.HighlightPalette.class, HighlightGenerator.createPalette(Color.RED, Color.RED, Color.RED));
        
        
        // get the image
        Image image = new BufferedImage(400, 400, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D)image.getGraphics();
        g.setColor(Color.WHITE);
        g.fill(new Rectangle2D.Double(0, 0, 400, 400));
        
        
        renderer.paint(molecule, new AWTDrawVisitor(g), new Rectangle2D.Double(0, 0, 400, 400), true);
        g.dispose();
        
        // write to file
        // write bash script to clean the folder /public/compoundImages once a day for free the space
        try {
        	String random_str = getSaltString(10);
        	String image_path = String.format("%s/CompoundImages/%s.%s", current_dir,random_str,"png");
            File file = new File(image_path);
            // System.out.println("here");
            
			ImageIO.write((RenderedImage)image, "PNG", file);
			String render_path = String.format("/CompoundImages/%s.%s", random_str,"png");
			return render_path;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
        
    }
	
	
	/**
	 * 
	 * @param random_number
	 * @return
	 */
	protected String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	
	
	
	
	/**
	 * Help to generate 3D structure based on RDKit confirmation algorithm
	 * @param option
	 * @param input
	 * @return
	 */
	public String Get3DConfirmation(String option, String input){

		String output_sdf_path = null;
		String s = null;
		Boolean error = null;
		try {
			String directoryName = String.format("%s/Script/ConvertTo3DMolSingle", current_dir);
			ProcessBuilder pb = new ProcessBuilder(directoryName, option, input);
//			pb.directory(new File(directoryName));
			Process p = pb.start();
			BufferedReader stdInput = new BufferedReader(new 
					InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new 
					InputStreamReader(p.getErrorStream()));
			
			if (error = stdError.readLine() != null) {
				if(error == true) {
					return null;
					}
				}
			
			while ((s = stdInput.readLine()) != null) {
				if(!s.equals("Error Occur")) {
					output_sdf_path = s;
				}else {
					output_sdf_path = null;
				}
			}
			
			return output_sdf_path;
			
		}catch (IOException e) {
			System.out.println(e);
		}
		
		
		return output_sdf_path;
	}
	
	/**
	 * 
	 * @param mol
	 * @return
	 */
	public IAtomContainer Get3DConfirmationCDK(IAtomContainer mol) {
		
		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(mol);
		try {
			sdg.generateCoordinates();
			IAtomContainer mole = sdg.getMolecule();
			ModelBuilder3D mb3d = ModelBuilder3D.getInstance(builder);
			IAtomContainer molecule_3D = mb3d.generate3DCoordinates(mole, false);
			return molecule_3D;
		} catch (CDKException e) {
			e.printStackTrace();
			return null;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	
	
	
	
	
}

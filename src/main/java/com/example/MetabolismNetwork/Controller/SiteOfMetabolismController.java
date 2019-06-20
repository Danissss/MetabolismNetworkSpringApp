package com.example.MetabolismNetwork.Controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.validation.Valid;


import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.geometry.GeometryUtil;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.MetabolismNetwork.AjaxBody.SiteOfMetabolismAjaxResponseBody;
import com.example.MetabolismNetwork.Helper.SiteOfMetabolismHelper;
import com.example.MetabolismNetwork.SubmitCriteria.SoMSubmitCriteria;

import Cheminformatics.Utilities.ReadMolecule;
import Xuan.SiteOfMetabolism.SomPrediction;


@Controller
public class SiteOfMetabolismController {

	
	private String current_dir = System.getProperty("user.dir");
	
	
	@GetMapping("/phase1som")
	public String Phase1somQuery(Model model) {
		return "phaseISoM/phase1somQuery";
	}
	
	
	/**
	 * In this case, ResponseEntity should return the status of the query
	 * @param submit
	 * @param errors
	 * @return
	 */
	@PostMapping("/phase1som")
	public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody SoMSubmitCriteria submit, Errors errors) {
		
		SiteOfMetabolismHelper helper = new SiteOfMetabolismHelper();
		
		SiteOfMetabolismAjaxResponseBody result = new SiteOfMetabolismAjaxResponseBody();
		
		if(errors.hasErrors()) {
			
			
			result.setErrorMsg(errors.getAllErrors()
					.stream().map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(",")));
			
			
			return ResponseEntity.badRequest().body(result);
		}
		else {
			
			
			// get smiles directly
			if(!submit.smiles.isEmpty()) {
				// System.out.println(submit.smiles);
				IChemObjectBuilder bldr   = SilentChemObjectBuilder.getInstance();
				SmilesParser smipar = new SmilesParser(bldr);
				
				try {
					IAtomContainer mol = smipar.parseSmiles(submit.smiles);
					// TODO: need to run confirmation creation from rdkit
					
					if(submit.smiles.contains(".")) {
						// unconnected smiles is hard to generate 3D structure
						result.setErrorMsg("The compound is not connected!");
						
					}else {
						
						String new_file_path = helper.Get3DConfirmation("-s", submit.smiles);
						if(new_file_path != null) {
							System.out.println("RDK confirmation");
							File sdfFile = new File(String.format("%s/%s", current_dir, new_file_path));					
							mol = SomPrediction.read_SDF_file(String.format("%s/%s", current_dir,new_file_path));
							if(sdfFile.exists()) {
								sdfFile.delete();
							}
						}else {
							System.out.println("CDK confirmation");
							mol = helper.Get3DConfirmationCDK(mol);
							if(mol == null) {
								result.setErrorMsg("Failed to generate 3D structure for molecule");
								result.setFail(true);
								return ResponseEntity.ok(result);
							}
						}
						
						try {
							
							HashMap<Integer,String> SoMresult = helper.RunSomPrediction(mol,submit.protein);
							ArrayList<IAtom> atomList = new ArrayList<IAtom>();
							for(Integer key : SoMresult.keySet()) {
								if(SoMresult.get(key).equals("Yes")) {
									// the key is the site index; yes means it is the som
									// withHighlight(Iterable<? extends IChemObject> chemObjs, Color color)
									IAtom atom = mol.getAtom(key);
									atomList.add(atom);
									
								}
							}
							
							String image_path = helper.drawMolecule(mol,atomList);
							if (image_path != null) {
								result.setSuccess(true);
								result.setStatus("Success");
								result.setImage_path(image_path);
								// System.out.println("Imageloaded");
							}
							
							
						} catch (CDKException | IOException | CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							result.setErrorMsg(e.toString());
							result.setFail(true);
						}
					}
					
				}
				catch(InvalidSmilesException e) {
					result.setErrorMsg("InvalidSmilesException");
					// result.setStatus("Fail");
					result.setFail(true);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CDKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				
			}
			
			// check if chemdraw exist
			else if(!submit.chemdraw.isEmpty()) {
				try {
					IAtomContainer mol = ReadMolecule.GetMoleculeFromMolBlock(submit.chemdraw);
					if(mol != null) {
						String smiles = ReadMolecule.ConvertIAtomContainerToSmiles(mol);
						// TODO: need to run confirmation creation from rdkit 
						System.out.println(smiles);
						if(smiles.contains(".")) {
							result.setErrorMsg("The compound is not connected!");
							
						}else {
							
							
							String new_file_path = helper.Get3DConfirmation("-s", smiles);
							if(new_file_path != null) {
								File sdfFile = new File(String.format("%s/%s", current_dir, new_file_path));					
								mol = SomPrediction.read_SDF_file(String.format("%s/%s", current_dir,new_file_path));
								if(sdfFile.exists()) {
									sdfFile.delete();
								}
							}else {
								mol = helper.Get3DConfirmationCDK(mol);
								if(mol == null) {
									result.setErrorMsg("Failed to generate 3D structure for molecule");
									result.setFail(true);
									return ResponseEntity.ok(result);
								}
							}
							
							try {
								
								HashMap<Integer,String> SoMresult = helper.RunSomPrediction(mol,submit.protein);
								ArrayList<IAtom> atomList = new ArrayList<IAtom>();
								for(Integer key : SoMresult.keySet()) {
									if(SoMresult.get(key).equals("Yes")) {
										// the key is the site index; yes means it is the som
										// withHighlight(Iterable<? extends IChemObject> chemObjs, Color color)
										IAtom atom = mol.getAtom(key);
										atomList.add(atom);
										
									}
								}
								
								String image_path = helper.drawMolecule(mol,atomList);
								if (image_path != null) {
									result.setSuccess(true);
									result.setStatus("Success");
									result.setImage_path(image_path);
									// System.out.println("Imageloaded");
								}
							}
							catch(InvalidSmilesException e) {
								result.setErrorMsg("InvalidSmilesException");
							}
						}
					}
					else {
						result.setErrorMsg("The compound is not appropriate!");
					}
					
				} catch (IOException | CDKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setErrorMsg(e.toString());
					// result.setStatus("Fail");
					result.setFail(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setErrorMsg(e.toString());
					result.setFail(true);
				}
				
			}
			
			
			return ResponseEntity.ok(result);
			
			
		}
	}
	
	
	/**
	 * do prediction based on sdf file
	 * @param submit
	 * @param errors
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/phase1somwithfile")
	@ResponseBody
	public ResponseEntity<?> getSearchResultViaAjaxFile(
			@RequestParam("file") MultipartFile multipartFile, @RequestParam("protein") String protein) {
		
		
		
		SiteOfMetabolismHelper helper = new SiteOfMetabolismHelper();
		SiteOfMetabolismAjaxResponseBody result = new SiteOfMetabolismAjaxResponseBody();
		
		if(multipartFile!=null) {
			// System.out.println("get file step");
			// System.out.println(protein);
			
			if(!multipartFile.getOriginalFilename().isEmpty()) {
				try {
					BufferedOutputStream outputStream = new BufferedOutputStream(
									new FileOutputStream(
											new File(String.format("%s/uploadedFile/%s",current_dir,multipartFile.getOriginalFilename()))));
					outputStream.write(multipartFile.getBytes());
					outputStream.flush();
					outputStream.close();
					
					String sdf_file_path = String.format("%s/uploadedFile/%s", current_dir,multipartFile.getOriginalFilename());
					File sdfFile = new File(sdf_file_path);					
					IAtomContainer mol = SomPrediction.read_SDF_file(sdf_file_path);
					AtomContainerManipulator.convertImplicitToExplicitHydrogens(mol);
					
					
					// check if the input file has 3D coordinate					
					// if failed to generate 3D structure, means that the task is failed
					if (!GeometryUtil.has3DCoordinates(mol)) {
						System.out.println("No 3D Coordinates");
						String new_file_path = helper.Get3DConfirmation("-f", sdf_file_path);
						if(new_file_path != null) {
							sdfFile = new File(String.format("%s/%s", current_dir, new_file_path));					
							mol = SomPrediction.read_SDF_file(String.format("%s/%s", current_dir,new_file_path));
						}else {
							mol = helper.Get3DConfirmationCDK(mol);
							if(mol == null) {
								result.setErrorMsg("Failed to generate 3D structure for molecule");
								result.setFail(true);
								return ResponseEntity.ok(result);
							}
						}
						
					}	
					
					
					HashMap<Integer,String> SoMresult = helper.RunSomPrediction(mol,protein);
					ArrayList<IAtom> atomList = new ArrayList<IAtom>();
					for(Integer key : SoMresult.keySet()) {
						// System.out.println(SoMresult.get(key));
						if(SoMresult.get(key).equals("Yes")) {
							// the key is the site index; yes means it is the som
							// withHighlight(Iterable<? extends IChemObject> chemObjs, Color color)
							IAtom atom = mol.getAtom(key);
							atomList.add(atom);
							
						}
					}
					//if(SaveImageToFolder(mol,atomList,"svg")) {
						// use https://cdk.github.io/cdk/1.5/docs/api/org/openscience/cdk/renderer/generators/HighlightGenerator.html
						// to highlight the image
						
					//}
					String image_path = helper.drawMolecule(mol,atomList);
					if (image_path != null) {
						result.setSuccess(true);
						result.setStatus("Success");
						result.setImage_path(image_path);
						// System.out.println("Imageloaded");
					}
					
					
					
					if(sdfFile.exists()) {
						sdfFile.delete();
					}
							
				} 
				catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setErrorMsg("CloneNotSupportedException.");
					result.setStatus("Fail");
					result.setFail(true);
					System.out.println("CloneNotSupportedException");
					
					
				}
				catch (FileNotFoundException e) {
							
					e.printStackTrace();
					result.setErrorMsg("FileNotFoundException.");
					result.setStatus("Fail");
					result.setFail(true);
					System.out.println("FileNotFoundException");
				} catch (IOException e) {
					result.setErrorMsg("IOException.");
					result.setStatus("Fail");
					e.printStackTrace();
					result.setFail(true);
					System.out.println("IOException");
				} catch (CDKException e) {
					result.setErrorMsg("CDKException.");
					result.setStatus("Fail");
					e.printStackTrace();
					result.setFail(true);
					System.out.println("CDKException");
				}
				
				
			}
		}
		else {
			result.setErrorMsg("Empty File.");
			result.setFail(true);
		}
		
		return ResponseEntity.ok(result);

		
			
		
	}
	
	
	
	
	

	

	
}

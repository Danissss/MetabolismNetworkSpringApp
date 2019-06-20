package com.example.MetabolismNetwork.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
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

import com.example.MetabolismNetwork.AjaxBody.CypReactAjaxResponseBody;
import com.example.MetabolismNetwork.Model.TransporterModel;
import com.example.MetabolismNetwork.SubmitCriteria.CypReactSubmitCriteria;

import Cheminformatics.Utilities.ReadMolecule;
import Siyang.CypReact.ReactantPred;
import weka.core.Instances;


@Controller
public class CypReactQueryController {
	
	private String current_dir = System.getProperty("user.dir");
	// render html page has to match the name of index
	@GetMapping("/cypreact")
	public String cypreactQuery(Model model) {
		model.addAttribute("CyReactModel", new TransporterModel());
		return "cypreact/CypReactQuery";
	}
	
	@PostMapping("/cypreact")
	public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody CypReactSubmitCriteria submit, Errors errors) throws Exception {

		CypReactAjaxResponseBody result = new CypReactAjaxResponseBody();
		
		if(errors.hasErrors()) {
			
			
			result.setErrorMsg(errors.getAllErrors()
					.stream().map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(",")));
			
			
			return ResponseEntity.badRequest().body(result);
		}
		else {
			
			// System.out.println("here=============================");
			ReactantPred CypReact = new ReactantPred();
			String supportFoldPath = String.format("%s/", System.getProperty("user.dir"));
			
			Instances testinstance = null;
			IAtomContainerSet inputMolecule = null;
			ArrayList<HashMap<String, String>> classifiedResult = new ArrayList<HashMap<String, String>>();
			
			if(!submit.SMILES.isEmpty()) {
				
				testinstance = CypReact.CreateTestInstances(String.format("SMILES=%s", submit.SMILES));
				inputMolecule = CypReact.CreateInputMolecule(String.format("SMILES=%s", submit.SMILES));
				classifiedResult = new ArrayList<HashMap<String, String>>();
				
				
			}else if(!submit.chemdraw.isEmpty()){
				
				try {
					IAtomContainer mol = ReadMolecule.GetMoleculeFromMolBlock(submit.chemdraw);
					if(mol != null) {
						String smiles = ReadMolecule.ConvertIAtomContainerToSmiles(mol);
						// System.out.println(smiles);
						
						if(smiles.contains(".")) {
							result.setErrorMsg("The compound is not connected!");
						}else {
							testinstance = CypReact.CreateTestInstances(String.format("SMILES=%s", smiles));
							inputMolecule = CypReact.CreateInputMolecule(String.format("SMILES=%s", smiles));
							classifiedResult = new ArrayList<HashMap<String, String>>();
						}
					}
					else {
						result.setErrorMsg("The compound is not appropriate!");
					}
					
				} catch (IOException | CDKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setErrorMsg(e.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setErrorMsg(e.toString());
				}
				
			}
			
			
			if (testinstance != null && inputMolecule !=null) {
				ArrayList<String> enzyme = GetSelectedEny(submit);
				// System.out.println("enzyme size : " + enzyme.size());
				
				for (int i = 0; i < enzyme.size(); i++) {
					try {
						// System.out.println(enzyme.get(i));
						ArrayList<HashMap<String,String>> temp_cypreact_result = CypReact.RunClassification(supportFoldPath,testinstance,enzyme.get(i),inputMolecule);
						if(temp_cypreact_result != null && temp_cypreact_result.size() > 0) {
							classifiedResult.add(temp_cypreact_result.get(0));
						}
						

						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						HashMap<String, String> errorEnzyme = new HashMap<String, String>();
						errorEnzyme.put(enzyme.get(i), "Unknown");
						classifiedResult.add(errorEnzyme);
						
						
					}
				}
				
				
				
				System.out.println("classifiedResult.size()=>"+classifiedResult.size());
				HashMap<String,String> aggregated_result = new HashMap<String,String>();
				for(int i = 0; i < classifiedResult.size(); i++) {
					HashMap<String,String> tmpMap = classifiedResult.get(i);
					for(String key :tmpMap.keySet()) {
						if(tmpMap.get(key) != "null") {
							aggregated_result.put(key, tmpMap.get(key));
						}
					}
				}
				
				for(String key : aggregated_result.keySet()) {
					// System.out.println(key+":"+aggregated_result.get(key));
					if(key.equals("1A2")) {
						result.setCYP1A2(aggregated_result.get(key));
					}
					else if(key.equals("1A2")) {
						result.setCYP1A2(aggregated_result.get(key));
					}
					else if(key.equals("2B6")) {
						result.setCYP2B6(aggregated_result.get(key));
					}
					else if(key.equals("2A6")) {
						result.setCYP2A6(aggregated_result.get(key));
					}
					else if(key.equals("2C8")) {
						result.setCYP2C8(aggregated_result.get(key));
					}
					else if(key.equals("2C9")) {
						result.setCYP2C9(aggregated_result.get(key));
					}
					else if(key.equals("2C19")) {
						result.setCYP2C19(aggregated_result.get(key));
					}
					else if(key.equals("2D6")) {
						result.setCYP2D6(aggregated_result.get(key));
					}
					else if(key.equals("2E1")) {
						result.setCYP2E1(aggregated_result.get(key));
					}
					else if(key.equals("3A4")) {
						result.setCYP3A4(aggregated_result.get(key));
					}
				}
				
				
			}
			
			return ResponseEntity.ok(result);
			
			
		}
	}
	
	
	
	
	/**
	 * this is for url /cypreactwithfile request
	 * designed for file uploader
	 * @param multipartFile
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/cypreactwithfile")
	@ResponseBody
	public ResponseEntity<?> getSearchResultViaAjaxFile(@RequestParam("file") MultipartFile multipartFile){
		
		CypReactAjaxResponseBody result = new CypReactAjaxResponseBody();
		ReactantPred CypReact = new ReactantPred();
		String supportFoldPath = String.format("%s/", System.getProperty("user.dir"));
		
		if(multipartFile!=null) {
			System.out.println("get file step");
			if(!multipartFile.getOriginalFilename().isEmpty()) {
						
				String smiles = null;
				SmilesGenerator sg      = new SmilesGenerator(SmiFlavor.Generic);
				try {
					BufferedOutputStream outputStream = new BufferedOutputStream(
									new FileOutputStream(
											new File(String.format("%s/uploadedFile/%s",current_dir,multipartFile.getOriginalFilename()))));
					outputStream.write(multipartFile.getBytes());
					outputStream.flush();
					outputStream.close();
					
					File sdfFile = new File(String.format("%s/uploadedFile/%s", current_dir,multipartFile.getOriginalFilename()));
					IteratingSDFReader reader = new IteratingSDFReader(
						new FileInputStream(sdfFile), DefaultChemObjectBuilder.getInstance());
							
					if(reader.hasNext()){
						IAtomContainer molecule = reader.next();
						smiles = sg.create(molecule);
						
						if (smiles != null) {
							
							Instances testinstance = CypReact.CreateTestInstances(String.format("SMILES=%s", smiles));
							IAtomContainerSet inputMolecule = CypReact.CreateInputMolecule(String.format("SMILES=%s", smiles));
							ArrayList<HashMap<String, String>> classifiedResult = new ArrayList<HashMap<String, String>>();
							
									
							if (testinstance != null && inputMolecule !=null) {
								//TODO: figure out add the submit CypReactSubmitCriteria
								ArrayList<String> enzyme = new ArrayList<String>();
								// System.out.println("enzyme size : " + enzyme.size());
								
								for (int i = 0; i < enzyme.size(); i++) {
									try {

										classifiedResult.addAll(CypReact.RunClassification(supportFoldPath,testinstance,enzyme.get(i),inputMolecule));

										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										HashMap<String, String> errorEnzyme = new HashMap<String, String>();
										errorEnzyme.put(enzyme.get(i), "Unknown");
										classifiedResult.add(errorEnzyme);
										
										
									}
								}
								
								System.out.println("classifiedResult.size()=>"+classifiedResult.size());
								for(int i = 0; i < classifiedResult.size(); i++) {
									HashMap<String,String> tmpMap = classifiedResult.get(i);
									for(String key : tmpMap.keySet()) {
										
										
										System.out.println(key+":"+tmpMap.get(key));
										if(key.equals("1A2")) {
											result.setCYP1A2(tmpMap.get(key));
										}
										else if(key.equals("2B6")) {
											result.setCYP2B6(tmpMap.get(key));
										}
										else if(key.equals("2A6")) {
											result.setCYP2A6(tmpMap.get(key));
										}
										else if(key.equals("2C8")) {
											result.setCYP2C8(tmpMap.get(key));
										}
										else if(key.equals("2C9")) {
											result.setCYP2C9(tmpMap.get(key));
										}
										else if(key.equals("2C19")) {
											result.setCYP2C19(tmpMap.get(key));
										}
										else if(key.equals("2D6")) {
											result.setCYP2D6(tmpMap.get(key));
										}
										else if(key.equals("2E1")) {
											result.setCYP2E1(tmpMap.get(key));
										}
										else if(key.equals("3A4")) {
											result.setCYP3A4(tmpMap.get(key));
										}
										
										
									}
								}
								
								
								
								
								
							}else {
								result.setErrorMsg("Error Occured");
							}
							
							
						}
						else {
							result.setErrorMsg("Couldn't Parse the File.");
						}
								 
					}else {
						result.setErrorMsg("Empty File.");
								 // return empty sdf file warning
					}
					
					if(sdfFile.exists()) {
						sdfFile.delete();
					}
					
					
					
					reader.close();
							
				} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CDKException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}

		}
		else {
			result.setErrorMsg("Empty File.");
		}
		
		return ResponseEntity.ok(result);
	}

	
	
	/**
	 * 
	 * @param submit
	 * @return
	 */
	public ArrayList<String> GetSelectedEny(CypReactSubmitCriteria submit){
		ArrayList<String> output = new ArrayList<String>();
		// System.out.println("submit.CYP2A6: "+submit.CYP2A6.getClass().getName());
		// System.out.println(submit.CYP2C19);
		if(submit.CYP1A2 != null) {
			output.add("CYP1A2");
		}
		if(submit.CYP2B6 != null) {
			output.add("CYP2B6");
		}
		if(submit.CYP2A6 != null) {
			output.add("CYP2A6");
		}
		if(submit.CYP2C8 != null) {
			output.add("CYP2C8");
		}
		if(submit.CYP2C9 != null) {
			output.add("CYP2C9");
		}
		if(submit.CYP2C19 != null) {
			
			output.add("CYP2C19");
		}
		if(submit.CYP2D6 != null) {
			output.add("CYP2D6");
		}
		if(submit.CYP2E1 != null) {
			output.add("CYP2E1");
		}
		if(submit.CYP3A4 != null) {
			output.add("CYP3A4");
		}
		// System.out.println("output: " + output.size());
		return output;
	}
	
	

	


	
}

package com.example.MetabolismNetwork.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
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

import com.example.MetabolismNetwork.AjaxBody.TransporterAjaxResponseBody;
import com.example.MetabolismNetwork.Helper.PrintStatus;
import com.example.MetabolismNetwork.Model.TransporterModel;
import com.example.MetabolismNetwork.SubmitCriteria.TransporterSubmitCriteria;

import Cheminformatics.Utilities.ReadMolecule;
import weka.core.Instances;
import Xuan.DrugExporter.RunClassification;


@Controller
public class TransporterQueryController {
	
	private String current_dir = System.getProperty("user.dir");
			
			
	// render html page has to match the name of index
	@GetMapping("/transporter")
	public String transporterQuery(Model model) {
		model.addAttribute("TransporterModel", new TransporterModel());
		return "transporter/TransporterQuery";
	}
	
	@PostMapping("/transporter")
	public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody TransporterSubmitCriteria submit, Errors errors) {

		
		
		TransporterAjaxResponseBody result = new TransporterAjaxResponseBody();
		
		if(errors.hasErrors()) {
			
			
			result.setErrorMsg(errors.getAllErrors()
					.stream().map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(",")));
			
			PrintStatus.PrintStatusMessage("DrugPorter", "ERROR");
			return ResponseEntity.badRequest().body(result);
		}
		else {
			RunClassification newclassifier = new RunClassification();
			
			// get smiles directly
			if(!submit.smiles.isEmpty()) {
				PrintStatus.PrintStatusMessage("DrugPorter", submit.smiles);
				Instances testinstance = newclassifier.CreateTestingInstance("-s", submit.smiles, submit.role);
				
				try {
					String result_role = newclassifier.ClassifyInstance(submit.role, submit.protein, testinstance);
					result.setPredictedRole(result_role);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setPredictedRole("Unknown (Error Occur)");
				}
				
			}
			else if(!submit.chemdraw.isEmpty()) {
				PrintStatus.PrintStatusMessage("DrugPorter", "ChemDraw");
				try {
					IAtomContainer mol = ReadMolecule.GetMoleculeFromMolBlock(submit.chemdraw);
					
					if(mol != null && mol.getAtomCount() > 0) {
						String smiles = ReadMolecule.ConvertIAtomContainerToSmiles(mol);
						if(smiles.contains(".")) {
							result.setErrorMsg("The compound is not connected!");
						}else {
							
							Instances testinstance = newclassifier.CreateTestingInstance("-s", smiles, submit.role);
							String result_role = newclassifier.ClassifyInstance(submit.role, submit.protein, testinstance);
							result.setPredictedRole(result_role);
						}
					}else if(mol.getAtomCount() == 0) {
						
						result.setPredictedRole("Please provide chemical structure to predict.");
					}
					else {
						result.setPredictedRole("The compound is not appropriate!");
					}
					
				} catch (IOException | CDKException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setPredictedRole(e.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setPredictedRole(e.toString());
				}
				
			}
			
			
			return ResponseEntity.ok(result);
			
			
		}
	}
	
	
//	@RequestMapping(value = "/file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody String myService(@RequestParam("file") MultipartFile file,
//            @RequestParam("id") String id) throws Exception {
//
//if (!file.isEmpty()) { 
//
//       //your logic
//                    }
//return "some json";
//
//            }
//}
//	@PostMapping(value = "/transporterwithfile", consumes = "multipart/form-data")
	@RequestMapping(method = RequestMethod.POST, value = "/transporterwithfile")
	@ResponseBody
	public ResponseEntity<?> getSearchResultViaAjaxFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("role") String role,
			@RequestParam("protein") String protein){
		PrintStatus.PrintStatusMessage("DrugPorter", multipartFile.getOriginalFilename());
		TransporterAjaxResponseBody result = new TransporterAjaxResponseBody();
		RunClassification newclassifier = new RunClassification();
		if(multipartFile!=null) {
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
							//TODO: change substrate to the options that user selected
							Instances testinstance = newclassifier.CreateTestingInstance("-s", smiles, role);
									
							try {
								String result_role = newclassifier.ClassifyInstance(role, protein, testinstance);
								result.setPredictedRole(result_role);
							} catch (Exception e) {
										// TODO Auto-generated catch block
								e.printStackTrace();
								result.setPredictedRole("Unknown (Error Occur)");
							}
						}
						else {
							result.setPredictedRole("Couldn't Parse the File.");
						}
								 
					}else {
						result.setPredictedRole("Empty File.");
								 // return empty sdf file warning
					}
					
					
					reader.close();
					
					if(sdfFile.exists()) {
						sdfFile.delete();
					}
							
				} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
					result.setPredictedRole(e.toString());
				} catch (IOException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
					result.setPredictedRole(e.toString());
				} catch (CDKException e) {
							// TODO Auto-generated catch block
					e.printStackTrace();
					result.setPredictedRole(e.toString());
				}
				
				
			}
		}else {
			result.setPredictedRole("Empty File.");
		}
		
		return ResponseEntity.ok(result);
	}
	
}
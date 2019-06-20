package com.example.MetabolismNetwork.StaticController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DownloadPageController {

	
	
	private String current_dir = System.getProperty("user.dir");
	
	/**
	 * Download DrugExporter Dataset file (SDF + CSV)
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/downloads/drugexporter", method = RequestMethod.GET)
	public ResponseEntity<Resource> DrugExporterDataset(String param) throws IOException {
	    
		File file = new File(String.format("%s/Downloads/DrugExporter.zip", current_dir));
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders(); 
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=DrugExporter.zip");
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	
	/**
	 * Download CypReact Dataset (SDF)
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/downloads/cypreact", method = RequestMethod.GET)
	public ResponseEntity<Resource> CypReactDataset(String param) throws IOException {
	    
		File file = new File(String.format("%s/Downloads/CypReactDataset.zip", current_dir));
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders(); 
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=CypReactDataset.zip");
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
	
	
	/**
	 * Download SOMPredictor
	 * @param param
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(path = "/downloads/sompredictor", method = RequestMethod.GET)
	public ResponseEntity<Resource> SOMPredictorDataset(String param) throws IOException {
	    
		File file = new File(String.format("%s/Downloads/SOMPredictor.zip", current_dir));
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders(); 
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=SOMPredictor.zip");
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(file.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource);
	}
}

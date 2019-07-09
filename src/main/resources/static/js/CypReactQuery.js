$("select").change(function() {
	var str = "";

	$("select option:selected").each(function() {
		str += $(this).text() + " ";
	});
	alert(str);
	$('#protein-info').text(str);
}).trigger("change");


$(document).ready(function(){
	
	$("#bth-search-sdf").click(function(event){
//		console.log("file");
		event.preventDefault();
		var file = $("#fileupload").val();
		if (file != "") {
			fire_ajax_submit_with_file();
		}
		else{
			var json = "<h4>Result: </h4><pre> NO FILE GIVEN !</pre>";
			$('.result').html(json);
		}
	});
	
	$("#bth-search-smiles").click(function(event){
//		console.log("smiles");
		event.preventDefault();
		var smiles = $("#smiles-string").val();
		if (smiles != ""){
			fire_ajax_submit();
		}else{
			var json = "<h4>Result: </h4><pre> NO SMILES GIVEN !</pre>";
			$('.result').html(json);
		}
	});
	
	$("#bth-search-draw").click(function(event){
//		console.log("draw");
		$('[name="sdf_content"]').val("");
		event.preventDefault();
		exportPromise = marvinSketcherInstance.exportStructure('mol');
		exportPromise.then(function(result) {
			$('[name="sdf_content"]').val(result);
			// console.log($('[name="sdf_content"]').val());
			if ($('[name="sdf_content"]').val() == "") {
				var response = "<h4>No Input!</h4>";
				$('.result').html(response);
			} else {
				console.log("fire_ajax_submit()");
				fire_ajax_submit();
			}
		});
	});
	
	
	$("#smiles-load-example").click(function(){
		console.log("smiles-load-example");
		$('#smiles-string').val("CN1C=NC(C[C@H](N)C(O)=O)=C1");
	})
	
	$("#sdf-remove-example").click(function(){
		$('#fileupload').val("");
	})
	
	
	$("#draw-load-example").click(function(){
		var marvinSketcherInstance;
		MarvinJSUtil.getEditor("#sketch").then(function(sketcherInstance) {
			marvinSketcherInstance = sketcherInstance;
			marvinSketcherInstance.importStructure("mol", s).catch(function(error) {
				alert(error);
			});
			}, function(error) {
				alert("Loading of the sketcher failed"+error);
	    }); 
		
		var s = "\n\n\n"+
		" 17 16  0     1  0  0  0  0  0999 V2000\n"+
		"    1.5719   -0.5512    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -1.5811    1.4029    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -1.6164   -0.3372    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.2020   -1.8251    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    1.3723   -0.0127    0.0000 C   0  0  2  0  0  0  0  0  0  0  0  0\n"+
		"   -0.0250   -0.3698    0.0000 C   0  0  1  0  0  0  0  0  0  0  0  0\n"+
		"    1.6198    1.4889    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -1.1395    0.2043    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.1469   -0.0043    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.1279   -0.4625    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.6574    1.6877    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    1.4366    1.9697    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    0.9929    1.9647    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -1.0877   -2.0590    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    0.5254   -2.2541    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.4815   -0.3389    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -2.3052    1.7645    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"  1  5  1  0  0  0  0\n"+
		"  1 16  1  0  0  0  0\n"+
		"  2  8  1  0  0  0  0\n"+
		"  2 17  1  0  0  0  0\n"+
		"  3  8  2  0  0  0  0\n"+
		"  4  6  1  0  0  0  0\n"+
		"  4 14  1  0  0  0  0\n"+
		"  4 15  1  0  0  0  0\n"+
		"  5  6  1  0  0  0  0\n"+
		"  5  7  1  0  0  0  0\n"+
		"  5 10  1  0  0  0  0\n"+
		"  6  8  1  0  0  0  0\n"+
		"  6  9  1  0  0  0  0\n"+
		"  7 11  1  0  0  0  0\n"+
		"  7 12  1  0  0  0  0\n"+
		"  7 13  1  0  0  0  0\n"+
		"M  END\n";
	})

	
	
})




function fire_ajax_submit() {

	var predict = {}
	/* set the search object to {"username":"$("#username").val()"} */
	/* username could be input smiles string */
	predict["smiles"] = $("#smiles-string").val();
	predict["sdf"] = $("#fileupload").val();
	predict["chemdraw"] = $('[name="sdf_content"]').val();

	// check box for enzyme
	if ($("#CYP1A2:checked").length > 0) {
		predict["CYP1A2"] = $("#CYP1A2").val();
	}
	if ($("#CYP2B6:checked").length > 0) {
		predict["CYP2B6"] = $("#CYP2B6").val();
	}
	if ($("#CYP2A6:checked").length > 0) {
		predict["CYP2A6"] = $("#CYP2A6").val();
	}
	if ($("#CYP2C8:checked").length > 0) {
		predict["CYP2C8"] = $("#CYP2C8").val();
	}
	if ($("#CYP2C9:checked").length > 0) {
		predict["CYP2C9"] = $("#CYP2C9").val();
	}
	if ($("#CYP2C19:checked").length > 0) {
		predict["CYP2C19"] = $("#CYP2C19").val();
	}
	if ($("#CYP2D6:checked").length > 0) {
		predict["CYP2D6"] = $("#CYP2D6").val();
	}
	if ($("#CYP2E1:checked").length > 0) {
		predict["CYP2E1"] = $("#CYP2E1").val();
	}
	if ($("#CYP3A4:checked").length > 0) {
		predict["CYP3A4"] = $("#CYP3A4").val();
	}

	$("#btn-search").prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/cypreact",
		data : JSON.stringify(predict),
		dataType : 'json',
		cache : false,
		timeout : 600000,

		beforeSend : function() {
			var json = "<h4>Loading ... </h4>";
			$('.result').html(json);

		},
		success : function(data) {
//			console.log(data);
			if (data.errorMsg == null) {
				var presented_data = CollectData(data);
				console.log(presented_data);

				var final_table = "";
				for (key in presented_data) {
					var tmp_column = "<tr><td>" + key + "</td><td>"
							+ presented_data[key] + "</td></tr>";
					final_table += tmp_column;
				}

				/*
				 * var json = "<h4>Result</h4><pre>" +
				 * JSON.stringify(presented_data, null, 4) + "</pre>";
				 */
				var json = "<h4>Result</h4><pre><table> " + final_table
						+ "</table></pre>";
				$('.result').html(json);
				$("#btn-search").prop("disabled", false);

			} else {
				var presented_data = data.errorMsg;
				var json = "<h4>Result</h4><pre>" + data.errorMsg + "</pre>";
				$('.result').html(json);
				$("#btn-search").prop("disabled", false);
			}

		},

		/* this e will be standard error prepared from spring framework */
		error : function(e) {

			var json = "<h4>Result</h4><pre>" + e.responseText + "</pre>";
			$('#feedback').html(json);

			/* console.log("ERROR : ", e); */
			$("#btn-search").prop("disabled", false);

		}
	});

}

var files = [];
$(document).on("change", "#fileupload", function(event) {
	files = event.target.files;
})

function fire_ajax_submit_with_file() {

	var MyForm = new FormData();
	MyForm.append("file", files[0]);

	// check box for enzyme
	// reference for optional params
	// http://codeflex.co/java-spring-rest-api-with-empty-or-optional-parameters/
	if ($("#CYP1A2:checked").length > 0) {
		MyForm.append("CYP1A2", $("#CYP1A2").val());
	}else{MyForm.append("CYP1A2","null");}
	
	if ($("#CYP2B6:checked").length > 0) {
		MyForm.append("CYP2B6", $("#CYP2B6").val());
	}else{MyForm.append("CYP2B6","null");}
	
	if ($("#CYP2A6:checked").length > 0) {
		MyForm.append("CYP2A6", $("#CYP2A6").val());
	}else{MyForm.append("CYP2A6","null");}
	
	if ($("#CYP2C8:checked").length > 0) {
		MyForm.append("CYP2C8", $("#CYP2C8").val());
	}else{MyForm.append("CYP2C8","null");}
	
	if ($("#CYP2C9:checked").length > 0) {
		MyForm.append("CYP2C9", $("#CYP2C9").val());
	}else{MyForm.append("CYP2C9","null");}
	
	if ($("#CYP2C19:checked").length > 0) {
		MyForm.append("CYP2C19", $("#CYP2C19").val());
	}else{MyForm.append("CYP2C19","null");}
	
	if ($("#CYP2D6:checked").length > 0) {
		MyForm.append("CYP2D6", $("#CYP2D6").val());
	}else{MyForm.append("CYP2D6","null");}
	
	if ($("#CYP2E1:checked").length > 0) {
		MyForm.append("CYP2E1", $("#CYP2E1").val());
	}else{MyForm.append("CYP2E1","null");}
	
	if ($("#CYP3A4:checked").length > 0) {
		MyForm.append("CYP3A4", $("#CYP3A4").val());
	}else{MyForm.append("CYP3A4","null");}

	$("#btn-search").prop("disabled", true);

	$.ajax({
		type : "POST",
		url : "/cypreactwithfile",
		dataType : 'json',
		data : MyForm,
		enctype : 'multipart/form-data',
		contentType : false,
		processData : false,
		cache : false,
		timeout : 600000,

		beforeSend : function() {
			var json = "<h4>Loading ... </h4>";
			$('.result').html(json);

		},
		success : function(data) {

			/*
			 * var json = "<h4>Result</h4><pre>" + JSON.stringify(data,
			 * null, 4) + "</pre>"; $('.result').html(json);
			 * $("#btn-search").prop("disabled", false);
			 */

//			console.log(data);
			if (data.errorMsg == null) {
				var presented_data = CollectData(data);
//				console.log(presented_data);

				var final_table = "";
				for (key in presented_data) {
					var tmp_column = "<tr><td>" + key + "</td><td>"
							+ presented_data[key] + "</td></tr>";
					final_table += tmp_column;
				}

				/*
				 * var json = "<h4>Result</h4><pre>" +
				 * JSON.stringify(presented_data, null, 4) + "</pre>";
				 */
				var json = "<h4>Result</h4><pre><table> " + final_table
						+ "</table></pre>";
				$('.result').html(json);
				$("#btn-search").prop("disabled", false);

			} else {
				var presented_data = data.errorMsg;
				var json = "<h4>Result</h4><pre>" + data.errorMsg + "</pre>";
				$('.result').html(json);
				$("#btn-search").prop("disabled", false);
			}

		},

		error : function(e) {

			var json = "<h4>Result</h4><pre>" + e.responseText + "</pre>";
			$('#feedback').html(json);
			$("#btn-search").prop("disabled", false);

		}
	});

}

function CollectData(data) {
	var final_result = {};
	var total_number = 0;

	if (data.cyp1A2 === "R") {
		final_result["CYP1A2"] = "Reactant";
		total_number++;
	} else if (data.cyp1A2 === "N") {
		final_result["CYP1A2"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2B6 === "R") {
		final_result["CYP2B6"] = "Reactant";
		total_number++;
	} else if (data.cyp2B6 === "N") {
		final_result["CYP2B6"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2A6 === "R") {
		final_result["CYP2A6"] = "Reactant";
		total_number++;
	} else if (data.cyp2A6 === "N") {
		final_result["CYP2A6"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2C8 === "R") {
		final_result["CYP2C8"] = "Reactant";
		total_number++;
	} else if (data.cyp2C8 === "N") {
		final_result["CYP2C8"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2C9 === "R") {
		final_result["CYP2C9"] = "Reactant";
		total_number++;
	} else if (data.cyp2C9 === "N") {
		final_result["CYP2C9"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2C19 === "R") {
		final_result["CYP2C19"] = "Reactant";
		total_number++;
	} else if (data.cyp2C19 === "N") {
		final_result["CYP2C19"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2D6 === "R") {
		final_result["CYP2D6"] = "Reactant";
		total_number++;
	} else if (data.cyp2D6 === "N") {
		final_result["CYP2D6"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp2E1 === "R") {
		final_result["CYP2E1"] = "Reactant";
		total_number++;
	} else if (data.cyp2E1 === "N") {
		final_result["CYP2E1"] = "Non-Reactant";
		total_number++;
	}

	if (data.cyp3A4 === "R") {
		final_result["CYP3A4"] = "Reactant";
		total_number++;
	} else if (data.cyp3A4 === "N") {
		final_result["CYP3A4"] = "Non-Reactant";
		total_number++;
	}

	/* final_result["number"] = total_number; */

	return final_result;
}
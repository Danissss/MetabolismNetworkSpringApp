$("select").change(function() {
	var str = "";

	$("select option:selected").each(function() {
		str += $(this).text() + " ";
	});
	alert(str);
	$('#protein-info').text(str);
}).trigger("change");

$(document).ready(function() {
	$("#submit-request").submit(function(event) {
		event.preventDefault();
		var smiles = $("#smiles-string").val();
		var file = $("#fileupload").val();
		if (smiles != "") {

			console.log("smiles=> " + smiles);
			fire_ajax_submit();
		} else if (file != "") {

			console.log("file =>" + file);
			fire_ajax_submit_with_file();
		} else {
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

		}

	});
});

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
			console.log(data);
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
		/* predict["CYP1A2"] = $("#CYP1A2").val(); */
		MyForm.append("CYP1A2", $("#CYP1A2").val());
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

			console.log(data);
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
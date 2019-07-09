/**
 * @author: Xuan Cao
 * @note: This JS is at head
 */


/**
 * button color functionality
 * @returns
 */
$("select").change(function() {
	var str = "";

	$("select option:selected").each(function() {
		str += $(this).text() + " ";
	});
	alert(str);
	$('#protein-info').text(str);
}).trigger("change");

// marvin sketch



/**
 * ajax send post request
 * @returns
 */
//$(document).ready(function() {
//	$("#submit-request").submit(function(event) {
//		event.preventDefault();
//		var smiles = $("#smiles-string").val();
//		var file = $("#fileupload").val();
//		if (smiles != "") {
//
//			console.log("smiles=> " + smiles);
//			fire_ajax_submit();
//		} else if (file != "") {
//
//			console.log("file =>" + file);
//			fire_ajax_submit_with_file();
//		} else {
//			
//			exportPromise = marvinSketcherInstance.exportStructure('mol');
//			exportPromise.then(function(result) {
//				$('[name="sdf_content"]').val(result);
//				console.log($('[name="sdf_content"]').val());
//				if ($('[name="sdf_content"]').val() == "") {
//					var response = "<h4>No Input!</h4>";
//					$('.result').html(response);
//				} else {
//					console.log("fire_ajax_submit()");
//					fire_ajax_submit();
//				}
//			});
//
//		}
//
//		/*
//		 * fire_ajax_submit_with_file(); fire_ajax_submit();
//		 */
//
//	});
//});

$(document).ready(function(){
	
	$("#bth-search-sdf").click(function(event){
		console.log("file");
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
		console.log("smiles");
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
		console.log("draw");
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
		" 14 15  0  0  0  0  0  0  0  0999 V2000\n"+
		"    0.5089    7.8316    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    1.2234    6.5941    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    1.2234    7.4191    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.2055    6.5941    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.9200    7.8316    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    0.5089    5.3566    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.2055    7.4191    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    0.5089    6.1816    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.9200    6.1816    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    0.5089    8.6566    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.4929    7.0066    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.0080    7.6740    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.0080    6.3391    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.2630    8.4586    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"  1  7  1  0  0  0  0\n"+
		"  8  2  1  0  0  0  0\n"+
		"  1  3  1  0  0  0  0\n"+
		"  2  3  2  0  0  0  0\n"+
		"  7  4  1  0  0  0  0\n"+
		"  4  8  1  0  0  0  0\n"+
		"  4  9  2  0  0  0  0\n"+
		"  7  5  1  0  0  0  0\n"+
		"  8  6  1  0  0  0  0\n"+
		"  1 10  2  0  0  0  0\n"+
		"  3 12  1  0  0  0  0\n"+
		"  2 13  1  0  0  0  0\n"+
		" 13 11  2  0  0  0  0\n"+
		" 12 11  1  0  0  0  0\n"+
		" 12 14  1  0  0  0  0\n"+
		"M  END\n";
	})

	
	
})




//$(document).ready(function(){
//var option = null;
//$("#bth-search-sdf").on("click",function(){
//	option = "file";
//});
//$("#bth-search-smiles").on("click",function(){
//	option = "smiles";
//});
//$("#bth-search-draw").on("click",function(){
//	option = "draw";
//});
//console.log(option);
//if (option == "file"){
//	console.log("file");
//	$("#submit-request").submit(function(event) {
//		event.preventDefault();
//		var file = $("#fileupload").val();
//		if (file != "") {
//			console.log("no file");
//		}
//		else{
//			console.log("file =>" + file);
//			fire_ajax_submit_with_file();
//		}
//	})
//}else if(option == "smiles"){
//	console.log("smiles");
//	
//	$("#submit-request").submit(function(event) {
//		event.preventDefault();
//		var smiles = $("#smiles-string").val();
//		if (smiles != ""){
//			fire_ajax_submit();
//		}			
//	})
//}
//else{
//	console.log("draw");
//	$("#submit-request").submit(function(event) {
//		event.preventDefault();
//		exportPromise = marvinSketcherInstance.exportStructure('mol');
//		exportPromise.then(function(result) {
//			$('[name="sdf_content"]').val(result);
//			// console.log($('[name="sdf_content"]').val());
//			if ($('[name="sdf_content"]').val() == "") {
//				var response = "<h4>No Input!</h4>";
//				$('.result').html(response);
//			} else {
//				console.log("fire_ajax_submit()");
//				fire_ajax_submit();
//			}
//		});
//	})
//}
//
//})

//$("#bth-search-sdf").on("click",function(){
//	option = "file";
//	console.log("click file");
//});
//$("#bth-search-smiles").on("click",function(){
//	option = "smiles";
//	console.log("click smiles");
//});
//$("#bth-search-draw").on("click",function(){
//	option = "draw";
//	console.log("click draw");
//});
//
//$(document).ready(function(){
//	$("#submit-request").submit(function(event) {
//		event.preventDefault();
//		var option = null;
//		$("#bth-search-sdf").on("click",function(){
//			option = "file";
//			console.log("click file");
//		});
//		$("#bth-search-smiles").on("click",function(){
//			option = "smiles";
//			console.log("click smiles");
//		});
//		$("#bth-search-draw").on("click",function(){
//			option = "draw";
//			console.log("click draw");
//		});
//		console.log(option);
//		if (option == "file"){
//			console.log("file");
//			var file = $("#fileupload").val();
//			if (file != "") {
//				console.log("no file");
//			}
//			else{
//				console.log("file =>" + file);
//				fire_ajax_submit_with_file();
//			}
//		}else if(option == "smiles"){
//			console.log("smiles");
//			
//			var smiles = $("#smiles-string").val();
//			if (smiles != ""){
//				fire_ajax_submit();
//			}	
//		}
//		else if (option == "draw"){
//			console.log("draw");
//
//			exportPromise = marvinSketcherInstance.exportStructure('mol');
//			exportPromise.then(function(result) {
//				$('[name="sdf_content"]').val(result);
//				// console.log($('[name="sdf_content"]').val());
//				if ($('[name="sdf_content"]').val() == "") {
//					var response = "<h4>No Input!</h4>";
//					$('.result').html(response);
//				} else {
//					console.log("fire_ajax_submit()");
//					fire_ajax_submit();
//				}
//			});
//		}
//	});
//});


var files = [];
$(document).on("change", "#fileupload", function(event) {
	files = event.target.files;
})

/**
 * taking argument from client side and process ajax execution with file 
 * @returns
 */
function fire_ajax_submit_with_file() {

	var MyForm = new FormData();

	/* set the search object to {"username":"$("#username").val()"} */
	/* username could be input smiles string */
	/*
	 * predict["smiles"] = $("#smiles-string").val(); predict["sdf"] =
	 * $("#fileupload").val(); predict["chemdraw"] = $("#pills-chemdraw").val();
	 */

	/*
	 * console.log($("#fileupload").val()); MyForm.append("files",
	 * $("#fileupload").val());
	 */
//	console.log(files[0]);
	MyForm.append("file", files[0]);
	/* console.log(MyForm); */

	// radio button for role
	if ($("#option-inhibitor:checked").length > 0) {
		/* predict["role"] = $("#option-inhibitor").val(); */
		MyForm.append("role", $("#option-inhibitor").val());
	} else if ($("#option-substrate:checked").length > 0) {
		/* predict["role"] = $("#option-substrate").val(); */
		MyForm.append("role", $("#option-inhibitor").val());
	}

	// check box for protein

	if ($("#check-MDR1:checked").length > 0) {
		/* predict["protein"] = $("#check-MDR1").val(); */
		MyForm.append("protein", $("#check-MDR1").val());
	} else if ($("#check-BCRP:checked").length > 0) {
		/* predict["protein"] = $("#check-BCRP").val(); */
		MyForm.append("protein", $("#check-MDR1").val());
	} else if ($("#check-MRP1:checked").length > 0) {
		/* predict["protein"] = $("#check-MRP1").val(); */
		MyForm.append("protein", $("#check-MDR1").val());
	} else if ($("#check-MRP2:checked").length > 0) {
		/* predict["protein"] = $("#check-MRP2").val(); */
		MyForm.append("protein", $("#check-MDR1").val());
	}

	$('#bth-search').prop("disabled", true);
	$.ajax({
		type : "POST",
		url : "/transporterwithfile",
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

		/*
		 * on success, pass data (from return value of java function) into below
		 * function
		 */
		success : function(data) {
			/*
			 * Use data.predictedRole to get the specific file from json data
			 * structure
			 */
			var json = "<h4>Result: </h4><pre>"
					+ JSON.stringify(data.predictedRole, null, 4) + "</pre>";
			$('.result').html(json);
			/* console.log("SUCCESS : ", data); */

			$('#bth-search').prop("disabled", false);
		},

		/* this e will be standard error prepared from spring framework */
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
			$('#bth-search').prop("disabled", false);

		}

	});

}

/**
 * taking argument from client side and process ajax execution
 * @params None
 * @returns
 */
function fire_ajax_submit() {

	var predict = {}
	/* set the search object to {"username":"$("#username").val()"} */
	/* username could be input smiles string */
	predict["smiles"] = $("#smiles-string").val();
	predict["sdf"] = $("#fileupload").val();
	/* predict["chemdraw"] = $("#pills-chemdraw").val(); */
	predict["chemdraw"] = $('[name="sdf_content"]').val();

	// radio button for role
	if ($("#option-inhibitor:checked").length > 0) {
		predict["role"] = $("#option-inhibitor").val();
	} else if ($("#option-substrate:checked").length > 0) {
		predict["role"] = $("#option-substrate").val();
	}

	// check box for protein

	if ($("#check-MDR1:checked").length > 0) {
		predict["protein"] = $("#check-MDR1").val();
	} else if ($("#check-BCRP:checked").length > 0) {
		predict["protein"] = $("#check-BCRP").val();
	} else if ($("#check-MRP1:checked").length > 0) {
		predict["protein"] = $("#check-MRP1").val();
	} else if ($("#check-MRP2:checked").length > 0) {
		predict["protein"] = $("#check-MRP2").val();
	}

	$('#bth-search').prop("disabled", true);

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/transporter",
		data : JSON.stringify(predict), /*
										 * JSON.stringify(the javascript
										 * object). JSON.stringify() converts a
										 * value to JSON notation representing
										 * it
										 */
		dataType : 'json',
		cache : false,
		timeout : 600000,

		beforeSend : function() {
			var json = "<h4>Loading ... </h4>";
			$('.result').html(json);

		},

		/*
		 * complete: function(){          var json = "";
		 * $('.result').html(json); $("#btn-search").prop("disabled", false);                   },
		 */

		/*
		 * on success, pass data (from return value of java function) into below
		 * function
		 */
		success : function(data) {
			/*
			 * Use data.predictedRole to get the specific file from json data
			 * structure
			 */
			var json = "<h4>Result: </h4><pre>"
					+ JSON.stringify(data.predictedRole, null, 4) + "</pre>";
			$('.result').html(json);
			$('#bth-search').prop("disabled", false);

		},

		/* this e will be standard error prepared from spring framework */
		error : function(e) {

			var json = "<h4>Ajax Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#feedback').html(json);

			console.log("ERROR : ", e);
			$('#bth-search').prop("disabled", false);

		}

	});

}
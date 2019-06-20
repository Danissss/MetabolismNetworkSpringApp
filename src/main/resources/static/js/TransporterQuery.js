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
$(document).ready(function() {
	$("#submit-request").submit(function(event) {
		event.preventDefault();
		var smiles = $("#smiles-string").val();
		var file = $("#fileupload").val();
		if (smiles != "") {
			/*
			 * var response = "<h4>file here</h4>";
			 * $('.result').html(response);
			 */
			console.log("smiles=> " + smiles);
			fire_ajax_submit();
		} else if (file != "") {
			/*
			 * var response = "<h4>"+file+"</h4>";
			 * $('.result').html(response);
			 */
			console.log("file =>" + file);
			fire_ajax_submit_with_file();
		} else {
			exportPromise = marvinSketcherInstance.exportStructure('mol');

			exportPromise.then(function(result) {
				$('[name="sdf_content"]').val(result);
				console.log($('[name="sdf_content"]').val());
				if ($('[name="sdf_content"]').val() == "") {
					var response = "<h4>No Input!</h4>";
					$('.result').html(response);
				} else {
					console.log("fire_ajax_submit()");
					fire_ajax_submit();
				}
			});

		}

		/*
		 * fire_ajax_submit_with_file(); fire_ajax_submit();
		 */

	});
});

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
	console.log(files[0]);
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
// button color functionality
$("select").change(function() {
	var str = "";

	$("select option:selected").each(function() {
		str += $(this).text() + " ";
	});
	alert(str);
	$('#protein-info').text(str);
}).trigger("change");

// marvin sketch

// ajax send post request
$(document).ready(function() {
	$("#submit-request").submit(function(event) {
		event.preventDefault();
		var smiles = $("#smiles-string").val();
		var file = $("#fileupload").val();
		$('#generated-image').remove();
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
				if ($('[name="sdf_content"]').val() == "") {
					var response = "<h4>No Input!</h4>";
					$('.result').html(response);
				} else {
					fire_ajax_submit();
				}
			});

		}

	});
});

var files = [];
$(document).on("change", "#fileupload", function(event) {
	files = event.target.files;
})

function fire_ajax_submit_with_file() {

	var MyForm = new FormData();
	MyForm.append("file", files[0]);
	/* console.log(MyForm); */

	// check box for protein
	console.log($('#cyp-select').find(":selected").val());
	MyForm.append("protein", $('#cyp-select').find(":selected").val());

	$('#bth-search').prop("disabled", true);
	$
			.ajax({
				type : "POST",
				url : "/phase1somwithfile",
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
					console.log(data);
					/*
					 * spring-boot only display static image from several
					 * default location
					 */
					/*
					 * ref:
					 * https://docs.spring.io/spring-boot/docs/1.1.x/reference/htmlsingle/#boot-features-spring-mvc-static-content
					 */
					if (data.fail === true) {
						var json = "<h4>Error: </h4><pre>"
								+ JSON.stringify(data.setErrorMsg, null, 4)
								+ "</pre>";
						$('.result').html(json);
						$('#bth-search').prop("disabled", false);
					} else if (data.success === true) {
						var json = "<pre>Success: Atom(s) with red color is the predicted site of metabolism </pre>";
						var images_div = "<img id=\"generated-image\" src=\"" + data.image_path
								+ "\" height=\"360px\" width=\"360px\">";
						$('.result').html(json);
						$('.show-image').append(images_div);

					}

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

function fire_ajax_submit() {

	var predict = {}
	/* set the search object to {"username":"$("#username").val()"} */
	/* username could be input smiles string */
	predict["smiles"] = $("#smiles-string").val();
	predict["sdf"] = $("#fileupload").val();
	predict["chemdraw"] = $('[name="sdf_content"]').val();

	// check box for protein
	predict["protein"] = $('#cyp-select').find(":selected").val();

	$('#bth-search').prop("disabled", true);

	$
			.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/phase1som",
				data : JSON.stringify(predict), /*
												 * JSON.stringify(the javascript
												 * object). JSON.stringify()
												 * converts a value to JSON
												 * notation representing it
												 */
				dataType : 'json',
				cache : false,
				timeout : 600000,

				beforeSend : function() {
					var json = "<h4>Loading ... </h4>";
					$('.result').html(json);

				},

				/*
				 * on success, pass data (from return value of java function)
				 * into below function
				 */
				success : function(data) {
					console.log(data);
					if (data.fail === true) {
						var json = "<h4>Error: </h4><pre>"
								+ JSON.stringify(data.setErrorMsg, null, 4)
								+ "</pre>";
						$('.result').html(json);
						$('#bth-search').prop("disabled", false);
					} else if (data.success === true) {
						var json = "<pre>Success: Atom(s) with red color is the predicted site of metabolism </pre>";
						var images_div = "<img id=\"generated-image\" src=\"" + data.image_path
								+ "\" height=\"360px\" width=\"360px\">";
						$('.result').html(json);
						$('.show-image').append(images_div);

					}

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
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

$(document).ready(function(){
	
	$("#bth-search-sdf").click(function(event){
		console.log("file");
		event.preventDefault();
		$('#generated-image').remove();
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
		$('#generated-image').remove();
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
		$('#generated-image').remove();
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
		" 18 18  0     0  0  0  0  0  0999 V2000\n"+
		"    4.3579    0.4145    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.4363   -0.2819    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.8263    1.0571    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -1.3970   -1.2865    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -2.1772    1.3916    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -2.7479   -0.9521    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    0.9725   -0.6306    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -3.1379    0.3870    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    2.0042    0.1995    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    3.3880   -0.2986    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -0.1150    1.8710    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -1.1121   -2.3357    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    1.1820   -1.6553    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -2.4820    2.4339    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -3.4962   -1.7341    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"   -4.1899    0.6473    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    1.8888    1.2254    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"    3.5005   -1.3484    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0\n"+
		"  1 10  2  0  0  0  0\n"+
		"  2  3  2  0  0  0  0\n"+
		"  2  4  1  0  0  0  0\n"+
		"  2  7  1  0  0  0  0\n"+
		"  3  5  1  0  0  0  0\n"+
		"  3 11  1  0  0  0  0\n"+
		"  4  6  2  0  0  0  0\n"+
		"  4 12  1  0  0  0  0\n"+
		"  5  8  2  0  0  0  0\n"+
		"  5 14  1  0  0  0  0\n"+
		"  6  8  1  0  0  0  0\n"+
		"  6 15  1  0  0  0  0\n"+
		"  7  9  2  0  0  0  0\n"+
		"  7 13  1  0  0  0  0\n"+
		"  8 16  1  0  0  0  0\n"+
		"  9 10  1  0  0  0  0\n"+
		"  9 17  1  0  0  0  0\n"+
		" 10 18  1  0  0  0  0\n"+
		"M  END\n";
	})

	
	
})

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
//					console.log(data);
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

//					console.log("ERROR : ", e);
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
//					console.log(data);
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
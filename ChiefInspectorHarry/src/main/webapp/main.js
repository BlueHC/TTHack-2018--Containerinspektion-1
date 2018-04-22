var g_latestUploadedFile;
var g_sensorList = [];
var g_refreshHeatMapInterval;
var g_REFRESH_RATE_MS = 1000;

window.onload = function() {
	//loadSelectGroundPlanTpl();
	initGroundPlanUploadHandler();
	registerSessionStartConfigBtn();
};

function initGroundPlanUploadHandler(){
    $("form#upload_inspectionimage_data").submit(function(){
    	
        var formData = new FormData(this);

        $.ajax({
            url: "rest/main/image/add",
            type: 'POST',
            data: formData,
            async: false,
            success: function (data) {
            	g_latestUploadedFile = formData;
            	$("#features18-x").empty();
            	loadGroundPlans();
            	$('html, body').animate({
                    scrollTop: $("#features18-x").offset().top
                }, 2000);
            	$('form#upload_inspectionimage_data').trigger("reset");
            },
            error: function (data) {
            	ajaxError(data)
            },
            cache: false,
            contentType: false,
            processData: false
        });

        return false;
    });        
};


function loadSelectGroundPlanTpl(){
/*	var data = {
		ground_plan_list: [
			{ id: 1,
			  filename: "IMG_1983.JPG",
			  name: "Adrian Zuhause",
			  description: "Irgend eine tolle beschreibung. Vieleicht auch mit addresse",
			  upload_date: "2017-03-11"
			},
			{ id: 2,
			  filename: "IMG_2029.JPG",
			  name: "Maren Zuhause",
			  description: "Irgend eine Maren beschreibung. Vieleicht auch mit addresse",
			  upload_date: "2017-10-11"
			},
			{ id: 3,
			  filename: "IMG_3547.JPG",
			  name: "Christian Zuhause",
			  description: "Irgend eine Christian beschreibung. Vieleicht auch mit addresse",
			  upload_date: "2017-12-11"
			}
		]	
	};*/
	
	/*$.ajax({
	    url : "rest/inspectionimage/all",
	    type : "get",
	    async: false,
	    success : function(resultGroundPlanList) {
				$.ajax({
				    url : "inspectionimages/select_ground_plan.tpl.html",
				    type : "get",
				    async: false,
				    success : function(template) {
				    	var data = { ground_plan_list: resultGroundPlanList }
					    var rendered = Mustache.render(template, data);
					    $('#target').html(rendered);
				    },
				    error: function(data) {
				       ajaxError();
				    }
				 });	
		    },
		    error: function(data) {
		       ajaxError();
		    }
		 });	*/
	
	/*$.get('inspectionimages/select_ground_plan.tpl.html', function(template) {
	    var rendered = Mustache.render(template, data);
	    $('#target').html(rendered);
	  });	*/
}

function loadGroundPlans(){
	/*$.ajax({
	    url : "rest/inspectionimage/all",
	    type : "get",
	    async: false,
	    success : function(resultGroundPlanList) {
				$.ajax({
				    url : "inspectionimages/ground_plan_galery_elements.tpl.html",
				    type : "get",
				    async: false,
				    success : function(template) {
				    	var data = { ground_plan_list: resultGroundPlanList }
					    var rendered = Mustache.render(template, data);
					    $('#features18-x').html(rendered);
				    },
				    error: function(data) {
				       ajaxError();
				    }
				 });	
		    },
		    error: function(data) {
		       ajaxError();
		    }
		 });		*/
}

function registerSessionStartConfigBtn(){
	$('#start_session_btn').click( function() {
		$.ajax({
		     async: false,
		     type: 'GET',
		     url: 'rest/main/inspection/new',
			 success : function(sessionData) {
			    	console.log("SUCCESS: " + sessionData);
			    },
			 error: function() {
			    	console.log("Failed to create new Session!(URL = 'rest/planing_session/add/" + $("#session_name_inputfield").val() + "/" + g_selectedGroundPlan.id + "/" + $("#session_description_inputfield").val() + "'");
			    }
		});
	});	
}

function ajaxError(data){
	$('#myErrorModal').on('shown.bs.modal', function () {
		  $('#myErrorModalMessage').focus()
	})
	$('#myErrorModalMessage').text(data.responseText);
	$('#myErrorModal').modal({
	  backdrop: true,
	  keyboard: true,
	  focus: true,
	  show: true
	});
}
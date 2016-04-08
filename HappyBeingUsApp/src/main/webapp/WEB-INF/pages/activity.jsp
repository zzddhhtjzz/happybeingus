<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin NEW Activity </title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
         href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
        src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
        src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<style>
        /* Remove the navbar's default margin-bottom and rounded borders */ 
        .navbar {
            margin-bottom: 0;
            border-radius: 0;
        }

        /* Add a gray background color and some padding to the footer */
      

        .jumbotron {
            background-color: orange;
        }
        .inv {
            display: none;
        }
        .dropdown-menu {
            width: 300px !important;
            height: 400px !important;
        }
        .dropdown-menu {
            min-width:auto;
            width:100%;
            }

        .btn-file2 {
            position: relative;
            overflow: hidden;
                  }
        .btn-file2 input[type=file] {
            position: absolute;
            top: 0;
            right: 0;
            min-width: 100%;
            min-height: 100%;
            font-size: 100px;
            text-align: right;
            filter: alpha(opacity=0);
            opacity: 0;
            outline: none;
            background: white;
            cursor: inherit;
            display: block;
            }
</style> 

<script>
	$(document).ready(function() {
// 		dropdown for all the templates.
		$('#activityTemplate').on('change', function() {
			var template_id = $('#activityTemplate').val();
			$("#activityTemplate > option").each(function() {
				if (template_id == this.value) {
					$("#"+this.value).css("display", "block");
				} else {
					$("#"+this.value).css("display", "none");
				}
			});
		});
		
	});
	
//		mcq template, update checkbox value with the input of user
	$(document).on('change', '#mcqOptions .option', function() {
		var num = this.id.split('_')[1];
		$('#mcqOptions #checkBox_'+num).val(this.name);
	});
	

//		adding more options inside the mcq template
	$(document).on('click', '#mcqMoreOptions', function() {
		var maxVal = $('#mcqMaxOptions').val();
		var nxtVal = parseInt(maxVal)+1;
		$('#mcqOptions').append(
				'<div class="mcqOption" id="mcqOption_'+nxtVal+'"  class="form-group"> '
					+'<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_'+nxtVal+'" /> ' 
					+'<input class="option  form-control" type="text" name="option_'+nxtVal+'" id="option_'+nxtVal+'" placeholder="Content for this choice" required/> '
					+'<button class="removeOption btn btn-primary btn_lg" id="removeOption_'+nxtVal+'" type="button">Remove</button><br> ' 
				+'</div>');
		$('#mcqMaxOptions').val(nxtVal);
		if(nxtVal == 5){
			$('#mcqMoreOptions').css("display", "none");
		}
	});
	
	//		mcq template, remove option
	$(document).on('click', "#mcqOptions .removeOption", function() {
		var removeId = this.id.split("_")[1];
		$('#mcqOptions #mcqOption_'+removeId).remove();
		var maxVal = $('#mcqMaxOptions').val();
		var newVal = parseInt(maxVal)-1;
		var initVal = 0;
		$("#mcqOptions .mcqOption").each(function() {
			initVal = initVal +1;
			this.id = "mcqOption_"+initVal;
			$('#'+this.id+' .chkbx')[0].id = "checkBox_"+initVal;
			$('#'+this.id+' .option')[0].id = "option_"+initVal;
			$('#'+this.id+' .option')[0].name = "option_"+initVal;
			$('#'+this.id+' .removeOption')[0].id = "removeOption_"+initVal;
		});
		$('#mcqMoreOptions').css("display", "block");
		$('#mcqMaxOptions').val(newVal);
	});
	
// 	Image upload messages
	function imageUploadMsg() {
	    var fileHolder = document.getElementById("imageFile");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("imageUploadMsg").innerHTML = msg;
	}

// 	Video upload messages
	function videoUploadMsg() {
	    var fileHolder = document.getElementById("videoFile");
	    var msg = "";
	    if ('files' in fileHolder) {
	        if (fileHolder.files.length == 0) {
	            msg = "Browse from computer";
	        } else {
	            var file = fileHolder.files[0];
	            if ('name' in file) {
	                msg += "File Name: " + file.name + "<br>";
	            }
	            if ('size' in file) {
	                msg += "File Size: " + file.size + " bytes <br>";
	            }
	        }
	    } else {
	        if (fileHolder.value == "") {
	            msg += "Browse from computer";
	        } else {
	            msg += "The files property is not supported by your browser!";
	            msg += "<br>The path of the selected file: " + fileHolder.value;
	            // If the browser does not support the files property, it will return the path of the selected file instead.
	        }
	    }
	    document.getElementById("videoUploadMsg").innerHTML = msg;
	}
</script>

</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#myNavbar">
                    <span class="icon-bar"></span> <span class="icon-bar"></span> <span
                        class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Admin Home</a>
            </div>
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
                            Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="jumbotron">
        <div class="container text-center">
            <h2>Create Activity</h2>
            <p> Create   Activities here: <p>
        </div>
    </div>


    <select name="activityTemplate" id="activityTemplate">
        <option value="">Select The Activity...</option>
        <c:forEach items="${activityTemplates}" var="template">
			<option value="template_${template.id}">${template.templateName}</option>
		</c:forEach>
    <select>
    
    <c:forEach items="${activityTemplates}" var="template">
    <c:if test="${template.id==1}">
   <form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
	    <div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="option1">Image Link</label>
	                <p id="videoUploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="videoFile" name="uploadFile" onchange="videoUploadMsg()" required></span>
	            </div>          
	
	
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" id="commentImage" placeholder="Enter Question Contents Here." required="true"></form:textarea>
	            </div>
	            
	            <div class="form-group">
	                <label for="comment">Answer Content:</label>
	                <textarea  class="form-control" rows="5" name="idealAnswer" placeholder="Enter Answer Here." required></textarea>
	            </div>
	
	            <input type="submit" class="btn btn-primary btn_lg" value="Add"/>
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	    </div>
	    <form:input type="hidden" path="activityType.id" />
		<form:input type="hidden" path="activityTemplate.id" value="${template.id}" />
		<form:input type="hidden" path="activityContainer.activityContainerId" />
    </form:form>
    </c:if>
    <c:if test="${template.id==2}">
    <form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
	    <div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="option1">Image Link</label>
	                <p id="imageUploadMsg">Browse from computer</p>
	                <span class="btn btn-default btn-file2">Browse<input type="file" id="imageFile" name="uploadFile" onchange="imageUploadMsg()" required></span>
	            </div>          
	
	
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" id="commentImage" placeholder="Enter Question Contents Here." required="true"></form:textarea>
	            </div>
	            
	            <div class="form-group">
	                <label for="comment">Answer Content:</label>
	                <textarea  class="form-control" rows="5" name="idealAnswer" placeholder="Enter Answer Here." required></textarea>
	            </div>
	
	            <input type="submit" class="btn btn-primary btn_lg" value="Add"/>
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	    </div>
	    <form:input type="hidden" path="activityType.id" />
		<form:input type="hidden" path="activityTemplate.id" value="${template.id}" />
		<form:input type="hidden" path="activityContainer.activityContainerId" />
    </form:form>
    </c:if>
    <c:if test="${template.id==3}">
	<form:form action="addActivity.action" method="post" name="mcqForm" id="mcqForm" modelAttribute="activity" enctype="multipart/form-data">
		<div id="template_${template.id}" style="display: none">
	
	        <div class="container">
	        <h2></h2>
	            <div class="form-group">
	                <label for="comment">Question Content:</label>
	                <form:textarea name="Question" path="activityText" class="form-control" rows="5" id="commentMCQ" placeholder="Enter Question Contents Here."></form:textarea>
	            </div>
	            
	            <div id="mcqOptions">
	            	
	            	<div class="mcqOption" id="mcqOption_1" class="form-group">
						<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_1" /> 
						<input class="option form-control" type="text" name="option_1" id="option_1"  placeholder="Content for this choice" required/>
						<button class="removeOption btn btn-primary btn_lg" id="removeOption_1" type="button">Remove</button><br> 
					</div>
	            	
	            	<div class="mcqOption" id="mcqOption_2" class="form-group">
						<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_2" /> 
						<input class="option form-control" type="text" name="option_2" id="option_2"  placeholder="Content for this choice" required/>
						<button class="removeOption btn btn-primary btn_lg" id="removeOption_2" type="button">Remove</button><br> 
					</div>
	            	
	            	<div class="mcqOption" id="mcqOption_3" class="form-group">
						<input type="Checkbox" name="correctAnswer" class="chkbx" id="checkBox_3" /> 
						<input class="option form-control" type="text" name="option_3" id="option_3"  placeholder="Content for this choice" required/>
						<button class="removeOption btn btn-primary btn_lg" id="removeOption_3" type="button">Remove</button><br> 
					</div>
	            </div>
	    	</div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	        <button id="mcqMoreOptions" type="button" class="btn btn-primary btn_lg">Add more options</button>
			<button type="submit" class="btn btn-primary btn_lg">Add</button>
			<form:input type="hidden" path="activityType.id" />
			<form:input type="hidden" path="activityTemplate.id"
				value="${template.id}" />
			<form:input type="hidden" path="activityContainer.activityContainerId" />
	
	    </div>
	</form:form>
	<input type="hidden" id="mcqMaxOptions" value="3"/>    
    </c:if>
    
    <c:if test="${template.id==4}">
    	<div id="content_1" class="inv"><div class="container">
	        <h2></h2>
	        <form role="form" action=# method="post">           
	            
	            <div class="form-group">
	                <label for="comment">Information Content:</label>
	                <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Information Content Here."></textarea>
	            </div>
	
	            <input type="submit" class="btn btn-primary btn_lg" value="submit"></input>
	        </form>
	    </div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	           <footer class="container-fluid text-right">
	
	           </footer>
	        </div>
	    </div>
    </c:if>
    <c:if test="${template.id==5}">
	    <div id="content_5" class="inv">
	    
	
	        <div class="container">
	                    <h2></h2>
	                    <form role="form" action=# method="post">
	                                
	
	
	                        <div class="form-group">
	                            <h1> Card 1 </h1>
	                            <label for="comment">Front Content:</label>
	                            <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Front Contents Here."></textarea>
	                            <label for="comment">Back Content:</label>
	                            <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Back Contents Here."></textarea>
	                        </div>
	                        <tr><td><br/></td></tr>
	                        <div class="form-group">
	                            <h1> Card 2 </h1>
	                            <label for="comment">Front Content:</label>
	                            <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Front Contents Here."></textarea>
	                            <label for="comment">Back Content:</label>
	                            <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Back Contents Here."></textarea>
	                        </div>
	                        <tr><td><br/></td></tr>
	                        <div class="form-group">
	                            <h1> Card 3 </h1>
	                            <label for="comment">Front Content:</label>
	                            <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Front Contents Here."></textarea>
	                            <label for="comment">Back Content:</label>
	                            <textarea  class="form-control" rows="5" id="comment" placeholder="Enter Back Contents Here."></textarea>
	                        </div>
	                        <tr><td><br/></td></tr>
	
	                        <div class="container text-right">
	                                <input type="submit" class="btn btn-primary btn_lg" value="submit"></input>
	
	                        </div> 
	                    </form>
	        </div>
	        <tr><td><br/></td></tr>
	        <div class="jumbotron">
	                        <footer class="container-fluid text-right">
	                        </footer>
	         </div>
	    </div>
    </c:if>
    </c:forEach>
    <tr><td><br/></td></tr>
</body>
</html>
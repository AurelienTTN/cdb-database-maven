$(document).submit(function(event) {
    var introduced = $("#introduced").val();
    var discontinued = $("#discontinued").val();
    var name = $("#computerName").val();
    console.log("message "+name);
    
    if((introduced.length>1)&&(discontinued.length>1)){
	    if(new Date(introduced)>new Date (discontinued)){
	    	console.log("date ");
	    	event.preventDefault();
	    	$("#alerteDate").show();
	    }
	 }
	 
	 if(name.startsWith(' ')){
	 	event.preventDefault();
	    $("#alerteName").show();
	 }
	 
});
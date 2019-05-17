/**
 * 
 */
jQuery(document).ready(function($) {
 
	
 
	 $('#one').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"1");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"1");
	 });
	
	$('#two').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"2");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"2");	
	 });
	
	$('#three').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"3");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"3");
	 });
	
	$('#four').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"4");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"4");
	 });
	
	$('#five').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"5");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"5");
	 });
	
	$('#six').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"6");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"6");	
	 });
	
	$('#seven').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"7");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"7");
	 });
	
	$('#eight').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"8");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"8");
	 });
	
	
	$('#nine').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"9");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"9");
	 });
	
	$('#zero').click(function() {
		 $( "#mainValue" ).val($( "#mainValue" ).val()+"0");
		 $( "#mainResult" ).val($( "#mainResult" ).val()+"0");	
	 });
	
	$('#clr').click(function() {
		 $( "#mainValue" ).val("");
		 $( "#mainResult" ).val("");
	 });
	
	$('#result').click(function() {
		var a= $("#mainResult" ).val();
		if (a.length != 0){$( "#formId" ).submit();}else{alert("Please Enter Number")}
		 
	 });
	
	$('#add').click(function() {
		var a= $("#mainResult" ).val();
		 //console.log(a)
if (a.length != 0){
var b=a.slice(-1);
if(b=="+" || b=="-" || b=="*" || b=="/")
{
alert("Please Enter Number after Operator")
}
else{
$( "#mainValue" ).val($( "#mainValue" ).val()+"&&+&&");
$( "#mainResult" ).val($( "#mainResult" ).val()+"+");
}
}
else{
alert("Please Enter Number First")
}
	 });
	
	$('#sub').click(function() {
		var a= $("#mainResult" ).val();
		 console.log(a)
if (a.length != 0){
var b=a.slice(-1);
if(b=="+" || b=="-" || b=="*" || b=="/")
{
	alert("Please Enter Number after Operator")
}
else{
$( "#mainValue" ).val($( "#mainValue" ).val()+"&&-&&");
$( "#mainResult" ).val($( "#mainResult" ).val()+"-");
}
}
else{
	alert("Please Enter Number First")
}
	 });
	
	$('#divide').click(function() {
		var a= $("#mainResult" ).val();
		 console.log(a)
if (a.length != 0){
var b=a.slice(-1);
if(b=="+" || b=="-" || b=="*" || b=="/")
{
	alert("Please Enter Number after Operator")
}
else{
$( "#mainValue" ).val($( "#mainValue" ).val()+"&&/&&");
$( "#mainResult" ).val($( "#mainResult" ).val()+"/");
}
}
else{
	alert("Please Enter Number First")
}
	 });
	
	$('#mul').click(function() {
		var a= $("#mainResult" ).val();
		 console.log(a)
if (a.length != 0){
var b=a.slice(-1);
if(b=="+" || b=="-" || b=="*" || b=="/")
{
	alert("Please Enter Number after Operator.")
}
else{
$( "#mainValue" ).val($( "#mainValue" ).val()+"&&*&&");
$( "#mainResult" ).val($( "#mainResult" ).val()+"*");
}
}
else{
	alert("Please Enter Number First.")
}	 });
	
	$('#goback').click(function() {
		$( "#resFormId" ).submit(); 
	 });
});

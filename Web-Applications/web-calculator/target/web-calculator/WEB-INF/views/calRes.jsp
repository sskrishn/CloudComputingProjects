<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Calculator</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  <link href="css/mainstyle.css" rel="stylesheet" />
  
</head>
<body>

<form action="home" id="resFormId" method="post">
		

<!-- <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Portfolio</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="#">About</a></li>
        <li><a href="#">Gallery</a></li>
        <li><a href="#">Contact</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>
    </div>
  </div>
</nav> -->


  
<div class="container-fluid bg-3 text-center">    
  <div class="row" id=jumb>
     <h1>Welcome to Online Calculator</h1>      
    <p>Start using the calculator. "Clear" button will clear all entered numbers and operations.
    Result Button will give you the final result.</p>
    <p>Note: Calculates result in same order as given. Division returns only Integer part. (Floor Division)  </p>
  
</div>
  
    <div class="row">
		<div class="col-sm-3">
		</div>
		<div class="col-sm-7">
  <div class="panel-group" style="
    box-shadow: 0 0 19px 15px #efdfdf;
">
    <div class="panel panel-primary">
      <div class="panel-heading" style="font-size: x-large;">Result</div>
      <div class="panel-body" style=" padding: 3%!important;">
         
     
    
      
      
      <div class="row"><h3> The result for ${calEquation}  : <span id="rs">${calResult}</span></h3></div>
      <div class="row"><button id="goback" class="btn">Go Back</button></div>
      </div>
  </div></div>
  
		</div>
		<div class="col-sm-2"></div>
		</div>
  
</div><br>

<br><br>

<footer class="container-fluid text-center">
  <p>Thanks for visiting -sskrishn.</p>
</footer>

<!-- <input type="text" name="calEquation" hidden id="mainValue"> -->
</form>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
  
</html>

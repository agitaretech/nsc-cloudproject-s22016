<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<script src= "http://ajax.googleapis.com/ajax/libs/angularjs/1.3.16/angular.min.js"></script> 
<script src="myjs.js"></script>


</head>

<body>


<div ng-app="myApp">

    <form ng-controller="FormCtrl" ng-submit="submitForm()">
    	File Name: <input type="text" ng-model="form.fileName">   <br /> <br />
        Tag1:<input type="text" ng-model="form.tag1">    
        Tag2:<input type="text" ng-model="form.tag2"> 
        Tag2:<input type="text" ng-model="form.tag3"> <br/><br/>
      
            
        <input type="submit" ngClick="Submit" >
    </form>

</div>



</body>
</html>

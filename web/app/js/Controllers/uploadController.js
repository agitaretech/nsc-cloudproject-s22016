	app.controller('uploadController', 





 function($scope,  $http, $window,$rootScope) {
	
	// console.log($rootScope.twitter.oauth_token);
	//	console.log($rootScope.twitter.oauth_token_secret); 
		  var uploadedCount = 0;
      $scope.files = [];
      $scope.file = {};
      $scope.uploadFiles = function() {
        var files = angular.copy($scope.files);
        if ($scope.file) {
          files.push($scope.file);
        }
        if (files.length === 0) {
          $window.alert('Please select files!');
          return false;
        }
        for (var i = files.length - 1; i >= 0; i--) { 
          var file = files[i];
		  console.log(file.base64);
		  var data=file.base64;
		var name=file.filename;
		  
		  
		   $http.post("http://ad440api.cloudapp.net/uploadImage", data,
		   {
	                method: 'post',  headers:{
									'username':$rootScope.username,
                                    'token': $rootScope.twitter.oauth_token,
                                    'secret':$rootScope.twitter.oauth_token_secret,
					                'tags':'',
					                'filename':name					                  
                         },
                url:' http://ad440api.cloudapp.net/uploadImage'
			
                   }
		   ).success(function(data) {
           console.log(data).error(function(response){console.log(response)});
        })
		  
//		 <!-- 	$http({
//	                method: 'post',  headers:{
//									'username':$rootScope.user_name,
//                                    'token': $rootScope.twitter.oauth_token,
//                                    'secret':$rootScope.twitter.oauth_token_secret,
//					                'tags':'sfjhsd,sdfsdf,sdfsdf',
//					                'filename':'test'					                  
//                         },
//                url:' http://ad440api.cloudapp.net/uploadImage'
//			
//                   }).success(function(data) {
//			                         console.log(data);
//                           }).error(function(response){console.log(response);});
//		  -->
//		  
		  
          //$http.post('server.php', file)
//          .success(function(res){
//            uploadedCount ++;
//            if (uploadedCount == files.length) {
//              $window.alert('View uploaded files?');
//              $window.location.assign('/uploads');
//            }
//          });
        }
      };  
		

/*
$scope.add = function(){
  var f = document.getElementById('file').files[0],
      r = new FileReader();
var string=$scope.myfile.base64;
console.log($scope.myfile);	  
  r.onloadend = function(e){
    var data = e.target.result;
    console.log($scope.myfile.base64);
	$http({
	method: 'post',  headers:{
				
					'username':$rootScope.user_name,
                    'token': $rootScope.twitter.oauth_token,
                    'secret':$rootScope.twitter.oauth_token_secret,
					'tags':'sfjhsd,sdfsdf,sdfsdf',
					'filename':'test'
					 
                        
                         },
                url:' http://ad440api.cloudapp.net/uploadImage',
				data:{string}
 }).success(function(data) {
			console.log(data);
}).error(function(response){console.log(response);});
	
	
	
	//send you binary data via $http or $resource or do anything else with it
  }
  r.readAsBinaryString(f);
}


*/


  });

//app.controller('connectController', function ($scope,
//                                $rootScope, $location) {
//	OAuth.initialize("0KqrS3BMyLqPKxXOPhlH3tY2Z7U");
//	$scope.connect = function() {
//		
//	OAuth.popup('twitter', function(err, res) {
//   res.get('/1.1/account/verify_credentials.json').done(function(data) {
//       console.log(data)
//       alert('Hello ' + data.name)
//   })
//});
//          
//	}
//});


app.controller('connectController', function ($scope,$http,
                                $rootScope, $location,config) {
		      OAuth.initialize("0KqrS3BMyLqPKxXOPhlH3tY2Z7U");
	          $scope.connect = function() {
                        OAuth.popup("twitter", function(err, res) {
				 		  if (err) return alert(err);
						   $rootScope.twitter = res;
                            
                             $location.path('/results');
                             $scope.$apply();
				             }).done(function(result){
			
result.get("/1.1/account/verify_credentials.json").done(function(data) {
     console.log(data.screen_name);
	  config.appName= data.screen_name;

});
								  
			                     // console.log(result);
								 // console.log(result.oauth_token);
								 // console.log(result.oauth_token_secret);
								
								 config.appToken=result.oauth_token;
                               config.appSecret=result.oauth_token_secret;
			
							 });
			                       // console.log(config.appName);
								//  console.log( config.appToken);
								//  console.log(config.appSecret);
								  
                            
							
							
	         };
			 
			 
		//	 //var req = {
//                    method: 'GET',
//                    url: 'http://ad440api.cloudapp.net/getImages',
//                    headers: {
//                        'token': result.oauth_token,
//                        'secret':result.oauth_token_secret,
//                        'username':name
//                               }
//                      };
//			
//			});
			 //$http(req).then(function(data){console.log(data);});
});
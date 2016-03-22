app.controller('connectController', function ($scope,$rootScope, $location,ImageApi) {
		OAuth.initialize("0KqrS3BMyLqPKxXOPhlH3tY2Z7U");
	
		
	    $scope.connect = function() {
        OAuth.popup("twitter", function(err, res) {
                if (err) return alert(err);
				
				$rootScope.secret=res.oauth_token;
				$rootScope.token=res.oauth_token_secret;
				
				
						res.me().done(function(me) {
 										alert('You are so logged in!');
										console.log("connect");
										$rootScope.username=me.alias;
										$rootScope.twitter = res;
										ImageApi.getImages().success(function(data) {
	    //console.log(data.request.imgs);
		$rootScope.posts=data.request.imgs;
		
	    $location.path('/results');
       // $scope.$apply();    
    
		
	});;
                                        
										
										
										}).fail(function(err) {
 											 //todo when the OAuth flow failed
										});
	
				
				
				
                
	    });
		
	}
	

});
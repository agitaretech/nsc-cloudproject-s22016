app.controller('connectController', function ($scope,$rootScope, $location) {
		OAuth.initialize("0KqrS3BMyLqPKxXOPhlH3tY2Z7U");
	
		
	    $scope.connect = function() {
        OAuth.popup("twitter", function(err, res) {
                if (err) return alert(err);
				
				$rootScope.secret=res.oauth_token;
				$rootScope.token=res.oauth_token_secret;
				
				
						res.me().done(function(me) {
 										alert('Hello ' + me.alias);
										console.log("connect");
										$rootScope.username=me.alias;
										$rootScope.twitter = res;
                                        $location.path('/results');
                                        $scope.$apply();
										
										
										}).fail(function(err) {
 											 //todo when the OAuth flow failed
										});
	
				
				
				
                
	    });
		
	}
	

});
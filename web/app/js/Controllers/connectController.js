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


app.controller('connectController', function ($scope,
                                $rootScope, $location) {
		OAuth.initialize("0KqrS3BMyLqPKxXOPhlH3tY2Z7U");
	$scope.connect = function() {
             OAuth.popup("twitter", function(err, res) {
				 

				 
                if (err) return alert(err);
                $rootScope.twitter = res;
                $location.path('/results');
                $scope.$apply();
				
				
	    });
	}
});
app.controller('uploadController', function ($scope,
                                $rootScope, $location) {
		OAuth.initialize("0KqrS3BMyLqPKxXOPhlH3tY2Z7U");
	$scope.connect = function() {
             OAuth.popup("twitter", function(err, res) {
				 
                 
				 
                if (err) return alert(err);
                $rootScope.twitter = res;
                $location.path('/upload');
                $scope.$apply();
				
				
	    });
	}
});
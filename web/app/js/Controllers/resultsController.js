
// app.controller('timelineController', function ($scope,
//                                         $rootScope) {
//  $rootScope.twitter.get('/1.1/statuses/home_timeline.json')
//  .done(function(data) {
//     $scope.tw_timeline = data;
//     $scope.$apply();
//  });
//});

app.controller('resultsController', 





 function($scope,  $http,$rootScope) {
	
	 
		 
	
			$http({
                method: 'GET',
                headers:{
				
					'username':'mobiletestgrou1',
                        'token': $rootScope.twitter.oauth_token,
                        'secret':$rootScope.twitter.oauth_token_secret
                        
                         },
                url: 'http://ad440api.cloudapp.net/getImages'
                }).then(function successCallback(response) {
                                  console.log(response);
                    }, function errorCallback(response) {
 
                       });	 
	 

$http({method: 'GET', url: 'postsproto.json'}).success(function(data) {
$scope.posts = data;
$scope.pickedImage = {
        url: ""
    };

   $scope.getPicked = function () {

        return $scope.picked;
    };

});



  }).directive('results', function() {
  return {
	  restrict: 'E',
        replace: true,
    templateUrl: 'js/Directives/results/results.html'
  };
});


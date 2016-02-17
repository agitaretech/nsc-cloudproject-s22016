

app.controller('resultsController', 

 function($scope, $http) {
	 
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
    templateUrl: 'js/directives/results.html'
  };
});



app.controller('searchController', 
function($scope,  $http,$rootScope,$location,ImageApi) {
	
	$scope.searchTags=function(data){
		
		ImageApi.searchImages(data).success(function(data) {
	    console.log(data.request.imgs);
		$scope.posts=data.request.imgs;
	   $scope.pickedImage = {url: "",tags:[],newTags:""};
$scope.getPicked = function () {
			return $scope.picked;
			};

	//$location.path('/results');
                             //    $scope.$apply();   
    
		
	});
		
		
		};
	
}).directive('results', function() {
			return {
					restrict: 'E',
					replace: true,
				    templateUrl: 'js/Directives/results/results.html'
};
});

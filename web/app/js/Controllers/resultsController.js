app.controller('resultsController', 
function($scope,  $http,$rootScope,ImageApi) {
//console.log($rootScope.twitter.oauth_token);
//console.log($rootScope.twitter.oauth_token_secret); 

$scope.delete = function(url) {
	console.log(url);
	ImageApi.deleteImage(url);
	$scope.refresh();
	};
//$rootScope.prev='true';
//console.log(function(){ImageApi.GetImages()});
$scope.updateTags=function(val,blob){
	

$http({
	method: 'PUT',  
	headers:{
		'token': $rootScope.twitter.oauth_token,
		'secret':$rootScope.twitter.oauth_token_secret,
		'blobURL':blob,
		'tags':val
		},
	url: 'http://ad440api.cloudapp.net/updateTags'
	}
	).success(function(data) {
	
	});

	console.log(val)
	$scope.refresh();

	
	
	};
//console.log(ImageApi.GetImages().request.imgs);
//$scope.pickedImage = {url: ""};
//$scope.getPicked = function () {return $scope.picked;};
$scope.refresh=function(){
	$http({
	method: 'GET',  
	headers:{
		'username':$rootScope.username,
		'token': $rootScope.twitter.oauth_token,
		'secret':$rootScope.twitter.oauth_token_secret
		},
	url: 'http://ad440api.cloudapp.net/getImages'
	}
	).success(function(data) {
	//console.log(data.request.imgs);	
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
	});
	
	
	
	};
$http({
	method: 'GET',  
	headers:{
		'username':$rootScope.username,
		'token': $rootScope.twitter.oauth_token,
		'secret':$rootScope.twitter.oauth_token_secret
		},
	url: 'http://ad440api.cloudapp.net/getImages'
	}
	).success(function(data) {
	console.log(data.request.imgs);	
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: "",tags:[],newTags:""};
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


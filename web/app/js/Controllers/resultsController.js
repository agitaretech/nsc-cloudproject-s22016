app.controller('resultsController', 
function($scope,  $http,$rootScope,ImageApi) {


$scope.delete = function(url) {
	console.log(url);
	ImageApi.deleteImage(url);
	$scope.refresh();
	};



$scope.next=function(tags){
	if($rootScope.posts[19]){
			if(!tags)
		ImageApi.getNext($rootScope.posts[19].photo_id).success(function(data) {
			$rootScope.posts=data.request.imgs;
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
			
			});
	else
ImageApi.getNextTags(tags,$rootScope.posts[19].photo_id).success(function(data) {
			$rootScope.posts=data.request.imgs;
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
			
			});
	};
	};

$scope.prev=function(tags){
	if($rootScope.posts[0]){
		if(!tags)
		ImageApi.getPrev($rootScope.posts[0].photo_id).success(function(data) {
			$rootScope.posts=data.request.imgs;
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
			
			});
	else
ImageApi.getPrevTags(tags,$rootScope.posts[0].photo_id).success(function(data) {
			$rootScope.posts=data.request.imgs;
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
			
			});
	};
	
	};

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
	$scope.refresh();
	});


	
	};

$scope.populate=function(){ImageApi.getImages().success(function(data) {
	console.log(data.request.imgs);	
	
	$rootScope.posts=data.request.imgs;
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
	});
};
$scope.populateSearch=function(tags){
	ImageApi.searchImages(tags).success(function(data) {
	console.log(data.request.imgs);	
	
	$rootScope.posts=data.request.imgs;
		
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
	});		
	
	
	};

$scope.refreshTags=function(tags){
	if(tags!="")$scope.populateSearch(tags);
	else{
$scope.populate();
		
		
		
		};
	
	

};
	










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
	$rootScope.posts=data.request.imgs;
		$scope.posts = data.request.imgs;
		$scope.pickedImage = {url: ""};
		$scope.getPicked = function () {return $scope.picked;};
	});
	
	
	
	};

//$scope.$watch(function () { return ImageApi.getPosts(); },
//   function (value) {
//       $scope.posts = value;
//   }
//);

$scope.pickedImage = {url: "",tags:[],newTags:""};
$scope.getPicked = function () {
			return $scope.picked;
			};
//$scope.posts=$rootScope.posts;

}).directive('results', function() {
			return {
					restrict: 'E',
					replace: true,
				    templateUrl: 'js/Directives/results/results.html'
};
});


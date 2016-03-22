  // JavaScript Document
  app.factory('ImageApi', function($http,$rootScope,$location,$q) {
	  var factory = {}; 
	  factory.refresh=function(){
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
											  $scope.posts = data.request.imgs;
											  $scope.pickedImage = {url: ""};
											  $scope.getPicked = function () {return $scope.picked;};
											  });
	 
					  };
  
  
  factory.getPrev=function(id){
  return $http({
  method: 'GET',  
  headers:{
  'username':$rootScope.username,
  'token': $rootScope.twitter.oauth_token,
  'secret':$rootScope.twitter.oauth_token_secret,
   'timestamp':id,
  'prev':'true'
  },
  url: 'http://ad440api.cloudapp.net/getImages'
  }
  );
  
  
  };
	  
  factory.getNext=function(id){
  return $http({
  method: 'GET',  
  headers:{
  'username':$rootScope.username,
  'token': $rootScope.twitter.oauth_token,
  'secret':$rootScope.twitter.oauth_token_secret,
   'timestamp':id,
  'prev':'false'
  
  },
  url: 'http://ad440api.cloudapp.net/getImages'
  }
  );
  
  
  };
  factory.getPrevTags=function(tags,id){
  return $http({
  method: 'GET',  
  headers:{
  'username':$rootScope.username,
  'token': $rootScope.twitter.oauth_token,
  'secret':$rootScope.twitter.oauth_token_secret,
   'timestamp':id,
  'prev':'true',
  'tags':tags
  },
  url: 'http://ad440api.cloudapp.net/getImages'
  }
  );
  
  
  };
	  
  factory.getNextTags=function(tags,id){
  return $http({
  method: 'GET',  
  headers:{
  'username':$rootScope.username,
  'token': $rootScope.twitter.oauth_token,
  'secret':$rootScope.twitter.oauth_token_secret,
  'timestamp':id,
  'prev':'false',
  'tags':tags
  },
  url: 'http://ad440api.cloudapp.net/getImages'
  }
  );
  
  
  };
  
  
  factory.getImages = function() {
  return $http({
  method: 'GET',  
  headers:{
  'username':$rootScope.username,
  'token': $rootScope.twitter.oauth_token,
  'secret':$rootScope.twitter.oauth_token_secret
  },
  url: 'http://ad440api.cloudapp.net/getImages'
  }
  );
  
  
  };
  
  factory.searchImages = function(tags) {
  
  
  return $http({
  method: 'GET',  
  headers:{
  'username':$rootScope.username,
  'token': $rootScope.twitter.oauth_token,
  'secret':$rootScope.twitter.oauth_token_secret,
  'tags':tags
  },
  url: 'http://ad440api.cloudapp.net/getImages'
  }
  );
  
  
  };
  
  factory.deleteImage = function(url) {
  
  $http({
	   method: 'DELETE',  
				   headers:{
					   
					   'token': $rootScope.twitter.oauth_token,
					   'secret':$rootScope.twitter.oauth_token_secret,
					   'blobUrl':url
  },
  url: 'http://ad440api.cloudapp.net/deleteImage'
  }
  ).success(function(data) {
  
  factory.refresh();
  
  
  });
  
  };
  
  
  
  return factory;
  });
// JavaScript Document

var app = angular.module('app', ['ngRoute','naif.base64']);

 

app.factory('ImageApi', function($http,$rootScope,$location,$q) {
     
    var factory = {}; 

 //console.log($rootScope.username);
 //console.log($rootScope.token);
 //in your factory
 
//return {
//   saveCustomer: function(data) {
//       var request = $http({...});
//
//       return request;
//   }
//}
//
////in your controller
//authFactor
//  .saveCustomer(data)
//  .success(function() {
//    //update controller here
//  })
 
    factory.getImages = function() {
		var deferred=$q.defer();
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
	deferred.resolve(data.request.imgs);	
//		$rootScope.posts = data.request.imgs;
		
	});
			
			
     return deferred.promise;
	
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
        
	
		
		
	});


		   
		   
        };
    factory.updateTags = function() {
            //..
        };
    factory.uploadImage = function() {
            //..
        };
    return factory;
});
app.config(['$httpProvider', function ($httpProvider) {
	$httpProvider.defaults.useXDomain = true;
     delete $httpProvider.defaults.headers.common['X-Requested-With'];
     delete $httpProvider.defaults.headers.post['Content-type'];
	delete $httpProvider.defaults.headers.post['Cache-Control'];
}]);

app.config(function($routeProvider) {
	
	$routeProvider.when('/timeline', {
	    templateUrl: '/js/Directives/timeline.html',
	    controller: 'timelineController'
	}).when('/connect', {
	    templateUrl: '/js/Directives/connect.html',
	    controller: 'connectController'
	}).when('/results', {
	    templateUrl: '/js/Directives/results/results.html',
	    controller: 'resultsController'
	}).when('/upload', {
	    templateUrl: '/js/Directives/upload.html',
	    controller: 'uploadController'	
	}).otherwise({
            redirectTo: '/connect'
	});
});


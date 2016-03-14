// JavaScript Document
//var app = angular.module('app', ['ngRoute']);
//var app=angular.module('app', ['ngRoute']);
//Example with Twitter with the cache option enabled



var app = angular.module('app', ['ngRoute']);

app.config(function($routeProvider) {
	$routeProvider.when('/timeline', {
	    templateUrl: '/js/Directives/timeline.html',
	    controller: 'timelineController'
	}).when('/connect', {
	    templateUrl: '/js/Directives/connect.html',
	    controller: 'connectController'
	}).when('/results', {
	    templateUrl: '/js/Directives/results/results.html',
	    controller: 'resultsController'}
	).otherwise({
            redirectTo: '/connect'
	});
});

    // do some stuff with result
//});
//var aoth=angular.module('app.authenticate', []);
//app.config(function($routeProvider){
//	$routeProvider
//	// route for the home page
//            .when('/', {
//                templateUrl : 'index.html',
//                controller  : 'resultsController'
//            })
//	   .when('/upload', {
//                templateUrl : 'angular_spa.html',
//                controller  : 'uploadController'
//            })
//			   
//	
//	
//	
//	
//	});

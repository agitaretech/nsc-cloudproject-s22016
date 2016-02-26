
//var ngResource = require('ng-resource');
var app = angular.module('app', ['ngRoute']);
var module=angular.module('module', []);
app.config(function($routeProvider){
	$routeProvider
	// route for the home page
            .when('/', {
                templateUrl : 'index.html',
                controller  : 'resultsController'
            })
	   .when('/upload', {
                templateUrl : 'angular_spa.html',
                controller  : 'uploadController'
            })
			   .when('/gallery', {
                templateUrl : 'index.html',
                controller  : 'resultsController'
            });
	
	
	
	
	});


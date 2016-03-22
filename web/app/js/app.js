// JavaScript Document

var app = angular.module('app', ['ngRoute','naif.base64']);


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


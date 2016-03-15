var app = angular.module('myApp', []);

app.controller('FormCtrl', function ($scope, $http) {

var formData = {
	alltags: "default",
  };


$scope.submitForm = function() {

    $http({

        url: "myform.php",
        data: $scope.form,
        method: 'POST',
        headers : {'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'}

    }).success(function(data){

        console.log("OK", data)

    }).error(function(err){"ERR", console.log(err)})
};

});
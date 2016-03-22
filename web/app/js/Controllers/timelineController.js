app.controller('timelineController', function ($scope,
                                         $rootScope) {
  $rootScope.twitter.get('/1.1/statuses/home_timeline.json')
  .done(function(data) {
     $scope.tw_timeline = data;
     $scope.$apply();
  });
});
angular.module('oserion-admin')

    .controller('loginCtrl', function($scope, $route, $routeParams, $location) {
        $scope.$route = $route;
        $scope.$location = $location;
        $scope.$routeParams = $routeParams;
        console.log("loginCtrlk");
    });
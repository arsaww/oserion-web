'use strict';
var app = angular.module('oserion-admin', ['ngRoute','ngCookies','ngFileUpload'])

    .config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/login', {
                    templateUrl: 'views/login.html',
                    controller: function(){}
                }).
                when('/main', {
                    templateUrl: 'views/main.html',
                    controller: function($scope, Upload) {
                        $scope.path="";
                        // upload later on form submit or something similar

                    }
                }).
                otherwise({
                    redirectTo: '/login'
                });

        }]);
window.location.href = "#/login";


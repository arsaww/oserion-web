'use strict';
var app = angular.module('oserion-admin', ['ngRoute','ngCookies'])

    .config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/login', {
                    templateUrl: 'views/login.html',
                    controller: function(){}
                }).
                when('/main', {
                    templateUrl: 'views/main.html'
                }).
                otherwise({
                    redirectTo: '/login'
                });

        }]);
window.location.href = "#/login";


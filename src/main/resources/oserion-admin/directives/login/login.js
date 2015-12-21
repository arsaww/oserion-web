'use strict';
app.directive('login', function () {
    return {
        restrict: 'AE',
        controller: function ($scope, $cookies, $http) {
            $scope.model = {};
            var redirect = function(){
                var access = $cookies.get('osr-access');
                if(access == 'ADMIN' || access == "SUPERADMIN"){
                    window.location.href = "#/main";
                }
            };

            $scope.authentication = function(){
                var req = {
                    method: 'POST',
                    url: '/oserion/services/login',
                    headers: {'Content-Type': 'application/json'},
                    data: {login: $scope.model.login, password: $scope.model.password}
                };

                $http(req).then(function(data, status, headers, config) {
                    window.location.reload();
                },function(data, status, headers, config) {
                    $scope.model.login = "";
                    $scope.model.password = "";
                    $scope.model.error = "Authentication failure.";
                });
            };
            redirect();
        },
        link: function(scope, element) {
            element.bind("keypress", function(event) {
                var keyCode = event.which || event.keyCode;
                if (keyCode == 13) {
                    scope.authentication();
                }
            });
        },
        template: '<div class="login container">' +
            '<div class="header">Authentication required</div>' +
            '<div class="content">' +
                '<div class="error">{{model.error}}</div>' +
                '<input type="text" ng-model="model.login" placeholder="Username" />' +
                '<input type="password" ng-model="model.password" placeholder="Password" />' +
            '</div>' +
            '<div class="footer">' +
                '<button class="button" id="submitBtn" ng-click="authentication()">Login</button>' +
                '<hr />' +
            '</div>' +
        '</div>'
    };
});


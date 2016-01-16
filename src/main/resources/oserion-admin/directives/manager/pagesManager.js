'use strict';
app.directive('pagesManager', function () {
    return {
        restrict: 'AE',
        scope: {},
        controller: function ($scope, $compile, $http, Upload, modalService) {
            $scope.model = {page: 'ressources', li: '1'};
            $scope.getTemplates = function () {
                var req = {
                    method: 'GET',
                    url: "/oserion/templates",
                    headers: {'Content-Type': 'application/json'}
                };

                $http(req).then(function (resp) {
                    $scope.model.templates = resp.data;
                }, function (resp) {
                    console.log(resp.status);
                    if (resp.status == 401) {
                        window.document.location.href = "/admin";
                    } else {
                        $scope.model.error = "Error " + resp.status + " : impossible to retrieve templates";
                    }
                });
            };
            var init = function () {
                $scope.getTemplates();
            };
            init();
        },
        link: function (scope, element) {
        },
        template: '<div class="header">' +
        '<div class="inner-nav">' +
        '<ul>' +
        '<li ng-click="model.page = \'ressources\'; model.li=1" ng-class="{\'current\':model.li==1}">Ressources</li>' +
        '<li ng-click="model.page = \'templates\'; model.li=2" ng-class="{\'current\':model.li==2}">Templates</li>' +
        '</ul>' +
        '</div>' +
        '</div>' +
        '<div class="content" style="min-height: 320px; height: auto;">' +
        '<div style="position:relative;min-width: 500px;float: left;" resources-manager ng-show="model.page == \'ressources\'" ></div>' +
        '<div style="position:relative;min-width: 500px;float: left;" templates-manager ng-show="model.page == \'templates\'"></div>' +
        '</div>'
    };
});


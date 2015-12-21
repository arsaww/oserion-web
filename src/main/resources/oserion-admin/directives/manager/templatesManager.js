'use strict';
app.directive('templatesManager', function () {
    return {
    restrict: 'AE',
    scope:{},
        controller: function ($scope, $compile, $http, Upload) {
            $scope.model = {showProgress:false};
            $scope.submit = function() {
                //if (form.file.$valid && $scope.file) {
                $scope.model.error = "";
                $scope.model.message = "";
                $scope.model.progress = 0;
                $scope.upload($scope.model.file);
                //}
            };
            $scope.$watch("model.file", function(newValue, oldValue) {
                if(newValue){
                    $scope.submit();
                }
            });
            // upload on file select or drop
            $scope.upload = function (file) {
                $scope.model.showProgress = true;
                Upload.upload({
                    url: '/oserion/upload/templates',
                    data: {file: file}
                }).then(function (resp) {
                    $scope.model.file = null;
                    $scope.model.showProgress = false;
                    $scope.model.message = "Success : "+resp.config.data.file.name+" is now in the Database as a template.";
                }, function (resp) {
                    $scope.model.file = null;
                    $scope.model.showProgress = false;
                    $scope.model.error = "Error "+resp.status+ " : upload failure.";
                    if(resp.status === 401) window.location.href = "#/login";
                }, function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    $scope.model.progress = progressPercentage;
                });
            };
        },
        link: function(scope, element) {},
        template:
        '<form>' +
            '<div class="inner container drop-box" ngf-drop ng-model="model.file">' +
                '<div class="header">' +
                    '<span>Template Manager</span>' +
                    '<span class="progress" ng-show="model.showProgress">{{model.progress}}%</span>' +
                '</div>' +
                '<div class="content vertical-small" >' +
                    '<div class="error" ng-show="model.error && model.error.length > 0" ng-click="model.error = \'\'">' +
                        '{{model.error}}' +
                    '</div>' +
                    '<div class="success" ng-show="!model.error || model.error.length < 1" ng-click="model.message = \'\'">' +
                        '{{model.message}}' +
                    '</div>' +
                    '<div ng-repeat="d in model.current.directories">' +
                        'Drag & Drop here, the HTML file you want to use as a template.<br />It must have an "UTF-8" encoding' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</form>'
    };
});


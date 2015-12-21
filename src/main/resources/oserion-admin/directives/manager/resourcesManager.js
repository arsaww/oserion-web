'use strict';
app.directive('resourcesManager', function () {
    return {
    restrict: 'AE',
    scope:{},
        controller: function ($scope, $compile, $http, Upload,modalService) {

            $scope.model = {current:{}, showProgress:false};
            $scope.edit = function(fileName){
                modalService.setParameters('PUT','/oserion/upload/resources',
                    {value:fileName,origin:fileName,path:$scope.model.current.path},
                    "Rename resource Window",
                    "Write a new name for the following element:",
                    true);
                modalService.show();
            };
            $scope.delete = function(fileName){
                modalService.setParameters('DELETE','/oserion/upload/resources',
                    {value:fileName,path:$scope.model.current.path},
                    "Delete resource Window",
                    "The following resource: \""+fileName+"\" will be delete, are you sure?",
                    false);
                modalService.show();
            };
            $scope.getRessources = function(path){
                if(path.length > 0) {
                    var req = {
                        method: 'GET',
                        url: "/oserion/upload" + path,
                        headers: {'Content-Type': 'application/json'},
                    };

                    $http(req).then(function (resp) {
                        $scope.model.current = resp.data;
                    }, function (resp) {
                        console.log(resp.status);
                        if (resp.status == 401) {
                            window.document.location.href = "/admin";
                        } else {
                            $scope.model.error = "Error "+resp.status+ " : impossible to retrieve resources";
                        }
                    });
                }
            };
            $scope.submit = function() {
                //if (form.file.$valid && $scope.file) {
                $scope.model.error = "";
                $scope.model.message = "";
                $scope.model.progress = 0;
                $scope.upload($scope.model.file,$scope.model.current.path);
                //}
            };
            $scope.$watch("model.file", function(newValue, oldValue) {
                if(newValue){
                    $scope.submit();
                }
            });
            // upload on file select or drop
            $scope.upload = function (file, path) {
                $scope.model.showProgress = true;
                Upload.upload({
                    url: '/oserion/upload/resources',
                    data: {file: file, path: path}
                }).then(function (resp) {
                    console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
                    $scope.model.file = null;
                    $scope.model.showProgress = false;
                    $scope.model.message = "Success : "+resp.config.data.file.name+" uploaded.";
                    $scope.getRessources($scope.model.current.path);
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
            var init = function(){
                $scope.getRessources("/resources");
            };
            init();
        },
        link: function(scope, element) {},
        template:
        '<form>' +
        '<div class="inner container drop-box" ngf-drop ng-model="model.file">' +
            '<div class="header">' +
                '<span resources-path-link path="model.current.path"></span>' +
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
                    '<span class="link" ng-click="getRessources(d.path)">{{d.name}}</span>' +
                    '<div class="ressources-corner-link">' +
                        '<i class="fa fa-times" ng-click="delete(d.name)"></i>' +
                        '<i class="fa fa-pencil-square-o" ng-click="edit(d.name)"></i>' +
                        '<hr />' +
                    '</div>' +
                '</div>' +
                '<div ng-repeat="f in model.current.files">{{f}}' +
                    '<div class="ressources-corner-link">' +
                        '<i class="fa fa-times" ng-click="delete(f)"></i>' +
                        '<i class="fa fa-pencil-square-o" ng-click="edit(f)"></i>' +
                        '<hr />' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</div>' +
        '</form>'
    };
});


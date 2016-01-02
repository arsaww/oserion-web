'use strict';
app.directive('templatesManager', function () {
    return {
    restrict: 'AE',
    scope:{},
        controller: function ($scope, $compile, $http, Upload, modalService) {
            $scope.model = {showProgress:false,templates:[],
                message:'Drag & Drop here, the HTML file "UTF-8" encoded.'};
            $scope.submit = function() {
                //if (form.file.$valid && $scope.file) {
                $scope.model.error = "";
                $scope.model.message = "";
                $scope.model.progress = 0;
                $scope.model.templates = [];
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
                    url: '/oserion/templates/upload',
                    data: {file: file}
                }).then(function (resp) {
                    $scope.model.file = null;
                    $scope.model.showProgress = false;
                    $scope.model.message = "Success : "+resp.config.data.file.name+" is now in the Database as a template.";
                    $scope.getTemplates();
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
            $scope.getTemplates = function(){
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
                        $scope.model.error = "Error "+resp.status+ " : impossible to retrieve templates";
                    }
                });
            };
            var callbackModal = function(msg){
                $scope.getTemplates();
                if(typeof msg !== "undefined" && msg.length > 0)
                    $scope.model.message = msg;
            };
            $scope.model = {current:{}, showProgress:false};
            $scope.addPage = function(templateName){
                modalService.setParameters('POST','/oserion/templates/addPage',
                    {value:"/.html",template:templateName},
                    "Page creation Window",
                    "Write the url of the new page associated to the template '" + templateName + "':",
                    true,
                    callbackModal);
                modalService.show();
            };
            var init = function(){
                $scope.getTemplates();
            };
            init();
        },
        link: function(scope, element) {},
        template:
        '<form style="margin:10px;">' +
            '<div class="inner container drop-box" ngf-drop ng-model="model.file">' +
                '<div class="header">' +
                    '<span>Templates:</span>' +
                    '<span class="progress" ng-show="model.showProgress">{{model.progress}}%</span>' +
                '</div>' +
                '<div class="content vertical-small" >' +
                    '<div class="error" ng-show="model.error && model.error.length > 0" ng-click="model.error = \'\'">' +
                        '{{model.error}}' +
                    '</div>' +
                    '<div class="success" ng-show="!model.error || model.error.length < 1" ng-click="model.message = \'\'">' +
                        '{{model.message}}' +
                    '</div>' +
                    '<div ng-repeat="t in model.templates">' +
                        '<span class="link" ng-click="t.show = !t.show">{{t.name}}</span>' +
                        '<div class="ressources-corner-link">' +
                            '<i class="fa fa-times" ng-click="deleteTemplate(t.name)"></i>' +
                            '<i class="fa fa-pencil-square-o" ng-click="editTempate(t.name)"></i>' +
                            '<i class="fa fa-plus" ng-click="addPage(t.name)"></i>' +
                            '<hr />' +
                        '</div>' +
                        '<div ng-repeat="page in t.listPage" class="no-border" ng-show="t.show"> - {{page.key}}:{{page.url}}</div>' +
                    '</div>' +
                '</div>' +
            '</div>' +
        '</form>'
    };
});


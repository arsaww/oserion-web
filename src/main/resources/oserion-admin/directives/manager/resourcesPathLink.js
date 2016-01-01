'use strict';
app.directive('resourcesPathLink', function () {
    return {
    restrict: 'AE',
    scope: {
        path: '='
    },
        controller: function ($scope) {
            $scope.model = {};
            $scope.getRessources = $scope.$parent.getRessources;
            $scope.getNavigationPath = function(folderPath){
                $scope.truncate = false;
                if(folderPath){
                    var dir = [];
                    var directories = folderPath.split("/");
                    if(directories.length > 5)
                        $scope.truncate = true;
                    for(var i = $scope.truncate ? directories.length-3 : 1; i < directories.length; i++){
                        var p = {};
                        if(i < directories.length - 1){
                            var link = "";
                            for(var j = 1; j <= i; j++){
                                link += "/" + directories[j];
                            }
                            p.class = "link";
                            p.link = link;
                            p.name = directories[i];
                        }else{
                            p.class = "";
                            p.link = "";
                            p.name = directories[i];
                        }
                        dir.push(p);
                    }
                    $scope.model.dir = dir;
                }
            };
            $scope.$watch("path", function(newValue, oldValue) {
                $scope.getNavigationPath(newValue);
            });
        },
        link: function(scope, element) {},
        template:
        '<span ng-show="truncate">' +
            '/<span class="link" ng-click="getRessources(\'/resources\');">resources</span>/...' +
        '</span>' +
        '<span ng-repeat="p in model.dir">' +
            '/<span ng-class="p.class" ng-click="getRessources(p.link);">' +
                '{{p.name}}' +
            '</span>' +
        '</span>'
    };
});


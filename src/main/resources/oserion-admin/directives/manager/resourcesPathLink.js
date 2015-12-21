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
                if(folderPath){
                    var dir = [];
                    var directories = folderPath.split("/");
                    for(var i = 1; i < directories.length; i++){
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
        '<span ng-repeat="p in model.dir">' +
            '/<span ng-class="p.class" ng-click="getRessources(p.link);">' +
                '{{p.name}}' +
            '</span>' +
        '</span>' +
        '<div class="ressources-corner-link">' +
            '<i class="fa fa-folder-o"></i><hr />' +
        '</div>'

        /*<button class="button" >Select</button>
        <button class="button" type="submit" ng-click="submit()">submit</button>
        <!--<div class="button" ngf-select ng-model="file" name="file" ngf-pattern="'image/*'"
    ngf-accept="'image/*'" ngf-max-size="20MB" ngf-min-height="100"
    ngf-resize="{width: 100, height: 100}">Select</div>


        </form>-->' +
        '' +
        ''*/
    };
});


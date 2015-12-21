'use strict';
app.factory('modalService', function ($http) {
    var model = {method:"",url:"",data:{},show:false,showInput:false,message:"",error:"",resp:{},title:"",description:""};
    return {
        setParameters : function(method,url,data,title,description,showInput){
            model.method = method;
            model.url = url;
            model.data = data;
            model.title = title;
            model.description = description;
            model.showInput = showInput;
        },
        getParameters : function(){
            return model;
        },
        show : function(){
            model.show = true;
        },
        hide : function(){
            model.showInput=false;model.description="";model.title="";model.method="";model.url="";model.data={};model.show=false;model.message="";model.error="";model.resp={};
        },
        submit : function(){
            var that = this;
            var req = {
                method: model.method,
                url: model.url,
                data: model.data,
                headers: {'Content-Type': 'application/json'}
            };
            $http(req).then(function (resp) {
                model.resp = resp.data;
                that.hide();
            }, function (resp) {
                model.error = "Error "+resp.status+" : an error occured on submit";
            });
        }

    }
});

app.directive('modal', function () {
    return {
        restrict: 'AE',
        scope:{},
        controller: function ($scope, modalService) {
            $scope.service = modalService;
            $scope.model = modalService.getParameters();
        },
        link: function(scope, element) {},
        template: '<div class="modal" ng-show="model.show">' +
            '<div class="container">' +
                '<div class="header">{{model.title}}</div>' +
                '<div class="content">' +
                    '<div class="error">{{model.error}}</div>' +
                    '<label>{{model.description}}</label>' +
                    '<input type="text" ng-model="model.data.value" ng-show="model.showInput" />' +
                '</div>' +
                '<div class="footer">' +
                    '<button class="button" ng-click="service.submit()">Submit</button>' +
                    '<button class="button" ng-click="service.hide()">Cancel</button>' +
                    '<hr />' +
                '</div>' +
            '</div>' +
        '</div>'
    };
});

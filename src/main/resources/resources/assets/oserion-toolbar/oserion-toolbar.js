(function( $ ) {
    /** START **/
    $.fn.oserion = function() {
        importManager.init($(this));
        return this;
    };

    /** IMPORT MANAGER **/
    var importManager = function(){

        //Private Attribute
        var IS_INIT = false;
        var loadScript = function( url, callback ) {
            var script = document.createElement( "script" );
            script.type = "text/javascript";
            if(script.readyState) {  //IE
                script.onreadystatechange = function() {
                    if ( script.readyState === "loaded" || script.readyState === "complete" ) {
                        script.onreadystatechange = null;
                        callback();
                    }
                };
            } else {  //Others
                script.onload = function() {
                    callback();
                };
            }

            script.src = url;
            document.getElementsByTagName( "head" )[0].appendChild( script );
        };

        var NB_SCRIPTS_LOADED = 0;
        var NB_SCRIPTS_TO_LOAD = 3;

        var addToolBarWhenReady = function(e){
            NB_SCRIPTS_LOADED++;
            if(NB_SCRIPTS_LOADED == NB_SCRIPTS_TO_LOAD){
                toolBarManager.init(e);
            }
        };

        //Public
        return {
            init : function(e){
                if(!IS_INIT){
                    IS_INIT = true;
                    $('head').append('<link rel="stylesheet" href="/assets/oserion-toolbar/oserion-toolbar.css" type="text/css" />');
                    loadScript("/assets/oserion-toolbar/oserion-editor.js", function() {addToolBarWhenReady(e);});
                    loadScript("/assets/oserion-toolbar/oserion-constants.js", function() {addToolBarWhenReady(e);});
                    loadScript("/assets/tinymce/tinymce.min.js", function() {addToolBarWhenReady(e);});
                }
            }
        };

    }();

    /** TOOLBAR MANAGER **/
    var toolBarManager = function(){

        //Private Attribute
        var IS_INIT = false;

        var BUTTON_LIST = [];

        //Private Methods
        var addToolbar = function(e){
            e.addClass(OSR_TOOLBAR_CONTAINER_CLASS);
            e.prepend('<div id="'+OSR_TOOLBAR_ID+'"></div>');
            return true;
        };

        var displayButtons = function(e){
            for(var i = 0; i < BUTTON_LIST.length; i ++){
                var b = BUTTON_LIST[i];
                b.init(e);
            }
        };

        var addInfoBlock = function(e){
            e.append('<div id="info_block"></div>');
        };

        var defineButtons = function(){
            //TODO add all new buttons here
            var editButton = {
                info : OSR_EDITABLE_INFO,
                enable : function(){osrEditor.getType(OSR_EDITABLE_ELEMENT_SELECTOR).highlight();},
                disable : function(){document.location.reload();}
            };
            toolBarManager.addButton(Button(editButton));
        };

        //Public
        return {
            init : function(e){
                if(!IS_INIT){
                    IS_INIT = true;
                    if(addToolbar(e)){
                        var f = $("#"+OSR_TOOLBAR_ID);
                        defineButtons();
                        displayButtons(f);
                        addInfoBlock(f);
                        osrEditor.getType(OSR_EDITABLE_ELEMENT_SELECTOR).init(f);
                    }
                }
            },
            addButton : function(b){
                BUTTON_LIST.push(b);
            }
        };
    }();

    /** BUTTON **/
    var Button = function(cb){

        //Private Attribute
        var ENABLED = false;
        var CUSTOM_BUTTON = cb;
        var ELEMENT = null;

        //Private Methods

        //Public
        return {
            init : function(container){
                var b = this;
                var e = $("<div></div>");
                e.addClass("editButton");
                e.click(function(){b.active();});
                e.mouseover(function(){b.showLegend();});
                e.mouseout(function(){b.hideLegend();});
                ELEMENT = e;
                container.append(e);
            },
            enable : function(){
                ENABLED = true;
                ELEMENT.addClass("active");
                CUSTOM_BUTTON.enable();
            },
            disable : function(){
                ENABLED = false;
                ELEMENT.removeClass("active");
                CUSTOM_BUTTON.disable();
            },
            active : function(){
                if(ENABLED)
                    this.disable();
                else
                    this.enable();
            },
            showLegend : function(){
                $(OSR_TOOLBAR_INFO_BLOCK_SELECTOR).html(CUSTOM_BUTTON.info);
            },
            hideLegend : function(){
                $(OSR_TOOLBAR_INFO_BLOCK_SELECTOR).html('');
            },
            isEnabled : function(){
                return ENABLED;
            }
        };
    };


}( jQuery ));
var osrGetCookie = function(name) {
    var match = document.cookie.match(new RegExp(name + '=([^;]+)'));
    if (match) return match[1];
};

$(document).ready(function(){
    var access = osrGetCookie("osr-access");
    if(access == "ADMIN" || access == "SUPERADMIN" )  $( "body" ).oserion();
});

'use strict';

(function( $ ) {
    /** START **/
    $.fn.oserion = function() {
        importManager.init();
        importManager.addToolBar($(this));
        this.filter( "a" ).each(function() {
            var link = $( this );
            link.append( " (" + link.attr( "href" ) + ")" );
        });
        return this;
    };

    /** IMPORT MANAGER **/
    var importManager = function(){

        //Private Attribute
        var IS_INIT = false;

        //Public
        return {
            init : function(){
                if(!IS_INIT){
                    IS_INIT = true;
                    $('head').append('<link rel="stylesheet" href="/assets/oserion-toolbar/oserion-toolbar.css" type="text/css" />');
                    $('head').append('<script src="/assets/tinymce/tinymce.min.js"></script>');

                }
            },
            addToolBar : function(e){
                toolBarManager.init(e);
            }
        };

    }();

    /** TOOLBAR MANAGER **/
    var toolBarManager = function(){

        //Private Attribute
        var IS_INIT = false;
        var TOOLBAR_ID = "oserion_toolbar";
        var TOOLBAR_CONTAINER_CLASS = "oserion_toolbar_container";
        var BUTTON_LIST = [];

        //Private Methods
        var addToolbar = function(e){
            e.addClass(TOOLBAR_CONTAINER_CLASS);
            e.prepend('<div id="'+TOOLBAR_ID+'"></div>');
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

        var addRichTextEditor = function(e){
            setTimeout(function(){
                $('head').append('<script type="text/javascript">tinymce.init({' +
                'selector: "textarea.tinymce",' +
                'plugins: ["advlist autolink lists link image charmap print preview anchor","searchreplace visualblocks code fullscreen","insertdatetime media table contextmenu paste"],' +
                'toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image"' +
                '});</script>');
            },1500);

            e.append('' +
            '<div class="richTextEditor">' +
            '<h1>Edition du contenu</h1>' +
            '<div class="textarea-container">' +
            '   <textarea class="tinymce"></textarea>' +
            '</div>' +
            '<div class="button-container">' +
            '   <div class="button cancel">Annuler</div>' +
            '   <div class="button submit">Valider</div>' +
            '</div>' +
            '</div>');
        };

        //Public
        return {
            init : function(e){
                if(!IS_INIT){
                    IS_INIT = true;
                    if(addToolbar(e)){
                        var f = $("#"+TOOLBAR_ID);
                        displayButtons(f);
                        addInfoBlock(f);
                        addRichTextEditor(f);
                    }

                }
            },
            addButton : function(b){
                BUTTON_LIST.push(b);
            }
        };

    }();

    /** EDITION MANAGER **/
    var editionManager = function(){

        var EDITABLE_ELEMENT_SELECTOR = ".editable";
        var RICH_TEXT_EDITOR_SELECTOR = "#oserion_toolbar .richTextEditor";
        var HIGHLIGHT_ELEMENT_CLASS = "oserion_highlight";

        return {

            highlight : function(){
                $(EDITABLE_ELEMENT_SELECTOR).each(function(){
                    var e =  $(this);
                    e.addClass(HIGHLIGHT_ELEMENT_CLASS);
                    e.click(function(){editionManager.edit(e)});
                })
            },
            edit : function(e){
                $(RICH_TEXT_EDITOR_SELECTOR).show();
                $(EDITABLE_ELEMENT_SELECTOR).each(function(){
                    if(e !=  $(this)){
                        e.removeClass(HIGHLIGHT_ELEMENT_CLASS);
                    }
                });
            }

        };

    }();

    /** WINDOW MANAGER **/

    var windowManager = function(){

        var RICH_TEXT_INIT = false;

        return {
            showRichTextEdition : function(){

                if(!RICH_TEXT_INIT){
                    $()
                }

            },
            hideRichTextEdition : function(){

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
                $("#info_block").html(CUSTOM_BUTTON.info);
            },
            hideLegend : function(){
                $("#info_block").html('');
            },
            isEnabled : function(){
                return ENABLED;
            }
        };
    };
    var editButton = {
        info : "Editer le contenu",
        enable : function(){editionManager.highlight()},
        disable : function(){document.location.reload();}
    };
    toolBarManager.addButton(Button(editButton));

}( jQuery ));
var osrGetCookie = function(name) {
    var match = document.cookie.match(new RegExp(name + '=([^;]+)'));
    if (match) return match[1];
};

$(document).ready(function(){
    var access = osrGetCookie("osr-access");
    if(access == "ADMIN" || access == "SUPERADMIN" )
    $( "body" ).oserion();
});

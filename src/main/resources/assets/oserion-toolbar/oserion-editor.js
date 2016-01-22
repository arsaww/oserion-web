var osrEditor = function(){

    var linkDisabler = function(){
        $("a").each(function() {
            var link = $( this );
            link.attr("trueLink",link.attr( "href" ));
            link.attr("href","javascript:void(0)");
        });
    };

    /*****************************************************************
     ********************* EDITABLE MANAGER **************************
     *****************************************************************/

    var editableManager = function(){
        var editableElement;
        var addRichTextEditor = function(e){
            $('head').append('<script type="text/javascript">tinymce.init({' +
                'height : "100",' +
                'selector: "textarea.tinymce",' +
                'plugins: ["advlist autolink lists link image charmap print preview anchor","searchreplace visualblocks code fullscreen","insertdatetime media table contextmenu paste", "textcolor"],' +
                'toolbar: "insertfile undo redo | fontsizeselect | forecolor backcolor | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist | link image",' +
                'forced_root_block : "",'+
                'force_br_newlines : true,'+
                'force_p_newlines : false,' +
                'body_class : "'+OSR_EDITOR_CONTENT_CLASS+'",'+
                '});</script>');

            e.append('' +
                '<div class="richTextEditor">' +
                '<h1>Edition du contenu</h1>' +
                '<div class="textarea-container">' +
                '   <textarea class="tinymce"></textarea>' +
                '</div>' +
                '<div class="oserion-button-container">' +
                '   <div class="button submit">Save changes</div>' +
                '   <div class="button preview">Preview</div>' +
                '   <div class="button cancel">Cancel changes</div>' +
                '</div>' +
                '</div>');
        };

        var cancelChanges = function(){
            document.location.reload();
        };
        var preview = function(){
            editableElement.html(tinyMCE.activeEditor.getContent());
            $(OSR_TOOLBAR_INFO_BLOCK_SELECTOR).html(OSR_EDITABLE_OVERVIEW);
            $(OSR_RICH_TEXT_EDITOR_SELECTOR).hide();
            setTimeout(function(){
                $(OSR_RICH_TEXT_EDITOR_SELECTOR).show();
                $(OSR_TOOLBAR_INFO_BLOCK_SELECTOR).html("");
            },OSR_OVERVIEW_TIMER);
        };
        var submit = function(){
            var req = $.ajax({
                type: "POST",
                url: "/oserion/services/content/"+OSR_EDITABLE_TYPE+"/"+editableElement.attr("id"),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                data: JSON.stringify({ref:editableElement.attr("id"), type: OSR_EDITABLE_TYPE, value: tinyMCE.activeEditor.getContent()}),
                success: function(){document.location.reload();}
            });
            req.always(function(r) {
                if(r.status != 200){
                    var m = jQuery.parseJSON(r.responseText);
                    alert(m.status + " : " + m.message );
                }else{
                    document.location.reload();
                }
            });
        };
        return {
            init : function(e){
                addRichTextEditor(e);
            },
            highlight : function(){
                linkDisabler();
                $(OSR_EDITABLE_ELEMENT_SELECTOR).each(function(){
                    var e =  $(this);
                    e.addClass(OSR_HIGHLIGHT_ELEMENT_CLASS);
                    e.click(function(){editableManager.edit(e)});
                })
            },
            edit : function(e){
                editableElement = e;
                $(OSR_RICH_TEXT_EDITOR_SELECTOR).show();
                $(OSR_EDITABLE_ELEMENT_SELECTOR).each(function(){
                    if(e !=  $(this)){
                        e.removeClass(OSR_HIGHLIGHT_ELEMENT_CLASS);
                    }
                });
                tinyMCE.activeEditor.setContent(e.html());
                applyCssToEditor(e);
                $(OSR_EDITABLE_CANCEL_BUTTON_SELECTOR).click(cancelChanges);
                $(OSR_EDITABLE_PREVIEW_BUTTON_SELECTOR).click(preview);
                $(OSR_EDITABLE_SUBMIT_BUTTON_SELECTOR).click(submit);
            }
        };
    }();
    return {
        getType : function(selector){
            switch(selector){
                case OSR_EDITABLE_ELEMENT_SELECTOR:
                    return editableManager;
            }
        }
    };
}();

/** SPECIAL FEATURES **/
function css(a) {var sheets = document.styleSheets, o = {}; for (var i in sheets) { var rules = sheets[i].rules || sheets[i].cssRules; for (var r in rules) { if (a.is(rules[r].selectorText)) { o = $.extend(o, css2json(rules[r].style), css2json(a.attr('style'))); } } } return o;}function css2json(css) { var s = {}; if (!css) return s; if (css instanceof CSSStyleDeclaration) { for (var i in css) { if ((css[i]).toLowerCase) { s[(css[i]).toLowerCase()] = (css[css[i]]); } } } else if (typeof css == "string") { css = css.split("; "); for (var i in css) { var l = css[i].split(": "); s[l[0].toLowerCase()] = (l[1]); } } return s;}
function applyCssToEditor(e){
    var cssProperties = ['background-color', 'color', 'font-size', 'font-weight', 'font-fontFamily', 'letter-spacing', 'line-height', 'text-align', 'text-transform', 'white-space', 'margin', 'padding', 'border'];
    for (var i = 0; i <  cssProperties.length; i++) {
        var property = cssProperties[i];
        var style = $(e).css(property);
        console.log(property + ": " + style);
        if (typeof style !== "undefined") {
            tinyMCE.activeEditor.dom.setStyle(tinyMCE.activeEditor.dom.select('.'+OSR_EDITOR_CONTENT_CLASS), property, style);
        }
    }
}

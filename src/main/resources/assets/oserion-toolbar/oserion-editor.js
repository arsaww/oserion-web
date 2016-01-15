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
                'height : "300",' +
                'selector: "textarea.tinymce",' +
                'plugins: ["advlist autolink lists link image charmap print preview anchor","searchreplace visualblocks code fullscreen","insertdatetime media table contextmenu paste"],' +
                'toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image"' +
                '});</script>');

            e.append('' +
                '<div class="richTextEditor">' +
                '<h1>Edition du contenu</h1>' +
                '<div class="textarea-container">' +
                '   <textarea class="tinymce"></textarea>' +
                '</div>' +
                '<div class="button-container">' +
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
                data: JSON.stringify({ref:editableElement.attr("id"), type: OSR_EDITABLE_TYPE, value: tinyMCE.activeEditor.getContent()}),
                success: function(){document.location.reload();},
                dataType: "application/json"
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
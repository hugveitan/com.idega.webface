<%@ page import="com.idega.webface.test.*" %>
<%@ page session="false"%>
<%
    ArticleItemBean bean = (ArticleItemBean) request.getSession().getAttribute("articleItemBean");    
    String bodyContent = "";
    try {
//    	bodyContent = request.getParameter("ta");
    	bodyContent = bean.getBody();    	
    } catch (Exception e) {}    
    String bodyId = ArticleBlock.BODY_ID;
%>
<html>
<head>
<title>Edit Article</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!-- Configure the path to the editor.  We make it relative now, so that the
    example ZIP file will work anywhere, but please NOTE THAT it's better to
    have it an absolute path, such as '/htmlarea/'. -->
<script type="text/javascript">
  _editor_url = "../";
  _editor_lang = "en";
</script>
<script type="text/javascript" src="../htmlarea.js"></script>

<style type="text/css">

html, body {
  font-family: Verdana,sans-serif;
  background-color: #fff;
  color: #000;
}
a:link, a:visited { color: #00f; }
a:hover { color: #048; }
a:active { color: #f00; }

textarea { background-color: #fff; border: 1px solid 00f; }
</style>

<script type="text/javascript">
var editor = null;
function initEditor() {
  editor = new HTMLArea("ta");
  var parentFormId = opener.document.forms[0].name;
  document.forms[0].elements['ta'].value=opener.document.forms[0].elements[parentFormId + ":" + '<% out.write(bodyId); %>'].value;

  // comment the following two lines to see how customization works
  editor.generate();
  return false;

//  var cfg = editor.config; // this is the default configuration
//  cfg.registerButton({
//    id        : "my-hilite",
//    tooltip   : "Highlight text",
//    image     : "ed_custom.gif",
//    textMode  : false,
//    action    : function(editor) {
//                  editor.surroundHTML("<span class=\"hilite\">", "</span>");
//                },
//    context   : 'table'
//  });

//  cfg.toolbar.push(["linebreak", "my-hilite"]); // add the new button to the toolbar

  // BEGIN: code that adds a custom button
  // uncomment it to test
  var cfg = editor.config; // this is the default configuration
  /*
  cfg.registerButton({
    id        : "my-hilite",
    tooltip   : "Highlight text",
    image     : "ed_custom.gif",
    textMode  : false,
    action    : function(editor) {
                  editor.surroundHTML("<span class=\"hilite\">", "</span>");
                }
  });
  */

function clickHandler(editor, buttonId) {
  switch (buttonId) {
    case "my-toc":
      editor.insertHTML("<h1>Table Of Contents</h1>");
      break;
    case "my-date":
      editor.insertHTML((new Date()).toString());
      break;
    case "my-bold":
      editor.execCommand("bold");
      editor.execCommand("italic");
      break;
    case "my-hilite":
      editor.surroundHTML("<span class=\"hilite\">", "</span>");
      break;
  }
};
//cfg.registerButton("my-toc",  "Insert TOC", "ed_custom.gif", false, clickHandler);
//cfg.registerButton("my-date", "Insert date/time", "ed_custom.gif", false, clickHandler);
//cfg.registerButton("my-bold", "Toggle bold/italic", "ed_custom.gif", false, clickHandler);
//cfg.registerButton("my-hilite", "Hilite selection", "ed_custom.gif", false, clickHandler);

cfg.registerButton("my-sample", "Class: sample", "ed_custom.gif", false,
  function(editor) {
    if (HTMLArea.is_ie) {
      editor.insertHTML("<span class=\"sample\">&nbsp;&nbsp;</span>");
      var r = editor._doc.selection.createRange();
      r.move("character", -2);
      r.moveEnd("character", 2);
      r.select();
    } else { // Gecko/W3C compliant
      var n = editor._doc.createElement("span");
      n.className = "sample";
      editor.insertNodeAtSelection(n);
      var sel = editor._iframe.contentWindow.getSelection();
      sel.removeAllRanges();
      var r = editor._doc.createRange();
      r.setStart(n, 0);
      r.setEnd(n, 0);
      sel.addRange(r);
    }
  }
);


  /*
  cfg.registerButton("my-hilite", "Highlight text", "ed_custom.gif", false,
    function(editor) {
      editor.surroundHTML('<span class="hilite">', '</span>');
    }
  );
  */
  cfg.pageStyle = "body { background-color: #efd; } .hilite { background-color: yellow; } "+
                  ".sample { color: green; font-family: monospace; }";
//  cfg.toolbar.push(["linebreak", "my-toc", "my-date", "my-bold", "my-hilite", "my-sample"]); // add the new button to the toolbar
  // END: code that adds a custom button

  editor.generate();
}
function insertHTML() {
  var html = prompt("Enter some HTML code here");
  if (html) {
    editor.insertHTML(html);
  }
}
function highlight() {
  editor.surroundHTML('<span style="background-color: yellow">', '</span>');
}
</script>

</head>

<!-- use <body onload="HTMLArea.replaceAll()" if you don't care about
     customizing the editor.  It's the easiest way! :) -->
<body onload="initEditor()">

<form action="" method="post" id="edit" name="edit">

<textarea id="ta" name="ta" style="width:100%;height:400px;">
</textarea>
<div style="padding:4px"></div>
<input type="button" name="ok" value="Done" onclick="updateParent();"/>

<!-- <a href="javascript:mySubmit()">submit</a> -->

<script type="text/javascript">
function mySubmit() {
// document.edit.save.value = "yes";
document.edit.onsubmit(); // workaround browser bugs.
document.edit.submit();
};

function updateParent() {
  var parentFormId = opener.document.forms[0].name;
  opener.document.forms[0].elements[parentFormId + ":" + '<% out.write(bodyId); %>'].value = editor.getHTML().trim();
  window.close();
};

</script>

</form>

</body>
</html>
 
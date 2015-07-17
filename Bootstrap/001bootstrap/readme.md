extensões

Abrir o site: wbond.net
	Instalar package Control: https://packagecontrol.io/browse/authors/Will%20Bond%20%28wbond%29
	
	Copiar o texto do site
		import urllib.request,os,hashlib; h = 'eb2297e1a458f27d836c04bb0cbaf282' + 'd0e7a3098092775ccb37ca9d6b2e4b7d'; pf = 'Package Control.sublime-package'; ipp = sublime.installed_packages_path(); urllib.request.install_opener( urllib.request.build_opener( urllib.request.ProxyHandler()) ); by = urllib.request.urlopen( 'http://packagecontrol.io/' + pf.replace(' ', '%20')).read(); dh = hashlib.sha256(by).hexdigest(); print('Error validating download (got %s instead of %s), please try manual install' % (dh, h)) if dh != h else open(os.path.join( ipp, pf), 'wb' ).write(by)

	No sublime, abrir o menu exibir, escolher exibir console, colar o texto e pressionar enter

No Sublime, pressione CTRL + SHIFT + P
	Digite install package <enter>
	Digite ZenCoding

	OpenFolder
	HTML5
	HtmlBeautify
	JavaScriptBeautify
	JavaScriptCompletion
	JsFormat
	JSHint
	JavaScriptNext
	SublimeOnSaveBuild
	
	
Download bootstrap: http://globocom.github.io/bootstrap/



------------------------------------------------------------------------------------------------------------------------------------------------------
JsFormat

https://github.com/jdc0589/JsFormat

JsFormat is a JavaScript formatting plugin. Behind the scenes, it uses the command line formatter from jsbeautifier.org to format full or portions of JavaScript and JSON files.

Features

    JavaScript formatting
    JSON formatting
    Full file formatting
    Selected text formatting
    Customizable settings for formatting options
    Customize per project with .jsbeautifyrc settings file

Usage

Either cmd+alt+f on OS X or ctrl+alt+f on Linux/Windows


------------------------------------------------------------------------------------------------------------------------------------------------------


JSHint

https://github.com/uipoet/sublime-jshint

“JSHint is a community-driven tool to detect errors and potential problems in JavaScript code and to enforce your team’s coding conventions. It is very flexible so you can easily adjust it to your particular coding guidelines and the environment you expect your code to execute in. JSHint is open source and will always stay this way.” - JSHint

Usage

ctrl+j on OS X or alt+j on Linux/Windows

If you would like to have JSHint run anytime you save a JavaScript file (highly suggest this), you will need to install the SublimeOnSaveBuild package.


------------------------------------------------------------------------------------------------------------------------------------------------------

JavaScriptNext

https://github.com/Benvie/JavaScriptNext.tmLanguage

This plugin is a better syntax highlighter for JavaScript. Not only does it improve syntax highlighting for current ES5, it also adds syntax highlighting for new ES6 syntax such as modules, succinct methods, arrow functions, classes, and generators.

Here is the original JavaScript syntax highlighter:
------------------------------------------------------------------------------------------------------------------------------------------------------


http://scottksmith.com/blog/2014/09/29/3-essential-sublime-text-plugins-for-node-and-javascript-developers/
https://github.com/alexnj/SublimeOnSaveBuild

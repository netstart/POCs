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
	
	
Download bootstrap: http://globocom.github.io/bootstrap/
var codeAmApp = angular.module('codeAmApp', []);

codeAmApp.controller("JoaoCtrl", ['$scope', function($scope){
	$scope.name = 'João';

	$scope.habilitaNome = function(){
		$scope.name = "Clayton K. N. Passos"
	}

	$scope.digiteNome = function(novoNome){
		$scope.name = novoNome;
	}


}]);

codeAmApp.controller("MariaCtrl", ['$scope', function($scope){
	$scope.name = 'Maria';
}]);
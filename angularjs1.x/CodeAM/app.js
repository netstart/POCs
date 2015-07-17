var codeAmApp = angular.module('codeAmApp', []);

codeAmApp.controller("JoaoCtrl", ['$scope', function($scope){
	$scope.name = 'João';

	$scope.habilitaNome = function(){
		$scope.name = "Clayton K. N. Passos"
	}


}]);

codeAmApp.controller("MariaCtrl", ['$scope', function($scope){
	$scope.name = 'Maria';
}]);
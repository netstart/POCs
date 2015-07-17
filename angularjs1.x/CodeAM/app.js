var codeAmApp = angular.module('codeAmApp', []);

codeAmApp.controller("JoaoCtrl", ['$scope', function($scope){
	$scope.name = 'João';
}]);

codeAmApp.controller("MariaCtrl", ['$scope', function($scope){
	$scope.name = 'Maria';
}]);
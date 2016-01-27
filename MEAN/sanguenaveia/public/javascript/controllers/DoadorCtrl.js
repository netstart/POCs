'use strict';
var sangueNaVeiaApp = angular.module('SangueNaVeiaApp', []);

sangueNaVeiaApp.controller('DoadorCtrl', function ($scope) {

	var doadores = [
		{
			sangue: "O+";
		},
		{
			sangue: "O+";
		},
		{
			sangue: "O-";
		},
		{
			sangue: "O-";
		},
		{
			sangue: "A";
		},
		{
			sangue: "B";
		},
		{
			sangue: "AB";
		}
	];
	

	$scope.buscaDoador = function(sangue) {
		$scope.doadores = doadores;

	}


}
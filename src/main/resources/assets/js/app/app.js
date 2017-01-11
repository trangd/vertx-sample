'use strict';

// Declare app level module which depends on filters, and services
angular.module('liaisonApp', [
  'ngRoute',
  'liaisonApp.controllers',
  'liaisonApp.services',
  'ngDialog'
]).
config(['$routeProvider','$locationProvider', function($routeProvider,$locationProvider) {
  $routeProvider.when('/assets', {templateUrl: 'pages/professor.html', controller: 'liaisonController'});
  $routeProvider.otherwise({redirectTo: '/assets'});
   if(window.history && window.history.pushState){
        $locationProvider.html5Mode({
             enabled: true,
             requireBase: false
        });
   }

}]);

angular.module('liaisonApp.services').factory(
	    'liaisonService',['$http',
	    function liaisonService($http){
            var service = {};
            service.getAllParent = function(){
                return $http.get("/rest/parent");
            }

            service.getAllParentMessage = function(parent){
                return $http.get("/rest/parent/"+parent);
            }

            service.confirmMessage = function(messages){
                return $http.put("/rest/parent", messages);
            }

            service.getAllMessage = function(){
                return $http.get("/rest/professor");
            }

            service.addMessage = function(form){
                return $http.post("/rest/professor",form);
            }


            return service;

	    }]
	    );

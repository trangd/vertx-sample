'use strict';

/* Controllers */
angular.module('liaisonApp.controllers')
    .controller('liaisonController',['$scope','liaisonService','ngDialog',function($scope,liaisonService,ngDialog){

        liaisonService.getAllParent().then(function(parents){
            $scope.allParent = parents.data;
            createParentSelection($scope.allParent);
        });

        function createParentSelection(allParent){
            $scope.optionParent = [];
            $scope.optionParent.push("tous");
            $scope.optionParent = $scope.optionParent.concat(allParent);
        }

        liaisonService.getAllMessage().then(function(message){
            $scope.listMessages = message.data;
        });

        $scope.formData = {
            name:'',
            content:''
        }
        $scope.openForm = function() {
            ngDialog.openConfirm({
                template : 'assets/pages/dialog.html',
                className : 'ngdialog-theme-default',
                scope:$scope
            }).then(function(value){
                liaisonService.addMessage(convertFormToMessage($scope.formData));
                liaisonService.getAllMessage().then(function(message){
                    $scope.listMessages = message.data;
                });
            });
        };

        $scope.chooseParent = function(parentSelected){
                if (parentSelected == "tous"){
                    $scope.formData.name = $scope.allParent.join(",");
                }else {
                    var tabParents = [];
                    if ($scope.formData.name){
                        tabParents = tabParents.concat($scope.formData.name.split(","))
                    }
                    var alreadyExists = tabParents.filter(function(parent){
                        return parent == parentSelected;
                    });
                    if (!alreadyExists || alreadyExists.length == 0){
                        tabParents.push(parentSelected);
                        $scope.formData.name= tabParents.join(",");
                    }
                }
         }

        $scope.chooseParentMsg = function(parent){
            $scope.parentChosen = parent;
            liaisonService.getAllParentMessage(parent).then(function(messages){
                $scope.listParentMessage = messages.data;
            });
        }

        $scope.updateStateMessage = function(){
            liaisonService.confirmMessage($scope.listParentMessage);
            liaisonService.getAllMessage().then(function(message){
                $scope.listMessages = message.data;
            });
        }

         $scope.removeParents = function(){
            $scope.formData.name= '';
         }

        function convertFormToMessage (formData){
            var listParent = formData.name.split(",");
            var parentConfirm = {};
            listParent.forEach(function(parent){
                parentConfirm[parent] = false;
            });
            return {
                id : null,
                content : formData.content,
                date : null,
                hasConfirmed : parentConfirm
            }

        }
    }]);
angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get('api/produtos').
        then(function(response) {
            $scope.produtos = response.data;
        });
});


$scope.deleteProduto = function(produto) { // Delete a Shipwreck. Issues a DELETE to /api/v1/shipwrecks/:id
    if (popupService.showPopup('Really delete this?')) {
      produto.$delete(function() {
        $scope.produtos = Produto.query(); 
        $state.go('produtos');
      });
    }
  };
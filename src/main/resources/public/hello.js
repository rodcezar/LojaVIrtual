angular.module('demo', [])
.controller('Hello', function($scope, $http) {
    $http.get('api/produtos').
        then(function(response) {
            $scope.produtos = response.data;
        });
});
angular.module('components')
        .directive('navbar', navbar);

function navbar(){
    return {
        restrict: 'E',
        scope : {
            tab: '='
        },
        controller: 'navbarController',
        controllerAs: 'vm',
        templateUrl: 'app/common/navbar/navbar.html',
        replace: true
    };
}
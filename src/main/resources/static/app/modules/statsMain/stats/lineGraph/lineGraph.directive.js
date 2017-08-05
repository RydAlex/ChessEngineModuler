angular.module('stats')
    .directive('lineGraph', lineGraphDirective);

function lineGraphDirective(){
    return {
        restrict: 'E',
        scope: {},
        controller: lineGraphController,
        controllerAs: 'vm',
        templateUrl: 'app/modules/stats/lineGraph/chessboard.html'
        //replace: true
    };
}
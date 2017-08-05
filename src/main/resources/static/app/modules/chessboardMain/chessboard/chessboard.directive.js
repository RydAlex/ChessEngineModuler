angular.module('components')
        .directive('chessBoard', chessBoardDirective);

function chessBoardDirective(){
    return {
        restrict: 'E',
        scope: {},
        controller: chessBoardController,
        controllerAs: 'vm',
        templateUrl: 'app/modules/chessboardMain/chessboard/chessboard.html'
        //replace: true
    };
}
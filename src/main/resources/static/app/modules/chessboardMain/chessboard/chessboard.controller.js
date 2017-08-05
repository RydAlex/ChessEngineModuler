angular.module('components')
    .controller('chessBoardController', chessBoardController);

function chessBoardController(){
    var vm = this;

    vm.board = {};
    vm.game = new Chess();
    vm.statusEl = $('#status');
    vm.fenEl = $('#fen');
    vm.pgnEl = $('#pgn');

    function onDragStart(source, piece, position, orientation) {
        if (vm.game.game_over() === true ||
            (vm.game.turn() === 'w' && piece.search(/^b/) !== -1) ||
            (vm.game.turn() === 'b' && piece.search(/^w/) !== -1)) {
            return false;
        }
    }

    function onDrop(source, target) {
        // see if the move is legal
        var move = vm.game.move({
            from: source,
            to: target,
            promotion: 'q' // NOTE: always promote to a queen for example simplicity
        });

        // illegal move
        if (move === null) return 'snapback';

        updateStatus();
    }

    // update the board position after the piece snap
    // for castling, en passant, pawn promotion
    function onSnapEnd() {
        vm.board.position(vm.game.fen());
    }

    function updateStatus() {
        var status = '';

        var moveColor = 'White';
        if (vm.game.turn() === 'b') {
            moveColor = 'Black';
        }

        // checkmate?
        if (vm.game.in_checkmate() === true) {
            status = 'Game over, ' + moveColor + ' is in checkmate.';
        }

        // draw?
        else if (vm.game.in_draw() === true) {
            status = 'Game over, drawn position';
        }

        // game still on
        else {
            status = moveColor + ' to move';

            // check?
            if (vm.game.in_check() === true) {
                status += ', ' + moveColor + ' is in check';
            }
        }

        vm.statusEl.html(status);
        vm.fenEl.html(vm.game.fen());
        vm.pgnEl.html(vm.game.pgn());
    }

    var cfg = {
        draggable: true,
        position: 'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR',
        pieceTheme: 'lib/chessboardjs/img/chesspieces/wikipedia/{piece}.png',
        onDragStart: onDragStart,
        onDrop: onDrop,
        onSnapEnd: onSnapEnd
    };
    vm.board = ChessBoard('board', cfg);

    updateStatus();
}
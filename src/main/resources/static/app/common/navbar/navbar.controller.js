angular.module('components')
        .controller('navbarController', navbarController);

function navbarController(){
    var vm = this;

    console.log(vm.tab);
}
function loginController($scope, $location) {
	var url = "" + $location.$$absUrl;
	$scope.displayLoginError = (url.indexOf("error") >= 0);
	
	 $scope.changeErrorVariableValue = function(){
		alert("avant click: " + $scope.displayLoginError);
        $scope.displayLoginError = !$scope.displayLoginError;
        alert("après click: " + $scope.displayLoginError);
    }
}
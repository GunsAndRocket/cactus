var app = angular.module('IndexApp', []);

app.controller('AppCtrl', ["$scope", function ($scope) {
    $scope.imagePath = 'https://pp.vk.me/c626219/v626219896/cbf4/i_IE_rIU7xo.jpg';
    // $scope.items = $scope.items = getAllPosts();;
    // $scope.items = getAllPosts();
    QB.init(QBApp.appId, QBApp.authKey, QBApp.authSecret, true);

// Create session
    var filter = {sort_asc: 'created_at'};
    QB.createSession(QBUser, function (err, result) {
        if (err) {
            console.log('Something went wrong: ' + err);
        } else {
            console.log('Session created with id ' + result.id);
            // Get all posts
            getAllPosts();

            console.log($scope.items);
        }
        function getAllPosts() {
            QB.data.list("Events", function(err, result){
                if (err) {
                    console.log(err);
                    console.log("IDI NAXUI");
                } else {
                    console.log(result);
                    console.log("MALADEC");
                    // $scope.items = result.items;
                    // return $scope.items;


                    var scope = angular.element(document.getElementsByTagName("body")).scope();
                    scope.$apply(function() {
                        scope.items = result.items;
                    });


                }
                // return $scope.items;
            });
        }
    });
    // $scope.items = getAllPosts();

}]);






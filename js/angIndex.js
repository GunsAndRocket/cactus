var app = angular.module('IndexApp', []);
var itemss = [];
// app.value('items', []);
app.controller('AppCtrl', ["$scope", function ($scope) {
    $scope.imagePath = 'https://pp.vk.me/c626219/v626219896/cbf4/i_IE_rIU7xo.jpg';
    // $scope.items = $scope.items = getAllPosts();;
    // $scope.items = getAllPosts();
    // $scope.items = [];

    QB.init(QBApp.appId, QBApp.authKey, QBApp.authSecret, true);

// Create session
    var filter = {sort_asc: 'created_at'};
    var ite
    QB.createSession(QBUser, function (err, result) {
        if (err) {
            console.log('Something went wrong: ' + err);
        } else {
            console.log('Session created with id ' + result.id);
            // Get all posts
            // getAllPosts(itemss);
            itemss = getAllPosts();
            // console.log(items);
        }
    });
    // $scope.items = getAllPosts();
    function getAllPosts() {
        QB.data.list("Events", function(err, result){
            if (err) {
                console.log(err);
                console.log("IDI NAXUI");
            } else {
                console.log(result);
                console.log("MALADEC");
                ite = result;
                // console.log(items);
                // console.log(a);
                return result;
            }
            console.log($scope.items);
            return $scope.items;
        });
    };
    console.log(ite);
}]);






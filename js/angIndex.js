var app = angular.module('IndexApp', []);

app.controller('AppCtrl', ["$scope", function ($scope) {
    $scope.imagePath = 'https://pp.vk.me/c626219/v626219896/cbf4/i_IE_rIU7xo.jpg';

    QB.init(QBApp.appId, QBApp.authKey, QBApp.authSecret, true);

// Create session
    var filter = {sort_asc: 'created_at'};
    QB.createSession(QBUser, function (err, result) {
        if (err) {
            console.log('Something went wrong: ' + err);
        } else {
            console.log('Session created with id ' + result.id);
            // Get all posts
            // getAllPosts();

        }
    });
}]);

function getAllPosts() {
    QB.data.list("Events", filter, function(err, result){
        if (err) {
            console.log(err);
        } else {
            console.log(result);

            return result.items;
        }
        return null;
    });
}




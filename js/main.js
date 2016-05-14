/**
 * Created by santos on 5/14/16.
 */
QB.init(QBApp.appId, QBApp.authKey, QBApp.authSecret, true);

// Create session
var filter = {sort_asc: 'created_at'};
QB.createSession(QBUser, function(err, result){
    if (err) {
        console.log('Something went wrong: ' + err);
    } else {
        console.log('Session created with id ' + result.id);
        // Get all posts
        // getAllPosts();

        $('#send_post').click(function(e) {
            e.preventDefault();

            var eventName = $('#text').val();
            var tag = $('#tag').val();
            var organiser = $('#organiser').val();
            var description = $('#description').val();
            var startDate = $('#startDate').val();
            var vkLink = $('#vkLink').val();
            var place = $('#place').val();
            // Adds a new post
            if (eventName && tag && organiser && description && startDate &&  vkLink && place) {
                // $("#load-img").show(0);
                addNewPost(eventName, tag, organiser, description, startDate, vkLink, place);
            } else {
                alert('Please complete all required fields');
            }
        });
    }
});

// function getAllPosts() {
//     QB.data.list("Blog", filter, function(err, result){
//         if (err) {
//             console.log(err);
//         } else {
//             console.log(result);
//
//             for (var i=0; i < result.items.length; i++) {
//                 var item = result.items[result.items.length-i-1];
//                 showPost(item.title, item.body, false);
//             }
//         }
//     });
// }

function addNewPost(eventName, tag, organiser, description, startDate, vkLink, place) {
    QB.data.create("Events", {
            tag: tag,
            name: eventName,
            organiser: organiser,
            description: description,
            startDate: startDate,
            vkLink: vkLink,
            place: place
        }, function(err, res){
        if (err) {
            console.log(err);
        } else {
            console.log(res);

            // $("#load-img").delay(1000).fadeOut(1000);
            // $('#myModal').modal('hide');
            $('#text').val('');
            $('#tag').val('');
            $('#description').val('');
            $('#startDate').val('');
            $('#place').val('');
            $('#organiser').val('');
            $('#vkLink').val('');

            // QB.data.list("Blog", filter, function(err, result){
            //     if (err) {
            //         console.log(err);
            //     } else {
            //         console.log(result);
            //
            //         showPost(textTitle, textBody, true);
            //     }
            // });
        }
    });
}

// $('#send_post').click(function(e) {
//     e.preventDefault();
//
//     var eventName = $('#text').val();
//     var tag = $('#tag').val();
//     var organiser = $('#organiser').val();
//     var description = $('#description').val();
//     var startDate = $('#startDate').val();
//     var vkLink = $('#vkLink').val();
//     var place = $('#place').val();
//     // Adds a new post
//     if (eventName && tag && organiser && description && startDate &&  vkLink && place) {
//         // $("#load-img").show(0);
//         addNewPost(eventName, tag, organiser, description, startDate, vkLink, place);
//     } else {
//         alert('Please complete all required fields');
//     }
// });
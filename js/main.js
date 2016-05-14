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

        var image_url;
        // image loading
        // $("#upload").click(function(e){
        //     e.preventDefault();
        //
        //     var inputFile = $("input[type=file]")[0].files[0];
        //     console.log(inputFile);
        //     // if (inputFile) {
        //     //     $("#progress").show(0);
        //     // }
        //
        //     // upload image
        //     QB.content.createAndUpload({name: inputFile.name, file: inputFile, type: inputFile.type, size: inputFile.size, 'public': false}, function(err, response){
        //         if (err) {
        //             console.log(err);
        //         } else {
        //             console.log(response);
        //
        //             // $("#progress").fadeOut(400);
        //
        //             var uploadedFile = response;
        //
        //             image_url = QB.content.privateUrl(uploadedFile.uid);
        //             // showImage(uploadedFile.uid, uploadedFile.name, false);
        //         }
        //     });
        // });

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
            if (eventName && tag && organiser && description && startDate && vkLink && place) {
                // $("#load-img").show(0);
                addNewPost(eventName, tag, organiser, description, startDate, vkLink, place);
            } else {
                alert('Please complete all required fields');
            }
        });
    }
});
$("#upload").change(function() {

    var val = $(this).val();

    switch(val.substring(val.lastIndexOf('.') + 1).toLowerCase()){
        case 'gif': case 'jpg': case 'png':
        //OK it`s image
        break;
        default:
            $(this).val('');
            // error message here
            alert("not an image");
            break;
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
// $("#upload").change(function () {
//     var file = $('#upload').val();
//     if (file !== null) {
//         if ($('.filename').val() !== null) {
//             $($('.filename')).empty();
//         }
//         $("<h1>"+file+"</h1>").appendTo('.filename');
//     }
// });


function addNewPost(eventName, tag, organiser, description, startDate, vkLink, place) {
    QB.data.create("Events", {
            tag: tag,
            name: eventName,
            organiser: organiser,
            description: description,
            startDate: startDate,
            vkLink: vkLink,
            place: place,
            followers: 0
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

            var params = {
                notification_type: 'push',
                user: {tags: {any: [tag]}},
                environment: 'development', // environment, can be 'production' as well.
                message: QB.pushnotifications.base64Encode('Allow, misha')
            };

            QB.pushnotifications.events.create(params, function(err, response) {
                if (err) {
                    console.log(err);
                } else {
                    // success
                }
            });

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
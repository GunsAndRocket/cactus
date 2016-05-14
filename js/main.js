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

        $("#upload").change(function () {
            // image extension validation
            var _validFileExtensions = [".jpg", ".jpeg", ".bmp", ".gif", ".png"];
            var sFileName = $("input[type=file]")[0].value;
            if (sFileName.length > 0) {
                var blnValid = false;
                for (var j = 0; j < _validFileExtensions.length; j++) {
                    var sCurExtension = _validFileExtensions[j];
                    if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                        blnValid = true;
                        break;
                    }
                }

                if (!blnValid) {
                    console.log("ALALLALAL")
                    alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
                    return false;
                }
            }

            // image name output
            var file = $("input[type=file]")[0].files[0];
            if (file !== null) {
                if ($('.filename').val() !== null) {
                    $($('.filename')).empty();
                }
                $('<span>' + file.name + '</span>').appendTo('.filename');
            }
        });

        $('#send_post').click(function(e) {
            e.preventDefault();

            var eventName = $('#text').val();
            var tag = $('#tag').val();
            var organiser = $('#organiser').val();
            var description = $('#description').val();
            var startDate = $('#startDate').val();
            var vkLink = $('#vkLink').val();
            var place = $('#place').val();

            // image loading

            var inputFile = $("input[type=file]")[0].files[0];
            console.log(inputFile);

            // upload image
            QB.content.createAndUpload({name: inputFile.name, file: inputFile, type: inputFile.type, size: inputFile.size, 'public': false}, function(err, response){
                if (err) {
                    console.log(err);
                } else {
                    console.log(response);

                    var uploadedFile = response;

                    var image_url = QB.content.privateUrl(uploadedFile.uid);

                    // Adds a new post
                    if (eventName && tag && organiser && description && startDate && vkLink && place && image_url) {
                        addNewPost(eventName, tag, organiser, description, startDate, vkLink, place, image_url);
                    } else {
                        alert('Please complete all required fields');
                    }
                }
            });




        });
    }
});

function addNewPost(eventName, tag, organiser, description, startDate, vkLink, place, image_url) {
    QB.data.create("Events", {
            tag: tag,
            name: eventName,
            organiser: organiser,
            description: description,
            startDate: startDate,
            vkLink: vkLink,
            place: place,
            followers: 0,
            image_url: image_url
        }, function(err, res){
        if (err) {
            console.log(err);
        } else {
            console.log(res);

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
            
        }
    });
}
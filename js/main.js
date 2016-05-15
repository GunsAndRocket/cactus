/**
 * Created by santos on 5/14/16.
 */
QB.init(QBApp.appId, QBApp.authKey, QBApp.authSecret, true);

// Create session
var filter = {sort_asc: 'created_at'};
QB.createSession(QBUser, function (err, result) {
    if (err) {
        console.log('Something went wrong: ' + err);
    } else {
        console.log('Session created with id ' + result.id);
        // Get all posts
        // $(document).delay(10000);
        setTimeout(function () {
            $(".selectpicker").selectpicker();
        }, 1000);
        getAllTags();


        $("#upload").change(function () {
            // image extension validation
            var _validFileExtensions = [".jpg", ".jpeg", ".bmp", ".png"];
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
                    alert("Sorry, allowed extensions are: " + _validFileExtensions.join(", "));
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

        $('#send_post').click(function (e) {
            e.preventDefault();

            var eventName = $('#text').val();
            var tag = $('.selectpicker').val();
            var organiser = $('#organiser').val();
            var description = $('#description').val();
            var startDate = $('#startDate').val() + " " + $('#startTime').val();
            var vkLink = $('#vkLink').val();
            var place = $('#place').val();

            // image loading

            var inputFile = $("input[type=file]")[0].files[0];
            console.log(inputFile);

            // upload image
            QB.content.createAndUpload({
                name: inputFile.name,
                file: inputFile,
                type: inputFile.type,
                size: inputFile.size,
                'public': true
            }, function (err, response) {
                if (err) {
                    console.log(err);
                } else {
                    console.log(response);

                    var uploadedFile = response;

                    var image_url = QB.content.publicUrl(uploadedFile.uid);

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
    }, function (err, res) {
        if (err) {
            console.log(err);
        } else {
            console.log(res);

            $('#text').val('');
            $('.selectpicker').val('');
            $('#description').val('');
            $('#startDate').val('');
            $('#startTime').val('');
            $('#place').val('');
            $('#organiser').val('');
            $('#vkLink').val('');

            var params = {
                notification_type: 'push',
                user: {tags: {any: [tag]}},
                environment: 'development', // environment, can be 'production' as well.
                message: QB.pushnotifications.base64Encode("New event" + eventName)
            };

            QB.pushnotifications.events.create(params, function (err, response) {
                if (err) {
                    console.log(err);
                } else {
                    // success
                }
            });

        }
    });
}

function getAllTags() {
    QB.data.list("Tags", function (err, result) {
        if (err) {
            console.log(err);
        } else {
            console.log(result);

            for (var i = 0; i < result.items.length; i++) {
                var item = result.items[result.items.length - i - 1];
                showTag(item.name, item.type, false);
            }
        }
    });
}

function showTag(name, type, lastPost) {
    var containerElement = $('.selectpicker #' + type);
    var postElement = $('<option></option>');
    postElement.append('<strong>' + name + '</strong>');

    if (lastPost) {
        containerElement.prepend(postElement);
    } else {
        containerElement.append(postElement);
    }
    $(".selectpicker").selectpicker("refresh");
}
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");
var uplBtn = document.getElementById("uploadBtn");
// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

function uploadFromVk() {
    uplBtn.style.display = "none";
    VK.init({
        apiId: 5379550,

    })
    VK.Api.call('groups.get', {userId: 'uid', extended: 1, filter: 'events'}, function (data) {
        // alert(data);
        if (data.error) {
            alert(data.error.error_msg);
        } else {
            modal.style.display = "block";
            console.log(data);
            for(i=1;i<=data.response[0];i++){
                var divchik = $('<div onclick="selectedEvent('+
                    data.response[i].gid+')"></div>').appendTo($('.modal-content'));

                $('<img src="'+data.response[i].photo_medium+'">').appendTo(divchik);
                $('<span>'+data.response[i].name+'</span>').appendTo(divchik);
                // modal.appendChild('<img src="'+data.response[i].photo_medium+'" onclick="selectedEvent('+
                //     data.response[i].gid+')">');
            }
        }
    });
}
span.onclick = function() {
    uplBtn.style.display = "block";
    modal.style.display = "none";
    $('.modal-content').html('<span class="close">x</span>');
};
window.onclick = function(event) {
    if (event.target == modal) {
        uplBtn.style.display = "block";
        modal.style.display = "none";
        $('.modal-content').html('<span class="close">x</span>');
    }
};
function selectedEvent(eventId) {
    uplBtn.style.display = "block";
    modal.style.display = "none";
    $('.modal-content').html('<span class="close">x</span>');
    VK.init({
        apiId: 5379550,

    })
    VK.Api.call('groups.getById', {group_ids: eventId}, function (data) {
        // alert(data);
        if (data.error) {
            alert(data.error.error_msg);
        } else {
            $('#text').val(data.response[0].name);
            // $('#text_loadimg').html(data.response[0].photo_big);

            $('#description').val('--------------------------'+'\n'+data.response[0].name+'\n'+'--------------------------');
            console.log(data);
            $('#vkLink').val('vk.com/'+data.response[0].screen_name);
        }
    });
    VK.Api.call('users.get', {userId: 'uid'}, function (data) {
        // alert(data);
        if (data.error) {
            alert(data.error.error_msg);
        } else {

            $('#organiser').val(data.response[0].first_name+' '+data.response[0].last_name);
            console.log(data);

        }
    });

}

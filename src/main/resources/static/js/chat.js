var userId;
var currentUser;
var superKostilNumberTwo = 0;
var itemConv = '' +
    '<div class="row">' +
    '<a href="#" class="a-st">' +
    '<div class="col-xs-4">' +
    '<img src="/image/other/4-user.png" class="phot img-circle"/>' +
    '</div>' +
    '<div class="col-xs-8 mart">' +
    '<b class="xas">Игорь Николаев</b>' +
    '<p class="qwerty">Вы: Добрый день</p>' +
    '</div>' +
    '</a>' +
    '</div>';
var conversations;
var superKostil;
function searchConvByUserEmail(user) {
    for (var i = 0; i < conversations.length; i++) {
        if (conversations[i].user1.email == user.email || conversations[i].user2.email == user.email) {
            currentConv = conversations[i].id;
            printMessages(conversations[i].id);
            return;
        }
    }
    superKostilNumberTwo = 0;
    $('#messages').html("");
    $('#messages').html("Напишите свое первое сообщение пользователю " + user.name + " " + user.lastName);
    $('#chat-user-info').html(user.name + " " + user.lastName);
    currentConv = null;
    $('#send-msg-form').css('display', 'block');
    superKostil = user.id;
}
var defAvatar = "/image/other/4-user.png";
function conv() {
    $.get("/messages", function (data) {
        conversations = data;
        $('#conversations').html("");
        for (var ob in conversations) {
            var senderName;
            var lastMessage;
            var sender;
            if (conversations[ob].user1.id == userId) {
                senderName = conversations[ob].user2.name + " " + conversations[ob].user2.lastName;
                sender = conversations[ob].user2;
            } else {
                senderName = conversations[ob].user1.name + " " + conversations[ob].user1.lastName;
                sender = conversations[ob].user1;

            }
            lastMessage = conversations[ob].messages[conversations[ob].messages.length - 1];
            if (lastMessage.from.id == userId) {
                lastMessage = "Вы: " + ((lastMessage.message.length > 15 ) ? lastMessage.message.substring(0, 13) + ".." : lastMessage.message);
            } else {
                lastMessage = lastMessage.from.name + ": " + ((lastMessage.message.length > 15) ? lastMessage.message.substring(0, 13) + ".." : lastMessage.message);
            }
            var itemConv = '' +
                '<div class="row convs-href" data-conv-id="' + conversations[ob].id + '">' +
                '<a href="#" class="a-st " >' +
                '<div class="col-xs-4">' +
                '<img src="' + (sender.avatar == null ? defAvatar : sender.avatar) + '" class="phot img-circle"/>' +
                '</div>' +
                '<div class="col-xs-8 mart">' +
                '<b class="xas">' + senderName + '</b><br>' +
                '<p class="qwerty">' + lastMessage + '</p>' +
                '</div>' +
                '</a>' +
                '</div>';
            $('#conversations').append(itemConv);
        }
    });
}
var currentConv;

function printMessages(convId) {
    $.get("/conf/" + convId, function (data) {
        for (var ob in conversations) {
            if (convId == conversations[ob].id) {
                if (conversations[ob].user1.id == userId) {
                    $('#chat-user-info').html(conversations[ob].user2.name + " " + conversations[ob].user2.lastName);
                } else {
                    $('#chat-user-info').html(conversations[ob].user1.name + " " + conversations[ob].user1.lastName);
                }
            }
        }
        var messages = data;
        var messageTmp;
        $('#messages').html("");
        $('#send-msg-form').css('display', 'block');
        for (var i = 0; i < messages.length; i++) {
            var message = messages[i];
            messageTmp = '<div class="media">' +
                '<a class="pull-left" href="#">' +
                '<img src="' + (message.from.avatar == null ? defAvatar : message.from.avatar ) + '" class="photr2 media-object img img-responsive img-circle">' +
                '</a>' +
                '<div class="media-body">' +
                '<h6 class="media-heading" style="color: #116898;">' + message.from.name + ' ' + message.from.lastName + '<span class="text-muted pull-right" style="font-weight: 300;font-size: 10px; margin-right: 15px;"> ' + moment(message.createdDate).calendar() + '</span></h6>' +
                '<div style="width: 340px; word-wrap: break-word">' +
                '<p>' + message.message + '</p>' +
                '</div>' +
                '</div>' +
                '</div>';
            $('#messages').append(messageTmp);
        }
        $('#messages').scrollTop($('#messages')[0].scrollHeight);
    });
}

$('#modal-sms').on('click', function () {
    $('#myModal2').modal();
    conv();

    $(document.body).on('click', '.convs-href', function () {
        var convId = $(this).attr('data-conv-id');
        currentConv = convId;
        console.log(convId);
        printMessages(convId);
    });

    $('#send-message').on('click', function () {
        var msg = $('#message-input').val();
        if ($.trim(msg).length === 0)
            return;
        var toUser;
        if (currentConv == null) {
            superKostilNumberTwo++;
            if (superKostilNumberTwo > 1) {

            } else {
                $('#messages').html("");
            }
            toUser = superKostil;
        } else {
            for (var i = 0; i < conversations.length; i++) {
                if (conversations[i].id == currentConv) {
                    if (conversations[i].user1.id == userId) {
                        toUser = conversations[i].user2.id;
                    } else {
                        toUser = conversations[i].user1.id;
                    }
                    break;
                }
            }
        }
        var messageTmp = '<div class="media">' +
            '<a class="pull-left" href="#">' +
            '<img src="' + (currentUser.avatar == null ? defAvatar : currentUser.avatar) + '" class="photr2 media-object img img-responsive img-circle">' +
            '</a>' +
            '<div class="media-body">' +
            '<h6 class="media-heading" style="color: #116898;">' + currentUser.name + ' ' + currentUser.lastName + '<span class="text-muted pull-right" style="font-weight: 300;font-size: 10px; margin-right: 15px;"> ' + moment().calendar() + '</span></h6>' +
            '<div style="width: 340px; word-wrap: break-word">' +
            '<p>' + encodeHTML(msg) + '</p>' +
            '</div>' +
            '</div>' +
            '</div>';

        $('#messages').append(messageTmp);
        $('#message-input').val("");
        autosize.update($('#message-input'))
        $('#messages').scrollTop($('#messages')[0].scrollHeight);
        sendMessage(msg, toUser);
    });

});
function encodeHTML(s) {
    return s.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/"/g, '&quot;');
}
function getInfoUser() {
    $.get("/user_info", function (data) {
        userId = data.id;
        currentUser = data;
        disconnect();
        connect();
    });
}
var stompClient = null;

var audio = new Audio('/sound/new_message.mp3');
$("#message-input").keyup(function (event) {
    if (event.keyCode == 13) {
        $("#send-message").click();
    }
});
function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages/' + userId, function (messageOutput) {
            messageOutput = JSON.parse(messageOutput.body);
            $('.top-left').notify({
                message: {
                    text: messageOutput.from.name + " " + messageOutput.from.lastName + ": " +
                    ((messageOutput.message.length > 30 ) ? messageOutput.message.substring(0, 30) + ".." : messageOutput.message)
                }
            }).show();
            audio.play();
            conv();


            for (var i = 0; i < conversations.length; i++) {
                if (conversations[i].user1.id == messageOutput.from.id || conversations[i].user2.id == messageOutput.from.id) {
                    if (currentConv == conversations[i].id && conversations[i].user1.id != conversations[i].user2.id) {
                        var messageTmp = '<div class="media">' +
                            '<a class="pull-left" href="#">' +
                            '<img src="' + (messageOutput.from.avatar == null ? defAvatar : messageOutput.from.avatar ) + '" class="photr2 media-object img img-responsive img-circle">' +
                            '</a>' +
                            '<div class="media-body">' +
                            '<h6 class="media-heading" style="color: #116898;">' + messageOutput.from.name + ' ' + messageOutput.from.lastName + '<span class="text-muted pull-right" style="font-weight: 300;font-size: 10px; margin-right: 15px;"> ' + moment(messageOutput.createdDate).calendar() + '</span></h6>' +

                            '<div style="width: 340px; word-wrap: break-word">' +
                            '<p>' + messageOutput.message + '</p>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                        $('#messages').append(messageTmp);
                        $('#messages').scrollTop($('#messages')[0].scrollHeight);
                    }
                }
            }
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage(msg, to) {
    if (msg == "") {
        return;
    }
    stompClient.send("/app/chat/" + to, {},
        JSON.stringify({'fromId': userId, 'message': msg}));
    setTimeout(function () {
        conv();
    }, 2300);

}

function showMessageOutput(messageOutput) {
    console.log(messageOutput);
}

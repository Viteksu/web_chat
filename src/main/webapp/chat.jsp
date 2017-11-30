<%@ page import="com.viteksu.kursach.web.backEnd.accounts.UserProfile"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Web Chat</title>
    <script>

    	class Message {

    		constructor() {
    			this.type = "MESSAGE";
    		}



    		parse(mess) {
    			var result = "";
    			var i = 1;
    			if (mess == null)
    				return;

    			if (mess.charAt(0) == "@" && mess.length > 1) {

    				for (; mess.charAt(i) != " " && i < mess.length; i++) {
    					result = result + mess.charAt(i);
    				}
    				this.recipient = result;
    				result = "";
    			} else {
    				this.recipient = "ALL";
    				i = 0;
    			}



    			for (; i < mess.length; i++) {
    				result = result + mess.charAt(i);
    			}

    			this.message = result;

    		}
    	}

    	var name;

            function init() {

                ws = new WebSocket("ws://localhost:8080/OOP/chatMess");

                ws.onopen = function (event) {
    				name = "<%= ((UserProfile) session.getAttribute("user")).getLogin()%>";
    				ws.send(name);
                }

                ws.onmessage = function (event) {

                    var $textarea = document.getElementById("messages");


    				var message = new Message();
    				JSON.parse(event.data, function(key, value) {


    					if (key == "type") {
    						message.type = value;
    					}
    					if (key == "recipient") {
    						message.recipient = value;
    					}
    					if (key == "sender") {
    						message.sender = value;
    					}
    					if (key == "message") {
    						message.message = value;
    					}

    					return message;
    				});

    				var $usersArea = document.getElementById("users");
    				var set = new Set();

    				if (message.type == "UPDATE_ADD") {
    					set.add(message.message);
    					$usersArea.value = "";

    					set.forEach( user => $usersArea.value = $usersArea.value + user);

    					return;
    				}

    				$textarea.value = $textarea.value + message.sender + ": " + message.message + "\n";
                }

                ws.onclose = function (event) {

                }
            };

            function sendMessage() {
                var messageField = document.getElementById("message");
                var userNameField = name;
                var message = userNameField + ":" + messageField.value;

    			var message = new Message();
    			message.parse(messageField.value);
    			message.sender = name;
    			var answer = JSON.stringify(message);
    			//console.log(answer);

    			ws.send(answer);


    			messageField.value = '';

            }
    	</script>
</head>
<body onload="init();">

<div id="body">
    <div id="menu">
        <p class="welcome">
            You signed in as <%= ((UserProfile) session.getAttribute("user")).getLogin()%>
        </p>

        <div style="clear: both"></div>
    </div>

    <div id="chatbox">
        <textarea id="messages" rows="20" cols="50" readonly="readonly"></textarea>
    </div>

    <form name="message" action="" onsubmit="sendMessage(); return false;">

        <form name="message" action="">
            <input name="usermsg" type="text" id="message" size="40"/>
            <input type="button" name="submitmsg" value="Send..." onclick="sendMessage();"/>
        </form>
    </form>
</div>

</body>
</html>

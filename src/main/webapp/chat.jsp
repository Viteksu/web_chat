	<%@ page import="com.viteksu.kursach.web.backEnd.accounts.UserProfile"%>
	<html>
	<head>
		<meta charset="UTF-8"/>
		<title>Web Chat</title>
		<!--<script language="JavaScript" src="http://local.host:8080/OOP/script.js></script>-->


		<script>
		class Message {

			constructor() {
				this.type = "MESSAGE";
			}

			parse(mess) {
				var result = "";
				var i = 1;
				if (mess == null) {
					return;
				}

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

		var onlineUsers = new Set();
		var chatingUsersIn = new Set();
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

		 if (message.type == "ERR") {

			 $textarea.value = $textarea.value + "ERROR \n";

			return;
		}


		if (message.type == "UPDATE_ADD") {

			onlineUsers.add(message.message);

			$usersArea.value = "";

			onlineUsers.forEach(function callback(item) {
				$usersArea.value = $usersArea.value + item + "\n";
			});

			return;
		}

		if (message.type == "MESSAGE") {
			var user;

			if (message.recipient == name) {
				user = name;
			} else {
				user = message.sender;
			}


			if (!chatingUsersIn.has(user) && (message.recipient != "ALL")) {
				chatingUsersIn.add(user);

				var parantElement = document.getElementById("tabs");
				var element = document.createElement('input');

				element.setAttribute("class", "tablink");
				element.setAttribute("type", "button");
				element.setAttribute("name", "inset");
				element.setAttribute("value", user);
				element.setAttribute("id", "button" + user);

				var openChat = "openChat('" + user + "', this);";
				element.setAttribute("onclick", openChat);


				parantElement.appendChild(element);


				parantElement = document.getElementById('chatbox');
				var element = document.createElement('textarea');

				element.setAttribute("class", "html-chat-history tabcontent");
				element.setAttribute("cols", "80");
				element.setAttribute("rows", "20");
				element.setAttribute("id", user);
				element.setAttribute("readonly", "readonly");

				parantElement.appendChild(element);


			}


			console.log("recipient " + message.recipient);
			var $textField = document.getElementById(user);



			if (message.recipient == "ALL") {
				$textarea.value = $textarea.value + message.sender + ": " + message.message + "\n";
			} else {
				$textField.value = $textField.value + message.sender + ": " + message.message + "\n";
			}

			return;
		}


		}

		ws.onerror = function (event) {
			  var $usersArea = document.getElementById("users");
			  $textarea.value = $textarea.value + "ERROR \nReson: " + event.code;
		}

		ws.onclose = function (event) {}

		};


		function sendMessage() {
			var messageField = document.getElementById("WebChatTextID");
			if (messageField.value.length == 0) {
				return;
			}
			var message = name + ":" + messageField.value;

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

	<!--<div id="body">
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
	</div> -->

	<div id="frame" class="frame-holder">


	<div id="usersBox" class="users-box">
		<h4><b>Online Users:</b><h4>
		<textarea class="html-users-box" id="users" rows="25" cols="25" readonly="readonly"></textarea>
	</div>

	<div id="html-chat">
		<div class="holder-html-chat">


			<div class="tabs" id = "tabs">

				<input class="tablink" type="button" name="in	" value="ALL" id="ALL" onclick="openChat('messages', this)">


				<!--<input class="tablink" type="button" name="inset" value="ALL" id="chatbox" onclick="openChat('messages1', this)">-->



				<div id="chatbox">
					<textarea class="html-chat-history tabcontent" id="messages" rows="20" cols="80" readonly="readonly"></textarea>
					<!--<textarea class="html-chat-history tabcontent" id="messages1" rows="20" cols="80" readonly="readonly"></textarea>-->
				</div>
			</div>



			<div class="html-chat-js-name">
				Hello <%= ((UserProfile) session.getAttribute("user")).getLogin()%>!
			</div>


			<textarea id="WebChatTextID" placeholder="Type message for chating.." class="html-chat-js-input"></textarea>

				<div class="html-chat-js-button-holder">


					<input type="button" name="submitmsg" value="Send.." onclick="sendMessage();"/>
						<div class="html-chat-js-answer">
							<a href="https://github.com/Viteksu/web_chat" id="answer" target="__blank">Sourses link</a>
						</div>
					</input>
				</div>
		</div>
	</div>
	</div>
	<script>
	function openChat(chatName,elmnt) {
		var i, tabcontent, tablinks;
		tabcontent = document.getElementsByClassName("tabcontent");
		for (i = 0; i < tabcontent.length; i++) {
			tabcontent[i].style.display = "none";
		}
		tablinks = document.getElementsByClassName("tablink");

		document.getElementById(chatName).style.display = "block";
	}
	// Get the element with id="defaultOpen" and click on it
	document.getElementById("ALL").click();
</script>
	<style>
		/* Здесь настроим css стили для чата*/
		.frame-holder{ border: 1px solid #ccc;padding:10px;background-color: #fff;width: 955px;}
		.holder-html-chat{ border: 1px solid #ccc;padding:10px;background-color: #fff;width: 600px; }
		.html-chat-history{ max-width: 600px; min-width: 200px; overflow: auto;max-height: 900px; min-height: 200px; border: 1px solid #ccc;padding: 5px;}
		.html-chat-js-name{ margin-top:7px; }
		.html-chat-js-input{ max-width: 600px; max-height: 100px; width: 600px; height: 45; margin-top:10px; min-width: 200px; min-height: 20px}
		.html-chat-js-button-holder{ margin-bottom: 0px;margin-top: 10px; }
		.html-chat-js-button-holder input{ width: 200px; }
		.html-chat-js-answer{ float:right; }
		.html-chat-js-answer a{ color: #777;font-size: 15px; font-family: cursive;}
		.html-chat-msg{ margin: 0px; }
		.users-box{position:fixed; top:20; left: 700; width:198px; border: 1px solid #ccc;padding:10px;background-color: #fff;}
		.html-users-box{resize: none}

		.html-chat-history{display:none;}
		.tabs { width: 100%; padding: 0px; margin: 0 auto; }
		.tabs>input { display:block float:left; width: 50px; height: 35px;}
		.tabs>div {
			padding: 0px;
			border: 1px solid #C0C0C0;
			background: #FFFFFF;
		}
		.tabs>label {
		display: inline-block;
		padding: 7px;
		margin: 0 -
		text-align: center;
		color: #666666;
		border: 1px solid #C0C0C0;
		background: #E0E0E0;
		}
		.tabs>input:checked + label {
		color: #000000;
		border: 1px solid #C0C0C0;
		border-bottom: 1px solid #FFFFFF;
		background: #FFFFFF;
}
#tab_1:checked ~ #txt_1,
#tab_2:checked ~ #txt_2,
#tab_3:checked ~ #txt_3,
#tab_4:checked ~ #txt_4 { display: block; }

		</style>
	</body>
	</html>

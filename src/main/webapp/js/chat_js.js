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
		var chattingUsersIn = new Set();
		var name;

		function init() {
		ws = new WebSocket("ws://localhost:8080/OOP/chatMess");

		ws.onopen = function (event) {
			name = document.getElementById("name").innerHTML;
			ws.send(name);
		}

		ws.onmessage = function (event) {

			var $textarea = document.getElementById("ALL");

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

		console.log(message);


		if (message.type == "HISTORY") {

			//$textarea.value = $textarea.value + message.sender + ": " + message.message + "\n";

			if (message.sender == name) {
				user = message.recipient;
			} else {
				user = message.sender;
			}

			if (!chattingUsersIn.has(user) && (message.recipient != "ALL")) {
				chattingUsersIn.add(user);

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
				element.setAttribute("onkeydown", "SendComment(event)");
				parantElement.appendChild(element);


			}


			var $textField = document.getElementById(user);



			if (message.recipient == "ALL") {
				$textarea.value = $textarea.value + message.sender + ": " + message.message + "\n";
			} else {
				$textField.value = $textField.value + message.sender + ": " + message.message + "\n";
			}

			return;
		}

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


		if (message.type == "UPDATE_DEL") {
			onlineUsers.delete(message.message);

			$usersArea.value = "";

			onlineUsers.forEach(function callback(item) {
				$usersArea.value = $usersArea.value + item + "\n";
			});

			return;
		}
		if (message.type == "MESSAGE") {
			var user;

			if (message.sender == name) {
				user = message.recipient;
			} else {
				user = message.sender;
			}


			if (!chattingUsersIn.has(user) && (message.recipient != "ALL")) {
				chattingUsersIn.add(user);

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
				element.setAttribute("onkeydown", "SendComment(event)");
				parantElement.appendChild(element);


			}


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

		function SendComment(e) {
			e = e || window.event;
			if (e.keyCode == 13 && e.ctrlKey) {
				sendMessage();
		};
};

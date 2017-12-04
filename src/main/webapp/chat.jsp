	<%@ page import="com.viteksu.kursach.web.backEnd.accounts.UserProfile"%>
	<html>
	<head>
		<meta charset="UTF-8"/>
		<title>Web Chat</title>
		<script type="text/javascript" src="/OOP/js/chat_js.js"></script>
		<link rel="stylesheet" href="/OOP/styles/style.css"></link>
	</head>
	<body onload="init();">

	<div id="frame" class="frame-holder">

	<div id="html-chat">
		<div class="holder-html-chat">
			<big>Happy chatting with <span id="clickedTab"></span>!</big>
			<div class="tabs" id = "tabs">


				<div id="chatbox">
					<textarea class="html-chat-history tabcontent" id="ALL" rows="20" cols="80" readonly="readonly"></textarea>
					<!--<textarea class="html-chat-history tabcontent" id="messages1" rows="20" cols="80" readonly="readonly"></textarea>-->
				</div>

				<input class="tablink" type="button" name="in" value="ALL" id="input_ALL" onclick="openChat('ALL', this)">

			</div>



			<div class="html-chat-js-name">
				You signed in as <span id="name"><%= ((UserProfile) session.getAttribute("user")).getLogin()%></span>!
			</div>


			<textarea id="WebChatTextID" placeholder="Type message for chatting.." class="html-chat-js-input" onkeydown="SendComment(event)"></textarea>

				<div class="html-chat-js-button-holder">


					<input type="button" name="submitmsg" value="Send.." onclick="sendMessage();"/>
						<div class="html-chat-js-answer">
							<a href="https://github.com/Viteksu/web_chat" id="answer" target="__blank">Sourses link</a>
						</div>
					</input>
				</div>
		</div>
	</div>
	<div id="usersBox" class="users-box">
		<big>Online Users:</big>
		<textarea class="html-users-box" id="users" rows="25" cols="25" readonly="readonly"></textarea>
	</div>
	</div>
	<script type="text/javascript" src="/OOP/js/tabs_js.js"></script>
	</body>
	</html>

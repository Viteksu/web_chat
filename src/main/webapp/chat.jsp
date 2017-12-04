	<%@ page import="com.viteksu.kursach.web.backEnd.accounts.UserProfile"%>
	<html>
	<head>
		<meta charset="UTF-8"/>
		<title>Web Chat</title>
		<!--<script language="JavaScript" src="http://local.host:8080/OOP/script.js></script>-->
		<script type="text/javascript" src="/OOP/js/chat_js.js"></script>
	</head>
	<body onload="init();">

	<div id="frame" class="frame-holder">

	<div id="html-chat">
		<div class="holder-html-chat">
			<big>Happy chatting!</big>
			<div class="tabs" id = "tabs">

				<!--<input class="tablink" type="button" name="inset" value="ALL" id="chatbox" onclick="openChat('messages1', this)">-->



				<div id="chatbox">
					<textarea class="html-chat-history tabcontent" id="messages" rows="20" cols="80" readonly="readonly"></textarea>
					<!--<textarea class="html-chat-history tabcontent" id="messages1" rows="20" cols="80" readonly="readonly"></textarea>-->
				</div>

				<input class="tablink" type="button" name="in	" value="ALL" id="ALL" onclick="openChat('messages', this)">

			</div>



			<div class="html-chat-js-name">
				You signed in as <span id="name"><%= ((UserProfile) session.getAttribute("user")).getLogin()%></span>!
			</div>


			<textarea id="WebChatTextID" placeholder="Type message for chatting.." class="html-chat-js-input"></textarea>

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

	<style>
		/* Здесь настроим css стили для чата*/
		.frame-holder{ border: 1px solid #ccc;padding:10px;background-color: #fff;width: 955px; margin-left: auto; margin-right: auto; height: auto;}
		.holder-html-chat{ border: 1px solid #ccc;padding:10px;background-color: #fff;width: 600px; }
		.html-chat-history{ max-width: 600px; min-width: 200px; overflow: auto;max-height: 900px; min-height: 200px; border: 1px solid #ccc;padding: 5px; width: 600;}
		.html-chat-js-name{ margin-top:7px; }
		.html-chat-js-input{ max-width: 600px; max-height: 100px; width: 600px; height: 45; margin-top:10px; min-width: 200px; min-height: 20px}
		.html-chat-js-button-holder{ margin-bottom: 0px;margin-top: 10px; }
		.html-chat-js-button-holder input{ width: 200px; }
		.html-chat-js-answer{ float:right; }
		.html-chat-js-answer a{ color: #777;font-size: 15px; font-family: cursive;}
		.html-chat-msg{ margin: 0px; }
		.html-users-box{resize: none}
		.users-box {
			position: relative;
			top: -505px;
			left: 680px;
			width: 198px;
			border: 1px solid #ccc;
			padding: 10px;
			background-color: #fff;
		}

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


		</style>
	</body>
	</html>

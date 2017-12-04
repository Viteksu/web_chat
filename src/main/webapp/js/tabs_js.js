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
	
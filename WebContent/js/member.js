function infoConfirm() {
	if(document.reg_form.id.value.length == 0){
		alert("id를 입력해 주세요.");
		reg_form.id.focus();
		return;
	}
	
	if(document.reg_form.id.value.length < 4) {
		alert("id의 길이는 4자 이상으로 해주세요.");
		reg_form.id.focus();
		return;
	}
	
	if(document.reg_form.pw.value.length == 0) {
		alert("pw를 입력해주세요.");
		reg_form.pw.focus();
		return;
	}
	
	if(document.reg_form.pw.value != document.reg_form.pw_check.value) {
		alert("pw가 같지 않습니다.");
		reg_form.pw.focus();
		return;
	}
	
	if(document.reg_form.name.value.length == 0) {
		alert("이름을 입력해주세요.");
		reg_form.name.focus();
		return;
	}
	
	if(document.reg_form.address.value.length == 0) {
		alert("주소를 입력해주세요.");
		reg_form.id.focus();
		return;
	}
	
	if(document.reg_form.eMail.value.length == 0) {
		alert("email주소를 입력해주세요.");
		reg_form.eMail.focus();
		return;
	}
	
	document.reg_form.submit();
}

function updateInfoConfirm() {
	if(document.reg_form.pw.value == "") {
		alert("비밀번호를 입력해주세요.");
		document.reg_form.pw.focus();
		return;
	}
	
	if(document.reg_form.pw.value != document.reg_form.pw_check.value) {
		alert("비밀번호가 같지 않습니다.");
		reg_form.pw.focus();
		return;
	}
	
	if(document.reg_form.eMail.value.length == 0) {
		alert("email 주소를 입력해주세요.");
		reg_form.eMail.focus();
		return;
	}
	
	document.reg_form.submit();
}
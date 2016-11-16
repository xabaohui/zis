//判断密码是否重复
function comparePassword() {
	var password = document.getElementById('password').value;
	var passwordAgain = document.getElementById('passwordAgain').value;
	var hideErrors = document.getElementsByClassName('showError');
	if(!(password == passwordAgain)) {
		for(var i = 0; i < hideErrors.length; i++){
			hideErrors[i].style = "display:none";
		}
		document.getElementById('errorPasswordAgain').style="display:block;";
	}else{
		document.getElementById('errorPasswordAgain').style="display:none";
	}
}

//判断密码是否重复，如果不重复form表单提交
function comparePasswordSubmit() {
	var password = document.getElementById('password').value;
	var passwordAgain = document.getElementById('passwordAgain').value;
	var hideErrors = document.getElementsByClassName('showError');
	if(!isChecked()){
		return;
	}
	if(!(password == passwordAgain)) {
		document.getElementById('errorPasswordAgain').style="display:block;";
		return;
	}
		document.getElementById('errorPasswordAgain').style="display:none";
		document.getElementById('registFrom').submit();
}

//全选
function checkAll(ClassName, idName){
	var all = document.getElementById(idName);
	var list= document.getElementsByClassName(ClassName);
	for(var i = 0; i < list.length; i++){
		if(all.checked){
			list[i].checked=true;
		}else{
			list[i].checked=false;
		}
	}
}

//判断是否选择权限
function isChecked(){
	var permissionIds = document.getElementsByName("permissionIds");
	var a = 0;
	for(var i = 0; i < permissionIds.length; i++){
		if(permissionIds[i].checked){
			a++;
		}
	}
	if(a == 0){
		alert("您没有选择权限");
		return false;
	}
	return true;
}

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

// 判断密码是否重复，如果不重复form表单提交
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

// 判断密码是否重复，如果不重复form表单提交
function passwordSubmit() {
	var password = document.getElementById('password').value;
	var passwordAgain = document.getElementById('passwordAgain').value;
	var hideErrors = document.getElementsByClassName('showError');
	if(!(password == passwordAgain)) {
		document.getElementById('errorPasswordAgain').style="display:block;";
		return;
	}
	document.getElementById('errorPasswordAgain').style="display:none";
	document.getElementById('registFrom').submit();
}

// 判断是否选择了权限
function permissionCheckedSubmit() {
	if(!isChecked()){
		return;
	}
	document.getElementById('registFrom').submit();
}

// 全选
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

// 判断是否选择权限
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

//选中RoleId和companyId
function checkRoleIdAndCompanyId() {
	var checkedIds = document.getElementById("checkedId");
	var roleIds= document.getElementsByName("roleId");
	var checkedCompanyId = document.getElementById("checkedCompanyId");
	var companyIds= document.getElementsByName("companyId");
		var checkedIdValue = checkedIds.value;
		var checkedCompanyIdValue = checkedCompanyId.value;
		for(var j = 0; j < roleIds.length; j++){
			var roleIdValue = roleIds[j].value;
			if(+checkedIdValue == +roleIdValue){
				roleIds[j].checked=true;
			}
		}
		for(var i = 0; i < companyIds.length; i++){
			var companyIdValue = companyIds[i].value;
			if(+checkedCompanyIdValue == +companyIdValue){
				companyIds[i].checked=true;
			}
		}
}

function getRoleName(roleName){
	document.getElementById("roleName").value = roleName;
}

function getCompanyName(companyName){
	document.getElementById("companyName").value = companyName;
}

//修改密码是否修改选中
function updateCheck(){
	var checkUpdate = document.getElementById("updatePassword");
	var passwordClass = document.getElementsByClassName("passwordClass");
	if(checkUpdate.checked){
		for(var i = 0; i < passwordClass.length; i++){
			passwordClass[i].style="display:inherit;";
		}
	}else{
		for(var i = 0; i < passwordClass.length; i++){
			passwordClass[i].style="display:none;";
		}
	}
}

function clearAll() {
	var list = document.getElementsByTagName('input');
	for ( var i = 0; i < list.length; i++) {
		if(list[i].getAttribute("type") == "text"){
			list[i].value = "";
		}
	}
}

function queryShopItemMapping(shopId) {
	window.location.href = "/zis/shop/queryShopItemMapping?shopId=" + shopId;
}

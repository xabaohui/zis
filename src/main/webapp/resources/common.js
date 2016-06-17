function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g, "");
}


function showShadow() {
	document.getElementById("bg-to-be-hidden").style.display = "block";
	document.getElementById("float-to-be-show").style.display = "block";
}

function hideShadow() {
	document.getElementById("bg-to-be-hidden").style.display = "none";
	document.getElementById("float-to-be-show").style.display = "none";
}

//全选
function checkAll() {
	var boxlist = document.getElementsByName("batchSelectedItem");
	var boxAll = document.getElementsByName("allCheck")[0];
	for ( var i = 0; i < boxlist.length; i++) {
		if (boxlist[i].disabled == false) {
			if (boxAll.checked == true) {
				boxlist[i].checked = true;
			} else {
				boxlist[i].checked = false;
			}
		}
	}
}
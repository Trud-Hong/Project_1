	function isDelete(){
	    if(confirm("정말로 삭제하시겠습니까?")) {
	        document.getElementById("communityDeleteForm").submit();
	    }
	}

	function openWriteForm() {
	    location.href = "<%=cp%>/community/write.do";
	}

	function likePost(postId) {
		location.href="<%=cp%>/communityLike/" + postId;
	}

	function viewPost(postId) {
		location.href="<%=cp%>/communityView/" + postId;
	}
	
function sendIt(){
    var f = document.searchForm;
    f.action = CONTEXT_PATH + "/community.do";
    f.method = "get";
    f.submit();
}
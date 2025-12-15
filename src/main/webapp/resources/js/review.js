	function isDelete(){
	    if(confirm("정말로 삭제하시겠습니까?")) {
	        document.getElementById("reviewDeleteForm").submit();
	    }
	}

	function openWriteForm() {
	    location.href = "<%=cp%>/review/write.do";
	}
	
function sendIt(){
    var f = document.searchForm;
    f.action = CONTEXT_PATH + "/review.do";
    f.method = "get";
    f.submit();
}
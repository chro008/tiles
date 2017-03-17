<div style="height: 200px;">
	<div>hello,<%=request.getAttribute("name") %></div>
	<button id="button">update</button>
	<span id="input"></span>
</div>

<script src="script/jquery.js" type="text/javascript" ></script>

<script type="text/javascript">
$("#button").click(function(){
	$.ajax({  
			type:"post",
			url : "traffic.do?action=homepage&operate=getHtml",
			contentType:"application/x-www-form-urlencoded;charset=utf-8",   
			dataType : "html",			
			data:{},
			async: true,  
			success:function(returnobj,status,xhr){
				$("#input").html(returnobj);
			}  
		}); 	
});
</script>
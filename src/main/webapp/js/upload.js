$(function () {
    $('#fileupload').fileupload({
    	maxChunkSize: 1000000 // 1 MB
    });
});

function findFiles(){
	$.get( "/file", function( data ) {
		let trHTML;  
		 $(".linha").remove();
		$.each(data,function(i, item){
			  console.log(item);
			  trHTML += '<tr class="linha">';
			  trHTML +=	'<td>' + item.identification + '</td>';
			  trHTML +=	'<td>' + item.fileName + '</td>';
			  trHTML +=	'<td>' + item.status + '</td>';
			  trHTML +=	'<td>' + item.sendingTime + '</td>';
			  trHTML +=	'<td>' + item.chunksQuantity + '</td>';
			  trHTML +=	'<td>' + item.linkDownloadFile + '</td>';
			  trHTML += '</tr>';
		  });
		  $('#records').append(trHTML);
	});
}



function log_in(){

	
  var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_USER_ID" ,
          		  user_name : $("#user_name").val()           
              }
        });

        request.done(function( registros ) {
        
          var register = JSON.parse(registros);
                $.each(register, function (index, user) {
                	console.log(user.ID_USER);
            	   window.location.href = "index.php?user_id="+user.ID_USER;
      
                });

        });
          
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });



}
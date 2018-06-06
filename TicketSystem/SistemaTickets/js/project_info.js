$( document ).ready(function() {
get_user_name();
get_project_name();
});   



function get_project_name(){

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_PROJECT_NAME",
                 id_project : $("#project_id").val()           
              }
        });
        request.done(function( registros ) {
         var register = JSON.parse(registros);
          $.each(register, function (index, user) {
          $("#current_project_name").append(user.NAME);
         
          });
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });


}


function get_user_name(){

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_USER_NAME",
                id_user : $("#user_id").val()            
              }
        });
        request.done(function( registros ) {
         var register = JSON.parse(registros);
          $.each(register, function (index, user) {
            $("#user_loged_in").append(user.NAME);
         
          });
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });


}


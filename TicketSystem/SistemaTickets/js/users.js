$( document ).ready(function() {
var id_user = $("#user_id").val();

get_all_users();
get_users_projects();
$("a.myTicket").prop("href", "tickets.php?id="+$("#project_id").val()+"&user_id="+id_user);
$("a.mydash").prop("href", "index.php?user_id="+id_user);

});      

function add_user_project(){

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "ADD_USER_PROJECT",
          id_user : $("#all_users").val(),
          id_project :$("#project_id").val(),
          rol : $("#rol_user").val()

                  
              }
        });
        request.done(function( registros ) {
                 
       
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function add_user(){

  check_user_exists();
  get_users_projects();

}

function delete_user_project(){

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "DELETE_USER_PROJECT",
                  id_user : $("#delete_user").val(),
                  id_project :$("#project_id").val()
                  
              }
        });
        request.done(function( registros ) {
        
        get_users_projects();
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });


}




function check_user_exists(){

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "EXIST_USER",
          id_user : $("#all_users").val(),
          id_project : $("#project_id").val()
        
                  
              }
        });
        request.done(function( registros ) {
          var register = JSON.parse(registros);
           $.each(register, function (index, user) {
          if(user.EXISTE == 0){
            add_user_project();      
          }else { alert("El usuario ya existe en el proyecto");}  
                
                });
          
                
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });



}

function get_all_users(){
	
var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_USERS"
                  
              }
        });
        request.done(function( registros ) {
          var register = JSON.parse(registros);
                $.each(register, function (index, user) {
					$("#all_users").append('<option value="'+user.ID_USER+'">'+user.NAME+'</option>');           
                });

        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function get_users_projects(){

$("#user_project_table_body").empty();
 $("#delete_user").empty();

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_USERS_PROJECTS",
          		  id_project : $("#project_id").val()
                  
              }
        });
        request.done(function( registros ) {
          var register = JSON.parse(registros);
                $.each(register, function (index, user) {
        				 $("#user_project_table_body").append('<tr> <td>'+user.name+'</td><td>'+user.USER_ROL+'</td></tr>')
                 $("#delete_user").append('<option value="'+user.ID_USER+'">'+user.name+'</option>')

                });
	       $("#user_project_table").dataTable();

        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });


}


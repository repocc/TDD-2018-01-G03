

$( document ).ready(function() {
var id_proyecto;
var id_user = $("#user_id").val();
get_projects();

});      


function get_projects(){

$("#projects").empty();
      var request = $.ajax({
          url: "PROJECT_BG.PHP",
          method: "POST",
          data: { case: "GET_PROJECTS",
                  id_user: $("#user_id").val()   
              }
        });

        request.done(function( registros ) {
                var register = JSON.parse(registros);
                $.each(register, function (index, project) {

                if(project.USER_ROL == "ADMIN"){
                  console.log(project.USER_ROL);
                 $("#projects").append('  <div class="col-xl-3 col-sm-6 mb-3">\
                  <div class="card text-white bg-primary o-hidden h-100">\
                    <div class="card-body">\
                      <div class="card-body-icon">\
                      </div>\
                      <div class="mr-5"><i class="fa fa-address-card-o" aria-hidden="true"></i> '+project.name+'</div>\
                    </div>\
                    <div class="card-footer  ">\
                      <a href="tickets.php?id='+project.id_proyect+'&user_id='+$("#user_id").val()+'" class= "text-white"><span class="float-right">Ver</span></a>\
                    <button onclick=delete_project('+project.id_proyect+');><span class="float-right"><i class="fa fa-trash" aria-hidden="true"></i></span></button>\
                    </div>\
                  </div>\
                </div>');
                    }else {

                 $("#projects").append('  <div class="col-xl-3 col-sm-6 mb-3">\
                  <div class="card text-white bg-primary o-hidden h-100">\
                    <div class="card-body">\
                      <div class="card-body-icon">\
                      </div>\
                      <div class="mr-5"><i class="fa fa-user" aria-hidden="true"></i> '+project.name+'</div>\
                    </div>\
                    <div class="card-footer  ">\
                      <a href="tickets.php?id='+project.id_proyect+'&user_id='+$("#user_id").val()+'" class= "text-white"><span class="float-right">Ver</span></a>\
                    </div>\
                  </div>\
                </div>');


                    }
                

                });
      
        });
          
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });
}




function new_project(){
$('#new_project').modal('toggle');
}


function delete_project(id){

 var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "DELETE_PROJECT",
                  id_project : id
              }
        });

        request.done(function( registros ) {
        
         get_projects(1);

        });
          
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function create_project(){


      var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "ADD_PROJECT",
                  name: $("#project_name").val()   
              }
        });

        request.done(function( registros ) {
        
        last_id_project();     
        add_user_to_project();    
        $('#new_project').modal('hide');
 
      
        });
          
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });



}

function add_user_to_project(){


  var project = id_proyecto;
  console.log(project);
 var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "ADD_USER_PROJECT",
                  id_project: project,
                  id_user : $("#user_id").val(),
                  rol : "ADMIN"

              }
        });

        request.done(function( registros ) {       
          get_projects();
        });
          
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });


}



function last_id_project(){

      var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "LAST_PROJECT_ID"                
              }
        });

        request.done(function( registros ) {
        
          var register = JSON.parse(registros);
                $.each(register, function (index, project) {

                 id_proyecto= project.id_proyecto;
              //  add_user_to_project() ;  
                });

        });
          
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}
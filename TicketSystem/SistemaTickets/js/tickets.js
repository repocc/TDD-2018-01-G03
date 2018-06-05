$( document ).ready(function() {
var id_user = $("#user_id").val();
var id_ticket_comment;
var id_ticket;
var id_ticket_state;
get_tickets();
var select_state;
get_states();
$("a.mylink").prop("href", "project_users.php?id="+$("#project_id").val()+"&user_id="+id_user);
$("a.mylinks").prop("href", "index.php?user_id="+id_user);

});      

function new_ticket(){
$("#new_ticket").modal('toggle');
}

function get_states(){

  var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_STATES"
                    
              }
        });
        request.done(function( registros ) {
       
                 var register = JSON.parse(registros);
                $.each(register, function (index, state) {
                $("#select_state").append('<option value="'+state.ID_TICKET_STATE+'">'+state.NAME+'</option>');
                });
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function get_tickets(){
  $("#ticket_table_body").empty();
var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_TICKETS",
                id_project : $("#project_id").val()                
              }
        });
        request.done(function( registros ) {
         var register = JSON.parse(registros);
                $.each(register, function (index, ticket) {
                var label_state;
                switch (ticket.state_name){
                  case 'OPEN':
                  label_state = '<span class="label label-success">'+ticket.state_name+'</span>';
                  break;
                   case 'TODO':
                  label_state = '<span class="label label-info">'+ticket.state_name+'</span>';
                  break;
                   case 'CLOSE':
                  label_state = '<span class="label label-danger">'+ticket.state_name+'</span>';
                  break;
                   case 'IN PROGRESS':
                  label_state = '<span class="label label-warning">'+ticket.state_name+'</span>';
                  break;
                   case 'DONE':
                  label_state = '<span class="label label-primary">'+ticket.state_name+'</span>';
                  break;
                  default:
                  label_state = ticket.state_name;

                }
              
                $("#ticket_table_body").append('<tr><td>'+ticket.NAME+'</td><td>'+ticket.DESCRIPTION+'</td><td>'+label_state+'</td><td align="center"><button type="button" class="btn btn-success" onclick="change_state('+ticket.ID_TICKET+')";> <span class="fa fa-edit"></span></button><button onclick="view_comment('+ticket.ID_TICKET+');" type="button" class="btn btn-info"><span class="fa fa-comment"></span></button></td></tr>')    
                });
                $("#ticket_table").DataTable( {"pageLength": 5});

        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function change_state(id){
id_ticket_state= id;
$("#modal_state").modal('toggle');


}

function save_new_ticket() {
	var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "ADD_TICKET",
          		  ticket_name : $("#ticket_name").val(),
          		  ticket_description : $("#ticket_description").val(),
          		  ticket_state : $("#ticket_state").val(),
          		  id_project : $("#project_id").val()
                  
              }
        });
        request.done(function( registros ) {
          //1 es id de open
        send_new_ticket(1);
        if($("#ticket_comment").val() != ""){
        save_comment();
        }
        var table =$("#ticket_table").DataTable();
        table.destroy();
        get_tickets();
        $("#new_ticket").modal('hide');

        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });
}

function save_comment(){
  last_ticket_id();
  var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "ADD_COMMENT",
                ticket_id : id_ticket,
                ticket_comment : $("#ticket_comment").val(),
                user_id : $("#user_id").val()
               
                  
              }
        });
        request.done(function( registros ) {
       
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function send_new_ticket(id) {
 
  var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_STATE_NAME",
                id_state : id
                                 
              }
        });
        request.done(function( registros ) {
        var register = JSON.parse(registros);
          $.each(register, function (index, state) {

              connect_server(state.NAME);
          });

        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

    
}

function connect_server(state_name){

var state = state_name.replace(" ", "+");
var obj = JSON.parse('{ "'+state_name+'":"*"}');
        $.ajax({

            type : "POST",
            contentType : "application/x-www-form-urlencoded; charset=utf-8",
            url : "http://localhost:9000/ticketUpdateG3",
            data : obj,
            cache : false,
            success : function(response) {
                console.log("PASO");
            },
            error : function(xhr, textStatus, errorThrown) {
                console.log("error : " + textStatus);
            }
        });
}

function last_ticket_id(){

var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "LAST_TICKET_ID"                
                }
        });

        request.done(function( registros ) {   
          var register = JSON.parse(registros);
                $.each(register, function (index, ticket) {
                 id_ticket= ticket.id_ticket;
                });
        });
       
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });
}

function edit_state(){


var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "EDIT_TICKET_STATE",
                  id_ticket : id_ticket_state,
                  id_state :  $("#select_state").val()              
              }
        });

        request.done(function( registros ) {    
          $("#ticket_table").DataTable().destroy();
          get_tickets();
          send_new_ticket($("#select_state").val() );
          $("#modal_state").modal('hide');
        });                
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}


function view_comment(id_ticket){
  id_ticket_comment = id_ticket;
  get_comments(id_ticket);
  $("#comment_modal").modal('toggle');
}



function get_comments(id){

  $("#comments").empty();
  var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "GET_COMMENTS",
                  id_ticket : id                
              }
        });

        request.done(function( registros ) {
        var register = JSON.parse(registros);
          $.each(register, function (index, comment) {

                 $("#comments").append(' <a class="list-group-item list-group-item-action" >\
                <div class="media">\
                  <img class="d-flex mr-3 rounded-circle" src="user-2.png" width="40px" height="40px" alt="">\
                  <div class="media-body">\
                    <strong>'+comment.NAME+'</strong> '+comment.DATE+'\
                    <div class="text-muted smaller">'+comment.COMMENT+'</div>\
                  </div>\
                </div>\
              </a>');

                });

        });
                
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}

function save_new_comment(){
  
  var request = $.ajax({
          url: "PROJECT_BG.PHP",
          async:false,
          method: "POST",
          data: { case: "ADD_COMMENT",
                ticket_id : id_ticket_comment,
                ticket_comment : $("#new_comment_text").val(),
                user_id : $("#user_id").val()
               
                  
              }
        });
        request.done(function( registros ) {
       
        });
              
        request.fail(function( jqXHR, textStatus ) {
          alert( "Request failed: " + textStatus );
        });

}
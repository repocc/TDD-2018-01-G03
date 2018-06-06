<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Ticket System</title>
  <!-- Bootstrap core CSS-->

  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <!-- Custom fonts for this template-->
  <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
  <!-- Page level plugin CSS-->
  <link href="vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">
  <!-- Custom styles for this template-->
  <link href="css/sb-admin.css" rel="stylesheet">
  <style type="text/css">
    .label-success {
    background-color: #5cb85c;
}
.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
}

.label-primary {
    background-color: #337ab7;
}
.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
}

.label-info {
    background-color: #5bc0de;
}
.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
}

.label-warning {
    background-color: #f0ad4e;
}
.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
}

.label-danger {
    background-color: #d9534f;
}
.label {
    display: inline;
    padding: .2em .6em .3em;
    font-size: 75%;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
}

.list-group {
    max-height: 300px;
    overflow-y: auto;
}
  </style>
</head>
<input type="hidden" id="project_id" value="<? if ($_GET['id']!=''){echo $_GET['id']; }else{echo '';} ?>"/>
<input type="hidden" id="user_id" value="<? if ($_GET['user_id']!=''){echo $_GET['user_id']; }else{echo '';} ?>"/>

<body class="fixed-nav sticky-footer bg-dark" id="page-top">
  <!-- Navigation-->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="index.html">Ticket System</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
      <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
      
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
          <a id="project_link" class="nav-link mylink" >
            <i class="fa fa-fw fa-user"></i>
            <span class="nav-link-text">Users</span>
          </a>
        </li>
        <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Tables">
          <a class="nav-link" href="">
            <i class="fa fa-fw fa-ticket"></i>
            <span class="nav-link-text">Tickets<span>
          </a>
        </li>
       
      
      
      
      </ul>
      <ul class="navbar-nav sidenav-toggler">
        <li class="nav-item">
          <a class="nav-link text-center" id="sidenavToggler">
            <i class="fa fa-fw fa-angle-left"></i>
          </a>
        </li>
      </ul>
      <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
         
          
        </li>
        <li class="nav-item dropdown">
      
        </li>
        <li class="nav-item">
         <a class="nav-link" id="user_loged_in">          
            <i class="fa fa-fw fa-user" ></i> </a>
        
        </li>
        <li class="nav-item">

          <a class="nav-link" data-toggle="modal" data-target="#exampleModal">          
            <i class="fa fa-fw fa-sign-out"></i>Logout</a>
        </li>
      </ul>
    </div>
  </nav>
  <div class="content-wrapper">
    <div class="container-fluid">
      <!-- Breadcrumbs-->
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <a class= "mylinks">Dashboard</a>
        </li>
        <li class="breadcrumb-item active"  id="current_project_name">Project name: </li>
      </ol>
     
      <!-- Area Chart Example-->
       <div class="card mb-3">
        <div class="card-header">
          <i class="fa fa-cogs"></i> Options</div>
        <div class="card-body">
        <button type="button" class="btn btn-success" onclick="new_ticket();">Add Ticket</button> 
      
        </div>
        </div>
       

            <div class="card mb-3">
        <div class="card-header">
          <i class="fa fa-ticket"></i> Tickets</div>
        <div class="card-body">

        <table class="table table-bordered" id="ticket_table" width="100%" cellspacing="0">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Description</th>
                 
                  <th>State</th>
                  <th>Action</th>              
                </tr>
              </thead>
             
              <tbody id="ticket_table_body">
                
                </tbody>
                </table>
          
        </div>
        <div class="card-footer small text-muted"></div>
      </div>
      <div class="row">
        <div class="col-lg-8">
          
         
        
         
         
        </div>

      </div>

    </div>
    <!-- /.container-fluid-->
    <!-- /.content-wrapper-->
    <footer class="sticky-footer">
      <div class="container">
        <div class="text-center">
          <small>TDD © 2018</small>
        </div>
      </div>
    </footer>
    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
      <i class="fa fa-angle-up"></i>
    </a>
    <!-- Logout Modal-->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
            <button class="close" type="button" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">×</span>
            </button>
          </div>
          <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
          <div class="modal-footer">
            <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
            <a class="btn btn-primary" href="login.html">Logout</a>
          </div>
        </div>
      </div>
    </div>
    <div class="modal" tabindex="-1" role="dialog" id="new_ticket">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">New ticket</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-lg-6">       
           <label>Name:</label>
           </div>
           <div class="col-lg-6">      
           <input id="ticket_name" type="" name="">
       </div>
       </div>
       <div class="row">
            <div class="col-lg-6">   
          
         
         </div>
                <div class="col-lg-6">   
         <input id="ticket_state" value="1" type="" name="" disabled="" placeholder="OPEN" hidden="">
         </div>
       </div>
          <div class="row">
            <div class="col-lg-6">   
          
         <label>Description:</label>
         </div>
                <div class="col-lg-6">   
         <textarea id="ticket_description" placeholder="Description"></textarea>
         </div>
       </div>

        <div class="row">
            <div class="col-lg-6">   
          
         <label>Comment:</label>
         </div>
                <div class="col-lg-6">   
         <textarea id="ticket_comment" placeholder="Comment"></textarea>
         </div>
       </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="save_new_ticket();">Save</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<div class="modal" tabindex="-1" role="dialog" id="modal_state">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Change state</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
      <div class="row">
        <div class="col-lg-2">       
           <label>State:</label>
      </div>
           <div class="col-lg-2">      
          <select id="select_state"></select>
       </div>
       </div>
     
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="edit_state();">Guardar</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>

<div class="modal" tabindex="-1" role="dialog" id="comment_modal">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
     
      <div class="modal-body">
    
<div class="card mb-3">
            <div class="card-header">
              <i class="fa fa-comment"></i> Comments</div>
            <div class="list-group list-group-flush small" id="comments">
          
             </div>

</div>
<div class="card mb-3">
            <div class="card-header">
              Leave a comment</div>
            <div class="list-group list-group-flush small" >
           
            <textarea id="new_comment_text"></textarea>


             </div>

</div>
          
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="save_new_comment()">Send comment</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
    <!-- Page level plugin JavaScript-->
    <script src="vendor/chart.js/Chart.min.js"></script>
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin.min.js"></script>
    <!-- Custom scripts for this page-->
    <!-- <script src="js/sb-admin-datatables.min.js"></script>
      <script src="js/sb-admin-charts.min.js"></script> -->
    <script src="js/tickets.js"></script>
    <script src="js/project_info.js"></script>

  </div>

</body>

</html>

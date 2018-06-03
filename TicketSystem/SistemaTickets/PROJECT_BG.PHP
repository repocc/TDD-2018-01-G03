<?php
 	error_reporting(E_ALL);
ini_set('display_errors', '1');
include_once('../cnnMySql.php');

switch($_REQUEST['case'])
{

	case "GET_PROJECTS":

			$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_PROJECTS";
			$AltaF->parametros = array($_REQUEST['id_user']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "ADD_PROJECT":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "ADD_PROJECT";
			$AltaF->parametros = array($_REQUEST['name']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "LAST_PROJECT_ID":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "LAST_ID_PROJECT";
			$AltaF->parametros = array();
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;
	case "LAST_TICKET_ID":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "LAST_TICKET_ID";
			$AltaF->parametros = array();
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "ADD_USER_PROJECT":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "ADD_PROJECT_USER";
			$AltaF->parametros = array($_REQUEST['id_user'],$_REQUEST['id_project'],$_REQUEST['rol']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

    case "DELETE_PROJECT":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "DELETE_PROJECT";
			$AltaF->parametros = array($_REQUEST['id_project']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;
		
    case "GET_USERS":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_USERS";
			$AltaF->parametros = array();
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "GET_USERS_PROJECTS":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_USERS_PROJECT";
			$AltaF->parametros = array($_REQUEST['id_project']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "ADD_TICKET":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "ADD_TICKET";
			$AltaF->parametros = array($_REQUEST['ticket_name'],$_REQUEST['ticket_state'],$_REQUEST['id_project'],$_REQUEST['ticket_description']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "ADD_COMMENT":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "ADD_COMMENT";
			$AltaF->parametros = array($_REQUEST['ticket_id'],$_REQUEST['ticket_comment'],$_REQUEST['user_id']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;
	case "GET_TICKETS":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_TICKETS";
			$AltaF->parametros = array($_REQUEST['id_project']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "ADD_USER_PROJECT":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "ADD_PROJECT_USER";
			$AltaF->parametros = array($_REQUEST['id_user'],$_REQUEST['id_project'],$_REQUEST['rol']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;	

	case "EXIST_USER":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "EXIST_PROJECT_USER";
			$AltaF->parametros = array($_REQUEST['id_user'],$_REQUEST['id_project']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;

	case "DELETE_USER_PROJECT":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "DELETE_PROJECT_USER";
			$AltaF->parametros = array($_REQUEST['id_project'],$_REQUEST['id_user']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;	

	case "GET_STATES":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_STATES";
			$AltaF->parametros = array();
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;	

	case "EDIT_TICKET_STATE":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "EDIT_TICKET_STATE";
			$AltaF->parametros = array($_REQUEST['id_ticket'],$_REQUEST['id_state']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;	

	case "GET_STATE_NAME":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_STATE_NAME";
			$AltaF->parametros = array($_REQUEST['id_state']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;	

	case "GET_USER_ID":
	$AltaF = new cnnMySql;
			$AltaF->Procedimiento  = "GET_USER_ID";
			$AltaF->parametros = array($_REQUEST['user_name']);
			$AltaF->procesarPro();
			$AltaF->datosObjeto= true;
			print_r(json_encode($AltaF->registros));
	break;	
}
?>
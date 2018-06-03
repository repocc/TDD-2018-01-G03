<?php
ini_set('display_errors', 0);
error_reporting(E_ERROR | E_WARNING | E_PARSE); 	

	class cnnMySql
	{
		/******************************************************************\
		|CLASE PARA REALIZAR LAS CONEXIONES CON UN SERVIDOR MYSQL DESDE PHP|
		\******************************************************************/
		/*******************************************\
		|DEFINO LAS PORPIEDADES PÚBLICAS DE LA CLASE|
		\*******************************************/
		/****************************************************************************\
		|CONTIENE EL NOMBRE DEL SERVIDOR DE BASE DE DATOS AL QUE QUEREMOS CONECTARNOS|
		\****************************************************************************/
		private $servidor       = "localhost";
		/**********************************************************************\
		|CONTIENE EL NOMBRE DE USUARIO PARA LA CONEXIÓN CONTRA LA BASE DE DATOS|
		\**********************************************************************/
		private $usuario        = "nahuel";
		/********************************************************************************\
		|CONTIENE EL NOMBRE DE LA BASE DE DATOS A LA QUE APUNTAREMOS CON NUESTRA CONEXIÓN|
		\********************************************************************************/
		private $baseDato       = "Ticket_system";
		/****************************************************************************************\
		|CONTIENE LA CLAVE DE LA BASE DE DATOS CORREPONDIENTE AL USUARIO UTILIZADO EN LA CONEXIÓN|
		\****************************************************************************************/
		private $contrasena     = "1234";
		/************************************************\
		|CONTIENE EL ID DE CONEXIÓN DEVUELTO POR LA CLASE|
		\************************************************/
		private $idConexion     = "";
		/************************************************************************************************\
		|CONTIENE EL ID DE REGISTROS PARA OBTENER UN ARRAY CON EL CONTENIDO QUE RETORNE LA BASE DE DATOS.|
		\************************************************************************************************/
		private $idRegistros      = "";
		/*********************************************\
		|CONTIENE LA CONSULTA SQL QUE SE VA A EJECUTAR|
		\*********************************************/
		public $spSql           = "";
		public $mayuscula           = true;
		/**************************************************************\
		|CONTIENE EL CONJUNTO DE REGISTROS QUE RETORNA LA SENTENCIA SQL|
		\**************************************************************/
		public $registros       = array();
		/***********************************************************\
		|CONTIENE EL CÓDIGO DE ERROR QUE RETORNÓ LA ULTIMA SENTENCIA|
		\***********************************************************/
		public $error           = "";
		/*****************************************************************\
		|CONTIENE LA DESCRIPCIÓN DEL ERROR QUE RETORNÓ LA ULTIMA SENTENCIA|
		\*****************************************************************/
		public $errno           = 0;
		/****************************************************************\
		|CONTIENE LA LISTA DE PARÁMETROS PAR EJECUTAR UN STORED PROCEDURE|
		|SE ESPERA QUE EL ARRAY TRAIGA UNA COLUMNA CON LA CLAVE Y OTRA   |
		|CON EL VALOR, [$parametros = array('CLAVE', 'VALOR')];          |
		\****************************************************************/
		public $parametros      = array();
	
		public $Procedimiento      = "";
		/***********************************************\
		|LA SIGUIENTE BANDERA ME DEFINE SI ES UN SP O NO|
		\***********************************************/
		public $esStoredProc    = false;
		/***************************************************************************\
		|LA SIGUIENTE BANDERA ME DEFINE SI RETORNA DATOS COMO OBJETO O COMO UN ARRAY|
		\***************************************************************************/
		public $datosObjeto     = false;
		
		function __construct( $servidor='', $baseDato='', $usuario='', $contrasena='' ){
			#   echo("Método: __construct");
			if($servidor != "" ){
				$this->servidor   = $servidor;
			}	#	if($servidor != "" ){
			if($baseDato != "" ){
				$this->baseDato   = $baseDato;
			}	#	if($baseDato != "" ){
			if($usuario != "" ){
				$this->usuario    = $usuario;
			}	#	if($usuario != "" ){
			if($contrasena != "" ){
				$this->contrasena = $contrasena;
			}	#	if($contrasena != "" ){
			$this->concectarMySql();
		}	#	function __construct()

		private function concectarMySql(){
			/***************************************************************\
			|ESTE MÉTODO PERMITE ESTABLECER LA CONEXIÓN CON LA BASE DE DATOS|
			\***************************************************************/
			#    echo("Método: concectarMySql");
			$this->idConexion = mysqli_connect( $this->servidor, $this->usuario, $this->contrasena );// or die( "Error al intentar conectar con el servidor de base de datos: " . mysql_connect_error( ) );
			if(!$this->idConexion){
				echo "Error al intentar conectar con el servidor de base de datos.";
				echo "Si los inconvenientes persisten comuniquese con el proveedor del sistema!!!";
				$this->errno = mysqli_connect_errno( $this->idConexion );
				$this->error = mysqli_connect_error( $this->idConexion );
			}	//	if(!$conexion){
			/***************************************************************\
			|AL CONECTAR CON LA BASE DE DATOS ADEMÁS SETEO LA BASE CON CUAL |
			|TRABAJAR                                                       |
			\***************************************************************/
			$this->seleccionarBaseDeDato();
		}	#	function concectarmysql()

		function seleccionarBaseDeDato(){
			/**********************************************************\
			|ESTE MÉTODO PERMITE ESTABLECER LA LA BASE ACTIVA PARA ESTA|
			|CONEXIÓN                                                  |
			\**********************************************************/
			#    echo("Método: seleccionarBaseDeDato");
			//mysqli_select_db( $this->baseDato, $this->idConexion ); //or die( "No fue posible seleccionar la Base de Datos." );
			if (!mysqli_select_db($this->idConexion, $this->baseDato  )){
				echo "Error seleccionando la base de datos.";
				echo "Si los inconvenientes persisten comuniquese con el proveedor del sistema!!!";
				$this->errno = mysqli_connect_errno( $this->idConexion );
				$this->error = mysqli_connect_error( $this->idConexion );
			}	//	if (!mysqli_select_db("juanchos_SGE",$conexion)){
		}	#	function SeleccionarBaseDeDato()
        
		function procesarSql(){
			/**********************************************************\
			|PROCESA UNA CONSULTA SQL Y PRETENDE OBTENER EL RESULTSET  |
			|EN CASE QUE FUERA UNA CONSULTA DE SELECCIÓN               |
			\**********************************************************/
			#   echo("Método: procesarSql");
			$this->idRegistros = mysqli_query( $this->idConexion,$this->spSql ) or die( "Error al intentar procesar la consulta sql: " . mysqli_connect_error( $this->idConexion ) );
			$this->errno = mysqli_connect_errno( $this->idConexion );
			$this->error =  mysqli_connect_error( $this->idConexion );
			if ( $this->datosObjeto ){
				$this->cargarArrayObjetos();
			}else{
				$this->cargarArray();
			}	#	if ( $datosObjeto ){	
		}	#	function procesarSql()
		function procesarPro(){
			/**********************************************************\
			|PROCESA UNA PROCEDIMIENTO SQL Y PRETENDE OBTENER EL RESULTSET  |
			|EN CASE QUE FUERA UNA CONSULTA DE SELECCIÓN               |
			\**********************************************************/
			#   echo("Método: procesarSql");
			
			$query="SET @USUARIO:= '".$_SESSION['usuario']."';";
			$variables="";
			$variables_devolucion="SELECT ";
			$i=0;
			$param_out=false;
			foreach($this->parametros as $datos )
			{
				if ($mayuscula == true)
				{
				$query.= "SET @p".$i."='".strtoupper($datos)."';";	
				}
				else
				{
				$query.= "SET @p".$i."='".$datos."';";		
				}
				$variables.="@p".$i.",";
				
				
				if ($datos == '@')
				{
				$this->parametros[$i]='';
				$variables_devolucion.=	"@p".$i.",";
				$param_out = true;
				}
				else
				{
				$variables_devolucion.=	" '".$datos.$i."',";	
				}
			$i++;

			}
			$variables=substr($variables, 0, -1);
			$variables_devolucion=substr($variables_devolucion, 0, -1);
			$query.= "CALL ".$this->Procedimiento."(".$variables.");";
			if (count($this->parametros)!=0)
			{
			$query.=$variables_devolucion.";";
			}
			$this->query =$query;
			$this->idRegistros = mysqli_multi_query( $this->idConexion,$query ) or die( "Error al intentar procesar la consulta sql: " . mysqli_connect_error( $this->idConexion ) );
			$this->errno = mysqli_connect_errno( $this->idConexion );
			$this->error =  mysqli_connect_error( $this->idConexion );
			
				
				
			
			if ( $this->datosObjeto ){
				$this->cargarArrayObjetos();
			}else{
				$this->cargarArray();
			}
			
			$m=0;
					
			if ($param_out)
			{
				foreach($this->registros[count($this->registros)-1][0] as $row)
				{
				$this->parametros[$m]= $row;	
				$m++;
				}
			}
			if ((count($this->registros) > 1) || (count($this->registros)==1 && $param_out==false))
			{
				$this->registros=$this->registros[0];
			}
			else
			{
				$this->registros=array();
			}			
				#	if ( $datosObjeto ){	
		}	#	function procesarSql()
        
		function cerrarConexion(){
			/****************************************************************\
			|ESTE MÉTODO CIERRA LA CONEXIÓN CON LA BASE DE DATOS Y LIBERAR LA|
			|MEMORIA UTILIZADA EN EL SERVIDOR POR DICHA CONEXIÓN             |
			\****************************************************************/
			/******************************\
			|LIBERAR CONJUNTO DE RESULTADOS|
			\******************************/
			#    echo("Método: cerrarConexion");
			mysql_free_result( $this->idRegistros ) or die( "Error al intentar liberar el conjunto de resultados alojado en memoria: " . mysqli_connect_error( $this->idConexion ) );
			$this->errno = mysqli_connect_errno( $this->idConexion );
			$this->error = mysqli_connect_error( $this->idConexion );
			/******************\
			|CERRAR LA CONEXION|
			\******************/
			mysqli_close( $this->idConexion ) or die( "Error al intentar cerrar la conexión: " . mysqli_connect_error( $this->idConexion ) );
		}	#	function cerrarConexion()

		private function cargarArrayObjetos(){
			/***********************************************************\
			|ESTE MÉTODO LEE EL ID DE LA CONSULTA Y RETORNA UN OBJETO EL| 
			|CUAL ASIGNO AL ARRAY DE SALIDA PARA PODER SER EXPLOTADO    |
			\***********************************************************/
			#    #    echo("Método: cargarArray");
			$j = 0;
			while ( $row = mysqli_fetch_object( $this->idRegistros ) ){
				$this->registros[$j]  = $row;
				$j++;
			}	#	while ( $row = mysql_fetch_array( $this->registros ) )
		}	#	private function cargarArray()

		public function cargarArray(){
			/***********************************************************\
			|ESTE MÉTODO LEE EL ID DE LA CONSULTA Y RETORNA UN OBJETO EL| 
			|CUAL ASIGNO AL ARRAY DE SALIDA PARA PODER SER EXPLOTADO    |
			\***********************************************************/
			#    #    echo("Método: cargarArray");
			$k = 0;
			$j = 0;
			
						if ($this->idRegistros) {
		do {
			
        /* almacenar primer juego de resultados */
        if ($result = mysqli_store_result( $this->idConexion)) {
			
			while ($row = mysqli_fetch_assoc($result)) {
				$this->registros[$k][$j]  = $row;
				$j++;
            }
            mysqli_free_result($result);
        
			$j=0;
			$k++;
		
		}
		

        /* mostrar divisor */
    } while (mysqli_next_result( $this->idConexion));
}

			
			
			/*while ( $row =  mysqli_fetch_array( $this->idRegistros ) ){
				$this->registros[$j]  = $row;
				$j++;*/
			//}	#	while ( $row = mysql_fetch_array( $this->registros ) )
		}	#	private function cargarArray()
		
	
		
		
	}	#	class cnnMySql
?>
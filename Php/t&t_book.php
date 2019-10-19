<?php

if(isset($_POST['pid']) && isset($_POST['uname']) && 
        isset($_POST['cmail']) && isset($_POST['dt']) && isset($_POST['days'])) {
    include 't&t_conn.php';
    
    if($con) {
        $id = $_POST['pid'];
        $un = $_POST['uname'];
        $cm = $_POST['cmail'];
        $dt = $_POST['dt'];
	$days = $_POST['days'];
        
	$flag = TRUE;
	for($i=1;$i<=$days;$i++) {
		$query = "INSERT INTO  book_tbl (p_id,uname,c_mail,b_date) "
                . "VALUES ('" . $id . "','" . $un . "','" . $cm . "','" . $dt . "')";
        
        	$res = mysqli_query($con,$query);
        
        	if(!$res) {
			$flag = FLASE;
			break;	
		}
		$dt++;
	}
        if($flag) {
            echo "Book True";
        } else {
            echo "Book False";
        }
    }
}

<?php

if(isset($_POST['user_name'])) {
    
    include "t&t_conn.php";
    
    if($con) {
        $query = "UPDATE emp_tbl SET admin_user=1 WHERE uname='" . $_POST['user_name'] . "'";
        $res = mysqli_query($con,$query);
        if($res) {
            echo "True";
        } else {
            echo "False";
        }
    }
}



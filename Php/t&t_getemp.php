<?php

include "t&t_conn.php";

if($con) {
    $query = "SELECT uname FROM emp_tbl WHERE admin_user=0";
    
    $res = mysqli_query($con,$query);
    if($res) {
        while($row = mysqli_fetch_assoc($res)) {
            echo $row['uname'] . " ";
        }
    } else {
        echo "Error";
    }
}

<?php

if(isset($_POST['hotel']) && isset($_POST['room']) && isset($_POST['av']) && isset($_POST['re'])) {

include 't&t_conn.php';
    
    if($con) {
        $h = $_POST['hotel'];
        $av = $_POST['av'];
        $re = $_POST['re'];
        $r = $_POST['room'];
        $h = str_replace(" ","_",$h);
        $query = "INSERT INTO pack_tbl (h_id, r_id, total_av, rent) "
                . "SELECT hotel_tbl.h_id, room_tbl.r_id, '" . $av . "', '" . $re . "' "
                . "FROM hotel_tbl "
                . "CROSS JOIN room_tbl "
                . "WHERE hotel_tbl.h_name='" . $h . "' AND "
                . "room_tbl.r_name='" . $r . "'";
        
        $res = mysqli_query($con,$query);
        if($res) {
            echo "Inserted";
        } else {
            echo "Error!";
        }
    }
}

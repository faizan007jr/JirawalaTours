<?php

if(isset($_POST['pid'])) {
    include 't&t_conn.php';
    
    if($con) {
        $id = $_POST['pid'];
        
        $query1 = "SELECT h_name FROM hotel_tbl WHERE h_id = "
                . "(SELECT h_id FROM pack_tbl WHERE p_id = '" . $id . "')";
        
        $query2 = "SELECT r_name FROM room_tbl WHERE r_id = "
                . "(SELECT r_id FROM pack_tbl WHERE p_id = '" . $id . "')";
        
        $query3 = "SELECT rent FROM  pack_tbl WHERE p_id = '" . $id . "'";
        
        $res1 = mysqli_query($con,$query1);
        $res2 = mysqli_query($con,$query2);
        $res3 = mysqli_query($con,$query3);
        
        if($res1 && $res2 && $res3) {
            $row1 = mysqli_fetch_row($res1);
            $row2 = mysqli_fetch_row($res2);
            $row3 = mysqli_fetch_row($res3);
            
            echo "Details ";
            echo $row1[0] . " ";
            echo $row2[0] . " ";
            echo $row3[0];
        }
    }
}

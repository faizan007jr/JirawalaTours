<?php

if(isset($_POST['loc']) && isset($_POST['hotel'])) {
    include 't&t_conn.php';
    
    if($con) {
        $l = $_POST['loc'];
        $h = $_POST['hotel'];
        $h = str_replace(" ","_",$h);
        $query = "SELECT count(*) FROM hotel_tbl WHERE h_name='" . $h . "' "
                . "AND l_id=(SELECT l_id FROM location_tbl WHERE l_name='" . $l . "')";
        
        $res = mysqli_query($con,$query);
        if($res) {
            $row = mysqli_fetch_assoc($res);
            if($row['count(*)'] != 1) {
                $q = "INSERT INTO hotel_tbl (h_name,l_id) "
                        . "SELECT '" . $h . "',l_id FROM location_tbl "
                        . "WHERE l_name='" . $l . "'";
                
                $res = mysqli_query($con,$q);
                if($res) {
                    echo "Inserted";
                } else {
                    echo 'Error';
                }
            } else {
                echo "Inserted";
            } 
        }
    }
}
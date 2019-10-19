<?php

if(isset($_POST['location'])) {
    include "t&t_conn.php";
    
    $loc = $_POST['location'];
    $query = "SELECT h_name FROM hotel_tbl WHERE l_id = (SELECT l_id FROM location_tbl WHERE l_name = '" . $loc . "')";
    $res = mysqli_query($con,$query);
    if($res) {
        echo "Hotel ";
        while ($row = mysqli_fetch_assoc($res)) {
            echo $row['h_name'] . " ";
        }
    } else {
        echo "Error!";
    }
}


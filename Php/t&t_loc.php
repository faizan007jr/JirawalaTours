<?php

if(isset($_POST['state'])) {
    include "t&t_conn.php";

    $state = $_POST['state'];
    $query = "SELECT l_name FROM location_tbl WHERE state_id=(SELECT state_id FROM state_tbl WHERE state_name='" . $state . "')";
    $res = mysqli_query($con,$query);

    if($res) {
        echo "Location ";
        while($row = mysqli_fetch_assoc($res)) {
            echo $row['l_name'] . " ";
        }
    } else {
        echo "Error!";
    }
}
<?php

include "t&t_conn.php";

$query = "SELECT state_name FROM state_tbl";
$res = mysqli_query($con,$query);

if($res) {
    echo "State ";
    while($row = mysqli_fetch_assoc($res)) {
        echo $row['state_name'] . " ";
    }
} else {
    echo "Error!";
}


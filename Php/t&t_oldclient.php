<?php

include "t&t_conn.php";

$query = "SELECT c_mail FROM client_tbl ORDER BY c_mail";

$res = mysqli_query($con,$query);

if($res) {
    echo "Old ";
    while($row = mysqli_fetch_array($res)) {
        echo $row['c_mail'] . " ";
    }
}


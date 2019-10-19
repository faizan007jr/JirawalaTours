<?php

if(isset($_POST['email']) && isset($_POST['f']) && isset($_POST['m'])
        && isset($_POST['l']) &&  isset($_POST['gen'])
                && isset($_POST['mo']) && isset($_POST['c'])) {
    include "t&t_conn.php";

    if($con) {
        $em = $_POST['email'];
        $f = $_POST['f'];
        $m = $_POST['m'];
        $l = $_POST['l'];
        $gen = $_POST['gen'];
        $mo = $_POST['mo'];
        $c = $_POST['c'];
        
        $query = "INSERT INTO client_tbl VALUES ('" . $em . "','" . $f . "',"
                . "'" . $m . "','" . $l . "','" . $gen . "','" . $mo . "',"
                . "'" . $c . "')";
        
        $res = mysqli_query($con,$query);
        if($res) {
            echo "New True";
        } else {
            echo "New False";
        }
     }
}

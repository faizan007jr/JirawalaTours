<?php

if(isset($_POST['user_name']) && isset($_POST['password']) && isset($_POST['ad']) && isset($_POST['branch'])) {
    include "t&t_conn.php";

    if($con) {
        $un = $_POST['user_name'];
        $pwd = $_POST['password'];
        $x = $_POST['ad'];
        $ad = ($x === 'TRUE'?1:0);
        $branch = $_POST['branch'];
        $query = "SELECT br_id FROM br_tbl WHERE br_name = '" . $branch . "'";
        $r = mysqli_query($con,$query);
        $x = mysqli_fetch_assoc($r);
        $br_id = (int)$x['br_id'];
        $query = "INSERT INTO emp_tbl (uname,pwd,admin_user,br_id) VALUES ('" . $un . "','" . $pwd . "','" . $ad . "','" . $br_id . "')";
        $res = mysqli_query($con,$query);
        if($res) {
            echo "True";
        } else {
            echo "False";
        }
    }
}


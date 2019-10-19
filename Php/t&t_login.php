<?php

if(isset($_POST['user_name']) && isset($_POST['password'])) {
    include "t&t_conn.php";

    if($con) {
        $un = $_POST['user_name'];
        $pwd = $_POST['password'];
        $query = "SELECT COUNT(*) FROM emp_tbl WHERE BINARY uname='" . $un . "' AND BINARY pwd='" . $pwd . "'";
        $res = mysqli_query($con,$query);
        $r = mysqli_fetch_assoc($res);
        $x = $r['COUNT(*)'];
        if($x > 0) {
            $query = "SELECT admin_user,flogin FROM emp_tbl WHERE uname='" . $un . "'";
            $res = mysqli_query($con,$query);
            $r = mysqli_fetch_assoc($res);
            $x = $r['flogin'];
            $a = $r['admin_user'];
            if($x == 0) {
                if($a == 0) {
                    echo "True " . "No " . "No";
                } else {
                    echo "True " . "No " . "Yes";
                }
            } else {
                if($a == 0) {
                    echo "True " . "Yes " . "No";
                } else {
                    echo "True " . "Yes " . "Yes";
                }
            }
        } else {
            echo "False";
        }
    }
}


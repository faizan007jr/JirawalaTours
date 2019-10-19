<?php

if(isset($_POST['uname']) && isset($_POST['fname']) && isset($_POST['lname'])
        && isset($_POST['mname']) &&  isset($_POST['pwd'])
                && isset($_POST['gen']) && isset($_POST['dob'])
                        && isset($_POST['mobile'])
                                && isset($_POST['email'])) {
    include "t&t_conn.php";

    if($con) {
        $un = $_POST['uname'];
        $fn = $_POST['fname'];
        $mn = $_POST['mname'];
        $ln = $_POST['lname'];
        $pwd = $_POST['pwd'];
        $gen = $_POST['gen'];
        $dob = $_POST['dob'];
        $mo = $_POST['mobile'];
        $em = $_POST['email'];
        $fl = 1;
        
        $query = "UPDATE emp_tbl SET pwd='" . $pwd . "', fname='" . $fn . "',"
                . " mname='" . $mn . "', lname='" . $ln . "',"
                . " gender='" . $gen . "', dob='" . $dob . "',"
                . " mobile='" . $mo . "', email='" . $em . "',"
                . " flogin='" . $fl . "'"
                . " WHERE uname='" . $un . "'";
        
        $res = mysqli_query($con,$query);
        
        if($res) {
            echo "True";
        } else {
            echo "False";
        }
    }
}

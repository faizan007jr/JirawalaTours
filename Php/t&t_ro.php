<?php

if(isset($_POST['hotel']) && isset($_POST['date']) && isset($_POST['days'])) {
    include "t&t_conn.php";
    
    $h = $_POST['hotel'];
    $d = $_POST['date'];
    $ds = $_POST['days'];
    
    $query = "SELECT p_id,total_av,rent,r_id FROM pack_tbl WHERE h_id = (SELECT h_id FROM hotel_tbl WHERE h_name = '" . $h . "')";
    $res = mysqli_query($con,$query);
    if($res) {
        echo "Room ";
        while($row = mysqli_fetch_assoc($res)) {
            $query1 = "SELECT r_name FROM room_tbl WHERE r_id='" . $row['r_id'] . "'";
            $r = mysqli_query($con,$query1);
	    echo $row['p_id'] . " ";
            while($row2 = mysqli_fetch_assoc($r)) {
                echo $row2['r_name'] . " ";
            }
            echo $row['rent']*$ds . " ";
            $t = (int)$row['total_av'];
            $temp = $d;
            for($i=1;$i<=$ds;$i++) {
                    $query2 = "SELECT count(*) FROM book_tbl WHERE p_id='" . $row['p_id'] . "' && b_date='" . $temp . "'";
                    $r2 = mysqli_query($con,$query2);
                    while($c = mysqli_fetch_assoc($r2)) {
                            $t = $t - (int)$c['count(*)'];
                    }
                    $temp++;
            }
            echo $t . " ";
        }
    } else {
        echo "Error!";
    }
}
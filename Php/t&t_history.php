<?php

include "t&t_conn.php";

$query = "SELECT location_tbl.l_name, hotel_tbl.h_name, room_tbl.r_name,"
        . " book_tbl.uname, book_tbl.c_mail, book_tbl.b_date FROM location_tbl"
        . " INNER JOIN hotel_tbl ON hotel_tbl.l_id = location_tbl.l_id"
        . " INNER JOIN pack_tbl ON pack_tbl.h_id = hotel_tbl.h_id"
        . " INNER JOIN room_tbl ON pack_tbl.r_id = room_tbl.r_id"
        . " INNER JOIN book_tbl ON book_tbl.p_id = pack_tbl.p_id";

$res = mysqli_query($con,$query);

if($res) {
    echo "History ";
    
    while($row = mysqli_fetch_assoc($res)) {
        echo $row['l_name'] . " ";
        echo $row['h_name'] . " ";
        echo $row['r_name'] . " ";
        echo $row['uname'] . " ";
        echo $row['c_mail'] . " ";
        echo $row['b_date'] . " ";
    }
    
} else {
    echo "Error!";
}
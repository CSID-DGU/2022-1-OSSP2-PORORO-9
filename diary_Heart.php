<?php
    $did = $_POST['num'];
    $uid = $_POST['id'];

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    $conn = new mysqli($host, $user, $password, $dbName);
    $sql = "INSERT INTO DIARY_HEART(did,uid) VALUES('{$did}','{$uid}')";

    if($conn -> query($sql)){
        echo "1";
    } else{
        echo "0 error";
    }

    mysqli_close($conn);
?>
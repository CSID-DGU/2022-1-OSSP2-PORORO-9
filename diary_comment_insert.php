<?php
    $did = $_POST['did'];
    $uid = $_POST['uid'];
    $text = $_POST['text'];
    $today = date("Y-m-d H:i:s");

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    $conn = new mysqli($host, $user, $password, $dbName);
    $sql = "INSERT INTO DIARY_COMMENT(did,uid, dccontent, dcdate) VALUES('{$did}','{$uid}','{$text}', '{$today}')";

    if($conn -> query($sql)){
        echo "1";
    } else{
        echo "0 error";
    }

    mysqli_close($conn);
?>
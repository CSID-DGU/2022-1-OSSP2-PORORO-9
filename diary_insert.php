<?php
    $id = $_POST['id'];
    $title = $_POST['title'];
    $text = $_POST['text'];
    $url = $_POST['url'];
    $onoff = $_POST['onoff'];
    $today = date("Y-m-d H:i:s");

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    $conn = new mysqli($host, $user, $password, $dbName);
    $sql = "INSERT INTO DIARY(uid, dtitle, dcontent, dimg, dprivate, ddate) VALUES('{$id}','{$title}','{$text}', '{$url}','{$onoff}','{$today}')";

    if($conn -> query($sql)){
        echo "1";
    } else{
        echo "0 error";
    }

    mysqli_close($conn);
?>
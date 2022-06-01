<?php

    error_reporting(E_ALL);
    ini_set('display_errors', '1');

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    $conn = new mysqli($host, $user, $password, $dbName);

    $ImageData = $_POST['image_path'];

    $title = $_POST['diary_title'];
    $content = $_POST['diary_content'];
    $onoff = $_POST['privateOn'];
    
    $today = date("Y-m-d H:i:s");

    $filename = date("YmdHis");
    $on_private = (int)$onoff;

    $ImagePath = "images/$filename.png";
    $ServerURL = "https://togaether.cafe24.com/$ImagePath";

    $sql = "INSERT INTO DIARY (dtitle, dcontent, dimg, dprivate, ddate) VALUES ('$title', '$content', '$ServerURL', '$on_private', '$today')";

    if($conn -> query($sql)){
        file_put_contents($ImagePath, base64_decode($ImageData));
        echo "Your Image Has Been Uploaded.";
    }
    else{
        echo "Not Uploaded";
    }

    mysqli_close($conn);
?>
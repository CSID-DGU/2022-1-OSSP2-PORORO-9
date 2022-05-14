<?php
    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    $conn = new mysqli($host, $user, $password, $dbName);
    
    if($_SERVER['REQUEST_METHOD'] == 'POST'){
        $DefaultId = 0;
        $ImageData = $_POST['image_path'];
        $ImageName = $_POST['image_name'];
        $GetOldIdSQL = "SELECT id FROM test_image ORDER BY id ASC";
        $Query = mysqli_query($conn, $GetOldIdSQL);

        while($row = mysqli_fetch_array($Query)){
            $DefaultId = $row['id'];
        }

        $filename = date("YmdHis");

        $ImagePath = "images/$filename.png";
        $ServerURL = "https://togaether.cafe24.com/$ImagePath";
        $InsertSQL = "INSERT INTO test_image (image_path, image_name) VALUES ('$ServerURL', '$ImageName')";
        if(mysqli_query($conn, $InsertSQL)){
            file_put_contents($ImagePath, base64_decode($ImageData));
            echo "Your Image Has Been Uploaded.";
        }

        mysqli_close($conn);
    } else{
        echo "Not Uploaded";
    }
?>
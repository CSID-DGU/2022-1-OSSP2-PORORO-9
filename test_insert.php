<?php
    $name = $_GET['name'];
    $address = $_GET['address'];
    $date = $_GET['birthday'];

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    $conn = new mysqli($host, $user, $password, $dbName);
    $sql = "INSERT INTO test(name, address, birth) VALUES('{$name}','{$address}','{$date}')";

    if($conn -> query($sql)){
        echo "1";
    }

    mysqli_close($conn);
?>
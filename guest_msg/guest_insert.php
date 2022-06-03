<?php header("Content-Type:text/html;charset=utf-8"); ?>
<?php
    $uid = $_POST['uid'];
    $pid = $_POST['pid'];
    $gcontent = $_POST['gcontent'];

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    header('Content-Type: application/json');

    $conn = new mysqli($host, $user, $password, $dbName);
    if(!($conn)){
        echo "db 연결 실패: " . mysqli_connect_error();
    }

    $sql = "INSERT INTO GUEST_MSG(uid, pid, gcontent, gdate) VALUES('{$uid}', '{$pid}', '{$gcon}', NOW())";
    $result = mysqli_query($conn, $sql);

    if(!($result)){
        echo "Insert 실패";
    }

    mysqli_close($conn);
?>
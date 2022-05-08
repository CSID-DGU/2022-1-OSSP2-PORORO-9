<?php
    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    header('Content-Type: application/json');

    $conn = new mysqli($host, $user, $password, $dbName);
    if(!($conn)){
        echo "db 연결 실패: " . mysqli_connect_error();
    }

    $sql = "Select * from test;";
    $result = mysqli_query($conn, $sql);
    $output = array();

    if(mysqli_num_rows($result) > 0){ // 쿼리 결과로 1행 이상 존재한다면
        while($row = mysqli_fetch_assoc($result)) {
            array_push($output,
                array('name' => $row['name'],
                    'address' => $row['address'],
                    'birthdate' => $row['birth']
                )
            );
        }
    } else{
        $output = array('message' => '쿼리 결과 없음');
    }

    echo json_encode($output);

    mysqli_close($conn);
?>

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

    $sql = "SELECT * FROM USER INNER JOIN DIARY_COMMENT ON USER.uid = DIARY_COMMENT.uid;";
    $result = mysqli_query($conn, $sql);
    $output = array();

    if(mysqli_num_rows($result) > 0){ // 쿼리 결과로 1행 이상 존재한다면
        while($row = mysqli_fetch_assoc($result)) {
            array_push($output,
                array('diary_id' => $row['did'],
                    'user_id' => $row['uid'],
                    'diary_content' => $row['dccontent'],
                    'write_date' => $row['dcdate']
                )
            );
        }
    } else{
        $output = array('message' => '쿼리 결과 없음');
    }

    echo json_encode($output);

    mysqli_close($conn);
?>
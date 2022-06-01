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

    $sql = "SELECT * FROM USER INNER JOIN DIARY ON USER.uid = DIARY.uid;";
    $result = mysqli_query($conn, $sql);
    $output = array();

    if(mysqli_num_rows($result) > 0){ // 쿼리 결과로 1행 이상 존재한다면
        while($row = mysqli_fetch_assoc($result)) {
            array_push($output,
                array('user_id' => $row['uid'],
                    'user_nickname' => $row['uname'],
                    'diary_title' => $row['dtitle'],
                    'diary_content' => $row['dcontent'],
                    'diary_img_url' => $row['dimg'],
                    'on_private?' => $row['dprivate'],
                    'write_date' => $row['ddate']
                )
            );
        }
    } else{
        $output = array('message' => '쿼리 결과 없음');
    }

    echo json_encode($output);

    mysqli_close($conn);
?>

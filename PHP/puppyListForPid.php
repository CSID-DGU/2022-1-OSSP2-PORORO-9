<?php header("Content-Type:text/html;charset=utf-8"); ?>
<?php
    $pid = $_GET['pid'];

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    header('Content-Type: application/json');

    $conn = new mysqli($host, $user, $password, $dbName);
    if(!($conn)){
        echo "db 연결 실패: " . mysqli_connect_error();
    }

    $sql = "SELECT p.*, v.*, t.pname AS vname, IF(NOW() > v.enddate || v.pid = v.vpid, 0, 1) AS visit FROM PUPPY AS p INNER JOIN PUPPY_VISIT AS v ON p.pid = v.pid LEFT JOIN PUPPY AS t ON v.vpid = t.pid WHERE p.pid='{$pid}';";
    $result = mysqli_query($conn, $sql);
    $output = new stdClass;

    if(mysqli_num_rows($result) > 0){ // 쿼리 결과로 1행 이상 존재한다면
        while($row = mysqli_fetch_assoc($result)) {
                $output -> pid = $row['pid'];
                $output -> pname = $row['pname'];
                $output -> uid = $row['uid'];
                $output -> psex = $row['psex'];
                $output -> pcall = $row['pcall'];
                $output -> pbdate = $row['pbdate'];
                $output -> pcustom = json_decode($row['pcustom']);
                $output -> pshare = $row['pshare'];
                $output -> visit = $row['visit'] == 0? false : true;
                $output -> vpid = $row['vpid'] == null? -1 : $row['vpid'];
                $output -> vname = $row['vname'] == null? "-" : $row['vname'];
        }
    } else{
              $output -> pid = -1;
        //$output = array('message' => '쿼리 결과 없음');
    }

    echo json_encode($output);

    mysqli_close($conn);
?>

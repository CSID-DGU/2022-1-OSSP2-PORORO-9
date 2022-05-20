<?php header("Content-Type:text/html;charset=utf-8"); ?>
<?php
    $pid = $_POST['pid'];
    $uid = $_POST['uid'];
    $enddate = $_POST['end'];
    $ran = (is_numeric($_POST['random']) ? (int)$_POST['random'] : 0);

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    header('Content-Type: application/json');

    $conn = new mysqli($host, $user, $password, $dbName);
    if(!($conn)){
        echo "db 연결 실패: " . mysqli_connect_error();
    }

    $sql = "SELECT *, IF(NOW() <= v.enddate && (v.vpid <> {$pid} || v.vpid = v.pid), 1, 0) AS skip FROM PUPPY AS p INNER JOIN PUPPY_VISIT AS v ON p.pid = v.pid WHERE p.uid<>'{$uid}' AND CURDATE() > p.pdate AND p.pshare = 1;";
    $result = mysqli_query($conn, $sql);
    $output;

    if(mysqli_num_rows($result) > 0){ // 쿼리 결과로 1행 이상 존재한다면
        $output = array();
        while($row = mysqli_fetch_assoc($result)) {
            array_push($output,
                array('pid' => $row['pid'],
                  'pname' => $row['pname'],
                  'uid' => $row['uid'],
                  'psex' => $row['psex'],
                  'pcall' => $row['pcall'],
                  'pbdate' => $row['pbdate'],
                  'pcustom' => json_decode($row['pcustom']),
                  'pshare' => $row['pshare'],
                  'skip' => $row['skip']
                )
            );
        }
        $ind = $ran % count($output);
        while ($output[$ind]['skip'] == 1) {
          $ind = ($ind+1)%count($output);
        }
        echo json_encode($output[$ind]);
        $sql = "UPDATE PUPPY_VISIT SET vpid = {$pid}, enddate = '{$enddate}' WHERE pid = {$output[$ind]['pid']} OR pid = {$pid}";
        $conn -> query($sql);
    } else{
        $output = new stdClass;
        $output -> message = '쿼리 결과 없음';
        echo json_encode($output);
    }


    mysqli_close($conn);
?>

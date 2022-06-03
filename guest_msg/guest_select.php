<?php header("Content-Type:text/html;charset=utf-8"); ?>
<?php
    $pid = $_POST['pid'];

    $host = 'localhost';
    $user = 'togaether';
    $password = 'togaetherTEAM9';
    $dbName = 'togaether';

    header('Content-Type: application/json');

    $conn = new mysqli($host, $user, $password, $dbName);
    if (!($conn)) {
        echo "db 연결 실패: " . mysqli_connect_error();
    }

    $sql = "SELECT g.*, u.uname FROM GUEST_MSG AS g INNER JOIN USER AS u ON u.uid = g.uid WHERE g.pid ='{$pid}';";
    $result = mysqli_query($conn, $sql);
    $output = array();

    if (mysqli_num_rows($result) > 0) {
        while ($row = mysqli_fetch_assoc($result)) {
            array_push($output, array('gid' => $row['gid'], 'uid' => $row['uid'], 'uname' => $row['uname'], 'pid' => $row['pid'], 'gcontent' => $row['gcontent'], 'gdate' => $row['gdate']));
        }
    }

    echo json_encode($output);
    mysqli_close($conn);
?>
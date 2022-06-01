<!doctype html>
<html lang="ko">
    <head>

    </head>
    <body>
        <h1>댓글을 작성해보세요!</h1>
        <form action="./diary_comment_insert.php" method="post">
            <p>글번호: <input type="text" name="did"></p>
            <p>작성자(ID) : <input type="id" name="uid"></p>
            <p>댓글: <textarea type="text" name="text" cols="50" row="10" ></textarea></p> 
     
            <p><input type="submit" value="댓글 등록"></p>
        </form>
    </body>
</html>
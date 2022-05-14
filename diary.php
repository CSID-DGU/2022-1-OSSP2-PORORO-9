<!doctype html>
<html lang="ko">
    <head>

    </head>
    <body>
        <h1>일기를 작성해보세요!</h1>
        <form action="./diary_insert.php" method="post">
            <p>작성자(ID) : <input type="id" name="id"></p>
            <p>글제목: <input type="title" name="title"></p>
            <p>내용: <textarea type="text" name="text" cols="50" row="10" ></textarea></p> 
            <p>이미지url: <input type="url" name="url"></p>
            <input type="radio" name="onoff" value="1">
            <label for="open">Private</label>
            <input type="radio" name="onoff" value="0">
            <label for="close">Public</label>
            <p><input type="submit"></p>
        </form>
    </body>
</html>
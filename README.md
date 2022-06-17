# 투개더 라이브러리 - PuppyView
투개더에 등록된 강아지를 가져올 수 있는 라이브러리입니다.

## ⭐ 사용 방법
#### internet permission
internet 퍼미션을 필요로 합니다. 
manifest.xml에 다음의 내용을 추가해줍니다.
````xml
<uses-permission android:name="android.permission.INTERNET" />
````

강아지를 추가할 때는 다음과 같이 사용합니다.
````java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PuppyView puppyView = new PuppyView(findViewById(R.id.lay_sample), this, 3); // 3번 id의 강아지 추가
        puppyView.setPosition(100,100); // 위치 이동 (단위: 
    }
````
## ⭐ 결과
![KakaoTalk_20220617_120533566](https://user-images.githubusercontent.com/93471263/174217078-4f50bc0e-6841-4b55-a98b-4cbaf31f1da3.jpg)

package com.example.togaether;

import java.util.ArrayList;

public class AddressConverter {
    //private static ArrayList<String> listFristAddr, listMidAddr, listLastAddr;
    public static String[] arrFirstAddr, arrMidAddr, arrLastAddr;
    public static int fSize, mSize, lSize;

    private AddressConverter() {
        fSize = 11;
        mSize = 3;
        lSize = 3;
        arrFirstAddr = new String[]{"세상시","푸른시","오늘시","아름시","무지개시","보미시","나의시","우주시","다정시","누리시","천상시"};
        arrMidAddr = new String[]{
                "가장구","최대구","제일구",
                "하늘구","구름구","바다구",
                "날씨구","기분구","웃음구",
                "다운구","드리구","답던구",
                "너머구","동산구","빛깔구",
                "정원구","들꽃구","바람구",
                "전부구","소망구","소중구",
                "은하구","한울구","마루구",
                "다감구","마음구","하다구",
                "달빛구","햇빛구","별빛구",
                "낙원구","일등구","최고구"};
        arrLastAddr = new String[]{
                "건강동","튼튼동","귀엽동","행운동","축복동","환희동","사랑동","애정동","다솜동",
                "샛별동","한별동","은하동","뭉게동","소미동","더미동","탐험동","전설동","보물동",
                "맑음동","푸름동","시원동","행복동","기쁨동","최고동","재미동","유머동","유쾌동",
                "인연동","우정동","만남동","추억동","기억동","리움동","매일동","하루동","항상동",
                "놀이동","공원동","들판동","풀잎동","잔디동","산새동","노랑동","초록동","보라동",
                "나비동","열매동","요정동","데이지동","민들레동","안개꽃동","꽃잎동","살랑동","보들동",
                "네게동","전부동","줄게동","희망동","소원동","기대동","가족동","친구동","모두동",
                "혜성동","유성동","미리내동","아라동","가람동", "미르동","가온동","다온동","라온동",
                "인사동","미소동","보조개동","배려동","감사동","은혜동","상냥동","친절동","자상동",
                "신비동","청아동","청초동","따뜻동","말랑동","포근동","찬란동","초롱동","반짝동",
                "으뜸동","일류동","최상동","멋지동","현명동","똑똑동","신나동","들뜨동","신명동"};
    }

    private static class InnerInstanceClazz {
        private static final AddressConverter instance = new AddressConverter();
    }

    public static AddressConverter getInstance() {
        return AddressConverter.InnerInstanceClazz.instance;
    }

    public static String getFirstAddrByPid(int pid) {
        int ind = (pid/(mSize*lSize))%fSize;
        return arrFirstAddr[ind];
    }

    public static String getMidAddrByPid(int pid) {
        int ind = (pid/lSize)%fSize;
        return arrMidAddr[ind];
    }

    public static String getLastAddrByPid(int pid) {
        int ind = pid%(fSize*mSize*lSize);
        return arrLastAddr[ind];
    }

    public static String getAddrByPid(int pid) {
        String res = getFirstAddrByPid(pid) + " " +
                getMidAddrByPid(pid) + " " +
                getLastAddrByPid(pid) + " " +
                getNumAddrByPid(pid);
        return res;
    }

    public static int getNumAddrByPid(int pid) {
        return pid/(fSize*mSize*lSize) + 1;
    }
}

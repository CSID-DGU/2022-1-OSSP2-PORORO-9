package com.example.togaether;

import com.google.gson.annotations.SerializedName;

public class PuppyItem {
    @SerializedName("FACE1")
    private PuppyPartItem face1;
    @SerializedName("FACE2")
    private PuppyPartItem face2;
    @SerializedName("FACE3")
    private PuppyPartItem face3;
    @SerializedName("BODY")
    private PuppyPartItem body;
    @SerializedName("MOUTH")
    private PuppyPartItem mouth;
    @SerializedName("NOSE")
    private PuppyPartItem nose;
    @SerializedName("EAR_L")
    private PuppyPartItem ear_l;
    @SerializedName("EAR_R")
    private PuppyPartItem ear_r;
    @SerializedName("EYE_L")
    private PuppyPartItem eye_l;
    @SerializedName("EYE_R")
    private PuppyPartItem eye_r;
    @SerializedName("EYEBROW_L")
    private PuppyPartItem eyebrow_l;
    @SerializedName("EYEBROW_R")
    private PuppyPartItem eyebrow_r;

    public PuppyItem(PuppyPartItem face1, PuppyPartItem face2, PuppyPartItem face3, PuppyPartItem body, PuppyPartItem mouth, PuppyPartItem nose, PuppyPartItem ear_l, PuppyPartItem ear_r, PuppyPartItem eye_l, PuppyPartItem eye_r, PuppyPartItem eyebrow_l, PuppyPartItem eyebrow_r) {
        this.face1 = face1;
        this.face2 = face2;
        this.face3 = face3;
        this.body = body;
        this.mouth = mouth;
        this.nose = nose;
        this.ear_l = ear_l;
        this.ear_r = ear_r;
        this.eye_l = eye_l;
        this.eye_r = eye_r;
        this.eyebrow_l = eyebrow_l;
        this.eyebrow_r = eyebrow_r;
    }

    public PuppyPartItem getBody() {
        return body;
    }

    public PuppyPartItem getEar_l() {
        return ear_l;
    }

    public PuppyPartItem getEar_r() {
        return ear_r;
    }

    public PuppyPartItem getEye_l() {
        return eye_l;
    }

    public PuppyPartItem getEye_r() {
        return eye_r;
    }

    public PuppyPartItem getEyebrow_l() {
        return eyebrow_l;
    }

    public PuppyPartItem getEyebrow_r() {
        return eyebrow_r;
    }

    public PuppyPartItem getFace1() {
        return face1;
    }

    public PuppyPartItem getFace2() {
        return face2;
    }

    public PuppyPartItem getFace3() {
        return face3;
    }

    public PuppyPartItem getMouth() {
        return mouth;
    }

    public PuppyPartItem getNose() {
        return nose;
    }

    public void setBody(PuppyPartItem body) {
        this.body = body;
    }

    public void setEar_l(PuppyPartItem ear_l) {
        this.ear_l = ear_l;
    }

    public void setEar_r(PuppyPartItem ear_r) {
        this.ear_r = ear_r;
    }

    public void setEye_l(PuppyPartItem eye_l) {
        this.eye_l = eye_l;
    }

    public void setEye_r(PuppyPartItem eye_r) {
        this.eye_r = eye_r;
    }

    public void setEyebrow_l(PuppyPartItem eyebrow_l) {
        this.eyebrow_l = eyebrow_l;
    }

    public void setEyebrow_r(PuppyPartItem eyebrow_r) {
        this.eyebrow_r = eyebrow_r;
    }

    public void setFace1(PuppyPartItem face1) {
        this.face1 = face1;
    }

    public void setFace2(PuppyPartItem face2) {
        this.face2 = face2;
    }

    public void setFace3(PuppyPartItem face3) {
        this.face3 = face3;
    }

    public void setMouth(PuppyPartItem mouth) {
        this.mouth = mouth;
    }

    public void setNose(PuppyPartItem nose) {
        this.nose = nose;
    }
}

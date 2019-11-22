package com.zhitu.entity;

import java.io.Serializable;

/**
 * 人脸照片类，包含属性
 */
public class Face implements Serializable {

    //人脸照片ID，数据库中主键，自增
    private int faceId;

    //图片id
    private int photoId;

    //年龄
    private String age;

    //颜值0～100评分
    private String beauty;

    //表情：none:不笑；smile:微笑；laugh:大笑
    private String expression;

    //脸型：square: 正方形 triangle:三角形 oval: 椭圆 heart: 心形 round: 圆形
    private String faceShape;

    //性别：male:男性 female:女性
    private String gender;

    //是否带眼镜：none:无眼镜，common:普通眼镜，sun:墨镜
    private String glasses;

    //情绪：angry:愤怒 disgust:厌恶 fear:恐惧 happy:高兴 sad:伤心 surprise:惊讶 neutral:无情绪
    private String emotion;

    //人种 yellow: 黄种人 white: 白种人 black:黑种人 arabs: 阿拉伯人
    private String race;

    public int getFaceId() {
        return faceId;
    }

    public void setFaceId(int faceId) {
        this.faceId = faceId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBeauty() {
        return beauty;
    }

    public void setBeauty(String beauty) {
        this.beauty = beauty;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFaceShape() {
        return faceShape;
    }

    public void setFaceShape(String faceShape) {
        this.faceShape = faceShape;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGlasses() {
        return glasses;
    }

    public void setGlasses(String glasses) {
        this.glasses = glasses;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
}

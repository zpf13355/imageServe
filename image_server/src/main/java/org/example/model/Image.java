package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Image {
    //图片id
    private Integer imageId;
    //图片名字
    private String imageName;
    //图片大小
    private Long size;
    //上传时间
    private String upload_time;
    //文件效验的唯一性：md5校验码
    private String md5;
    //图片类型
    private String contentType;
    //图片保存的本地路径
    private String path;

}

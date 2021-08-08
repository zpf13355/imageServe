package org.example.dao;

import org.example.model.Image;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageDAOTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void queryCount() {
    }

    @Test
    public void insert1() {
        Image image=new Image();
        //测试用例1
        image.setImageName("zpf.png");
        image.setSize((long)47227);
        image.setUpload_time("2021-08-06 20:22");
        image.setMd5("3c6fa8bbf8348c246b8dbe9706ed0a68");
        image.setContentType("image/png");
        image.setPath("/3c6fa8bbf8348c246b8dbe9706ed0a68");
        ImageDAO.insert(image);

    }
    @Test
    public void insert2() {
        Image image=new Image();
        //测试用例1
        image.setImageName("zpf.png");
        image.setSize((long)47227d);
        image.setUpload_time("2021-08-06 20:22");
        image.setMd5("3c6fa8bbf8348c246b8dbe9706ed0a68");
        image.setContentType("image/png");
        image.setPath("/3c6fa8bbf8348c246b8dbe9706ed0a68");
        ImageDAO.insert(image);

    }
    @Test
    public void queryAll() {
    }

    @Test
    public void queryId() {
    }

    @Test
    public void delete() {
    }
}
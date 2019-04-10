package com.jinandaxue.zy.songs;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jszx on 2018/11/8.
 */
public class songsTest {
    Songs songs;
    @Before
    public void setUp() throws Exception {
        songs =new Songs();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getSetName() throws Exception {
        String name="zhang san";
        songs.setName(name);
        Assert.assertEquals(name, songs.getName());
    }

    @Test
    public void getPictureId() throws Exception {

    }

    @Test
    public void setPictureId() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void set() throws Exception {

    }

    @Test
    public void getDay() throws Exception {

    }

    @Test
    public void setDay() throws Exception {

    }

}
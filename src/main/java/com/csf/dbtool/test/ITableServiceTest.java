package com.csf.dbtool.test;

import com.csf.dbtool.service.IDBTableService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ITableServiceTest {

    @Autowired
    private IDBTableService idbTableService;

    //@Test
    @SneakyThrows(Exception.class)
    public void selectAllTest(){

    }


    public void selectTableNameByTableNames(){

    }

}

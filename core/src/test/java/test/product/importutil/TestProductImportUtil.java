package test.product.importutil;

import com.smart.home.external.importutil.ImportProductUtil;
import com.smart.home.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author jason
 * @date 2021/4/9
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestProductImportUtil {

    @Resource
    private ImportProductUtil importProductUtil;

    @Test
    public void testImportProductList() {
        try {
            importProductUtil.beginImport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

import com.bank2.Bank2Application;
import com.bank2.mapper.AccountInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Bank2Application.class)
public class MybatisTest {


    @Autowired
    private AccountInfoMapper accountInfoMapper;


    @Test
    public void userServiceTest(){

        int i = accountInfoMapper.addBalance("1", 10);

        System.out.println(i);

    }


}

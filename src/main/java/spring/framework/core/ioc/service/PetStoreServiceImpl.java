package spring.framework.core.ioc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import spring.framework.core.ioc.dao.AccountDao;
import spring.framework.core.ioc.dao.DmpDao;
import spring.framework.core.ioc.dao.ItemDao;
import spring.framework.core.ioc.dao.ProductDao;

/**
 * @author 曲元涛
 * @date 2020/4/12 16:26
 */
@Service
@Slf4j
public class PetStoreServiceImpl implements BeanNameAware {

    private AccountDao accountDao;

    private ItemDao itemDao;

    public void setAccountDao(AccountDao accountDao) {
    }

    public void setItemDao(ItemDao itemDao) {
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    @Override
    @Lookup
    public void setBeanName(String beanName) {
        System.out.println("beanName:" + beanName);
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/java/spring/framework/core/ioc/services.xml", "file:src/main/java/spring/framework/core/ioc/daos.xml");
//        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/java/spring/framework/core/ioc/services.xml", "src/main/java/spring/framework/core/ioc/daos.xml");

        AccountDao accountDao = context.getBean("accountDao", AccountDao.class);
        ItemDao itemDao = context.getBean("itemDao", ItemDao.class);
        ProductDao productDao = context.getBean("productDao", ProductDao.class);
        log.info("accountDao => {}", accountDao);
        log.info("itemDao => {}", itemDao);
        log.info("productDao => {}", productDao);

        DmpDao dmpDao = new DmpDao();
        log.info("dmpDao => {}", dmpDao);
        context.getBeanFactory().registerSingleton("dmpDao", dmpDao);
         dmpDao = context.getBean("dmpDao", DmpDao.class);
        log.info("dmpDao => {}", dmpDao);

        PetStoreServiceImpl petStoreService = context.getBean("petStoreService", PetStoreServiceImpl.class);
        log.info("petStoreService => {}", petStoreService);
        petStoreService = context.getBean("ps_1", PetStoreServiceImpl.class);
        log.info("petStoreService ps1 => " + petStoreService);
        petStoreService = context.getBean("ps_2", PetStoreServiceImpl.class);
        log.info("petStoreService ps2 => " + petStoreService);
    }
}

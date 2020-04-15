package spring.framework.core.ioc.service;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import spring.framework.core.ioc.dao.AccountDao;
import spring.framework.core.ioc.dao.ItemDao;

/**
 * @author 曲元涛
 * @date 2020/4/12 16:26
 */
@Service
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
}

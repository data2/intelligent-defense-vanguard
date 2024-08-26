package com.data2.defense.core.run;

import com.data2.defense.core.config.IpConfiguration;
import com.data2.defense.core.dto.Pair;
import com.data2.defense.core.service.BaseService;
import com.data2.defense.core.service.ParentService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class TestService implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void test() {
        Collection<BaseService> values = applicationContext.getBeansOfType(BaseService.class).values();
        //调用每个子类的方法
        values.forEach(BaseService::exists);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

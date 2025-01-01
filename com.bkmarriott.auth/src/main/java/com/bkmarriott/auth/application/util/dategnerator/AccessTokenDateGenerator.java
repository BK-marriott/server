package com.bkmarriott.auth.application.util.dategnerator;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDateGenerator implements DateGenerator {

    @Override
    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    @Override
    public Date calcExpireDate(long exp) {
        long currentTime = System.currentTimeMillis();
        return new Date(currentTime + exp);
    }
}

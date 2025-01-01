package com.bkmarriott.gateway.util.dategnerator;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenDateGenerator implements DateGenerator {

    @Override
    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}

package com.bkmarriott.auth.application.util.dategnerator;

import java.util.Date;

public interface DateGenerator {

    Date getCurrentDate();

    Date calcExpireDate(long exp);
}

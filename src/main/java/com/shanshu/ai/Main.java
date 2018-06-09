/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.shanshu.ai;

import com.shanshu.ai.common.DBUtils;
import com.shanshu.ai.common.ShellAction;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Frank Huang (runping@shanshu.ai)
 * @date 2018/06/07
 */
public class Main {
    private static Logger logger = Logger.getLogger("my.log");
    public static void main(String[] args)
                    throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        String path = args[0];
        logger.info(ShellAction.runShell(path));

        DBUtils.initConn("deppon");
        System.out.println(DBUtils.sampling("express_pact_1803"));
    }
}

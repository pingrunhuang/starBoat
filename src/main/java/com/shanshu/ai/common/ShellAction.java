/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.shanshu.ai.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Liu Zuobin (zuobin@shanshu.ai)
 * @date 2018/01/24
 */
public class ShellAction {

    public static String runShell(String path, String... args) throws IOException, InterruptedException {
        StringBuilder shellScript = new StringBuilder(path);
        for (String arg: args){
            shellScript.append(" ").append(arg);
        }
        Process ps = Runtime.getRuntime().exec(shellScript.toString());
        ps.waitFor();

        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

}
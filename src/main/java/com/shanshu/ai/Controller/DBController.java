/*
 * Copyright (c) 2018, Cardinal Operations and/or its affiliates. All rights reserved.
 * CARDINAL OPERATIONS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.shanshu.ai.Controller;

import com.shanshu.ai.common.DBUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.SQLException;
import java.util.List;

/*
 * @author GuoPeng (guopeng@shanshu.ai)
 * @date 2018/06/06
 */

@Controller
public class DBController {

    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String showDB(Model theModel) throws SQLException{
        DBUtils.initConn();
        List theDbs = DBUtils.showDatabases();
        theModel.addAttribute("dbs",theDbs);
        return "index";
    }

    @PostMapping(value="/table")
    public String showTable(@ModelAttribute String db,Model theModel) throws SQLException {
        DBUtils.initConn(db);
        List theTables = DBUtils.showTables();
        theModel.addAttribute("tables",theTables);
        return "table";
    }




}


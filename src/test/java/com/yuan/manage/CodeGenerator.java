package com.yuan.manage;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

/**
 * MyBatis-plus 代码生成器
 * Created by wangfan on 2019-02-11 上午 9:50.
 */
public class CodeGenerator {
    // 数据库连接配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/easyweb-shiro";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123456";
    // 数据库表前缀
    private static final String[] TB_PREFIX = new String[]{"sys_", "tb_"};
    // 输出位置
    private static final String OUTPUT_DIR = "d:\\codeGen";
    private static final String PACKAGE_NAME = "com.yuan.manage.system";
    private static final String[] TABLE_NAMES = {"sys_user","sys_role"};


    public static void main(String[] args) {
        generateByTables(false, PACKAGE_NAME, TABLE_NAMES);
    }

    public static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        AutoGenerator mpg = new AutoGenerator();  // 代码生成器
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(OUTPUT_DIR);  // 输出位置
        gc.setFileOverride(true);  // 覆盖文件
        gc.setAuthor("wangfan");
        gc.setOpen(true);
        gc.setActiveRecord(false);  // ActiveRecord 模式
        gc.setEnableCache(false);  // 二级缓存
        if (!serviceNameStartWithI) {
            gc.setServiceName("%sService");  // service命名方式
        }
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(DB_URL);
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);
        pc.setMapper("dao");
        pc.setXml("xml");
        pc.setController("controller");
        pc.setEntity("model");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);  // 大写命名
        strategy.setEntityLombokModel(false); // lombok模型
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 表名映射策略
        strategy.setNaming(NamingStrategy.underline_to_camel);  // 字段映射策略
        strategy.setTablePrefix(TB_PREFIX);  // 表前缀
        strategy.setInclude(tableNames);
        mpg.setStrategy(strategy);

        // 模板引擎
        mpg.setTemplateEngine(new BeetlTemplateEngine());

        mpg.execute();
    }
}

package com.astar.sharetalent.generator;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xiaoleilu.hutool.setting.dialect.Props;
import com.xiaoleilu.hutool.util.StrUtil;

import java.io.File;
import java.util.*;

/**
 * MybatisPlus代码生成工具
 **/
public class CodeGeneratorUtil {

    //包文件路径前缀
    private static String packageName="com.astar.sharetalent";
    //项目磁盘路径
    private static String path = "D:\\Users\\IdeaProject\\sharetalent";
    //作者
    private static String authorName="zhangyu";
    //要生成代码的table名字，多个以逗号相隔
    private static String table="user_wechat";
    //private static String prefix="sc_";
    //table前缀
    //controller所在目录，因为本次小程序(controller在web)以及管理后台(controller在manage)没有拆分全部在sharetalent
    private static String controllerDir = "web";//manage  or web
    //cfg 注入配置 本次小程序用api 管理后台用manage
    private static String cfgControllerDir = "api";
    //数据库连接
    private static String driverClass = "com.mysql.cj.jdbc.Driver";
    private static String userName = "root";
    private static String passWord = "123456";
    private static String url = "jdbc:mysql://127.0.0.1:3306/sharetalent?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    public static void generate() {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
                // 全局配置
                new GlobalConfig()
                        .setOutputDir(path+"/src/main/java")//输出目录
                        .setFileOverride(true)// 是否覆盖文件
                        .setActiveRecord(false)// 开启 activeRecord 模式
                        .setEnableCache(false)// XML 二级缓存
                        .setBaseResultMap(false)// XML ResultMap
                        .setBaseColumnList(true)// XML columList
                        .setOpen(false)//生成后打开文件夹
                        .setAuthor(authorName)
                        // 自定义文件命名，注意 %s 会自动填充表实体属性！
                        .setMapperName("%sMapper")
                        .setXmlName("%sMapper")
                        .setServiceName("%sService")
                        .setServiceImplName("%sServiceImpl")
                        .setControllerName("%sController")
        ).setDataSource(
                // 数据源配置
                new DataSourceConfig()
                        .setDbType(DbType.MYSQL)// 数据库类型
                        .setTypeConvert(new MySqlTypeConvert() {
                            // 自定义数据库表字段类型转换【可选】
                            @Override
                            public DbColumnType processTypeConvert(String fieldType) {
                                System.out.println("转换类型：" + fieldType);
                                // if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
                                //    return DbColumnType.BOOLEAN;
                                // }
                                return super.processTypeConvert(fieldType);
                            }
                        })
                        .setDriverName(driverClass)
                        .setUsername(userName)
                        .setPassword(passWord)
                        .setUrl(url)
        ).setStrategy(
                // 策略配置
                new StrategyConfig()
                        // .setCapitalMode(true)// 全局大写命名
                        //.setDbColumnUnderline(true)//全局下划线命名
                        //.setTablePrefix(new String[]{prefix})// 此处可以修改为您的表前缀
                        .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                        .setInclude(new String[] { table }) // 需要生成的表
                        .setRestControllerStyle(true)
                        //.setExclude(new String[]{"test"}) // 排除生成的表
                        // 自定义实体父类
                         .setSuperEntityClass(packageName + ".common.base.BaseDo")
                        // 自定义实体，公共字段
                        .setSuperEntityColumns(new String[]{"id","time_stamp"})
                        .setTableFillList(tableFillList)
                        // 自定义 mapper 父类
                         .setSuperMapperClass("com.baomidou.mybatisplus.mapper.BaseMapper")
                        // 自定义 service 父类
                        // .setSuperServiceClass("com.baomidou.demo.TestService")
                        // 自定义 service 实现类父类
                        // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                        // 自定义 controller 父类
                        .setSuperControllerClass(packageName + "." + "common.web.BaseController")
                // 【实体】是否生成字段常量（默认 false）
                // public static final String ID = "test_id";
                // .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                // public User setName(String name) {this.name = name; return this;}
                // .setEntityBuilderModel(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                 .setEntityLombokModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                 .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
        ).setPackageInfo(
                // 包配置
                new PackageConfig()
                        //.setModuleName("User")
                        .setParent(packageName + "." + "web")// 自定义包路径
                        .setController("controller")// 这里是控制器包名，默认 web
                        .setEntity("entity")
                        .setMapper("dao")
                        .setService("service")
                        .setServiceImpl("service.impl")
                //.setXml("mapper")
        ).setCfg(
                // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
                new InjectionConfig() {
                    @Override
                    public void initMap() {
                        //可以在模板中使用的变量
                        Map<String, Object> map = new HashMap<>();
                        map.put("controllerDir",cfgControllerDir);
                        this.setMap(map);
                    }
                }.setFileOutConfigList(getFileOutConfigList())
        ).setTemplate(
                // 关闭默认 xml 生成，调整生成 至 根目录
                new TemplateConfig().setXml(null)
                // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                 .setController(null)
                 .setEntity(null)
                 .setMapper(null)
                 .setService(null)
                 .setServiceImpl(null)
        );
        // 执行生成
        mpg.execute();

    }
    private static List<FileOutConfig> getFileOutConfigList () {
        List<FileOutConfig> focList = new ArrayList<>();

        // 调整 controller 生成目录
        focList.add(new FileOutConfig("/templates/controller.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path+"/src/main/java/com/astar/sharetalent/" + controllerDir + "/controller/" + tableInfo.getEntityName() + "Controller.java";
            }
        });
        // 调整 vo 生成目录
        focList.add(new FileOutConfig("/templates/entityvo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path+"/src/main/java/com/astar/sharetalent/web" +"/vo/" + tableInfo.getEntityName() + "Vo.java";
            }
        });
        // 调整 do 生成目录
        focList.add(new FileOutConfig("/templates/entitydo.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path+"/src/main/java/com/astar/sharetalent/web" +"/entity/" + tableInfo.getEntityName() + "Do.java";
            }
        });
        // 调整 service 生成目录
        focList.add(new FileOutConfig("/templates/service.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path+"/src/main/java/com/astar/sharetalent/web" +"/service/" + tableInfo.getEntityName() + "Service.java";
            }
        });
        // 调整 serviceImpl 生成目录
        focList.add(new FileOutConfig("/templates/serviceImpl.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path+"/src/main/java/com/astar/sharetalent/web" +"/service/impl/" + tableInfo.getEntityName() + "ServiceImpl.java";
            }
        });
        // 调整 mapper 生成目录
        focList.add(new FileOutConfig("/templates/mapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path+"/src/main/java/com/astar/sharetalent/web" +"/dao/" + tableInfo.getEntityName() + "Mapper.java";
            }
        });
        // 调整 mapper xml 生成目录
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return  path+"/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        return focList;
    }
}

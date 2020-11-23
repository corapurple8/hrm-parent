package cn.itsource.basic.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CodeGenerator {


    public static void main(String[] args) {

        //默认读取resources目录下的文件，properties后缀省略
        ResourceBundle bundle = ResourceBundle.getBundle("generator-config-user");


        // 代码生成器 - 核心对象
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(bundle.getString("OutputDir") + "/src/main/java");
        gc.setAuthor(bundle.getString("Author"));
        gc.setOpen(true);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setSwagger2(true);
        //覆盖同名文件
        gc.setFileOverride(true);
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(bundle.getString("jdbc.url"));
        dsc.setDriverName(bundle.getString("jdbc.driver"));
        dsc.setUsername(bundle.getString("jdbc.username"));
        dsc.setPassword(bundle.getString("jdbc.password"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(bundle.getString("ParentPage"));
        //默认的domain的包名为entity
        pc.setEntity("domain");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        /**
         * mapper.xml默认生成在java目录下，但是我们应该重新通过自定义配置生成到resources目录下
         * mybatisplus不会自动生成query，需要手动通过自定义配置生成
         */
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        String templatePath = "/templates/mapper.xml.vm";
        // 自定义配置会被优先输出 优先级大于默认生成配置
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return bundle.getString("OutputDirXml") + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        //query的自定义生成
        templatePath = "/templates/query.java.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return bundle.getString("OutputDirQuery") + "/" + tableInfo.getEntityName() + "Query" + StringPool.DOT_JAVA;
            }
        });

        //domain的自定义生成
        templatePath = "/templates/entity.java.vm";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return bundle.getString("OutputDirDomain") + "/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);


        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setController("/templates/controller.java");
        //模板设置为null，则不会重复生成文件
        templateConfig.setXml(null);
        templateConfig.setEntity(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(bundle.getString("tables").replaceAll(" ","").split(","));
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
    }

}
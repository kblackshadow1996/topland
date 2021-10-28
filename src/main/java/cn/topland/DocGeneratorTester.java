package cn.topland;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * api doc生成
 */
public class DocGeneratorTester {

    public static void main(String[] args) {

        DocsConfig config = new DocsConfig();
        config.setProjectPath("/home/zhuliangbin/programs/topland"); // root project path
        config.setProjectName("图澜管理系统"); // project name
        config.setApiVersion("V1.0");       // api version
        config.setDocsPath("/home/zhuliangbin/programs/doc"); // api docs target path
        config.setAutoGenerate(Boolean.TRUE);  // auto generate
        Docs.buildHtmlDocs(config); // execute to generate
    }
}
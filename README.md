#### 工具类搜集

#### 自定义View搜集

#### 在本地创建自己的maven仓库，并引用库中的aar库，结合项目 TestMavnLibrary 一同使用

1、创建一个model，然后将此model打包成aar文件，然后上传到自己本地的maven仓库中

2、在项目所在的gradle.properties中添加全局的设置如下：（也可选择在model项目下的gradle.properties）

    # 包信息，根据model的包名来设置
    PROJ_GROUP=com.example.utillibrary
    # 版本号
    PROJ_VERSION=1.0

    # 固定参数，包含一些协议等信息
    # Licence信息
    PROJ_LICENCE_NAME=The Apache Software License, Version 2.0
    PROJ_LICENCE_URL=http://www.apache.org/licenses/LICENSE-2.0.txt
    PROJ_LICENCE_DEST=repo

    # 项目信息
    # Developer 信息
    DEVELOPER_ID=com.example.utillibrary
    DEVELOPER_NAME=utillibrary

    # 生成本地maven仓库的位置，文件夹所在的位置，
    # 表示项目所在根目录，并在此根目录下创建home/lv/.m2/repository/文件路径
    LOCAL_REPO_URL=file:///home/lv/.m2/repository/

3、在model所在的build.gradle中添加事件用于生成aar文件

    apply plugin: 'maven'
    uploadArchives {
        repositories.mavenDeployer {
            repository(url: LOCAL_REPO_URL)
            pom.groupId = PROJ_GROUP
            pom.artifactId = PROJ_ARTIFACTID
            pom.version = PROJ_VERSION
        }
    }

4、在项目所在路径下执行命令

    命令：gradlew -p 自己命名的仓库名 clean build uploadArchives --info
    # 两种方式，一种是直接在as的Terminal中运行，另一种是命令行进入项目文件路径运行

5、生成成功后会在相应的路径看到对应的文件

    文件包含有以下文件：

    localrepo
    │   │           ├── 1.0.0
    │   │           │   ├── localrepo-1.0.0.aar
    │   │           │   ├── localrepo-1.0.0.aar.md5
    │   │           │   ├── localrepo-1.0.0.aar.sha1
    │   │           │   ├── localrepo-1.0.0.pom
    │   │           │   ├── localrepo-1.0.0.pom.md5
    │   │           │   └── localrepo-1.0.0.pom.sha1
    │   │           ├── maven-metadata.xml
    │   │           ├── maven-metadata.xml.md5
    │   │           └── maven-metadata.xml.sha1

6、引用本地库（两种引用方式）

    # 第一种是直接将aar文件导入项目的lib文件夹下，在app下的build.gradle与android{}同等级下添加引用lib下的库

        repositories {
            flatDir {
                dirs 'libs'
            }
        }

     # 在dependencies中添加引用

        compile(name:'utillibrary-debug', ext:'aar')

    # =====================================

    # 第二种是通过引用本地maven库的形式进行引用

    # 在项目的build.gradle中添加本地maven路径，在buildscript下和allprojects下均添加下面的内容：

        repositories {
                maven { url 'E:/home/lv/.m2/repository/' }// 文件路径为上面生成的路径
                jcenter()
            }

     # 引用，在app的dependencies中添加以下依赖（根据生成的库自行修改）

        compile 'com.example.utillibrary:utillibrary:1.0'

7、须知：

    http://blog.csdn.net/fyfcauc/article/details/70174960

    http://blog.bugtags.com/2016/01/27/embrace-android-studio-maven-deploy/

    https://blog.csdn.net/jinyp/article/details/55095310

    路径问题：http://www.it1352.com/146505.html





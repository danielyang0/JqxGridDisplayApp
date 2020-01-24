## Spring Boot, MySQL, JPA, Hibernate Restful CRUD API Tutorial
https://www.callicoder.com/spring-boot-rest-api-tutorial-with-mysql-jpa-hibernate/

[Spring Boot教程(十三)：Spring Boot文件上传](https://blog.csdn.net/gnail_oug/article/details/80324120)

[通过jQuery Ajax使用FormData对象上传文件](https://www.jianshu.com/p/46e6e03a0d53)

## 打包
只有设成打war包成功了。
执行打包: $mvn clean install 在target里生成xxx.war
执行程序: $java -jar xxx.war
[SpringBoot--关于使用webapp目录](https://blog.csdn.net/cc907566076/article/details/80090556)

尝试打jar包:但打出来的包没有包含webapp资源文件。于是通过在pom里面加resource的方式。
src/main/resources和src/main/webapp里面的东西到了jar包里面的classes文件夹下面。
<resources>
            <resource>
                <directory>src/main/resources</directory>
            </resource>
            <resource>
                <directory>src/main/webapp</directory>
            </resource>
            <resource>
                <directory>src/main/webapp</directory>
                <targetPath>webapp</targetPath> <!-- 文件会被拷贝到jar包里classes/webapp里面 -->
            </resource>
        </resources>

## 一些没有解决打jar包问题的参考资料

https://stackoverflow.com/questions/29782915/spring-boot-jsp-404
https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-static-content
https://stackoverflow.com/questions/28725635/spring-boot-configure-it-to-find-the-webapp-folder

## log4j配置文件path问题
https://stackoverflow.com/questions/227486/find-where-java-class-is-loaded-from

## junit测试参考
https://blog.csdn.net/weixin_39800144/article/details/79241620
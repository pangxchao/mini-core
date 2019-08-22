# sn-mini

简单的mvc框架

使用时通过如下方式引入Jar包
---------------

    <!-- 私有仓库配置 --> 
    <repositories> 
        <repository> 
            <id>GitHub</id> 
            <name>GitHub Private Maven</name> 
            <url> https://raw.github.com/pangxchao/maven/master/ </url> 
        </repository> 
    </repositories>
    
    <!-- mini mvc 依赖库 -->
    <dependency>
        <groupId>com.mini</groupId>
        <artifactId>mvc</artifactId>
        <version>1.0</version>
    </dependency>
    
    <!-- mini 工具类依赖库 -->
    <dependency>
        <groupId>com.mini</groupId>
        <artifactId>core</artifactId>
        <version>1.0</version>
    </dependency>
    
    <!-- mini 源码生成依赖库 -->
    <dependency>
        <groupId>com.mini</groupId>
        <artifactId>code</artifactId>
        <version>1.0</version>
    </dependency>

源码库依赖了MVC库，MVC库依赖了工具类库

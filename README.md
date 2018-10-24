# <a id="a_top">Java 8</a>
[Java 发行版本说明](https://www.oracle.com/technetwork/java/javase/jdk-relnotes-index-2162236.html)  
[Java 8 下载地址](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)  
[Java 8 发行说明](https://www.oracle.com/technetwork/java/javase/8-relnotes-2226341.html)  
[Java 8 新功能](https://www.oracle.com/technetwork/java/javase/8-whats-new-2157071.html)  
[Java 8 API](https://docs.oracle.com/javase/8/docs/api/overview-summary.html)  

|作者|Mutistic|
|---|---|
|邮箱|mutistic@qq.com|

---
### <a id="a_catalogue">目录</a>：
1. <a href="#a_java8">Java 8概述</a>
98. <a href="#a_ea">ea</a>
99. <a href="#a_down">down</a>

---
### <a id="a_java8">一、Java 8概述：</a> <a href="#a_top">last</a> <a href="#a_info">next</a>
1、Java 8 是什么：
```
  Java 8，又称为 jdk 1.8 是Java语言开发的一个主要版本。
  Oracle公司于2014年3月18日发布Java 8，它支持函数式编程（Lambda 表达式），新的JavaScript引擎，新的日期API，新的StreamAPI等
```

2、Java 8 新功能介绍：  
2.1、Java编程语言：
```
  1、Lambda Expressions是一种新的语言功能，已在此版本中引入。它们使您能够将功能视为方法参数，或将代码视为数据。
Lambda表达式允许您更紧凑地表示单方法接口（称为功能接口）的实例。
  2、方法引用为已经具有名称的方法提供易于阅读的lambda表达式。
  3、默认方法允许将新功能添加到库的接口，并确保与为这些接口的旧版本编写的代码的二进制兼容性。
  4、重复注释提供了对同一声明或类型使用多次应用相同注释类型的功能。
  5、类型注释提供了在使用类型的任何地方应用注释的功能，而不仅仅是在声明上。与可插拔类型系统一起使用时，此功能可以改进代码的类型检查。
  6、改进的类型推断。
  7、方法参数反射。
```
2.2、集合：
```
  1、新java.util.stream包中的类提供Stream API以支持对元素流的功能样式操作。
Stream API集成在Collections API中，可以对集合进行批量操作，例如顺序或并行map-reduce转换。
  2、具有关键冲突的HashMaps的性能改进
```
2.3、[Compact Profiles](https://docs.oracle.com/javase/8/docs/technotes/guides/compactprofiles)包含Java SE平台的预定义子集，并支持不需要在小型设备上部署和运行整个Platform的应用程序。  
2.4、安全：
```
  1、默认情况下启用客户端TLS 1.2
  2、AccessController的新变体 doPrivileged，它允许代码断言其特权的子集，而不会阻止堆栈的完整遍历检查其他权限
  3、更强大的基于密码加密的算法
  4、JSSE服务器中的SSL / TLS服务器名称指示（SNI）扩展支持
  5、支持AEAD算法：SunJCE提供程序已得到增强，可支持AES / GCM / NoPadding密码实现以及GCM算法参数。SunJSSE提供商已得到增强，可支持基于AEAD模式的密码套件。请参阅Oracle Providers文档，JEP 115。
  6、KeyStore增强功能，包括新的Domain KeyStore类型java.security.DomainLoadStoreParameter，以及-importpasswordkeytool实用程序的新命令选项
  7、SHA-224消息摘要
  8、增强了对NSA Suite B密码学的支持
  9、更好地支持高熵随机数生成
  10、java.security.cert.PKIXRevocationChecker用于配置X.509证书的吊销检查的新类
  11、适用于Windows的64位PKCS11
  12、Kerberos中的新rcache类型5重播缓存
  13、支持Kerberos 5协议转换和约束委派
  14、默认情况下禁用Kerberos 5弱加密类型
  15、用于GSS-API / Kerberos 5机制的未绑定SASL
  16、多个主机名的SASL服务
  17、JNI在Mac OS X上桥接到本机JGSS
  18、在SunJSSE提供商中支持更强大的临时DH密钥
  19、支持JSSE中的服务器端密码套件首选项定制
```
2.4、日期时间包：
```
  一组新的包，提供全面的日期时间模型。
  JDK 8中引入的Date-Time API是一组用于模拟日期和时间最重要方面的包。java.time包中的核心类使用ISO-8601中定义的日历系统（基于公历系统）作为默认日历。
其他非ISO日历系统可以使用java.time.chrono包表示，并提供了几个预定义的年表，例如Hijrah和Thai Buddhist
```
2.5、IO和NIO：
```
  1、SelectorProvider基于Solaris事件端口机制的Solaris的新实现。要使用，
请在系统属性java.nio.channels.spi.Selector设置为值的情况下运行sun.nio.ch.EventPortSelectorProvider。
  2、减小<JDK_HOME>/jre/lib/charsets.jar文件的大小
  3、java.lang.String(byte[], *)构造函数和java.lang.String.getBytes()方法的性能改进。
```
2.6、java.lang和java.util包：
```
  1、并行阵列排序
  2、标准编码和解码Base64
  3、无符号算术支持
```
2.7、联网：
```
  1、添加新的类java.net.URLPermission。
  2、在类中java.net.HttpURLConnection，如果安装了安全管理器，则请求打开连接的调用需要权限。
```
2.8、并发：
```
  1、类和接口已添加到java.util.concurrent包中。
  2、方法已添加到java.util.concurrent.ConcurrentHashMap类中，以支持基于新添加的流工具和lambda表达式的聚合操作。
  3、已将类添加到java.util.concurrent.atomic包中以支持可伸缩的可更新变量。
  4、方法已添加到java.util.concurrent.ForkJoinPool类中以支持公共池。
  5、该java.util.concurrent.locks.StampedLock班已添加到提供基于能力的锁有三种模式控制读/写访问。
```

---
<a id="a_down"></a>  
<a href="#a_top">Top</a> 
<a href="#a_catalogue">Catalogue</a>
